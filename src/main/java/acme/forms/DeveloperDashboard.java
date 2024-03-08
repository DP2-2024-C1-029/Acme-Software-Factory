
package acme.entities;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;
	int							totalTrainingModuleWithUpdateMoment;

	int							totalNumberOfTrainingSessionsWithLink;

	Double						averageTrainingModuleTime;

	Double						deviationTrainingModuleTime;

	Double						minimumTrainingModuleTime;

	Double						maximumTrainingModuleTime;

}
