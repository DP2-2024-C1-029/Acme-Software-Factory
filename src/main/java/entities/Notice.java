
package entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@PastOrPresent
	private LocalDateTime		instantiationMoment;

	@NotBlank
	@Size(max = 75)
	private String				title;

	@NotBlank
	@Size(max = 75)
	private String				author;

	@NotBlank
	@Size(max = 100)
	private String				message;

	private String				emailAddress;

	@URL
	private String				link;


	// Additional method to set author based on username and full name
	@Transient
	public void setAuthorFromPrincipal(final String username, final String fullName) {
		this.author = username + " - " + fullName;
	}
}
