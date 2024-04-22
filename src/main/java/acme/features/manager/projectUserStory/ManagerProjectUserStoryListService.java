
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryListService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal = super.getRequest().getPrincipal();
		int projectId = super.getRequest().getData("id", int.class);
		Collection<ProjectUserStory> objects = this.repository.findAllByManagerAndProject(principal.getActiveRoleId(), projectId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "userStory.title", "userStory.priority", "userStory.estimatedCost");
		if (super.getRequest().getLocale().getLanguage().equals("es"))
			dataset.put("published", object.getUserStory().isDraftMode() ? "No" : "Si");
		else if (super.getRequest().getLocale().getLanguage().equals("en"))
			dataset.put("published", object.getUserStory().isDraftMode() ? "No" : "Yes");
		super.getResponse().addData(dataset);
	}
}
