
package acme.entities.objectives;

import java.sql.Date;
import java.time.Duration;
import java.time.temporal.Temporal;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@Enumerated
	private ObjectivePriority	priority;

	private boolean				isCritical;

	private Date				endMoment;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	@PositiveOrZero
	@Transient
	public Duration duration() {
		return Duration.between((Temporal) this.instantiationMoment, (Temporal) this.endMoment);
	}

	// Relationships ----------------------------------------------------------
}
