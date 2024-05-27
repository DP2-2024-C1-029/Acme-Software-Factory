
package acme.features.manager.projectUserStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.features.manager.project.ManagerProjectRepository;
import acme.features.manager.userStory.ManagerUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository	repository;

	@Autowired
	private ManagerUserStoryRepository			managerUserStoryRepository;

	@Autowired
	private ManagerProjectRepository			managerProjectRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status = false;
		int projectUserStoryIdOrProjectId = super.getRequest().getData("id", int.class);
		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		if (super.getRequest().hasData("uhId")) {
			int userStoryId = super.getRequest().getData("uhId", int.class);
			Project project = this.managerProjectRepository.findOneProjectByIdAndManagerId(projectUserStoryIdOrProjectId, managerId);
			UserStory userStory = this.managerUserStoryRepository.findOneUserStoryByIdAndManagerId(userStoryId, managerId);
			status = project != null && userStory != null;
		} else {
			ProjectUserStory projectUserStory = this.repository.findProjectUserStoryByIdAndManager(projectUserStoryIdOrProjectId, managerId);
			status = projectUserStory != null;
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int projectUserStoryId = super.getRequest().getData("id", int.class);
		ProjectUserStory object = this.repository.findProjectUserStoryById(projectUserStoryId);
		if (object == null && super.getRequest().hasData("uhId")) {
			int projectId = super.getRequest().getData("id", int.class);
			int userStoryId = super.getRequest().getData("uhId", int.class);
			Project project = this.managerProjectRepository.findOneProjectById(projectId);
			UserStory userStory = this.managerUserStoryRepository.findOneUserStoryById(userStoryId);
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
