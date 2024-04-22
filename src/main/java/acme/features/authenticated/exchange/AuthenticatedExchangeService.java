
package acme.features.authenticated.exchange;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.entities.exchange.Exchange;
import acme.entities.exchange.ExchangeRate;

@Service
public class AuthenticatedExchangeService {

	// Internal state ---------------------------------------------------------
	@Autowired
	public AuthenticatedExchangeRepository repository;

	// Methods for changing the Exchange --------------------------------------


	public Money changeForCurrencyToCurrency(final Double amount, final String fromCurrency, final String local, final Map<String, Double> data) {
		Money res;
		double change;
		String toCurrency;

		toCurrency = local.equals("en") ? "USD" : "EUR";

		change = data.get(toCurrency) / data.get(fromCurrency);

		res = new Money();
		res.setAmount(change * amount);
		res.setCurrency(toCurrency);

		return res;
	}

	public Map<String, Double> getChangesFromAPI() {
		RestTemplate api;
		ExchangeRate data;

		MomentHelper.sleep(1000);

		api = new RestTemplate();
		data = api.getForObject(//
			"https://openexchangerates.org/api/latest.json?app_id={0}",//
			ExchangeRate.class, //
			"ea2faffa03404c2cb0a30c299c0f5a97");

		assert data != null;

		Collection<Exchange> allExchanges = new ArrayList<>();
		for (Entry<String, Double> e : data.getRates().entrySet()) {
			Exchange exchange = new Exchange();
			exchange.setExpireDate(MomentHelper.deltaFromCurrentMoment(1L, ChronoUnit.HOURS));
			exchange.setCurrency(e.getKey());
			exchange.setAmount(e.getValue());
			allExchanges.add(exchange);
		}
		this.repository.saveAll(allExchanges);
		return data.getRates();
	}

	public Map<String, Double> getChanges() {
		Collection<Exchange> exchanges;

		exchanges = this.repository.findExchangeByCurrency(MomentHelper.getCurrentMoment());

		if (exchanges.isEmpty())
			return this.getChangesFromAPI();
		else
			// summingDouble funciona bien porque sabemos con seguridad que no hay 2 currencies iguales, entonces dará solo la de ese currency
			return exchanges.stream().collect(Collectors.groupingBy(Exchange::getCurrency, Collectors.summingDouble(Exchange::getAmount)));
	}
}
