
package acme.features.developer.trainingsession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionShowService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingSessionId;
		TrainingSession trainingSession;

		trainingSessionId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(trainingSessionId);
		status = trainingSession != null && super.getRequest().getPrincipal().hasRole(trainingSession.getTrainingModule().getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int developerId;

		developerId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(developerId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		int developerId;
		SelectChoices choices;
		Dataset dataset;

		Collection<TrainingModule> trainingModules;
		SelectChoices choicesTrainingModules;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		trainingModules = this.repository.findManyTrainingModuleByDeveloperId(developerId);
		choicesTrainingModules = SelectChoices.from(trainingModules, "code", object.getTrainingModule());

		dataset = super.unbind(object, "code", "startTime", "endTime", "location", "instructor", "contactEmail", "furtherInformationLink", "draftMode");
		dataset.put("trainingModule", choicesTrainingModules.getSelected().getKey());
		dataset.put("trainingModules", choicesTrainingModules);
		dataset.put("masterId", object.getTrainingModule().getId());

		super.getResponse().addData(dataset);
	}

}
