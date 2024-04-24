
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
	private ManagerProjectUserStoryListService managerProjectUserStoryListUserStoryService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-by-project", "list", this.managerProjectUserStoryListUserStoryService);
	}
}
