
package acme.components;

import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.format.Formatter;

public class BooleanFormatter implements Formatter<Boolean> {

	@Override
	public String print(final Boolean value, final Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("/src/main/resources/messages", Locale.getDefault());
		String trueValue = bundle.getString("true");
		String falseValue = bundle.getString("false");
		return value ? trueValue : falseValue;
	}

	@Override
	public Boolean parse(final String text, final Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}
}
