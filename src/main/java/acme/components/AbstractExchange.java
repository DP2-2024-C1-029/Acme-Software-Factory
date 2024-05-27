
package acme.components;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import acme.entities.exchange.Exchange;

public abstract class AbstractExchange {

	public abstract List<String> findAllCurrencies();

	public abstract boolean existsExchanges(List<String> allCurrenciesSystem);

	public abstract List<Exchange> findExchangeByCurrencySourceAndTarget(final Date currentMoment, final String source, final String target);

	public abstract List<Exchange> findExchangeByCurrency(List<String> currencies);

	public abstract List<Exchange> saveAll(Collection<Exchange> exchanges);

}
