
package acme.features.authenticated.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AuthenticatedRiskShowService extends AbstractService<Authenticated, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneRiskById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "link");
		dataset.put("value", object.value());

		super.getResponse().addData(dataset);
	}

}
