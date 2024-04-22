
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
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		// ojear

		boolean status;
		int Id;
		Contract Contract;
		Client Client;

		Id = super.getRequest().getData("id", int.class);
		Contract = this.repository.findContractById(Id);
		Client = Contract == null ? null : Contract.getClient();
		status = Contract != null && super.getRequest().getPrincipal().hasRole(Client) && Contract.getProject() != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
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
	public void perform(final Contract object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choicesProject;
		Dataset dataset;

		projects = this.repository.findProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "moment", "initialExecutionPeriod", "endingExecutionPeriod", "amount", "type", "email", "link");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}

}
