
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
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {
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
		int userStoryId = super.getRequest().getData("userStoryId", int.class);
		int projectId = super.getRequest().getData("projectId", int.class);
		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		UserStory userStory = this.managerUserStoryRepository.findOneUserStoryByIdAndManagerId(userStoryId, managerId);
		Project project = this.managerProjectRepository.findOneProjectByIdAndManagerId(projectId, managerId);

		boolean status = userStory != null && project != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object = new ProjectUserStory();

		int userStoryId = super.getRequest().getData("userStoryId", int.class);
		UserStory userStory = this.managerUserStoryRepository.findOneUserStoryById(userStoryId);

		int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.managerProjectRepository.findOneProjectById(projectId);
		object.setProject(project);
		object.setUserStory(userStory);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;

		ProjectUserStory projectUserStory = this.repository.findByProjectAndUserStory(super.getRequest().getPrincipal().getActiveRoleId(), object.getProject().getId(), object.getUserStory().getId());
		super.state(projectUserStory == null, "*", "manager.project-user-story.form.error.exist");

		if (!object.getUserStory().isDraftMode() && !object.getProject().isDraftMode())
			super.state(object.getUserStory().isDraftMode() && object.getProject().isDraftMode(), "*", "manager.project-user-story.form.error.detele.published");

	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		object.setId(0);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "project.code", "userStory.title", "project.id", "userStory.id");
		super.getResponse().addData(dataset);
	}
}
