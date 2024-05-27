
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.features.authenticated.manager.AuthenticatedManagerRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository		repository;

	@Autowired
	private AuthenticatedManagerRepository	authenticatedManagerRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findOneProjectByIdAndNotPublished(projectId);
		Manager manager = project == null ? null : project.getManager();
		Manager principal = this.authenticatedManagerRepository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		boolean status = manager != null && super.getRequest().getPrincipal().hasRole(manager) && project != null && project.getManager().getId() == principal.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		Project object = this.repository.findOneProjectByIdAndNotPublished(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractText", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		int id = super.getRequest().getData("id", int.class);
		Project projectPreSave = this.repository.findOneProjectByIdAndNotPublished(id);
		Collection<ProjectUserStory> listProjectUserStory = this.repository.findUserStoryByProjectPublished(id);
		if (!super.getBuffer().getErrors().hasErrors("published")) {
			super.state(projectPreSave.isDraftMode(), "published", "manager.project.form.error.published");
			super.state(!listProjectUserStory.isEmpty(), "published", "manager.project.form.error.published.without_userStory");
			super.state(!projectPreSave.isIndication(), "published", "manager.project.form.error.published.fatal_error");
		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstractText", "indication", "cost", "link");
		dataset.put("published", !object.isDraftMode());

		super.getResponse().addData(dataset);
	}
}
