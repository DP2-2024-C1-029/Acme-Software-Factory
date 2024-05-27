
package acme.features.authenticated.exchange;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.entities.configuration.Configuration;
import acme.entities.exchange.Exchange;
import acme.entities.exchange.ExchangeRate;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.helper.ExchangeHelper;

@Service
public class AuthenticatedExchangeService {

	// Internal state ---------------------------------------------------------

	private final String						API_URL			= "https://openexchangerates.org/api/latest.json?app_id={0}&base={1}";

	private final String						SYMBOLS			= "&symbols={2}";

	private final String						API_KEY			= "ea2faffa03404c2cb0a30c299c0f5a97";

	private final String						CURRENCY_BASE	= "USD";

	private final DecimalFormat					df				= new DecimalFormat("#.00");

	@Autowired
	public AuthenticatedExchangeRepository		repository;

	@Autowired
	public AdministratorConfigurationRepository	administratorConfigurationRepository;


	public List<String> getAllCurrenciesFromApi() {

		List<String> currencies = ExchangeHelper.getExchange().findAllCurrencies();
		List<String> result = new ArrayList<>();
		if (currencies == null || currencies.isEmpty()) {
			List<Exchange> exhanges = this.getFromApi(null);
			for (Exchange c : exhanges)
				result.add(c.getCurrency());
		} else
			result.addAll(currencies);

		return result;
	}

	// Methods for changing the Exchange --------------------------------------
	public List<Money> changeSourceToTarget(final Money money, final boolean allAcepptedCurrencies) {
		List<Money> result = new ArrayList<>();
		StringBuilder symbols = new StringBuilder();
		String currencySource = money.getCurrency();

		Configuration configuration = this.administratorConfigurationRepository.findConfigurationOfSystem();
		String currenciesSystem = configuration.getAcceptedCurrencies().replace(";", ",");
		List<String> allCurrenciesSystem = Lists.newArrayList(currenciesSystem.split(","));
		//La primera vez que llamemos, obtenemos de la api todas las permitidas
		if (Boolean.FALSE.equals(ExchangeHelper.getExchange().existsExchanges(allCurrenciesSystem)))
			this.getAllCurrenciesFromApi();

		if (!allAcepptedCurrencies) {
			allCurrenciesSystem = Lists.newArrayList();
			allCurrenciesSystem.add(configuration.getCurrency());
		}

		for (String target : allCurrenciesSystem) {
			Money moneyResult = new Money();
			if (!currencySource.equals(target)) {
				List<Exchange> changes = ExchangeHelper.getExchange().findExchangeByCurrencySourceAndTarget(MomentHelper.getCurrentMoment(), currencySource, target);

				if (changes != null && !changes.isEmpty()) {
					if (changes.size() == 2)
						this.calculateTargetMoney(moneyResult, changes, target, money.getAmount());
					else if (changes.size() == 1) {
						String existingCurrency = changes.get(0).getCurrency();
						String foundCurrency;
						if (existingCurrency.equals(currencySource))
							foundCurrency = target;
						else
							foundCurrency = currencySource;

						symbols.append(foundCurrency);
						changes.addAll(this.getFromApi(symbols));
						this.calculateTargetMoney(moneyResult, changes, target, money.getAmount());
					}
				} else {
					symbols.append(currencySource).append(",").append(target);
					changes = this.getFromApi(symbols);
					this.calculateTargetMoney(moneyResult, changes, target, money.getAmount());
				}
			} else {
				moneyResult.setAmount(money.getAmount());
				moneyResult.setCurrency(money.getCurrency());
			}

			result.add(moneyResult);
		}
		return result;
	}

	private Money calculateTargetMoney(final Money result, final List<Exchange> changes, final String currencyTarget, final Double amountCurrentMoney) {
		double amountSource;
		double amountTarget;
		if (changes.get(0).getCurrency().equals(currencyTarget)) {
			amountTarget = changes.get(0).getAmount();
			amountSource = changes.get(1).getAmount();
		} else {
			amountTarget = changes.get(1).getAmount();
			amountSource = changes.get(0).getAmount();
		}

		double change = amountTarget / amountSource;
		double amount = change * amountCurrentMoney;
		result.setAmount(Double.parseDouble(this.df.format(amount)));
		result.setCurrency(currencyTarget);

		return result;
	}

	private List<Exchange> getFromApi(final StringBuilder symbols) {
		RestTemplate restTemplate = new RestTemplate();
		ExchangeRate data;
		if (symbols != null)
			data = restTemplate.getForObject(this.API_URL, //
				ExchangeRate.class, //
				this.API_KEY, //
				this.CURRENCY_BASE, //
				symbols.toString());
		else
			data = restTemplate.getForObject(this.API_URL, //
				ExchangeRate.class, //
				this.API_KEY, //
				this.CURRENCY_BASE);

		assert data != null;

		Collection<Exchange> allExchanges = new ArrayList<>();
		List<String> currencies = new ArrayList<>();
		for (Entry<String, Double> e : data.getRates().entrySet()) {
			Exchange exchange = new Exchange();
			exchange.setExpireDate(MomentHelper.deltaFromCurrentMoment(1L, ChronoUnit.HOURS));
			exchange.setCurrency(e.getKey());
			exchange.setAmount(e.getValue());
			allExchanges.add(exchange);
			currencies.add(e.getKey());
		}

		Collection<Exchange> exchangesToSave = new ArrayList<>();
		//Buscamos si ya existia la moneda porque haya expirado el tiempo, para no generar una nueva sino actualizarla
		List<Exchange> existingCurrencies = ExchangeHelper.getExchange().findExchangeByCurrency(currencies);
		for (Exchange exchange : allExchanges) {
			boolean found = false;
			for (Exchange exchangeToUpdate : existingCurrencies)
				if (exchange.getCurrency().equals(exchangeToUpdate.getCurrency())) {
					exchangeToUpdate.setExpireDate(MomentHelper.deltaFromCurrentMoment(1L, ChronoUnit.HOURS));
					exchangeToUpdate.setAmount(exchange.getAmount());
					exchangesToSave.add(exchangeToUpdate);
					found = true;
				}

			if (!found)
				exchangesToSave.add(exchange);
		}
		return ExchangeHelper.getExchange().saveAll(exchangesToSave);
	}
}
