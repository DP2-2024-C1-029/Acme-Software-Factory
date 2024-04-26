
package acme.forms.sponsor;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalInvoicesWithTaxLowerTo21;

	private Integer				totalSponsorshipsWithLink;

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
