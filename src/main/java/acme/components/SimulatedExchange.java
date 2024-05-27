
package acme.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import acme.entities.exchange.Exchange;

@Service
@Profile("!development")
public class SimulatedExchange extends AbstractExchange {

	public SimulatedExchange() {
		super();
	}

	@Override
	public List<String> findAllCurrencies() {
		return Lists.newArrayList("EUR", "USD", "GBP");
	}

	@Override
	public boolean existsExchanges(final List<String> allCurrenciesSystem) {
		return true;
	}

	@Override
	public List<Exchange> findExchangeByCurrencySourceAndTarget(final Date currentMoment, final String source, final String target) {
		List<Exchange> listResult = new ArrayList<>();
		Exchange exchange = new Exchange();

		exchange.setAmount(0.78537);
		exchange.setCurrency(source);
		listResult.add(exchange);

		exchange = new Exchange();
		exchange.setAmount(0.921901);
		exchange.setCurrency(target);
		listResult.add(exchange);

		return listResult;
	}

	@Override
	public List<Exchange> findExchangeByCurrency(final List<String> currencies) {
		return new ArrayList<>();
	}

	@Override
	public List<Exchange> saveAll(final Collection<Exchange> exchanges) {
		return List.of();
	}

}
