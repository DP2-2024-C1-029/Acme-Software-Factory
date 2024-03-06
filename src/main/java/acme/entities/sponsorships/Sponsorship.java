
package acme.entities.sponsorships;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.datatypes.Money;
import acme.entities.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Sponsorship {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	// Methods for the duration
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				initialExecutionPeriod;

	// TODO - Validar en servicios mínimo un mes después
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endingExecutionPeriod;

	// TODO - Must be positive (in the servicies)
	@NotNull
	private Money				amount;

	@NotNull
	private SponsorType			type;

	@Email
	@Length(max = 255)
	private String				email;

	@URL
	@Length(max = 255)
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne
	private Project				project;
}
