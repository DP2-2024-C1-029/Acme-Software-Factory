
package acme.entities;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;
	private Integer				totalNumberOfPrincipalsWithAdministrator;

	private Integer				totalNumberOfPrincipalsWithManager;

	private Integer				totalNumberOfPrincipalsWithDeveloper;

	private Integer				totalNumberOfPrincipalsWithSponsor;

	private Integer				totalNumberOfPrincipalsWithAuditor;

	private Integer				totalNumberOfPrincipalsWithClient;

	private Double				ratioOfNoticesWithEmailAndLink;

	private Double				ratioOfCriticalObjectives;

	private Double				ratioOfNonCriticalObjectives;

	private Double				averageValueInTheRisks;

	private Double				minValueInTheRisks;

	private Double				maxValueInTheRisks;

	private Double				deviationValueInTheRisks;

}
