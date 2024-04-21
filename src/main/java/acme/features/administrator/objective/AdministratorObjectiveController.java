
package acme.features.administrator.objective;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.objectives.Objective;

@Controller
public class AdministratorObjectiveController extends AbstractController<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorObjectiveCreateService createService;

	// AbstractController interface ----------------------------------------------


	@PostConstruct
	public void initialize() {
		this.addBasicCommand("create", this.createService);
	}
}
