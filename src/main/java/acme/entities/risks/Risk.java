
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//	a reference (pattern “R-[0-9]{3}”), not blank, unique)
	@Column(unique = true)
	@NotBlank(message = "{validation.risk.reference.notblank}")
	@Pattern(regexp = "R-\\d{3}", message = "{validation.risk.reference.pattern}")
	private String				reference;

	//	an identification date (in the past)
	@Past(message = "{validation.risk.date}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date				identificationDate;

	//	an impact (positive real number)
	@Positive(message = "{validation.risk.impact}")
	private double				impact;

	//	a probability, 
	@NotNull(message = "{validation.risk.probability.notnull}")
	@Range(min = 0, max = 1)
	private double				probability;

	//	a description (not blank, shorter than 101 characters)
	@NotBlank(message = "{validation.risk.description.notblank}")
	@Length(max = 100, message = "{validation.risk.description.max}")
	private String				description;

	//	an optional link with further information
	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	//	a value (result of the multiplication of impact and probability)
	@Transient
	public Double value() {
		return this.impact * this.probability;
	}
}
