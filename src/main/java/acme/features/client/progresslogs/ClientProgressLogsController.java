
package acme.features.client.progresslogs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Controller
public class ClientProgressLogsController extends AbstractController<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ClientProgressLogsListService		listService;

	@Autowired
	private ClientProgressLogsShowService		showService;

	@Autowired
	private ClientProgressLogsCreateService		createService;

	@Autowired
	private ClientProgressLogsDeleteService		deleteService;

	@Autowired
	private ClientProgressLogsUpdateService		updateService;

	@Autowired
	private ClientProgressLogsPublishService	publishService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
