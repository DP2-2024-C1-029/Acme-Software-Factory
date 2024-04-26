
package acme.features.manager.projectUserStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int projectUserStoryId = super.getRequest().getData("id", int.class);
		Manager principal = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryById(projectUserStoryId);

		boolean status = false;
		if (projectUserStory == null && super.getRequest().hasData("uhId")) {
			int projectId = super.getRequest().getData("id", int.class);
			int userStoryId = super.getRequest().getData("uhId", int.class);
			Project project = this.repository.findProjectById(projectId);
			UserStory userStory = this.repository.findOneUserStoryById(userStoryId);
			status = project != null && userStory != null && principal.getId() == project.getManager().getId() && principal.getId() == userStory.getManager().getId();
		} else
			status = projectUserStory != null && projectUserStory.getProject().getManager().getId() == principal.getId() && projectUserStory.getUserStory().getManager().getId() == principal.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int projectUserStoryId = super.getRequest().getData("id", int.class);
		ProjectUserStory object = this.repository.findProjectUserStoryById(projectUserStoryId);
		if (object == null && super.getRequest().hasData("uhId")) {
			int projectId = super.getRequest().getData("id", int.class);
			int userStoryId = super.getRequest().getData("uhId", int.class);
			Project project = this.repository.findProjectById(projectId);
			UserStory userStory = this.repository.findOneUserStoryById(userStoryId);
			object = new ProjectUserStory();
			object.setProject(project);
			object.setUserStory(userStory);
		}

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "project.code", "userStory.title");
		dataset.put("published", !object.getUserStory().isDraftMode() && !object.getProject().isDraftMode());
		if (object.getId() == 0) {
			dataset.put("addUH", true);
			dataset.put("projectId", object.getProject().getId());
			dataset.put("userStoryId", object.getUserStory().getId());
		}
		super.getResponse().addData(dataset);
	}
}
