
package acme.features.developer.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, TrainingModule> {

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
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "creationMoment", "details", "code", "diffitultyLevel", "updateMoment", "link", "estimatedTotalTime");
		object.setProject(project);

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		int masterId = object.getId();
		// Obtener las sesiones de entrenamiento en modo borrador asociadas al módulo de entrenamiento
		boolean hasDraftTrainingSessions = !this.repository.findManyTrainingSessionsByTrainingModuleIdAndDraftMode(masterId).isEmpty();

		// Si no hay sesiones en modo borrador, es seguro eliminar el módulo de entrenamiento
		super.state(!hasDraftTrainingSessions, "*", "developer.trainingModule.form.error.trainingSession-published");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> trainingSessions;

		trainingSessions = this.repository.findManyTrainingSessionsByTrainingModuleId(object.getId());
		this.repository.deleteAll(trainingSessions);
		this.repository.delete(object);
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
