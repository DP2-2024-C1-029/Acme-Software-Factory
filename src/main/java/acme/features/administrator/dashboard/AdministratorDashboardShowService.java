
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
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
		Double totalNoticesWithEmailAndLink;
		Integer totalNotices;

		Double totalCriticalObjectives;
		Double totalNonCriticalObjectives;
		Integer totalObjectives;

		int sizeValueRisk;

		totalNumberOfPrincipalsWithAdministrator = this.repository.totalNumberOfPrincipalsWithAdministrator();
		totalNumberOfPrincipalsWithManager = this.repository.totalNumberOfPrincipalsWithManager();
		totalNumberOfPrincipalsWithDeveloper = this.repository.totalNumberOfPrincipalsWithDeveloper();
		totalNumberOfPrincipalsWithSponsor = this.repository.totalNumberOfPrincipalsWithSponsor();
		totalNumberOfPrincipalsWithAuditor = this.repository.totalNumberOfPrincipalsWithAuditor();
		totalNumberOfPrincipalsWithClient = this.repository.totalNumberOfPrincipalsWithClient();

		totalNoticesWithEmailAndLink = this.repository.findTotalNoticesWithEmailAndLink();
		totalNotices = this.repository.findTotalNotices();
		if (totalNoticesWithEmailAndLink == null || totalNotices == null || totalNotices == 0)
			ratioOfNoticesWithEmailAndLink = 0.0;
		else
			ratioOfNoticesWithEmailAndLink = totalNoticesWithEmailAndLink / totalNotices * 100;

		totalCriticalObjectives = this.repository.findTotalCriticalObjectives();
		totalNonCriticalObjectives = this.repository.findTotalNonCriticalObjectives();
		totalObjectives = this.repository.findTotalObjectives();
		if (totalCriticalObjectives == null || totalObjectives == null || totalObjectives == 0)
			ratioOfCriticalObjectives = 0.0;
		else
			ratioOfCriticalObjectives = totalCriticalObjectives / totalObjectives * 100;
		if (totalNonCriticalObjectives == null || totalObjectives == null || totalObjectives == 0)
			ratioOfNonCriticalObjectives = 0.0;
		else
			ratioOfNonCriticalObjectives = totalNonCriticalObjectives / totalObjectives * 100;

		allValueRisk = this.repository.findAllValueOfRisk();
		sizeValueRisk = allValueRisk.size();

		averageValueInTheRisks = allValueRisk.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
		deviationValueInTheRisks = this.deviation(allValueRisk, averageValueInTheRisks, sizeValueRisk);
		minValueInTheRisks = allValueRisk.stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
		maxValueInTheRisks = allValueRisk.stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);

		dashboard = new AdministratorDashboard();

		dashboard.setTotalNumberOfPrincipalsWithAdministrator(totalNumberOfPrincipalsWithAdministrator);
		dashboard.setTotalNumberOfPrincipalsWithAuditor(totalNumberOfPrincipalsWithAuditor);
		dashboard.setTotalNumberOfPrincipalsWithClient(totalNumberOfPrincipalsWithClient);
		dashboard.setTotalNumberOfPrincipalsWithDeveloper(totalNumberOfPrincipalsWithDeveloper);
		dashboard.setTotalNumberOfPrincipalsWithManager(totalNumberOfPrincipalsWithManager);
		dashboard.setTotalNumberOfPrincipalsWithSponsor(totalNumberOfPrincipalsWithSponsor);

		if (!allValueRisk.isEmpty()) {
			dashboard.setAverageValueInTheRisks(averageValueInTheRisks);
			dashboard.setDeviationValueInTheRisks(deviationValueInTheRisks);
			dashboard.setMinValueInTheRisks(minValueInTheRisks);
			dashboard.setMaxValueInTheRisks(maxValueInTheRisks);
		}

		if (!(totalCriticalObjectives == null || totalObjectives == null))
			dashboard.setRatioOfNoticesWithEmailAndLink(ratioOfNoticesWithEmailAndLink);

		if (!(totalNonCriticalObjectives == null || totalObjectives == null))
			dashboard.setRatioOfNonCriticalObjectives(ratioOfNonCriticalObjectives);

		if (!(totalNoticesWithEmailAndLink == null || totalNotices == null))
			dashboard.setRatioOfCriticalObjectives(ratioOfCriticalObjectives);

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

		dataset = super.unbind(object, "averageValueInTheRisks", "deviationValueInTheRisks", "minValueInTheRisks", "maxValueInTheRisks", "ratioOfNoticesWithEmailAndLink", "ratioOfNonCriticalObjectives", "ratioOfCriticalObjectives",
			"totalNumberOfPrincipalsWithAdministrator", "totalNumberOfPrincipalsWithManager", "totalNumberOfPrincipalsWithDeveloper", "totalNumberOfPrincipalsWithSponsor", "totalNumberOfPrincipalsWithAuditor", "totalNumberOfPrincipalsWithClient");

		super.getResponse().addData(dataset);
	}

}
