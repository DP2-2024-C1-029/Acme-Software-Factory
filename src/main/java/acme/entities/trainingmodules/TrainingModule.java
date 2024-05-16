
package acme.entities.trainingmodules;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				creationMoment;

	@NotBlank
	@NotNull
	@Length(max = 100)
	private String				details;

	@NotNull
	private DifficultyLevel		difficultyLevel;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				updateMoment;

	@URL
	private String				link;

	@NotNull
	@Range(min = 1)
	private Integer				estimatedTotalTime;

	@NotNull
	private boolean				draftMode;

	// Relationships

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	//La asociación don el rol developer se pide para el entregable D03, por lo que se terminará de desarrollar en el D03
	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Developer			developer;
}
