
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectPublishService extends AbstractService<Any, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Project> objects;
		objects = this.repository.findAllPublished();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "cost");

		//Published & Fatal errors
		if (super.getRequest().getLocale().getLanguage().equals("en"))
			dataset.put("indication", object.isDraftMode() ? "Yes" : "No");
		else
			dataset.put("indication", object.isIndication() ? "Si" : "No");

		super.getResponse().addData(dataset);
	}
}
