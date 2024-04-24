
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.ProjectUserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select u from ProjectUserStory u where u.project.manager.id = :managerId and u.project.id = :projectId")
	public Collection<ProjectUserStory> findAllByManagerAndProject(int managerId, int projectId);

}
