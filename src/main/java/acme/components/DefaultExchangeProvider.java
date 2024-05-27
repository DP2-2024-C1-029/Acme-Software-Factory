
package acme.components;

import org.springframework.stereotype.Component;

import acme.internals.helpers.EnvironmentHelper;

@Component
public class DefaultExchangeProvider {

	// Constructors -----------------------------------------------------------

	protected DefaultExchangeProvider() {
	}

	// Singleton --------------------------------------------------------------


	public static final DefaultExchangeProvider	INSTANCE	= new DefaultExchangeProvider();

	// Internal state ---------------------------------------------------------

	private static AbstractExchange				backbone;


	public AbstractExchange getExchange() {
		AbstractExchange result;
		String profiles;

		if (DefaultExchangeProvider.backbone == null) {
			profiles = EnvironmentHelper.getRequiredProperty("spring.profiles.active", String.class);
			if (!profiles.contains("development"))
				DefaultExchangeProvider.backbone = new SimulatedExchange();
			else
				DefaultExchangeProvider.backbone = new RealExchange();
		}
		result = DefaultExchangeProvider.backbone;

		return result;
	}

}
