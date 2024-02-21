
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalUserStoryMust;

	private Integer				totalUserStoryShould;

	private Integer				totalUserStoryCould;

	private Integer				totalUserStoryWont;

	private Double				averageCost;

	private Double				deviationCost;

	private Double				minimumCost;

	private Double				maximumCost;

	private Double				averageEstimatedCost;

	private Double				deviationEstimatedCost;

	private Double				minimumEstimatedCost;

	private Double				maximumEstimatedCost;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
