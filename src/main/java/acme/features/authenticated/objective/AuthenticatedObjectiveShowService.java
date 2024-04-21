
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.ObjectivePriority;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int objectiveId;
		Objective objective;

		objectiveId = super.getRequest().getData("id", int.class);
		objective = this.repository.findOneObjectiveById(objectiveId);
		status = objective != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Objective objects;
		int objectiveId;

		objectiveId = super.getRequest().getData("id", int.class);
		objects = this.repository.findOneObjectiveById(objectiveId);

		super.getBuffer().addData(objects);
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

		super.getResponse().addData(dataset);
	}
}
