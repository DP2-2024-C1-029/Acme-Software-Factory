
package acme.entities.trainingsessions;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.trainingmodules.TrainingModule;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	// Attributes

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endTime;

	@NotBlank
	@NotNull
	@Length(max = 75)
	private String				location;

	@NotBlank
	@NotNull
	@Length(max = 75)
	private String				instructor;

	@NotBlank
	@NotNull
	@Email
	@Length(max = 255)
	private String				contactEmail;

	@URL
	@Length(max = 255)
	private String				furtherInformationLink;

	@NotNull
	private boolean				draftMode;

	// Relationships

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private TrainingModule		trainingModule;

}
