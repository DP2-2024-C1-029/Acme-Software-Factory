
package acme.features.authenticated.developer.trainingsession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		status = trainingModule != null && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		TrainingSession trainingSession;

		int masterId;
		TrainingModule trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);

		trainingSession = new TrainingSession();

		trainingSession.setTrainingModule(trainingModule);
		trainingSession.setDraftMode(true);

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

			super.state(!existingCode, "code", "developer.trainingsession.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startTime") && !super.getBuffer().getErrors().hasErrors("endTime")) {
			Date startTime = MomentHelper.deltaFromMoment(object.getStartTime(), 1, ChronoUnit.WEEKS);

			// Comprobamos que sea una semana
			super.state(MomentHelper.isAfter(object.getEndTime(), startTime), "endTime", "developer.trainingsession.form.error.end-date-less-than-week");
		}

		if (!super.getBuffer().getErrors().hasErrors("creationMoment") && !super.getBuffer().getErrors().hasErrors("startTime")) {
			Date startTime = MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 1, ChronoUnit.WEEKS);
			super.state(MomentHelper.isAfter(object.getStartTime(), startTime), "endTime", "developer.trainingsession.form.error.date-between-creation-startDate-must-be-one-week");

		}

		if (!super.getBuffer().getErrors().hasErrors("creationMoment") && !super.getBuffer().getErrors().hasErrors("endTime")) {
			boolean endBeforeCreation = MomentHelper.isAfter(object.getEndTime(), object.getTrainingModule().getCreationMoment());
			super.state(endBeforeCreation, "endTime", "developer.trainingSession.form.error.end-before-creation");
		}

		int masterId = super.getRequest().getData("masterId", int.class);
		TrainingModule trainingModule = this.repository.findOneTrainingModuleById(masterId);
		final boolean noDraftTrainingModule = trainingModule.isDraftMode();
		super.state(noDraftTrainingModule, "*", "developer.trainingSession.form.error.training-module-no-draft");

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startTime", "endTime", "location", "instructor", "contactEmail", "furtherInformationLink", "draftMode");
		dataset.put("masterId", object.getTrainingModule().getId());

		super.getResponse().addData(dataset);
	}
}
