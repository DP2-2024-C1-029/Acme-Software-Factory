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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

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
		Double averageEstimatedCost = this.repository.averageEstimatedCost(manager.getId());
		Double deviationEstimatedCost = this.repository.deviationEstimatedCost(manager.getId());
		double minimumEstimatedCost = this.repository.minimumEstimatedCost(manager.getId());
		double maximumEstimatedCost = this.repository.maximumEstimatedCost(manager.getId());
		Double averageCostProject = this.repository.averageCostProject(manager.getId());
		Double deviationCostProject = this.repository.deviationCostProject(manager.getId());
		double minimumCostProject = this.repository.minimumCostProject(manager.getId());
		double maximumCostProject = this.repository.maximumCostProject(manager.getId());

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

}
