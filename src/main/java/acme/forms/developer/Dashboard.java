
package acme.forms.developer;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;
	int							totalTrainingModuleWithUpdateMoment;

	int							totalNumberOfTrainingSessionsWithLink;

	Double						averageTrainingModuleTime;

	Double						deviationTrainingModuleTime;

	double						minimumTrainingModuleTime;

	double						maximumTrainingModuleTime;

}
