
package acme.features.authenticated.developer.trainingsession;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
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

		/*if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "reference", "developer.trainingSession.form.error.duplicated");
		}

		Date startTime = object.getStartTime();
		Date endTime = object.getEndTime();

		// Calcular la diferencia en milisegundos
		long diffInMilliseconds = endTime.getTime() - startTime.getTime();

		// Convertir de milisegundos a días
		long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);

		// Comprobamos que sea una semana
		if (diffInDays < 7)
			super.state(true, "updateMoment", "developer.trainingSession.form.error.update-moment-less-than-week");

		if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(object.getStartTime().compareTo(object.getEndTime()) > 0, "updateMoment", "developer.trainingSession.form.error.update-moment-cant-be-past");
	}*/

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startTime", "endTime", "location", "instructor", "contactEmail", "furtherInformationLink");
		dataset.put("masterId", object.getTrainingModule().getId());

		super.getResponse().addData(dataset);
	}
}
