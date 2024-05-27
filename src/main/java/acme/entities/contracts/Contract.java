
package acme.entities.contracts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(indexes = {

	@Index(columnList = "code"), @Index(columnList = "id"), @Index(columnList = "client_id")
})

public class Contract extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//	a reference (pattern “[A-Z]{1-3}-[0-9]{3}”), not blank, unique) OK
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@Length(max = 75)
	@NotBlank
	private String				providerName;

	@Length(max = 75)
	@NotBlank
	private String				customerName;

	@Length(max = 100)
	@NotBlank
	private String				goals;

	// Service will apply the constraint
	@NotNull
	private Money				budget;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private Project				project;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private Client				client;

}
