
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
	public SponsorSponsorshipListService	listService;

	@Autowired
	public SponsorSponsorshipShowService	showService;

	@Autowired
	public SponsorSponsorshipCreateService	createService;

	//	@Autowired
	//	public SponsorSponsorshipUpdateService	updateService;

	@Autowired
	public SponsorSponsorshipDeleteService	deleteService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
