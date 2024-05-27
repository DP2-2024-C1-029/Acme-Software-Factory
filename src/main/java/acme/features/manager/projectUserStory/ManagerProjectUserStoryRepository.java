
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.ProjectUserStory;
import acme.entities.userstories.UserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select pus from ProjectUserStory pus where pus.id = :projectUserStoryId")
	public ProjectUserStory findProjectUserStoryById(final int projectUserStoryId);

	@Query("select pus from ProjectUserStory pus where pus.id = :projectUserStoryId and pus.project.manager.id = :managerId and pus.userStory.manager.id = :managerId")
	public ProjectUserStory findProjectUserStoryByIdAndManager(final int projectUserStoryId, final int managerId);

	@Query("select pus from ProjectUserStory pus where pus.id = :projectUserStoryId and pus.project.draftMode = true")
	public ProjectUserStory findProjectUserStoryByIdAndNotPublished(final int projectUserStoryId);

	@Query("select u from ProjectUserStory u where u.project.manager.id = :managerId and u.project.id = :projectId")
	public Collection<ProjectUserStory> findAllByManagerAndProject(int managerId, int projectId);

	@Query("select u from UserStory u where u.manager.id = :managerId and u.id NOT IN (select pu.userStory.id from ProjectUserStory pu where pu.project.id = :projectId and pu.project.manager.id = :managerId) ")
	public Collection<UserStory> findUserStoryToAdd(int managerId, int projectId);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId and pus.userStory.id = :userStoryId and pus.project.manager.id = :managerId and pus.userStory.manager.id = :managerId")
	public ProjectUserStory findByProjectAndUserStory(final int managerId, final int projectId, final int userStoryId);

	@Query("select pus from ProjectUserStory pus where pus.userStory.id = :userStoryId")
	public Collection<ProjectUserStory> findProjectUserStoryByUserStoryId(final int userStoryId);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId")
	public Collection<ProjectUserStory> findProjectUserStoryByProjectId(final int projectId);

}
