
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.Priority;
import acme.entities.userstories.UserStory;
import acme.features.manager.projectUserStory.ManagerProjectUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository			repository;

	@Autowired
	private ManagerProjectUserStoryRepository	managerProjectUserStoryRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int userStoryId = super.getRequest().getData("id", int.class);
		UserStory userStory = this.repository.findOneUserStoryByIdAndNotPublished(userStoryId);
		Manager manager = userStory == null ? null : userStory.getManager();
		boolean status = manager != null && super.getRequest().getPrincipal().hasRole(manager) && userStory != null && userStory.getManager().getId() == manager.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		UserStory object = this.repository.findOneUserStoryByIdAndNotPublished(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(object.isDraftMode(), "published", "manager.userstory.form.error.detele.published");

		Collection<ProjectUserStory> projectUserStory = this.managerProjectUserStoryRepository.findProjectUserStoryByUserStoryId(object.getId());
		super.state(projectUserStory == null || projectUserStory.isEmpty(), "*", "manager.userstory.form.error.detele.project");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put("published", !object.isDraftMode());
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}
}
