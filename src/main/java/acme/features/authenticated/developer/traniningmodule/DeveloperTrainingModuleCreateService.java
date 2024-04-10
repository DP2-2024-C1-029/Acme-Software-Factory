
package acme.features.authenticated.developer.traniningmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneJobByCode(object.getCode());
			super.state(existing == null, "reference", "developer.trainingModule.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("estimatedTotalTime"))
			super.state(object.getEstimatedTotalTime() > 0, "estimatedTotalTime", "developer.trainingModule.form.error.negative-estimated-total-time");

		if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(object.getUpdateMoment().compareTo(object.getCreationMoment()) > 0, "salary", "developer.trainingModule.form.error.update-moment-cant-be-past");
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
