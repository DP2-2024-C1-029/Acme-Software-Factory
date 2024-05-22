
package acme.features.administrator.currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import acme.entities.currency.Currency;

@Service
public class AdministratorCurrencyService {

	private final String					API_CURRENCIES	= "https://openexchangerates.org/api/currencies.json?app_id={0}";

	private final String					API_KEY			= "ea2faffa03404c2cb0a30c299c0f5a97";

	@Autowired
	private AdministratorCurrencyRepository	administratorCurrencyRepository;


	public List<String> getAllCurrenciesFromApi() {

		List<Currency> currencies = this.administratorCurrencyRepository.findAllCurrenciesTypes();
		List<String> result = new ArrayList<>();
		if (currencies == null || currencies.isEmpty()) {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResult = restTemplate.getForObject(this.API_CURRENCIES, //
				String.class, //
				this.API_KEY);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<String, String> resultMap = objectMapper.readValue(jsonResult, new TypeReference<Map<String, String>>() {
				});
				assert resultMap != null;

				currencies = new ArrayList<>();
				for (Entry<String, String> entry : resultMap.entrySet()) {
					Currency currency = new Currency();
					currency.setCurrencyType(entry.getKey());
					currency.setDescription(entry.getValue());
					currencies.add(currency);
				}
				this.administratorCurrencyRepository.saveAll(currencies);
			} catch (Exception e) {
				assert true;
			}
		}

		for (Currency c : currencies)
			result.add(c.getCurrencyType());

		return result;
	}
}
