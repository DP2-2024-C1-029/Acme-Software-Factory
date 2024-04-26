
package acme.features.manager.projectUserStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoryController extends AbstractController<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryShowService		managerProjectUserStoryShowService;

	@Autowired
	private ManagerProjectUserStoryListService		managerProjectUserStoryListService;

	@Autowired
	private ManagerProjectUserStoryListAddService	managerProjectUserStoryListAddService;

	@Autowired
	private ManagerProjectUserStoryCreateService	managerProjectUserStoryCreateService;

	@Autowired
	private ManagerProjectUserStoryDeleteService	managerProjectUserStoryDeleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("delete", this.managerProjectUserStoryDeleteService);
		super.addBasicCommand("show", this.managerProjectUserStoryShowService);
		super.addCustomCommand("list-by-project", "list", this.managerProjectUserStoryListService);
		super.addCustomCommand("list-to-add", "list", this.managerProjectUserStoryListAddService);
		super.addCustomCommand("add", "create", this.managerProjectUserStoryCreateService);
	}
}
