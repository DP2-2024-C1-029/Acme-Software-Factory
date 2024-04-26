
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.developer.Dashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int developerId;
		Dashboard dashboard;
		Integer totalTrainingModuleWithUpdateMoment;
		Integer totalNumberOfTrainingSessionsWithLink;
		Double averageTrainingModuleTime;
		Double deviationTrainingModuleTime;
		Double minimumTrainingModuleTime;
		Double maximumTrainingModuleTime;
		Collection<Integer> allTimeTrainingModules;
		int sizeTrainingModule;

		developerId = this.getRequest().getPrincipal().getActiveRoleId();

		totalTrainingModuleWithUpdateMoment = this.repository.totalTrainingModuleWithUpdateMoment(developerId);
		totalNumberOfTrainingSessionsWithLink = this.repository.totalNumberOfTrainingSessionsWithLink(developerId);

		allTimeTrainingModules = this.repository.findAllTimeOfTrainingModule(developerId);
		sizeTrainingModule = allTimeTrainingModules.size();

		averageTrainingModuleTime = allTimeTrainingModules.isEmpty() ? Double.NaN : allTimeTrainingModules.stream().mapToDouble(Integer::intValue).average().orElse(Double.NaN);
		deviationTrainingModuleTime = allTimeTrainingModules.isEmpty() ? Double.NaN : this.deviation(allTimeTrainingModules, averageTrainingModuleTime, sizeTrainingModule);
		minimumTrainingModuleTime = allTimeTrainingModules.isEmpty() ? Double.NaN : allTimeTrainingModules.stream().mapToDouble(Integer::intValue).min().orElse(Double.NaN);
		maximumTrainingModuleTime = allTimeTrainingModules.isEmpty() ? Double.NaN : allTimeTrainingModules.stream().mapToDouble(Integer::intValue).max().orElse(Double.NaN);

		dashboard = new Dashboard();

			dashboard.setTotalTrainingModuleWithUpdateMoment(totalTrainingModuleWithUpdateMoment);

			dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);

		if (!Double.isNaN(averageTrainingModuleTime))
			dashboard.setAverageTrainingModuleTime(averageTrainingModuleTime);

		if (!Double.isNaN(deviationTrainingModuleTime))
			dashboard.setDeviationTrainingModuleTime(deviationTrainingModuleTime);

		if (!Double.isNaN(minimumTrainingModuleTime))
			dashboard.setMinimumTrainingModuleTime(minimumTrainingModuleTime);

		if (!Double.isNaN(maximumTrainingModuleTime))
			dashboard.setMaximumTrainingModuleTime(maximumTrainingModuleTime);

		super.getBuffer().addData(dashboard);
	}

	public double deviation(final Collection<Integer> cl, final double average, final int size) {
		return cl.isEmpty() ? 0
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m - average, 2)).sum() / size);
	}

	@Override
	public void unbind(final Dashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalTrainingModuleWithUpdateMoment", "totalNumberOfTrainingSessionsWithLink", "averageTrainingModuleTime", "deviationTrainingModuleTime", "minimumTrainingModuleTime", "maximumTrainingModuleTime");

		super.getResponse().addData(dataset);
	}

}
