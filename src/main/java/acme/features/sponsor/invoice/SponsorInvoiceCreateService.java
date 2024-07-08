
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean hasIdInvoice;
		boolean status;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor()) && !sponsorship.isPublished();

		// Prevenir errores de hacking por id
		hasIdInvoice = super.getRequest().hasData("id", Integer.class);
		status = status && (!hasIdInvoice || super.getRequest().getData("id", int.class).equals(0));

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Invoice object;
		int masterId;
		Sponsorship invoice;

		masterId = super.getRequest().getData("masterId", int.class);
		invoice = this.repository.findOneSponsorshipById(masterId);

		object = new Invoice();
		object.setSponsorship(invoice);
		object.setPublished(false);
		object.setRegistrationTime(MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.MILLIS));

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

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.notExistsDuplicatedCodeLike(object.getCode()), "code", "sponsor.invoice.form.error.duplicated");

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), minimumDeadline), "dueDate", "sponsor.invoice.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(this.repository.isAmongAcceptedCurrencies(object.getQuantity().getCurrency()), "quantity", "sponsor.invoice.form.error.quantity-not-valid");

			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.negative-amount");
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished");
		dataset.put("totalAmount", object.totalAmount());
		dataset.put("masterId", object.getSponsorship().getId());

		super.getResponse().addData(dataset);
	}
}
