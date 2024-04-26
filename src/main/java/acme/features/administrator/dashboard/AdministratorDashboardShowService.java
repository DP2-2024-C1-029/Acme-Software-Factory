
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AdministratorDashboard;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int developerId;
		Principal principal;
		AdministratorDashboard dashboard;
		Integer totalNumberOfPrincipalsWithAdministrator;
		Integer totalNumberOfPrincipalsWithManager;
		Integer totalNumberOfPrincipalsWithDeveloper;
		Integer totalNumberOfPrincipalsWithSponsor;
		Integer totalNumberOfPrincipalsWithAuditor;
		Integer totalNumberOfPrincipalsWithClient;
		Double ratioOfNoticesWithEmailAndLink;
		Double ratioOfCriticalObjectives;
		Double ratioOfNonCriticalObjectives;
		Double averageValueInTheRisks;
		Double minValueInTheRisks;
		Double deviationValueInTheRisks;
		Double maxValueInTheRisks;
		Collection<Double> allValueRisk;
		int sizeValueRisk;

		/*
		 * totalNumberOfPrincipalsWithAdministrator = this.repository.totalNumberOfPrincipalsWithAdministrator();
		 * totalNumberOfPrincipalsWithManager = this.repository.totalNumberOfPrincipalsWithManager();
		 * totalNumberOfPrincipalsWithDeveloper = this.repository.totalNumberOfPrincipalsWithDeveloper();
		 * totalNumberOfPrincipalsWithSponsor = this.repository.totalNumberOfPrincipalsWithSponsor();
		 * totalNumberOfPrincipalsWithAuditor = this.repository.totalNumberOfPrincipalsWithAuditor();
		 * totalNumberOfPrincipalsWithClient = this.repository.totalNumberOfPrincipalsWithClient();
		 */
		allValueRisk = this.repository.findAllValueOfRisk();
		sizeValueRisk = allValueRisk.size();

		averageValueInTheRisks = allValueRisk.isEmpty() ? Double.NaN : allValueRisk.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
		deviationValueInTheRisks = allValueRisk.isEmpty() ? Double.NaN : this.deviation(allValueRisk, averageValueInTheRisks, sizeValueRisk);
		minValueInTheRisks = allValueRisk.isEmpty() ? Double.NaN : allValueRisk.stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
		maxValueInTheRisks = allValueRisk.isEmpty() ? Double.NaN : allValueRisk.stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);

		dashboard = new AdministratorDashboard();

		/*
		 * if (totalTrainingModuleWithUpdateMoment != null && totalTrainingModuleWithUpdateMoment != 0)
		 * dashboard.setTotalTrainingModuleWithUpdateMoment(totalTrainingModuleWithUpdateMoment);
		 * 
		 * if (totalNumberOfTrainingSessionsWithLink != null && totalNumberOfTrainingSessionsWithLink != 0)
		 * dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);
		 */

		if (!allValueRisk.isEmpty()) {
			dashboard.setAverageValueInTheRisks(averageValueInTheRisks);
			dashboard.setDeviationValueInTheRisks(deviationValueInTheRisks);
			dashboard.setMinValueInTheRisks(minValueInTheRisks);
			dashboard.setMaxValueInTheRisks(maxValueInTheRisks);
		}

		super.getBuffer().addData(dashboard);
	}

	public double deviation(final Collection<Double> cl, final double average, final int size) {
		return cl.isEmpty() ? 0
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m - average, 2)).sum() / size);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "averageValueInTheRisks", "deviationValueInTheRisks", "minValueInTheRisks", "maxValueInTheRisks");

		super.getResponse().addData(dataset);
	}

}
