
package acme.features.any.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.sponsorships.Sponsorship;

@Controller
public class AnySponsorshipController extends AbstractController<Any, Sponsorship> {

	@Autowired
	public AnySponsorshipListAllService	listAllService;

	@Autowired
	public AnySponsorshipShowService	showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}
}
