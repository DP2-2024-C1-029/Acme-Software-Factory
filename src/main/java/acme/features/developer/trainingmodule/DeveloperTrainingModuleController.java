
package acme.features.developer.trainingmodule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, TrainingModule> {

	@Autowired
	public DeveloperTrainingModuleListService		listService;

	@Autowired
	public DeveloperTrainingModuleShowService		showService;

	@Autowired
	public DeveloperTrainingModulePublishService	publishService;

	@Autowired
	public DeveloperTrainingModuleCreateService		createService;

	@Autowired
	public DeveloperTrainingModuleUpdateService		updateService;

	@Autowired
	public DeveloperTrainingModuleDeleteService		deleteService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
