
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceDeleteService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Invoice sponsorship;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneInvoiceById(masterId);
		status = sponsorship != null && !sponsorship.isPublished() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsorship().getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int masterId;

		masterId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(masterId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "dueDate", "quantity", "tax", "link");
		dataset.put("totalAmount", object.totalAmount());
		dataset.put("masterId", object.getSponsorship().getId());
		dataset.put("isPublished", object.getSponsorship().isPublished());

		super.getResponse().addData(dataset);
	}
}
