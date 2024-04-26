
package acme.features.authenticated.exchange;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.exchange.Exchange;

@Repository
public interface AuthenticatedExchangeRepository extends AbstractRepository {

	@Query("select e from Exchange e where e.expireDate >= :now")
	Collection<Exchange> findExchangeByCurrency(Date now);
}
