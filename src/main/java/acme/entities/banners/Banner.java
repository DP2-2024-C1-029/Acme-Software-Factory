
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
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

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayStartMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayEndMoment;

	@URL
	@NotBlank
	@Length(max = 255)
	private String				picture;

	@Length(max = 75)
	@NotBlank
	private String				slogan;

	@URL
	@NotBlank
	@Length(max = 255)
	private String				link;

	// Derivative Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

}
