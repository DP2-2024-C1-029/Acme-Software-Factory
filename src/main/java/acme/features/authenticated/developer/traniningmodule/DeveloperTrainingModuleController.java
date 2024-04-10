
package acme.features.authenticated.developer.traniningmodule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, TrainingModule> {

	@Autowired
	public DeveloperTrainingModuleListService	listService;

	@Autowired
	public DeveloperTrainingModuleShowService	showService;

	//	@Autowired
	//	public DeveloperTrainingModuleShowService	showService;
	//
	//	@Autowired
	//	public DeveloperTrainingModuleCreateService	createService;
	//
	//	@Autowired
	//	public DeveloperTrainingModuleUpdateService	updateService;
	//
	//	@Autowired
	//	public DeveloperTrainingModuleDeleteService	deleteService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listService);
	}
}
