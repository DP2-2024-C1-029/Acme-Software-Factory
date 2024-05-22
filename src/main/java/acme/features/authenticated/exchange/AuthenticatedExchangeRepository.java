
package acme.features.authenticated.exchange;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.exchange.Exchange;

@Repository
public interface AuthenticatedExchangeRepository extends AbstractRepository {

	@Query("select e from Exchange e where e.expireDate >= :now")
	Collection<Exchange> findExchangeByCurrencyAndExpirationDate(Date now);

	@Query("select e from Exchange e where e.expireDate >= :now and (e.currency = :currencySource or e.currency = :currencyTarget)")
	List<Exchange> findExchangeByCurrencySourceAndTarget(Date now, String currencySource, String currencyTarget);

	@Query("select e from Exchange e where e.currency in :currencies")
	List<Exchange> findExchangeByCurrency(List<String> currencies);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Exchange e WHERE e.currency IN :currencies")
	Boolean existsExchanges(List<String> currencies);
}
