
package acme.features.administrator.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.forms.AdministratorDashboard;

@Controller
public class AdministratorDashboardController extends AbstractController<Administrator, AdministratorDashboard> {

	@Autowired
	public AdministratorDashboardShowService showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("show", this.showService);
	}

}
