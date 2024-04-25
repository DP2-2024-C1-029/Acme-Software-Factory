
package acme.features.administrator.objective;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.ObjectivePriority;

@Service
public class AdministratorObjectiveCreateService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Objective objects;

		objects = new Objective();
		objects.setInstantiationMoment(MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.MILLIS));

		super.getBuffer().addData(objects);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "title", "isCritical", "description", "initialExecutionPeriod", "endingExecutionPeriod", "link", "priority");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		// TODO - Preguntar si es una validación válida
		if (!super.getBuffer().getErrors().hasErrors("endingExecutionPeriod") && !super.getBuffer().getErrors().hasErrors("initialExecutionPeriod")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getInitialExecutionPeriod(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfter(object.getEndingExecutionPeriod(), minimumDeadline), "endingExecutionPeriod", "administrator.objective.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("initialExecutionPeriod"))
			super.state(MomentHelper.isAfter(object.getInitialExecutionPeriod(), object.getInstantiationMoment()), "initialExecutionPeriod", "administrator.objective.form.error.not-after");

		super.state(super.getRequest().getData("confirmation", boolean.class), "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(ObjectivePriority.class, object.getPriority());

		dataset = super.unbind(object, "title", "isCritical", "instantiationMoment", "description", "initialExecutionPeriod", "endingExecutionPeriod", "link");
		dataset.put("priority", object.getPriority());
		dataset.put("priorities", choices);
		super.getResponse().addGlobal("confirmar", false);

		super.getResponse().addData(dataset);
	}
}
