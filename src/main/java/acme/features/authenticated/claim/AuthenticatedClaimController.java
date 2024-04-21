
package acme.features.authenticated.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.claims.Claim;

@Controller
public class AuthenticatedClaimController extends AbstractController<Authenticated, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedClaimShowService	managerClaimShowService;

	@Autowired
	private AuthenticatedClaimCreateService	managerClaimCreateService;

	@Autowired
	private AuthenticatedClaimListService	managerClaimListService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.managerClaimShowService);
		super.addBasicCommand("list", this.managerClaimListService);
		super.addCustomCommand("publish", "create", this.managerClaimCreateService);
	}

}
