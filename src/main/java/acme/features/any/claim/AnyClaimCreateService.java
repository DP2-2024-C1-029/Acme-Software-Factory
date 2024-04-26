
package acme.features.any.claim;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;

@Service
public class AnyClaimCreateService extends AbstractService<Any, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyClaimRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object = new Claim();
		object.setInstantiationMoment(MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.MILLIS));
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "heading", "description", "department", "emailAddress", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Claim existing = this.repository.findOneClaimByCode(object.getCode());
			super.state(existing == null, "code", "authenticated.claim.form.error.duplicated");
		}

		boolean confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "authenticated.claim.form.error.confirmation");

	}

	@Override
	public void perform(final Claim object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "heading", "description", "department", "emailAddress", "link");
		super.getResponse().addData(dataset);
	}
}
