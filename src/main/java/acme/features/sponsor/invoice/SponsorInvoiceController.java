
package acme.features.sponsor.invoice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Invoice;
import acme.roles.Sponsor;

@Controller
public class SponsorInvoiceController extends AbstractController<Sponsor, Invoice> {

	@Autowired
	public SponsorInvoiceListService	listService;

	@Autowired
	public SponsorInvoiceShowService	showService;

	@Autowired
	public SponsorInvoiceCreateService	createService;

	@Autowired
	public SponsorInvoiceUpdateService	updateService;

	@Autowired
	public SponsorInvoiceDeleteService	deleteService;

	@Autowired
	public SponsorInvoicePublishService	publishService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
