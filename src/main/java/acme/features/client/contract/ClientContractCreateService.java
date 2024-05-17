
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {

		Contract contract;
		Client client;
		Integer clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		client = this.repository.findOneClientById(clientId);
		contract = new Contract();
		contract.setClient(client);
		contract.setDraftMode(true);
		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project");

		Date instantiationMoment;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		instantiationMoment = new Date(currentMoment.getTime() - 5000);
		object.setInstantiationMoment(instantiationMoment);

	}

	public boolean isCurrencyAccepted(final Money budget) {

		SystemConfiguration systemMoney = this.repository.findSystemConfiguration();

		String[] currencyTypes = systemMoney.getAcceptedCurrencies().split(",");
		for (String type : currencyTypes)
			if (budget.getCurrency().equals(type))
				return true;

		return false;
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicatedCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(object.getBudget().getAmount() > 0, "budget", "client.contract.form.error.negative-budget");

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("budget") && !super.getBuffer().getErrors().hasErrors("project"))
		 * super.state(this.repository.currencyTransformerUsd(object.getProject().getCost()) >= this.repository.currencyTransformerUsd(object.getBudget()), "budget", "client.contract.form.error.budget");
		 * 
		 * if (!super.getBuffer().getErrors().hasErrors("budget"))
		 * super.state(this.isCurrencyAccepted(object.getBudget()), "budget", "client.contract.form.error.acceptedCurrency");
		 */

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			String[] acceptedCurrencies = this.repository.findAcceptedCurrencies().split(";");
			super.state(Stream.of(acceptedCurrencies).anyMatch(c -> c.equals(object.getBudget().getCurrency())), //
				"budget", "client.contract.form.error.acceptedCurrency");
		}

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "client.contract.form.error.project-not-published");

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("budget") && this.sysConfigRepository.existsCurrency(object.getBudget().getCurrency())) {
		 * boolean validBudget = object.getBudget().getAmount() >= 0. && this.sysConfigRepository.convertToUsd(object.getBudget()).getAmount() <= 1000000.0;
		 * super.state(validBudget, "budget", "client.contract.form.error.budget-negative");
		 * }
		 */
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		SelectChoices choices;
		Collection<Project> projects = this.repository.findPublishedProjects();
		Dataset dataset;

		choices = SelectChoices.from(projects, "title", null);
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project");
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}
}
