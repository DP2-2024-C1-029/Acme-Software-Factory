
package acme.features.authenticated.developer.trainingsession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	public DeveloperTrainingSessionListService		listService;

	@Autowired
	public DeveloperTrainingSessionShowService		showService;

	//	@Autowired
	//	public DeveloperTrainingSessionShowService	showService;
	//
	@Autowired
	public DeveloperTrainingSessionCreateService	createService;
	//
	@Autowired
	public DeveloperTrainingSessionUpdateService	updateService;
	//
	@Autowired
	public DeveloperTrainingSessionDeleteService	deleteService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

	}

}
