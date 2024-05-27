
package acme.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acme.components.AbstractExchange;

@Component
public class ExchangeHelper {

	@Autowired
	private AbstractExchange exchange;


	public AbstractExchange getExchange() {
		return this.exchange;
	}
}
