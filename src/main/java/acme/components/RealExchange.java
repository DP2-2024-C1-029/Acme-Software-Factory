
package acme.components;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.MomentHelper;
import acme.entities.exchange.Exchange;
import acme.features.authenticated.exchange.AuthenticatedExchangeRepository;

public class RealExchange extends AbstractExchange {

	@Autowired
	AuthenticatedExchangeRepository authenticatedExchangeRepository;


	public RealExchange() {
		super();
	}

	@Override
	public List<String> findAllCurrencies() {
		return this.authenticatedExchangeRepository.findAllCurrencies();
	}

	@Override
	public boolean existsExchanges(final List<String> allCurrenciesSystem) {
		return this.authenticatedExchangeRepository.existsExchanges(allCurrenciesSystem);
	}

	@Override
	public List<Exchange> findExchangeByCurrencySourceAndTarget(final Date currentMoment, final String source, final String target) {
		return this.authenticatedExchangeRepository.findExchangeByCurrencySourceAndTarget(MomentHelper.getCurrentMoment(), source, target);
	}

	@Override
	public List<Exchange> findExchangeByCurrency(final List<String> currencies) {
		return this.authenticatedExchangeRepository.findExchangeByCurrency(currencies);
	}

	@Override
	public List<Exchange> saveAll(final Collection<Exchange> exchanges) {
		return this.authenticatedExchangeRepository.saveAll(exchanges);
	}
}
