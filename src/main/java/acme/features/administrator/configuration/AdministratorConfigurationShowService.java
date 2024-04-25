
package acme.features.administrator.configuration;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.configuration.Configuration;
import acme.features.authenticated.exchange.AuthenticatedExchangeService;

@Service
public class AdministratorConfigurationShowService extends AbstractService<Administrator, Configuration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorConfigurationRepository	repository;

	@Autowired
	public AuthenticatedExchangeService			exchangeService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Configuration banner;

		banner = this.repository.findConfigurationOfSystem();
		status = banner != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Configuration object;

		// Método para cargar los datos de la api en la base de datos
		if (this.repository.findCurrenciesFromAPI().isEmpty())
			this.exchangeService.getChanges();

		object = this.repository.findConfigurationOfSystem();

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;

		String allCurrentCurrencies;
		String allAcceptedByAPI;
		Dataset dataset;

		allCurrentCurrencies = this.repository.findAllCurrentCurrencies().stream().collect(Collectors.joining(";"));
		allAcceptedByAPI = this.repository.findCurrenciesFromAPI().stream().collect(Collectors.joining(";"));

		dataset = super.unbind(object, "currency", "acceptedCurrencies");
		dataset.put("currentCurrencies", allCurrentCurrencies);
		dataset.put("currenciesFromAPI", allAcceptedByAPI);

		super.getResponse().addData(dataset);
	}
}
