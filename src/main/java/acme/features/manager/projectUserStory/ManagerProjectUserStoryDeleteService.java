
package acme.features.manager.projectUserStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int projectUserStoryId = super.getRequest().getData("id", int.class);
		Manager principal = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryByIdAndNotPublished(projectUserStoryId);
		boolean status = projectUserStory != null && projectUserStory.getProject().getManager().getId() == principal.getId() && projectUserStory.getUserStory().getManager().getId() == principal.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryByIdAndNotPublished(id);
		super.getBuffer().addData(projectUserStory);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractText", "indication", "cost", "link");
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;

		if (!object.getUserStory().isDraftMode() && !object.getProject().isDraftMode())
			super.state(object.getUserStory().isDraftMode() && object.getProject().isDraftMode(), "*", "manager.project-user-story.form.error.detele.published");
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.delete(object);
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
