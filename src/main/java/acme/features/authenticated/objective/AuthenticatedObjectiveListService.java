
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objectives.Objective;

@Service
public class AuthenticatedObjectiveListService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Objective> objects;

		objects = this.repository.findManyObjectives();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "priority", "isCritical", "instantiationMoment");

		super.getResponse().addData(dataset);
	}
}
