
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		status = sponsorship != null && (sponsorship.isPublished() || super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int sponsorId;

		sponsorId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyInvoicesByMasterId(sponsorId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		String trueValue;

		trueValue = super.getRequest().getLocale().toString().equals("es") ? "Sí" : "Yes";

		dataset = super.unbind(object, "code", "dueDate");
		dataset.put("isPublished", object.isPublished() ? trueValue : "No");
		dataset.put("totalAmount", object.totalAmount());

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> object) {
		assert object != null;

		int masterId;
		Sponsorship sponsorship;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		showCreate = !sponsorship.isPublished() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
