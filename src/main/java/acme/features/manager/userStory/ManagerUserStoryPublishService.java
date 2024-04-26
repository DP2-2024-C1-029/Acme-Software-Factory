
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int userStoryId = super.getRequest().getData("id", int.class);
		UserStory userStory = this.repository.findOneUserStoryById(userStoryId);
		Manager manager = userStory == null ? null : userStory.getManager();
		boolean status = super.getRequest().getPrincipal().hasRole(manager) && userStory != null && userStory.getManager().getId() == manager.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		UserStory object = this.repository.findOneUserStoryById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		object.setDraftMode(false);
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

		int id = super.getRequest().getData("id", int.class);
		UserStory userStoryPreSave = this.repository.findOneUserStoryById(id);
		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(userStoryPreSave.isDraftMode(), "published", "manager.userstory.form.error.published");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put("published", !object.isDraftMode());

		super.getResponse().addData(dataset);
	}
}
