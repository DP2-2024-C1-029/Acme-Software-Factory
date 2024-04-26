
package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);
		status = project != null && !project.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectByIdAndPublished(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstractText", "indication", "cost", "link");
		dataset.put("published", !object.isDraftMode());

		super.getResponse().addData(dataset);
	}
}
