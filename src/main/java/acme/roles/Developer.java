
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				degree;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				specialisation;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				listOfSkills;

	@URL
	private String				link;

	@Email
	@NotBlank
	@NotNull
	private String				email;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
