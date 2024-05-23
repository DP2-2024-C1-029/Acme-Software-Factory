
package acme.entities.codeaudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), //
})
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//	Code audits are essential pieces to ensure the quality of a project. 
	//	The system must store the following data about them: 
	//		a code (pattern “[A-Z]{1,3}-[0-9]{3}”, not blank, unique), 
	//		an execution date (in the past), 
	//		a type (“Static”, “Dynamic”),
	//		a list of proposed corrective actions (not blank, shorter than 101 characters), 
	//		a mark (computed as the mode of the marks in the corresponding auditing records; ties must be broken towards the smallest mark)
	//		an optional link with further information.

	// a code (pattern “[A-Z]{1,3}-[0-9]{3}”, not blank, unique)
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-\\d{3}")
	private String				code;

	// an execution date (in the past)
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	// a type (“Static”, “Dynamic”)
	@NotNull
	private AuditType			type;

	// a list of proposed corrective actions (not blank, shorter than 101 characters)
	@NotBlank
	@Length(min = 1, max = 100)
	private String				correctiveActions;

	// an optional link with further information.
	@URL
	@Length(max = 255)
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// a mark (computed as the mode of the marks in the corresponding auditing records; ties must be broken towards the smallest mark) 

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor				auditor;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;
}
