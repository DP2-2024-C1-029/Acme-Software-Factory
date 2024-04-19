
package acme.features.sponsor.sponsordashboard;

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
import acme.forms.Sponsordashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, Sponsordashboard> {

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
		Sponsordashboard dashboard;
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
		int sizeSponsorships;
		int sizeInvoices;
		Collection<Money> allAmountsSponsorships;
		Collection<Money> allQuantitiesInvoices;

		sponsorId = this.getRequest().getPrincipal().getActiveRoleId();

		totalInvoicesWithTaxLowerTo21 = this.repository.totalInvoicesWithTaxLowerTo21(sponsorId);
		totalSponsorshipsWithLink = this.repository.totalSponsorshipsWithLink(sponsorId);

		allChanges = this.exchangeService.getChanges();
		allAmountsSponsorships = this.changeCurrency(this.repository.findAllAmountsOfSponsorships(sponsorId), allChanges);
		allQuantitiesInvoices = this.changeCurrency(this.repository.findAllQuantitiesOfInvoices(sponsorId), allChanges);
		sizeSponsorships = allAmountsSponsorships.size();
		sizeInvoices = allQuantitiesInvoices.size();

		// Average, deviation, minimum and maximum amounts of sponsorships
		averageAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).average().orElse(0);
		deviationAmountSponsorships = this.deviation(allAmountsSponsorships, averageAmountSponsorships, sizeSponsorships);
		minimumAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).min().orElse(0);
		maximumAmountSponsorships = allAmountsSponsorships.stream().mapToDouble(Money::getAmount).max().orElse(0);

		// Average, deviation, minimum and maximum quantities of invoices
		averageQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).average().orElse(0);
		deviationQuantityInvoices = this.deviation(allQuantitiesInvoices, averageQuantityInvoices, sizeInvoices);
		minimumQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).min().orElse(0);
		maximumQuantityInvoices = allQuantitiesInvoices.stream().mapToDouble(Money::getAmount).max().orElse(0);

		dashboard = new Sponsordashboard();
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

	public double deviation(final Collection<Money> cl, final double average, final int size) {
		return cl.isEmpty() ? 0
			: Math.sqrt(cl.stream()//
				.mapToDouble(m -> Math.pow(m.getAmount() - average, 2)).sum() / size);
	}

	public Money moneyOfAmount(final Double amount) {
		Money res;

		res = new Money();
		res.setAmount(amount);
		res.setCurrency(super.getRequest().getGlobal("$locale", String.class).equals("es") ? "EUR" : "USD");

		return res;
	}

	@Override
	public void unbind(final Sponsordashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalInvoicesWithTaxLowerTo21", "totalSponsorshipsWithLink", "averageAmountSponsorships", "deviationAmountSponsorships", "minimumAmountSponsorships", "maximumAmountSponsorships", "averageQuantityInvoices",
			"deviationQuantityInvoices", "minimumQuantityInvoices", "maximumQuantityInvoices");

		super.getResponse().addData(dataset);
	}

}
