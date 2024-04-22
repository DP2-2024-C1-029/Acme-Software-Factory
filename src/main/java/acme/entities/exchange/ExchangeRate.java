
package acme.entities.exchange;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRate {

	// Attributes -------------------------------------------------------------

	public String				disclaimer;
	public String				license;
	public long					timestamp;
	public String				base;
	public Map<String, Double>	rates;

}
