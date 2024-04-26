
package acme.features.authenticated.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.projects.Project;

@Controller
public class AuthenticatedProjectController extends AbstractController<Authenticated, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuthenticatedProjectPublishService	authenticatedProjectPublishService;

	@Autowired
	private AuthenticatedProjectShowService		authenticatedProjectShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.authenticatedProjectShowService);
		super.addCustomCommand("list-published", "list", this.authenticatedProjectPublishService);
	}

}
