
package acme.features.sponsor.sponsordashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.Sponsordashboard;
import acme.roles.Sponsor;

@Controller
public class SponsorDashboardController extends AbstractController<Sponsor, Sponsordashboard> {

	@Autowired
	public SponsorDashboardShowService showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("show", this.showService);
	}
}
