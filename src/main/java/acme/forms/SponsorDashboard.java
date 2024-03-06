
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalInvoicesWithTaxLowerTo21;

	private Integer				totalSponsorshipsWithLink;

	private Integer				averageAmount;

	private Double				deviationAmount;

	private Double				minimumAmount;

	private Double				maximumAmount;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
