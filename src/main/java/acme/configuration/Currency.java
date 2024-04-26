
package acme.configuration;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	private CurrencyType		currency;


	public Currency() {
		this.currency = CurrencyType.EUR;
	}

}
