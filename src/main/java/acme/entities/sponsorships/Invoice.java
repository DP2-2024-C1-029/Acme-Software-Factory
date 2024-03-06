
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	// TODO - 1 month later to registrationTime in servicies
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	@Min(1)
	private int					quantity;

	@Min(0)
	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 100)
	private double				tax;

	@NotNull
	@Length(max = 255)
	private String				link;
	// Derived attributes -----------------------------------------------------


	@Transient
	private double totalAmount() {
		return this.tax * this.quantity;
	}


	// Relationships ----------------------------------------------------------
	@ManyToOne
	private Sponsorship sponsorship;
}
