
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.configuration.Configuration;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.authenticated.exchange.AuthenticatedExchangeService;
import acme.forms.sponsor.Dashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorDashboardRepository			repository;

	@Autowired
	public AuthenticatedExchangeService			exchangeService;

	@Autowired
	public AdministratorConfigurationRepository	administratorConfigurationRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorId;
		Dashboard dashboard;
		Integer totalInvoicesWithTaxLowerTo21;
		Integer totalSponsorshipsWithLink;
		Double averageAmountSponsorships;
		Double deviationAmountSponsorships;
		Double minimumAmountSponsorships;
		Double maximumAmountSponsorships;
		Double averageQuantityInvoices;
		Double deviationQuantityInvoices;
		Double minimumQuantityInvoices;
		Double maximumQuantityInvoices;
		Collection<Money> allAmountsSponsorships;
		Collection<Money> allQuantitiesInvoices;

		sponsorId = this.getRequest().getPrincipal().getActiveRoleId();

		Configuration configuration = this.administratorConfigurationRepository.findConfigurationOfSystem();
		String systemCurrency = configuration.getCurrency();

		allAmountsSponsorships = this.changeCurrency(this.repository.findAllAmountsOfSponsorships(sponsorId));
		allQuantitiesInvoices = this.changeCurrency(this.repository.findAllQuantitiesOfInvoices(sponsorId));

		if (allQuantitiesInvoices.isEmpty())
			totalInvoicesWithTaxLowerTo21 = null;
		else
			totalInvoicesWithTaxLowerTo21 = this.repository.totalInvoicesWithTaxLowerTo21(sponsorId);

		if (allAmountsSponsorships.isEmpty())
			totalSponsorshipsWithLink = null;
		else
			totalSponsorshipsWithLink = this.repository.totalSponsorshipsWithLink(sponsorId);

		// Average, deviation, minimum and maximum amounts of sponsorships
		averageAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN);
		deviationAmountSponsorships = this.deviation(allAmountsSponsorships, averageAmountSponsorships);
		minimumAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).min().orElse(Double.NaN);
		maximumAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).max().orElse(Double.NaN);

		// Average, deviation, minimum and maximum quantities of invoices
		averageQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN);
		deviationQuantityInvoices = this.deviation(allQuantitiesInvoices, averageQuantityInvoices);
		minimumQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).min().orElse(Double.NaN);
		maximumQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).max().orElse(Double.NaN);

		dashboard = new Dashboard();
		dashboard.setTotalInvoicesWithTaxLowerTo21(totalInvoicesWithTaxLowerTo21);
		dashboard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);
		dashboard.setAverageAmountSponsorships(this.moneyOfAmount(averageAmountSponsorships, systemCurrency));
		dashboard.setDeviationAmountSponsorships(this.moneyOfAmount(deviationAmountSponsorships, systemCurrency));
		dashboard.setMinimumAmountSponsorships(this.moneyOfAmount(minimumAmountSponsorships, systemCurrency));
		dashboard.setMaximumAmountSponsorships(this.moneyOfAmount(maximumAmountSponsorships, systemCurrency));
		dashboard.setAverageQuantityInvoices(this.moneyOfAmount(averageQuantityInvoices, systemCurrency));
		dashboard.setDeviationQuantityInvoices(this.moneyOfAmount(deviationQuantityInvoices, systemCurrency));
		dashboard.setMinimumQuantityInvoices(this.moneyOfAmount(minimumQuantityInvoices, systemCurrency));
		dashboard.setMaximumQuantityInvoices(this.moneyOfAmount(maximumQuantityInvoices, systemCurrency));

		super.getBuffer().addData(dashboard);
	}

	public List<Money> changeCurrency(final Collection<Money> ls) {
		return ls.stream()//
			.map(m -> this.exchangeService.changeSourceToTarget(m, false).get(0))//
			.collect(Collectors.toList());
	}

	public Double deviation(final Collection<Money> cl, final double average) {
		return cl.isEmpty() ? null
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m.getAmount() - average, 2)).sum() / cl.size());
	}

	public Money moneyOfAmount(final Double amount, final String systemCurrency) {
		Money res;

		if (amount != null && !amount.equals(Double.NaN)) {
			res = new Money();
			res.setAmount(amount);
			res.setCurrency(systemCurrency);
		} else
			res = null;

		return res;
	}

	@Override
	public void unbind(final Dashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalInvoicesWithTaxLowerTo21", "totalSponsorshipsWithLink", "averageAmountSponsorships", "deviationAmountSponsorships", "minimumAmountSponsorships", "maximumAmountSponsorships", "averageQuantityInvoices",
			"deviationQuantityInvoices", "minimumQuantityInvoices", "maximumQuantityInvoices");

		super.getResponse().addData(dataset);
	}

}
