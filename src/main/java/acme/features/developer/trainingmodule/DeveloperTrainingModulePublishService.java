
package acme.features.developer.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
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
			TrainingModule existingCode;
			int objectId = object.getId();

			existingCode = this.repository.findTrainingModuleByCode(object.getCode());

			boolean isDuplicatedCode = existingCode != null && existingCode.getId() != objectId;
			super.state(!isDuplicatedCode, "code", "developer.trainingModule.form.error.duplicated-code");
		}

		int masterId = super.getRequest().getData("id", int.class);
		boolean someDraftTrainingSession = this.repository.findManyTrainingSessionsByTrainingModuleIdAndDraftMode(masterId).isEmpty();
		super.state(someDraftTrainingSession, "*", "developer.trainingModule.form.error.trainingSession-draft");

		int id = object.getId();
		boolean hasPublishedTrainingSession = !this.repository.findTrainingSessionsPublishedByTrainingModuleId(id).isEmpty();
		super.state(hasPublishedTrainingSession, "*", "developer.trainingModule.form.error.no-published-training-session");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "developer.trainingModule.form.error.drafted-project");

		int trainingModuleId = super.getRequest().getData("id", int.class);
		TrainingModule someTrainingModule = this.repository.findOneTrainingModuleById(trainingModuleId);
		super.state(someTrainingModule != null, "*", "developer.trainingModule.form.error.trainingModule-empty");
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
