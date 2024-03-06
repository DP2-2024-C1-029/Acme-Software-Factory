
package acme.entities.codeaudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//  The result of each code audit is based on the analysis of their audit records

	//	The system must store
	//	the following data about them: 
	//		a code (pattern “AU-[0-9]{4}-[0-9]{3}”, not blank, unique), 
	//		the	period during which the subject was audited (in the past, at least one hour long), 
	//		a mark (“A+”, “A”,“B”, “C”, “F”, or “F-”),
	//		an optional link with further information.

	// a code (pattern “AU-[0-9]{4}-[0-9]{3}”, not blank, unique)
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "AU-\\d{4}-\\d{3}")
	private String				code;

	// the	period during which the subject was audited (in the past, at least one hour long)
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				startPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				endPeriod;

	// a mark (“A+”, “A”,“B”, “C”, “F”, or “F-”)
	@NotNull
	private Mark				mark;

	// an optional link with further information.
	@URL
	@Length(max = 255)
	private String				link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private CodeAudit			codeAudit;

	// Relationships ----------------------------------------------------------

	//@NotNull
	//@Valid
	//@ManyToOne(optional = false)
	//private Project				project;

}
