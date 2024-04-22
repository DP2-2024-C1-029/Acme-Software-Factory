
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ClientDashboard dashboard;
		int numberLogsWithCompletenessBelow25;
		int numberLogsWithCompletenessBetween25And50;
		int numberLogsWithCompletenessBetween50And75;
		int numberLogsWithCompletenessAbove75;
		int clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		float percentaje25 = 25.0f;
		float percentaje50 = 50.0f;
		float percentaje75 = 75.0f;
		numberLogsWithCompletenessBelow25 = this.repository.logsBelowCompletenessValue(clientId, percentaje25);
		numberLogsWithCompletenessBetween25And50 = this.repository.logsBetweenCompletenessValuesForClient(clientId, percentaje25, percentaje50);
		numberLogsWithCompletenessBetween50And75 = this.repository.logsBetweenCompletenessValuesForClient(clientId, percentaje50, percentaje75);
		numberLogsWithCompletenessAbove75 = this.repository.logsAboveCompletenessValue(clientId, percentaje75);

		dashboard = new ClientDashboard();

		// Progress Logs
		dashboard.setNumberLogsWithCompletenessBelow25(numberLogsWithCompletenessBelow25);
		dashboard.setNumberLogsWithCompletenessBetween25And50(numberLogsWithCompletenessBetween25And50);
		dashboard.setNumberLogsWithCompletenessBetween50And75(numberLogsWithCompletenessBetween50And75);
		dashboard.setNumberLogsWithCompletenessAbove75(numberLogsWithCompletenessAbove75);

		//Contracts
		Collection<Money> contractBudgets = this.repository.findAllBudgetsFromClient(clientId);

		dashboard.setAverageBudget(contractBudgets.stream().mapToDouble(u -> this.repository.currencyTransformerUsd(u)).average().getAsDouble());
		dashboard.setDeviationBudgets(this.invoicesDeviationQuantity(contractBudgets));
		dashboard.setMinimunBudget(contractBudgets.stream().mapToDouble(u -> this.repository.currencyTransformerUsd(u)).min().getAsDouble());
		dashboard.setMaximunBudget(contractBudgets.stream().mapToDouble(u -> this.repository.currencyTransformerUsd(u)).max().getAsDouble());

		super.getBuffer().addData(dashboard);
	}

	private double invoicesDeviationQuantity(final Collection<Money> quantites) {
		double deviation;

		double average = quantites.stream().mapToDouble(u -> this.repository.currencyTransformerUsd(u)).average().orElse(0.0);

		double sumOfSquares = quantites.stream().mapToDouble(x -> Math.pow(x.getAmount() - average, 2)).sum();

		double vari = sumOfSquares / quantites.size();

		double dev = Math.sqrt(vari);

		deviation = dev;

		return deviation;
	}

	@Override
	public void unbind(final ClientDashboard clientDashboard) {
		Dataset dataset;

		dataset = super.unbind(clientDashboard, //
			"numberLogsWithCompletenessBelow25", "numberLogsWithCompletenessBetween25And50", // 
			"numberLogsWithCompletenessBetween50And75", "numberLogsWithCompletenessAbove75", //
			"averageBudget", "deviationBudgets",//
			"minimunBudget", "maximunBudget");

		super.getResponse().addData(dataset);
	}
}
