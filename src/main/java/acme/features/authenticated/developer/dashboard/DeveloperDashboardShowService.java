
package acme.features.authenticated.developer.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.Dashboard;
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

		// Average, deviation, minimum and maximum amounts of Training Modules
		averageTrainingModuleTime = allTimeTrainingModules.stream().mapToDouble(Integer::intValue).average().orElse(0);
		deviationTrainingModuleTime = this.deviation(allTimeTrainingModules, averageTrainingModuleTime, sizeTrainingModule);
		minimumTrainingModuleTime = allTimeTrainingModules.stream().mapToDouble(Integer::intValue).min().orElse(0);
		maximumTrainingModuleTime = allTimeTrainingModules.stream().mapToDouble(Integer::intValue).max().orElse(0);

		dashboard = new Dashboard();
		dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);
		dashboard.setTotalTrainingModuleWithUpdateMoment(totalTrainingModuleWithUpdateMoment);
		dashboard.setAverageTrainingModuleTime(averageTrainingModuleTime);
		dashboard.setDeviationTrainingModuleTime(deviationTrainingModuleTime);
		dashboard.setMinimumTrainingModuleTime(minimumTrainingModuleTime);
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
