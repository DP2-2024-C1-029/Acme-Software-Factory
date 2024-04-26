
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
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryById(projectUserStoryId);
		boolean status = projectUserStory != null && projectUserStory.getProject().getManager().getId() == principal.getId() && projectUserStory.getUserStory().getManager().getId() == principal.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory projectUserStory = this.repository.findProjectUserStoryById(id);
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

		Dataset dataset = super.unbind(object, "code", "title", "abstractText", "indication", "cost", "link");

		super.getResponse().addData(dataset);
	}
}
