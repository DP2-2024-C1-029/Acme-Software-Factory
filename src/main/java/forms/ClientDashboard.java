
package forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberLogsWithCompletenessBelow25;

	Integer						numberLogsWithCompletenessBetween25And50;

	Integer						numberLogsWithCompletenessBetween50And75;

	Integer						numberLogsWithCompletenessAbove75;

	Double						averageBudget;

	Double						deviationBudgets;

	Double						minimunBudget;

	Double						maximunBudget;

}
