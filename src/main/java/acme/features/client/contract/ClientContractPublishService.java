
package acme.features.client.contract;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.features.authenticated.exchange.AuthenticatedExchangeService;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientContractRepository			repository;

	@Autowired
	private AuthenticatedExchangeService	exchangeService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		boolean status;
		int Id;
		Contract Contract;
		Client Client;

		Id = super.getRequest().getData("id", int.class);
		Contract = this.repository.findContractById(Id);
		Client = Contract == null ? null : Contract.getClient();
		status = Contract != null && Contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(Client) && Contract.getProject() != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project", "draftMode");
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findContractByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicatedCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			String[] acceptedCurrencies = this.repository.findAcceptedCurrencies().split(";");
			super.state(Stream.of(acceptedCurrencies).anyMatch(c -> c.equals(object.getBudget().getCurrency())), //
				"budget", "client.contract.form.error.acceptedCurrency");
		}

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "client.contract.form.error.project-not-published");

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			boolean validBudget = object.getBudget().getAmount() >= 0 && object.getBudget().getAmount() <= 1000000.0;
			super.state(validBudget, "budget", "client.contract.form.error.maximum-negative-budget");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget") && !super.getBuffer().getErrors().hasErrors("project"))
			super.state(this.exchangeService.changeForCurrencyToCurrency(object.getProject().getCost().getAmount(), object.getProject().getCost().getCurrency(), // 
				super.getRequest().getGlobal("$locale", String.class), this.exchangeService.getChanges()).getAmount() >= this.exchangeService
					.changeForCurrencyToCurrency(object.getBudget().getAmount(), object.getBudget().getCurrency(), //
						super.getRequest().getGlobal("$locale", String.class), this.exchangeService.getChanges())
					.getAmount(),
				"budget", "client.contract.form.error.budget");

		if (!super.getBuffer().getErrors().hasErrors("budget") && !super.getBuffer().getErrors().hasErrors("project")) {
			int projectId = object.getProject().getId();
			Collection<Contract> contracts = this.repository.findContractsByProjectIdExceptThis(projectId, object.getId());
			contracts.add(object);
			Double totalBudgetUsd = contracts.stream().mapToDouble(u -> this.exchangeService.changeForCurrencyToCurrency(u.getBudget().getAmount(), //
				u.getBudget().getCurrency(), super.getRequest().getGlobal("$locale", String.class), this.exchangeService.getChanges()).getAmount()).sum();
			Double projectCostUsd = this.exchangeService.changeForCurrencyToCurrency(object.getProject().getCost().getAmount(), object.getProject().getCost().getCurrency(), // 
				super.getRequest().getGlobal("$locale", String.class), this.exchangeService.getChanges()).getAmount();

			super.state(totalBudgetUsd <= projectCostUsd, "*", "client.contract.form.error.publishError");
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
