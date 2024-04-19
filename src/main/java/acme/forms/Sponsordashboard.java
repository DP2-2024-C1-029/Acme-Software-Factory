
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sponsordashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private int					totalInvoicesWithTaxLowerTo21;

	private int					totalSponsorshipsWithLink;

	private Money				averageAmountSponsorships;

	private Money				deviationAmountSponsorships;

	private Money				minimumAmountSponsorships;

	private Money				maximumAmountSponsorships;

	private Money				averageQuantityInvoices;

	private Money				deviationQuantityInvoices;

	private Money				minimumQuantityInvoices;

	private Money				maximumQuantityInvoices;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
