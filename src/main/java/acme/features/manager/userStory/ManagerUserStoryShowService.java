
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstories.Priority;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

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
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(Priority.class, object.getPriority());
		Dataset dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		dataset.put("published", !object.isDraftMode());
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}
}
