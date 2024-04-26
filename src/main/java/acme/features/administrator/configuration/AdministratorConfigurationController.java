
package acme.features.administrator.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.configuration.Configuration;

@Controller
public class AdministratorConfigurationController extends AbstractController<Administrator, Configuration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorConfigurationShowService	showService;

	@Autowired
	public AdministratorConfigurationUpdateService	updateService;

	// AbstractController interface ----------------------------------------------


	@PostConstruct
	public void initialize() {
		this.addBasicCommand("show", this.showService);
		this.addBasicCommand("update", this.updateService);
	}
}
