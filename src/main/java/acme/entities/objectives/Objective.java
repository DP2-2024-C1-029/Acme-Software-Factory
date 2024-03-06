
package acme.entities.objectives;

import java.sql.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Objective extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	private ObjectivePriority	priority;

	private boolean				isCritical;

	// Methods for the duration
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialExecutionPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endingExecutionPeriod;

	@URL
	@NotNull	// It can have "" string.
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
