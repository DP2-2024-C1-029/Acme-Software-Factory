
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

		int projectId = object.getProject().getId();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findContractByCode(object.getCode());
			if (existing != null)
				super.state(existing.getId() == object.getId(), "code", "client.contract.form.error.duplicatedCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			Collection<Contract> contracts = this.repository.findContractsByProjectId(projectId);

			Double totalBudgetUsd = contracts.stream().mapToDouble(u -> this.repository.currencyTransformerUsd(u.getBudget())).sum();
			Double projectCostUsd = this.repository.currencyTransformerUsd(object.getProject().getCost());

			super.state(totalBudgetUsd <= projectCostUsd, "*", "client.contract.form.error.publishError");
		}

		{
			Collection<ProgressLogs> numProgressLogsPublished;
			numProgressLogsPublished = this.repository.findManyProgressLogsNotPublishedByContractId(object.getId());
			super.state(numProgressLogsPublished.isEmpty(), "*", "client.contract.form.error.not-all-progress-logs-published");
		}
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

		projects = this.repository.findPublishedProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}

}
