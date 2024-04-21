
package acme.features.manager.userStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryCreateService	managerUserStoryCreateService;

	@Autowired
	private ManagerUserStoryDeleteService	managerUserStoryDeleteService;

	@Autowired
	private ManagerUserStoryListMineService	managerUserStoryListMineService;

	@Autowired
	private ManagerUserStoryPublishService	managerUserStoryPublishService;

	@Autowired
	private ManagerUserStoryShowService		managerUserStoryShowService;

	@Autowired
	private ManagerUserStoryUpdateService	managerUserStoryUpdateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.managerUserStoryShowService);
		super.addBasicCommand("create", this.managerUserStoryCreateService);
		super.addBasicCommand("update", this.managerUserStoryUpdateService);
		super.addBasicCommand("delete", this.managerUserStoryDeleteService);

		super.addCustomCommand("list-mine", "list", this.managerUserStoryListMineService);
		super.addCustomCommand("publish", "update", this.managerUserStoryPublishService);
	}
}
