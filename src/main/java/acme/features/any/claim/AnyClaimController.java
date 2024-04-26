
package acme.features.any.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.claims.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyClaimShowService		managerClaimShowService;

	@Autowired
	private AnyClaimCreateService	managerClaimCreateService;

	@Autowired
	private AnyClaimListService		managerClaimListService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.managerClaimShowService);
		super.addBasicCommand("list", this.managerClaimListService);
		super.addCustomCommand("publish", "create", this.managerClaimCreateService);
	}

}
