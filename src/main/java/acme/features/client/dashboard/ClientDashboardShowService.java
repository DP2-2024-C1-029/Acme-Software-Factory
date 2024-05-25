
package acme.features.client.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.authenticated.exchange.AuthenticatedExchangeService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientDashboardRepository			repository;

	@Autowired
	private AuthenticatedExchangeService		exchangeService;

	@Autowired
	public AdministratorConfigurationRepository	administratorConfigurationRepository;

	private Map<String, List<Money>>			mapAllCurrecies;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		ClientDashboard dashboard;
		int totalLogsWithCompletenessBelow25;
		int totalLogsWithCompletenessBetween25And50;
		int totalLogsWithCompletenessBetween50And75;
		int totalLogsWithCompletenessAbove75;
		int clientId;
		Double AverageBudgetOfContracts = null;
		Double MinimunBudgetOfContracts = null;
		Double MaximunBudgetOfContracts = null;
		Double DeviationBudgetOfContracts = null;
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		double percentaje25 = 25.0;
		double percentaje50 = 50.0;
		double percentaje75 = 75.0;

		totalLogsWithCompletenessBelow25 = this.repository.logsBelowCompletenessValue(clientId, percentaje25);
		totalLogsWithCompletenessBetween25And50 = this.repository.logsBetweenCompletenessValuesForClient(clientId, percentaje25, percentaje50);
		totalLogsWithCompletenessBetween50And75 = this.repository.logsBetweenCompletenessValuesForClient(clientId, percentaje50, percentaje75);
		totalLogsWithCompletenessAbove75 = this.repository.logsAboveCompletenessValue(clientId, percentaje75);

		//Contracts
		Collection<Money> allMoneyBudget = this.repository.getAllMoneyContractBudget(clientId);
		if (allMoneyBudget != null && !allMoneyBudget.isEmpty()) {
			Map<String, List<Money>> allMoneyBudgetInOneCurrency = this.changeCurrency(allMoneyBudget, false);

			for (Map.Entry<String, List<Money>> entry : allMoneyBudgetInOneCurrency.entrySet()) {
				this.mapAllCurrecies = this.changeCurrency(entry.getValue(), true);

				AverageBudgetOfContracts = this.average(entry.getValue());
				MinimunBudgetOfContracts = this.minimum(entry.getValue());
				MaximunBudgetOfContracts = this.maximum(entry.getValue());
				DeviationBudgetOfContracts = this.deviation(entry.getValue(), AverageBudgetOfContracts);
			}
		}
		dashboard = new ClientDashboard();

		// Progress Logs
		dashboard.setNumberLogsWithCompletenessBelow25(totalLogsWithCompletenessBelow25);
		dashboard.setNumberLogsWithCompletenessBetween25And50(totalLogsWithCompletenessBetween25And50);
		dashboard.setNumberLogsWithCompletenessBetween50And75(totalLogsWithCompletenessBetween50And75);
		dashboard.setNumberLogsWithCompletenessAbove75(totalLogsWithCompletenessAbove75);

		dashboard.setAverageBudget(AverageBudgetOfContracts);
		dashboard.setMinimunBudget(MinimunBudgetOfContracts);
		dashboard.setMaximunBudget(MaximunBudgetOfContracts);
		dashboard.setDeviationBudgets(DeviationBudgetOfContracts);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard clientDashboard) {
		Dataset dataset;

		dataset = super.unbind(clientDashboard, //
			"numberLogsWithCompletenessBelow25", "numberLogsWithCompletenessBetween25And50", // 
			"numberLogsWithCompletenessBetween50And75", "numberLogsWithCompletenessAbove75");

		List<String> acceptedCurrency = Lists.newArrayList(this.administratorConfigurationRepository.findConfigurationOfSystem().getAcceptedCurrencies().split(";"));
		Map<String, Double> averageBudget = new HashMap<>();
		Map<String, Double> deviationBudget = new HashMap<>();
		Map<String, Double> minimumBudget = new HashMap<>();
		Map<String, Double> maximumBudget = new HashMap<>();

		for (Map.Entry<String, List<Money>> entry : this.mapAllCurrecies.entrySet()) {
			Double average = this.average(entry.getValue());
			Double deviation = this.deviation(entry.getValue(), average);
			double minimun = this.minimum(entry.getValue());
			double maximum = this.maximum(entry.getValue());

			if (!averageBudget.containsKey(entry.getKey()))
				averageBudget.put(entry.getKey(), average);

			if (!deviationBudget.containsKey(entry.getKey()))
				deviationBudget.put(entry.getKey(), deviation);

			if (!minimumBudget.containsKey(entry.getKey()))
				minimumBudget.put(entry.getKey(), minimun);

			if (!maximumBudget.containsKey(entry.getKey()))
				maximumBudget.put(entry.getKey(), maximum);
		}

		if (minimumBudget.isEmpty())
			for (String currency : acceptedCurrency)
				if (!minimumBudget.containsKey(currency))
					minimumBudget.put(currency, 0.);

		if (maximumBudget.isEmpty())
			for (String currency : acceptedCurrency)
				if (!maximumBudget.containsKey(currency))
					maximumBudget.put(currency, 0.);

		dataset.put("acceptedCurrency", acceptedCurrency);
		dataset.put("averageBudget", averageBudget);
		dataset.put("deviationBudget", deviationBudget);
		dataset.put("minimumBudget", minimumBudget);
		dataset.put("maximumBudget", maximumBudget);

		super.getResponse().addData(dataset);
	}

	private Double average(final List<Money> list) {
		double totalAmount = 0;
		int count = 0;

		for (Money money : list) {
			totalAmount += money.getAmount();
			count++;
		}
		return count > 0 ? totalAmount / count : null;
	}

	private double minimum(final List<Money> list) {
		double minimum = Double.MAX_VALUE;

		for (Money money : list) {
			double amount = money.getAmount();
			if (amount < minimum)
				minimum = amount;
		}
		return Math.min(minimum, 0);
	}

	private double maximum(final List<Money> list) {
		double maximum = Double.MIN_VALUE;

		for (Money money : list) {
			double amount = money.getAmount();
			if (amount > maximum)
				maximum = amount;
		}
		return Math.max(maximum, 0);
	}

	private Map<String, List<Money>> changeCurrency(final Collection<Money> listMoney, final boolean allAcceptanceCurrency) {

		Map<String, List<Money>> result = new HashMap<>();
		List<Money> list = new ArrayList<>();
		for (Money m : listMoney) {
			list = this.exchangeService.changeSourceToTarget(m, allAcceptanceCurrency);

			for (Money mo : list)
				if (result.containsKey(mo.getCurrency()))
					result.get(mo.getCurrency()).add(mo);
				else {
					result.put(mo.getCurrency(), new ArrayList<>());
					result.get(mo.getCurrency()).add(mo);
				}
		}

		return result;
	}

	private double deviation(final Collection<Money> cl, final double average) {
		double result;
		if (cl.isEmpty())
			result = 0;
		else {
			double sumOfSquaredDifferences = 0;
			for (Money money : cl) {
				double difference = money.getAmount() - average;
				sumOfSquaredDifferences += Math.pow(difference, 2);
			}
			double variance = sumOfSquaredDifferences / cl.size();
			double standardDeviation = Math.sqrt(variance);
			result = standardDeviation;
		}

		return result;

	}
}
