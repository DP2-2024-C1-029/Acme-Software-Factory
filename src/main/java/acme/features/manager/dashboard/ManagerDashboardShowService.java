/*
 * AdministratorManagerDashboardShowService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.dashboard;

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
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository			repository;

	@Autowired
	public AuthenticatedExchangeService			exchangeService;

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
		Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		int totalUserStoryMust = this.repository.totalUserStoryMust(manager.getId());
		int totalUserStoryShould = this.repository.totalUserStoryShould(manager.getId());
		int totalUserStoryCould = this.repository.totalUserStoryCould(manager.getId());
		int totalUserStoryWont = this.repository.totalUserStoryWont(manager.getId());
		double minimumEstimatedCost = 0;
		double maximumEstimatedCost = 0;
		Double averageEstimatedCost = null;
		Double deviationEstimatedCost = null;
		Double averageCostProject = null;
		Double deviationCostProject = null;
		double minimumCostProject = 0;
		double maximumCostProject = 0;
		this.mapAllCurrecies = new HashMap<>();

		Collection<Double> allEstimatedCost = this.repository.findAllEstimatedCost(manager.getId());
		if (allEstimatedCost != null && !allEstimatedCost.isEmpty()) {
			minimumEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).min().orElse(0);
			maximumEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).max().orElse(0);
			averageEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).average().orElse(0);
			final Double average = averageEstimatedCost;
			deviationEstimatedCost = Math.sqrt(allEstimatedCost.stream().mapToDouble(m -> Math.pow(m - average, 2)).sum() / allEstimatedCost.size());
		}

		Collection<Money> allMoneyCost = this.repository.getAllMoneyProjectCost(manager.getId());
		if (allMoneyCost != null && !allMoneyCost.isEmpty()) {
			Map<String, List<Money>> allMoneyCostInOneCurrency = this.changeCurrency(allMoneyCost, false);

			for (Map.Entry<String, List<Money>> entry : allMoneyCostInOneCurrency.entrySet()) {
				this.mapAllCurrecies = this.changeCurrency(entry.getValue(), true);

				averageCostProject = this.average(entry.getValue());
				deviationCostProject = this.deviation(entry.getValue(), averageCostProject);
				minimumCostProject = this.minimum(entry.getValue());
				maximumCostProject = this.maximum(entry.getValue());
			}
		}

		ManagerDashboard dashboard = new ManagerDashboard();

		//User stories
		dashboard.setTotalUserStoryMust(totalUserStoryMust);
		dashboard.setTotalUserStoryShould(totalUserStoryShould);
		dashboard.setTotalUserStoryCould(totalUserStoryCould);
		dashboard.setTotalUserStoryWont(totalUserStoryWont);
		dashboard.setAverageEstimatedCost(averageEstimatedCost);
		dashboard.setDeviationEstimatedCost(deviationEstimatedCost);
		dashboard.setMinimumEstimatedCost(minimumEstimatedCost);
		dashboard.setMaximumEstimatedCost(maximumEstimatedCost);

		//Projects
		dashboard.setAverageCostProject(averageCostProject);
		dashboard.setDeviationCostProject(deviationCostProject);
		dashboard.setMinimumCostProject(minimumCostProject);
		dashboard.setMaximumCostProject(maximumCostProject);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalUserStoryMust", "totalUserStoryShould", // 
			"totalUserStoryCould", "totalUserStoryWont", //
			"averageEstimatedCost", "deviationEstimatedCost", "minimumEstimatedCost", "maximumEstimatedCost");

		List<String> acceptedCurrency = Lists.newArrayList(this.administratorConfigurationRepository.findConfigurationOfSystem().getAcceptedCurrencies().split(";"));
		Map<String, Double> averageCostProject = new HashMap<>();
		Map<String, Double> deviationCostProject = new HashMap<>();
		Map<String, Double> minimumCostProject = new HashMap<>();
		Map<String, Double> maximumCostProject = new HashMap<>();

		for (Map.Entry<String, List<Money>> entry : this.mapAllCurrecies.entrySet()) {
			Double average = this.average(entry.getValue());
			Double deviation = this.deviation(entry.getValue(), average);
			double minimun = this.minimum(entry.getValue());
			double maximum = this.maximum(entry.getValue());

			if (!averageCostProject.containsKey(entry.getKey()))
				averageCostProject.put(entry.getKey(), average);

			if (!deviationCostProject.containsKey(entry.getKey()))
				deviationCostProject.put(entry.getKey(), deviation);

			if (!minimumCostProject.containsKey(entry.getKey()))
				minimumCostProject.put(entry.getKey(), minimun);

			if (!maximumCostProject.containsKey(entry.getKey()))
				maximumCostProject.put(entry.getKey(), maximum);
		}

		if (minimumCostProject.isEmpty())
			for (String currency : acceptedCurrency)
				if (!minimumCostProject.containsKey(currency))
					minimumCostProject.put(currency, 0.);

		if (maximumCostProject.isEmpty())
			for (String currency : acceptedCurrency)
				if (!maximumCostProject.containsKey(currency))
					maximumCostProject.put(currency, 0.);

		dataset.put("acceptedCurrency", acceptedCurrency);
		dataset.put("averageCostProject", averageCostProject);
		dataset.put("deviationCostProject", deviationCostProject);
		dataset.put("minimumCostProject", minimumCostProject);
		dataset.put("maximumCostProject", maximumCostProject);

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
