
package acme.features.sponsor.dashboard;

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
import acme.forms.sponsor.Dashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorDashboardRepository	repository;

	@Autowired
	public AuthenticatedExchangeService	exchangeService;

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
		Map<String, Double> allChanges;
		Collection<Money> allAmountsSponsorships;
		Collection<Money> allQuantitiesInvoices;

		sponsorId = this.getRequest().getPrincipal().getActiveRoleId();

		allChanges = this.exchangeService.getChanges();
		allAmountsSponsorships = this.changeCurrency(this.repository.findAllAmountsOfSponsorships(sponsorId), allChanges);
		allQuantitiesInvoices = this.changeCurrency(this.repository.findAllQuantitiesOfInvoices(sponsorId), allChanges);

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
		dashboard.setAverageAmountSponsorships(this.moneyOfAmount(averageAmountSponsorships));
		dashboard.setDeviationAmountSponsorships(this.moneyOfAmount(deviationAmountSponsorships));
		dashboard.setMinimumAmountSponsorships(this.moneyOfAmount(minimumAmountSponsorships));
		dashboard.setMaximumAmountSponsorships(this.moneyOfAmount(maximumAmountSponsorships));
		dashboard.setAverageQuantityInvoices(this.moneyOfAmount(averageQuantityInvoices));
		dashboard.setDeviationQuantityInvoices(this.moneyOfAmount(deviationQuantityInvoices));
		dashboard.setMinimumQuantityInvoices(this.moneyOfAmount(minimumQuantityInvoices));
		dashboard.setMaximumQuantityInvoices(this.moneyOfAmount(maximumQuantityInvoices));

		super.getBuffer().addData(dashboard);
	}

	public List<Money> changeCurrency(final Collection<Money> ls, final Map<String, Double> changes) {
		return ls.stream()//
			.map(m -> this.exchangeService.changeForCurrencyToCurrency(m.getAmount(), m.getCurrency(), super.getRequest().getGlobal("$locale", String.class), changes))//
			.collect(Collectors.toList());
	}

	public Double deviation(final Collection<Money> cl, final double average) {
		return cl.isEmpty() ? null
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m.getAmount() - average, 2)).sum() / cl.size());
	}

	public Money moneyOfAmount(final Double amount) {
		Money res;

		if (amount != null && !amount.equals(Double.NaN)) {
			res = new Money();
			res.setAmount(amount);
			res.setCurrency(super.getRequest().getGlobal("$locale", String.class).equals("es") ? "EUR" : "USD");
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
