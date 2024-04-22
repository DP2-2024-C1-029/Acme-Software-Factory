
package acme.features.sponsor.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.sponsor.Dashboard;
import acme.roles.Sponsor;

@Controller
public class SponsorDashboardController extends AbstractController<Sponsor, Dashboard> {

	@Autowired
	public SponsorDashboardShowService showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("show", this.showService);
	}
}
