
package acme.features.authenticated.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface AuthenticatedProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(final int projectId);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(final String code);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllPublished();

	@Query("select p from Project p where p.id = :projectId and p.draftMode = false")
	Project findOneProjectByIdAndPublished(final int projectId);
}
