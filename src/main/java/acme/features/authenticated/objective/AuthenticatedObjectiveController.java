
package acme.features.authenticated.objective;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.objectives.Objective;

@Controller
public class AuthenticatedObjectiveController extends AbstractController<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AuthenticatedObjectiveListService	listService;

	@Autowired
	public AuthenticatedObjectiveShowService	showService;

	// AbstractController interface ----------------------------------------------


	@PostConstruct
	public void initialize() {
		this.addBasicCommand("show", this.showService);
		this.addBasicCommand("list", this.listService);
	}
}
