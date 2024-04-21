
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectCreateService		managerProjectCreateService;

	@Autowired
	private ManagerProjectDeleteService		managerProjectDeleteService;

	@Autowired
	private ManagerProjectListMineService	managerProjectListMineService;

	@Autowired
	private ManagerProjectPublishService	managerProjectPublishService;

	@Autowired
	private ManagerProjectShowService		managerProjectShowService;

	@Autowired
	private ManagerProjectUpdateService		managerProjectUpdateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.managerProjectShowService);
		super.addBasicCommand("create", this.managerProjectCreateService);
		super.addBasicCommand("update", this.managerProjectUpdateService);
		super.addBasicCommand("delete", this.managerProjectDeleteService);

		super.addCustomCommand("list-mine", "list", this.managerProjectListMineService);
		super.addCustomCommand("publish", "update", this.managerProjectPublishService);
	}

}
