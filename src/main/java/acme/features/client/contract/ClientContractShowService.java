
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int clientId;
		Contract contract;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(id);
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		status = clientId == contract.getClient().getId() && contract.getProject() != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int Contractid;

		Contractid = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(Contractid);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		Collection<Project> projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
