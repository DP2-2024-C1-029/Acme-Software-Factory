
package acme.features.any.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingModuleId;
		TrainingModule trainingModule;

		trainingModuleId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(trainingModuleId);
		status = trainingModule != null && trainingModule.isDraftMode() == false;

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
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		Collection<Project> projects;
		SelectChoices choicesProject;

		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		projects = this.repository.findManyProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "creationMoment", "details", "code", "updateMoment", "estimatedTotalTime", "link", "draftMode");
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficultyLevels", choices);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);

	}
}
