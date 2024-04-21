
package acme.features.authenticated.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;

@Service
public class AuthenticatedClaimCreateService extends AbstractService<Authenticated, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedClaimRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
	}

	@Override
	public void load() {
		Claim object = new Claim();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		//		if (!super.getBuffer().getErrors().hasErrors("code")) {
		//			Claim existing = this.repository.findOneClaimByCode(object.getCode());
		//			super.state(existing == null, "code", "manager.claim.form.error.duplicated");
		//		}

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

		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "emailAddress", "link");
		super.getResponse().addData(dataset);
	}
}
