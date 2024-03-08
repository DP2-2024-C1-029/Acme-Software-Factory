
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;
	int							totalNumberOfPrincipalsWithAdministrator;

	int							totalNumberOfPrincipalsWithManager;

	int							totalNumberOfPrincipalsWithDeveloper;

	int							totalNumberOfPrincipalsWithSponsor;

	int							totalNumberOfPrincipalsWithAuditor;

	int							totalNumberOfPrincipalsWithClient;

	Double						ratioOfNoticesWithEmailAndLink;

	Double						ratioOfCriticalObjectives;

	Double						ratioOfNonCriticalObjectives;

	Double						averageValueInTheRisks;

	double						minValueInTheRisks;

	double						maxValueInTheRisks;

	Double						deviationValueInTheRisks;

}
