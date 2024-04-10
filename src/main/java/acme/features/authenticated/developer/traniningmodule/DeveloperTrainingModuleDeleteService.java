
package acme.features.authenticated.developer.traniningmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.DifficultyLevel;
import acme.entities.trainingmodules.TrainingModule;
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
		status = trainingModule != null && super.getRequest().getPrincipal().hasRole(developer);

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

		int developerId;
		Developer developer;

		developerId = super.getRequest().getData("developer", int.class);
		developer = this.repository.findOneDeveloperById(developerId);

		super.bind(object, "creationMoment", "details", "code", "diffitultyLevel", "updateMoment", "link", "estimatedTotalTime");
		object.setDeveloper(developer);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;

		projects = this.repository.findManyProjectsByTrainingModuleId(object.getId());
		this.repository.deleteAll(projects);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		int developerId;
		Collection<TrainingModule> trainingModules;
		SelectChoices choices;
		Dataset dataset;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		trainingModules = this.repository.findManyTrainingModuleByDeveloperId(developerId);
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "creationMoment", "details", "code", "updateMoment", "link", "estimatedTotalTime");
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}
}
