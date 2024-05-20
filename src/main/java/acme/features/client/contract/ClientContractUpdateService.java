
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
public class ClientContractUpdateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientContractRepository			repository;

	@Autowired
	private AuthenticatedExchangeService	exchangeService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int contractId;
		Contract contract;
		int clientId;
		boolean status;

		contractId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(contractId);
		clientId = super.getRequest().getPrincipal().getActiveRoleId();

		status = clientId == contract.getClient().getId() && contract.isDraftMode() && contract.getProject() != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		Integer contractId;

		contractId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(contractId);

		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract contract) {
		assert contract != null;

		Integer managerId = super.getRequest().getPrincipal().getActiveRoleId();
		Client client = this.repository.findOneClientById(managerId);

		contract.setClient(client);
		super.bind(contract, "code", "project", "providerName", "customerName", "instantiationMoment", "budget", "goals");
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
	}

	@Override
	public void perform(final Contract contract) {
		assert contract != null;

		this.repository.save(contract);
	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;

		Dataset dataset;

		Collection<Project> projects = this.repository.findPublishedProjects();
		SelectChoices options;

		options = SelectChoices.from(projects, "title", null);

		dataset = super.unbind(contract, "code", "project", "providerName", "customerName", "instantiationMoment", "budget", "goals", "draftMode");
		dataset.put("projects", options);

		super.getResponse().addData(dataset);
	}
}
