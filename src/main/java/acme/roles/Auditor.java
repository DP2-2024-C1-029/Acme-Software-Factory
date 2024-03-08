
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//	firm (not blank, shorter than 76 characters)
	@NotBlank
	@Length(max = 75)
	private String				firm;

	//	professional ID (not blank, shorter than 26 characters)
	@NotBlank
	@Length(max = 25)
	private String				professionalId;

	//	a list of certifications (not blank, shorter than 101 characters)
	@NotBlank
	@Length(max = 100)
	private String				certifications;

	//	an optional link with further information
	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
