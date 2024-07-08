
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Invoice invoice;

		masterId = super.getRequest().getData("id", int.class);
		invoice = this.repository.findOneInvoiceById(masterId);
		status = invoice != null && super.getRequest().getPrincipal().hasRole(invoice.getSponsorship().getSponsor()) && !invoice.isPublished();

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

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.notExistsDuplicatedCodeExceptThisIdLike(object.getCode(), object.getId()), "code", "sponsor.invoice.form.error.duplicated");

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isAfter(object.getDueDate(), minimumDeadline), "dueDate", "sponsor.invoice.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(this.repository.isAmongAcceptedCurrencies(object.getQuantity().getCurrency()), "quantity", "sponsor.invoice.form.error.quantity-not-valid");

			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.negative-amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			// Para comprobar que tiene la currency del resto de invoices publicadas, y si no de la del sponsorship
			super.state(object.getSponsorship().getAmount().getCurrency().equals(object.getQuantity().getCurrency()), "quantity", "sponsor.invoice.form.error.currency-diferent-from-sponsorship");

			// Para comprobar que tiene la suma de quantity menor o igual que la amount del sponsorship
			super.state(this.calculateIsLowerOrEqualCuantityToSponsorship(object), "quantity", "sponsor.invoice.form.error.more-than-sponsorship");
		}
	}

	private boolean calculateIsLowerOrEqualCuantityToSponsorship(final Invoice object) {
		double cuantityOfNewInvoice;
		Double sumOfCuantitiesPublished;

		cuantityOfNewInvoice = object.getQuantity().getAmount() * (100.0 + object.getTax()) / 100;
		sumOfCuantitiesPublished = this.repository.sumOfQuantitiesOfInvoicesOfSponsorship(object.getSponsorship().getId());

		if (sumOfCuantitiesPublished == null)
			sumOfCuantitiesPublished = 0.0;

		return sumOfCuantitiesPublished + cuantityOfNewInvoice <= object.getSponsorship().getAmount().getAmount();
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		object.setPublished(true);
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
