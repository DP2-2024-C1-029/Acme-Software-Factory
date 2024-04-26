
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListPublishedService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Project> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findAllByManager(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "cost");

		//Published & Fatal errors
		if (super.getRequest().getLocale().getLanguage().equals("en")) {
			dataset.put("indication", object.isDraftMode() ? "Yes" : "No");
			dataset.put("published", object.isDraftMode() ? "No" : "Yes");
		} else {
			dataset.put("indication", object.isIndication() ? "Si" : "No");
			dataset.put("published", object.isDraftMode() ? "No" : "Si");
		}

		super.getResponse().addData(dataset);
	}
}
