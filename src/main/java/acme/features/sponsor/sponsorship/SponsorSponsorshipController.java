
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	public SponsorSponsorshipListMineService	listMineService;

	@Autowired
	public SponsorSponsorshipListAllService	listAllService;

	@Autowired
	public SponsorSponsorshipShowService	showService;

	@Autowired
	public SponsorSponsorshipCreateService	createService;

	@Autowired
	public SponsorSponsorshipUpdateService	updateService;

	@Autowired
	public SponsorSponsorshipDeleteService	deleteService;

	@Autowired
	public SponsorSponsorshipPublishService	publishService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}
}
