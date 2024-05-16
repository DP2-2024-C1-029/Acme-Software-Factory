
package acme.features.developer.trainingmodule;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		int masterId = super.getRequest().getData("id", int.class);
		TrainingModule trainingModule = this.repository.findOneTrainingModuleById(masterId);

		boolean status = trainingModule != null && trainingModule.isDraftMode() && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule trainingModule;
		int id;

		id = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(trainingModule);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findOneProjectById(projectId);

		super.bind(object, "details", "code", "difficultyLevel", "link", "estimatedTotalTime", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			int id;
			boolean existingCode;

			id = super.getRequest().getData("id", int.class);
			existingCode = this.repository.findAllTrainingModules().stream().filter(e -> e.getId() != id).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!existingCode, "code", "developer.trainingModule.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
			super.state(object.getEstimatedTotalTime() > 0, "estimatedTotalTime", "developer.trainingModule.form.error.negative-estimated-total-time");

		int masterId = super.getRequest().getData("id", int.class);
		List<TrainingSession> ls = this.repository.findManyTrainingSessionsByTrainingModuleId(masterId).stream().toList();
		boolean someDraftTrainingSession = ls.stream().anyMatch(Session -> Session.isDraftMode());
		boolean noSession = ls.isEmpty();
		super.state(!noSession, "*", "developer.trainingModule.form.error.trainingSession-empty");
		super.state(!someDraftTrainingSession, "*", "developer.trainingModule.form.error.trainingSession-draft");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "developer.trainingModule.form.error.drafted-project");

		int trainingModuleId = super.getRequest().getData("id", int.class);
		Collection<TrainingModule> trainingModules = this.repository.findAllTrainingModules();
		boolean someTrainingModule = trainingModules.stream().anyMatch(Module -> Module.getId() == trainingModuleId);
		super.state(someTrainingModule, "*", "developer.trainingModule.form.error.trainingModule-empty");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		SelectChoices choicesProject;
		Collection<Project> projects;

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		projects = this.repository.findManyProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		Dataset dataset = super.unbind(object, "creationMoment", "details", "code", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "project", "draftMode");

		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficultyLevels", choices);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}

}
