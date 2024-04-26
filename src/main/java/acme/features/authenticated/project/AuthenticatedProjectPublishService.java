
package acme.features.authenticated.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectPublishService extends AbstractService<Authenticated, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().isAuthenticated());
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
