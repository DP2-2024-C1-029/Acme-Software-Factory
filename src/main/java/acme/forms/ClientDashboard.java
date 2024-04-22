
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberLogsWithCompletenessBelow25;

	int							numberLogsWithCompletenessBetween25And50;

	int							numberLogsWithCompletenessBetween50And75;

	int							numberLogsWithCompletenessAbove75;

	Double						averageBudget;

	Double						deviationBudgets;

	Double						minimunBudget;

	Double						maximunBudget;

}
