
package acme.features.manager.project;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.authenticated.manager.AuthenticatedManagerRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository			repository;

	@Autowired
	public AdministratorConfigurationRepository	administratorConfigurationRepository;

	@Autowired
	private AuthenticatedManagerRepository		authenticatedManagerRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findOneProjectByIdAndNotPublished(projectId);
		Manager manager = project == null ? null : project.getManager();
		Manager principal = this.authenticatedManagerRepository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		boolean status = super.getRequest().getPrincipal().hasRole(manager) && project != null && project.getManager().getId() == principal.getId();

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing = this.repository.findOneProjectByCode(object.getCode());
			int projectId = super.getRequest().getData("id", int.class);
			if (existing != null && existing.getId() != projectId)
				super.state(existing == null, "code", "manager.project.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			final Money rP = object.getCost();
			final List<String> acceptedCurrency = Arrays.asList(this.administratorConfigurationRepository.findConfigurationOfSystem().getAcceptedCurrencies().split(";"));
			super.state(acceptedCurrency.contains(rP.getCurrency()), "cost", "manager.project.form.error.cost.currency");
			super.state(object.getCost().getAmount() >= 0, "cost", "manager.project.form.error.negative-cost");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstractText", "indication", "cost", "link");
		dataset.put("published", false);

		super.getResponse().addData(dataset);
	}
}
