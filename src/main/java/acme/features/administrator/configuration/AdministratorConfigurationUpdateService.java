
package acme.features.administrator.configuration;

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

		if (!super.getBuffer().getErrors().hasErrors("currency"))
			super.state(object.getAcceptedCurrencies().matches(".*" + object.getCurrency() + ".*"), "currency", "administrator.configuration.form.error.currency-not-in-accepted");

		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies")) {
			sizeAcceptedObject = object.getAcceptedCurrencies().split(";").length;
			sizeDistinctAccepted = (int) Stream.of(object.getAcceptedCurrencies().split(";")).distinct().count();
			super.state(sizeAcceptedObject == sizeDistinctAccepted, "acceptedCurrencies", "administrator.configuration.form.error.duplicated-currency");
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

		Dataset dataset;

		dataset = super.unbind(object, "currency", "acceptedCurrencies");

		super.getResponse().addData(dataset);
	}
}
