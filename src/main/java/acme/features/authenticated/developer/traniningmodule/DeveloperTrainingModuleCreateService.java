
package acme.features.authenticated.developer.traniningmodule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;

		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new TrainingModule();
		object.setDeveloper(developer);
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		Principal principal;
		Developer developer;

		int projectId;
		Project project;

		principal = super.getRequest().getPrincipal();
		developer = this.repository.findOneDeveloperById(principal.getActiveRoleId());

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		Date moment = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(moment.getTime() - 600000); // Le restamos 9 min para asegurar que esta en el pasado

		super.bind(object, "creationMoment", "details", "code", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime");
		object.setDeveloper(developer);
		object.setProject(project);
		object.setCreationMoment(creationMoment);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			int id;
			boolean existingCode;

			id = super.getRequest().getData("id", int.class);
			existingCode = this.repository.findAllTrainingModules().stream().filter(e -> e.getId() != id).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!existingCode, "code", "developer.trainingmodule.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
			super.state(object.getEstimatedTotalTime() > 0, "estimatedTotalTime", "developer.trainingModule.form.error.negative-estimated-total-time");

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		int developerId;
		SelectChoices choicesProject;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		projects = this.repository.findManyProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "creationMoment", "details", "code", "updateMoment", "link", "estimatedTotalTime", "draftMode");
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficultyLevels", choices);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}
}
