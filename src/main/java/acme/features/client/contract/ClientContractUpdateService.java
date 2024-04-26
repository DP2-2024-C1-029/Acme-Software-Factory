
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Service
public class ClientContractUpdateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientContractRepository repository;

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

	public boolean isCurrencyAccepted(final Money moneda) {
		SystemConfiguration moneys;
		moneys = this.repository.findSystemConfiguration();

		String[] listaMonedas = moneys.getAcceptedCurrencies().split(",");
		for (String divisa : listaMonedas)
			if (moneda.getCurrency().equals(divisa))
				return true;

		return false;
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findContractByCode(object.getCode());
			if (existing != null)
				super.state(existing.getId() == object.getId(), "code", "client.contract.form.error.duplicatedCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget") && !super.getBuffer().getErrors().hasErrors("project")) {

			Project referencedProject = object.getProject();
			super.state(this.repository.currencyTransformerUsd(referencedProject.getCost()) >= this.repository.currencyTransformerUsd(object.getBudget()), "budget", "client.contract.form.error.budget");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(object.getBudget().getAmount() > 0, "budget", "client.contract.form.error.negative-budget");

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(this.isCurrencyAccepted(object.getBudget()), "budget", "client.contract.form.error.acceptedCurrency");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "client.contract.form.error.project-not-published");

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			ProgressLogs fetchedPl;
			fetchedPl = this.repository.findProgressLogEarliestRegistrationMomentByContractId(object.getId());
			Date registrationMoment;
			if (fetchedPl == null)
				registrationMoment = null;
			else
				registrationMoment = fetchedPl.getRegistrationMoment();
			if (registrationMoment != null)
				super.state(object.getInstantiationMoment().before(this.repository.findProgressLogEarliestRegistrationMomentByContractId(object.getId()).getRegistrationMoment()), "instantiationMoment", "client.contract.form.error.instantiation-moment");
		}
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
