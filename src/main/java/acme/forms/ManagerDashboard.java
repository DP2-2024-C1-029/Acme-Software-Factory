
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

	private int					totalUserStoryMust;

	private int					totalUserStoryShould;

	private int					totalUserStoryCould;

	private int					totalUserStoryWont;

	private Double				averageEstimatedCost;

	private Double				deviationEstimatedCost;

	private double				minimumEstimatedCost;

	private double				maximumEstimatedCost;

	private Double				averageCostProject;

	private Double				deviationCostProject;

	private double				minimumCostProject;

	private double				maximumCostProject;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
