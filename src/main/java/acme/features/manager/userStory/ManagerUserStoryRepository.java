
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.userstories.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select u from UserStory u where u.manager.id = :managerId ")
	public Collection<UserStory> findAllByManager(int managerId);

	@Query("select u from UserStory u where u.id = :userStoryId")
	UserStory findOneUserStoryById(final int userStoryId);

	@Query("select u from UserStory u where u.id = :userStoryId and u.manager.id = :managerId")
	UserStory findOneUserStoryByIdAndManagerId(final int userStoryId, final int managerId);

	@Query("select u from UserStory u where u.id = :userStoryId and u.draftMode = true")
	UserStory findOneUserStoryByIdAndNotPublished(final int userStoryId);
}
