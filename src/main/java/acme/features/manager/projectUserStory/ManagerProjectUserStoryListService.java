
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.features.authenticated.manager.AuthenticatedManagerRepository;
import acme.features.manager.project.ManagerProjectRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryListService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository	repository;

	@Autowired
	private ManagerProjectRepository			managerProjectRepository;

	@Autowired
	private AuthenticatedManagerRepository		authenticatedManagerRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		int projectId = super.getRequest().getData("id", int.class);
		projectId = super.getRequest().getData("id", int.class);
		Project project = this.managerProjectRepository.findOneProjectById(projectId);
		Manager manager = project == null ? null : project.getManager();
		Manager principal = this.authenticatedManagerRepository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		status = super.getRequest().getPrincipal().hasRole(manager) && project != null && project.getManager().getId() == principal.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int managerId = super.getRequest().getPrincipal().getActiveRoleId();
		int projectId = super.getRequest().getData("id", int.class);
		Collection<ProjectUserStory> objects = this.repository.findAllByManagerAndProject(managerId, projectId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset = new Dataset();
		dataset.put("id", object.getId());
		dataset.put("code", object.getProject().getCode());
		dataset.put("projectId", object.getProject().getId());
		dataset.put("title", object.getUserStory().getTitle());
		dataset.put("estimatedCost", object.getUserStory().getEstimatedCost());
		dataset.put("priority", object.getUserStory().getPriority());
		if (super.getRequest().getLocale().getLanguage().equals("es"))
			dataset.put("published", object.getUserStory().isDraftMode() ? "No" : "Si");
		else if (super.getRequest().getLocale().getLanguage().equals("en"))
			dataset.put("published", object.getUserStory().isDraftMode() ? "No" : "Yes");

		super.getResponse().addData(dataset);
	}
}
