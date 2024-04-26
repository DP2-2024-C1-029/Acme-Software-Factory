/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = acme.entities.userstories.Priority.MUST and u.draftMode = false")
	int totalUserStoryMust(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = acme.entities.userstories.Priority.SHOULD and u.draftMode = false")
	int totalUserStoryShould(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = acme.entities.userstories.Priority.COULD and u.draftMode = false")
	int totalUserStoryCould(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = acme.entities.userstories.Priority.WONT and u.draftMode = false")
	int totalUserStoryWont(int managerId);

	@Query("select p.cost from Project p where p.manager.id=:managerId and p.draftMode = false")
	Collection<Money> getAllMoneyProjectCost(final int managerId);

	@Query("select u.estimatedCost from UserStory u where u.manager.id = :managerId and u.draftMode = false")
	Collection<Double> findAllEstimatedCost(int managerId);
}
