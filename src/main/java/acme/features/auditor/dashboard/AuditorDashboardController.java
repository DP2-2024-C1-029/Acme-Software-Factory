
package acme.features.auditor.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.auditor.Dashboard;
import acme.roles.Auditor;

@Controller
public class AuditorDashboardController extends AbstractController<Auditor, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
