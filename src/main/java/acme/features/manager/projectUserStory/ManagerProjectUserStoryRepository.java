
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select pus from ProjectUserStory pus where pus.id = :projectUserStoryId")
	public ProjectUserStory findProjectUserStoryById(final int projectUserStoryId);

	@Query("select u from ProjectUserStory u where u.project.manager.id = :managerId and u.project.id = :projectId")
	public Collection<ProjectUserStory> findAllByManagerAndProject(int managerId, int projectId);

	@Query("select p from Project p where p.id = :projectId")
	public Project findProjectById(final int projectId);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select u from UserStory u where u.id = :userStoryId")
	UserStory findOneUserStoryById(final int userStoryId);

	@Query("select u from UserStory u where u.manager.id = :managerId and u.id NOT IN (select pu.userStory.id from ProjectUserStory pu where pu.project.id = :projectId and pu.project.manager.id = :managerId) ")
	public Collection<UserStory> findUserStoryToAdd(int managerId, int projectId);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId and pus.userStory.id = :userStoryId and pus.project.manager.id = :managerId and pus.userStory.manager.id = :managerId")
	public ProjectUserStory findByProjectAndUserStory(final int managerId, final int projectId, final int userStoryId);

}
