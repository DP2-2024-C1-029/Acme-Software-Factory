
package acme.features.administrator.configuration;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.configuration.Configuration;

@Service
public class AdministratorConfigurationUpdateService extends AbstractService<Administrator, Configuration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Configuration configuration;

		configuration = this.repository.findConfigurationOfSystem();
		status = configuration != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Configuration object;

		object = new Configuration();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Configuration object) {
		assert object != null;

		super.bind(object, "currency", "acceptedCurrencies");
	}

	@Override
	public void validate(final Configuration object) {
		assert object != null;

		int sizeAcceptedObject;
		int sizeDistinctAccepted;
		Collection<String> objectCurrencies;
		Collection<String> allCurrenciesOfAPI;
		Collection<String> allCurrenciesInSystem;

		if (!super.getBuffer().getErrors().hasErrors("currency"))
			super.state(object.getAcceptedCurrencies().matches(".*" + object.getCurrency() + ".*"), "currency", "administrator.configuration.form.error.currency-not-in-accepted");

		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies")) {
			sizeAcceptedObject = object.getAcceptedCurrencies().split(";").length;
			sizeDistinctAccepted = (int) Stream.of(object.getAcceptedCurrencies().split(";")).distinct().count();
			super.state(sizeAcceptedObject == sizeDistinctAccepted, "acceptedCurrencies", "administrator.configuration.form.error.duplicated-currency");

			objectCurrencies = Stream.of(object.getAcceptedCurrencies().split(";")).toList();
			allCurrenciesOfAPI = this.repository.findCurrenciesFromAPI();
			allCurrenciesInSystem = this.repository.findAllCurrentCurrencies();
			super.state(allCurrenciesOfAPI.containsAll(objectCurrencies), "*", "administrator.configuration.form.error.not-accepted-by-api");
			super.state(objectCurrencies.containsAll(allCurrenciesInSystem), "*", "administrator.configuration.form.error.not-all-of-the-system");
		}
	}

	@Override
	public void perform(final Configuration object) {
		assert object != null;

		this.repository.save(object);
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
