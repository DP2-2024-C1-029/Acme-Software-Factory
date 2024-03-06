
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

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
	@NotBlank
	@Pattern(regexp = "R-\\d{3}")
	private String				reference;

	//	an identification date (in the past)
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				identificationDate;

	//	an impact (positive real number)
	@Min(0)
	private double				impact;

	//	a probability, 
	@Range(min = 0, max = 1)
	private double				probability;

	//	a description (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 100)
	private String				description;

	//	an optional link with further information
	@URL
	@Length(max = 255)
	private String				link;

	// Derived attributes -----------------------------------------------------


	//	a value (result of the multiplication of impact and probability)
	@Transient
	public Double value() {
		return this.impact * this.probability;
	}
}
