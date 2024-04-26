
package acme.forms.auditor;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//	The system must handle auditor dashboards with the following data: 
	//	total number of code audits for “Static” and “Dynamic” types
	//		average, deviation, minimum, and maximum number of audit records 
	//			in their audits,
	//		average, deviation, minimum, and maximum time of the period lengths 
	//			in their audit records.

	private int					totalStaticCodeAudits;

	private int					totalDynamicCodeAudits;

	private Double				averageAuditRecords;

	private Double				deviationAuditRecords;

	private int					minimunAuditRecords;

	private int					maximunAuditRecords;

	private Double				averageDurationAuditRecords;

	private Double				deviationDurationAuditRecords;

	private double				minimunDurationAuditRecords;

	private double				maximunDurationAuditRecords;
}
