
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractDeleteService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		boolean status;
		int ContractId;
		Contract Contract;
		Client Client;

		ContractId = super.getRequest().getData("id", int.class);
		Contract = this.repository.findContractById(ContractId);
		Client = Contract == null ? null : Contract.getClient();

		status = Contract != null && super.getRequest().getPrincipal().hasRole(Client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract Contract;
		int id;

		id = super.getRequest().getData("id", int.class);
		Contract = this.repository.findContractById(id);

		super.getBuffer().addData(Contract);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		Client client = this.repository.findOneClientById(id);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		object.setClient(client);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		Collection<ProgressLogs> progressLogs;

		progressLogs = this.repository.findManyProgressLogsByContractId(object.getId());

		this.repository.deleteAll(progressLogs);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choicesProject;
		Dataset dataset;

		projects = this.repository.findProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}
}
