
package acme.features.any.trainingmodule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.trainingmodules.TrainingModule;

@Controller
public class AnyTrainingModuleController extends AbstractController<Any, TrainingModule> {

	@Autowired
	public AnyTrainingModuleListAllService	listAllService;

	@Autowired
	public AnyTrainingModuleShowService		showService;


	@PostConstruct
	protected void initialize() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}
}
