
package acme.helper;

import acme.components.AbstractExchange;
import acme.components.DefaultExchangeProvider;

public abstract class ExchangeHelper {

	protected ExchangeHelper() {
	}


	static {
		ExchangeHelper.exchange = (AbstractExchange) DefaultExchangeProvider.INSTANCE.getExchange();
	}

	// Internal state ---------------------------------------------------------

	private static AbstractExchange exchange;


	public static AbstractExchange getExchange() {
		return ExchangeHelper.exchange;
	}
}
