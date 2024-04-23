
package acme.features.authenticated.developer.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.developer.Dashboard;
import acme.roles.Developer;

@Controller
public class DeveloperDashboardController extends AbstractController<Developer, Dashboard> {

	@Autowired
	public DeveloperDashboardShowService showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("show", this.showService);
	}

}
