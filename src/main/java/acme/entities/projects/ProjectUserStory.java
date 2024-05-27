
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import acme.client.data.AbstractEntity;
import acme.entities.userstories.UserStory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "project_id, user_story_id"), //
	@Index(columnList = "project_id"), //
})
public class ProjectUserStory extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@Valid
	@ManyToOne(optional = false)
	private UserStory			userStory;

}
