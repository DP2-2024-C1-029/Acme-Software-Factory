
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notices.Notice;

@Service
public class AuthenticatedNoticeCreateService extends AbstractService<Authenticated, Notice> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedNoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
	}

	@Override
	public void load() {
		Notice object = new Notice();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice object) {
		assert object != null;

		Date moment = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(moment.getTime() - 600000); // Le restamos 9 min para asegurar que esta en el pasado

		super.bind(object, "title", "instantiationMoment", "author", "message", "email", "link");

		object.setInstantiationMoment(creationMoment);
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment")) {
			Date now = MomentHelper.getCurrentMoment();
			super.state(MomentHelper.isBefore(object.getInstantiationMoment(), now), "instantiationMoment", "authenticated.claim.form.error.future");
		}

		boolean confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "authenticated.claim.form.error.confirmation");

	}

	@Override
	public void perform(final Notice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "instantiationMoment", "author", "message", "email", "link");
		super.getResponse().addData(dataset);
	}
}
