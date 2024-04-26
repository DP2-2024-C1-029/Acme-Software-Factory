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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.features.authenticated.exchange.AuthenticatedExchangeService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository	repository;

	@Autowired
	public AuthenticatedExchangeService	exchangeService;

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

		Collection<Double> allEstimatedCost = this.repository.findAllEstimatedCost(manager.getId());
		if (allEstimatedCost != null && !allEstimatedCost.isEmpty()) {
			minimumEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).min().orElse(0);
			maximumEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).max().orElse(0);
			averageEstimatedCost = allEstimatedCost.stream().mapToDouble(Double::doubleValue).average().orElse(0);
			final Double average = averageEstimatedCost;
			deviationEstimatedCost = Math.sqrt(allEstimatedCost.stream().mapToDouble(m -> Math.pow(m - average, 2)).sum() / allEstimatedCost.size());
		}

		Map<String, Double> allChanges = this.exchangeService.getChanges();
		Collection<Money> allMoneyCost = this.repository.getAllMoneyProjectCost(manager.getId());
		if (allMoneyCost != null && !allMoneyCost.isEmpty()) {
			Collection<Money> allMoneyCostInOneCurrency = this.changeCurrency(allMoneyCost, allChanges);
			averageCostProject = allMoneyCostInOneCurrency.stream().mapToDouble(Money::getAmount).average().orElse(0);
			deviationCostProject = this.deviation(allMoneyCostInOneCurrency, averageCostProject);
			minimumCostProject = allMoneyCostInOneCurrency.stream().mapToDouble(Money::getAmount).min().orElse(0);
			maximumCostProject = allMoneyCostInOneCurrency.stream().mapToDouble(Money::getAmount).max().orElse(0);
		}

		ManagerDashboard dashboard = new ManagerDashboard();
		dashboard.setTotalUserStoryMust(totalUserStoryMust);
		dashboard.setTotalUserStoryShould(totalUserStoryShould);
		dashboard.setTotalUserStoryCould(totalUserStoryCould);
		dashboard.setTotalUserStoryWont(totalUserStoryWont);
		dashboard.setAverageEstimatedCost(averageEstimatedCost);
		dashboard.setDeviationEstimatedCost(deviationEstimatedCost);
		dashboard.setMinimumEstimatedCost(minimumEstimatedCost);
		dashboard.setMaximumEstimatedCost(maximumEstimatedCost);
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
			"averageEstimatedCost", "deviationEstimatedCost", "minimumEstimatedCost", "maximumEstimatedCost", "averageCostProject", "deviationCostProject", "minimumCostProject", "maximumCostProject");

		super.getResponse().addData(dataset);
	}

	private List<Money> changeCurrency(final Collection<Money> ls, final Map<String, Double> changes) {
		return ls.stream()//
			.map(m -> this.exchangeService.changeForCurrencyToCurrency(m.getAmount(), m.getCurrency(), super.getRequest().getGlobal("$locale", String.class), changes))//
			.collect(Collectors.toList());
	}

	private double deviation(final Collection<Money> cl, final double average) {
		return cl.isEmpty() ? 0
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m.getAmount() - average, 2)).sum() / cl.size());
	}

}
