
package acme.features.authenticated.developer.trainingsession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingSession trainingSession;

		masterId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(masterId);
		status = trainingSession != null && trainingSession.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingSession.getTrainingModule().getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		TrainingSession trainingSession = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(trainingSession);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startTime", "endTime", "location", "instructor", "contactEmail", "furtherInformationLink");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			int id;
			boolean existingCode;

			id = super.getRequest().getData("id", int.class);
			existingCode = this.repository.findAllTrainingSessions().stream().filter(e -> e.getId() != id).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!existingCode, "code", "developer.trainingModule.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime")) {
			Date startTime = MomentHelper.deltaFromMoment(object.getStartTime(), 1, ChronoUnit.WEEKS);

			// Comprobamos que sea una semana
			super.state(MomentHelper.isAfter(object.getEndTime(), startTime), "endTime", "developer.trainingSession.form.error.update-moment-less-than-week");
		}

		/*
		 * int masterId = super.getRequest().getData("masterId", int.class);
		 * TrainingModule trainingModule = this.repository.findOneTrainingModuleByTrainingSessionId(masterId);
		 * final boolean noDraftTrainingModule = trainingModule.isDraftMode();
		 * super.state(noDraftTrainingModule, "*", "developer.trainingSession.form.error.trainingModule-noDraft");
		 */

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "startTime", "endTime", "location", "instructor", "contactEmail", "furtherInformationLink", "draftMode");

		dataset.put("masterId", object.getTrainingModule().getId());
		dataset.put("isPublished", object.getTrainingModule().isDraftMode());
		super.getResponse().addData(dataset);
	}

}
