
package acme.features.administrator.currency;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.currency.Currency;

@Repository
public interface AdministratorCurrencyRepository extends AbstractRepository {

	@Query("select c from Currency c")
	List<Currency> findAllCurrenciesTypes();
}
