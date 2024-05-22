
package acme.features.developer.trainingmodule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleUpdateService extends AbstractService<Developer, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();

		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

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

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		Date moment = MomentHelper.getCurrentMoment();
		Date updateMoment = new Date(moment.getTime() - 60000); // Le restamos tiempo para asegurar que esta en el pasado

		super.bind(object, "details", "code", "difficultyLevel", "link", "estimatedTotalTime");
		object.setProject(project);
		object.setUpdateMoment(updateMoment);
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

		if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(object.getUpdateMoment().compareTo(object.getCreationMoment()) > 0, "updateMoment", "developer.trainingModule.form.error.update-moment-cant-be-past");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "developer.trainingModule.form.error.drafted-project");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choicesProject;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

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
