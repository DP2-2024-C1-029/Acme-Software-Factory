
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Sponsor;

@Service
public class AuthenticatedSponsorUpdateService extends AbstractService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = this.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsor object;
		int userAccountId;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		object = this.repository.findOneSponsorByUserAccountId(userAccountId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsor object) {
		assert object != null;

		super.bind(object, "name", "benefits", "link", "email");
	}

	@Override
	public void validate(final Sponsor object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsor object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsor object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "name", "benefits", "link", "email");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
