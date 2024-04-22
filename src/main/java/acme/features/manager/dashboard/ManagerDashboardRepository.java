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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = 0")
	int totalUserStoryMust(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = 1")
	int totalUserStoryShould(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = 2")
	int totalUserStoryCould(int managerId);

	@Query("select count(u) from UserStory u where u.manager.id = :managerId and u.priority = 3")
	int totalUserStoryWont(int managerId);

	@Query("select avg(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	Double averageEstimatedCost(int managerId);

	@Query("select sqrt((sum(u.estimatedCost * u.estimatedCost) / count(u) - avg(u.estimatedCost) * avg(u.estimatedCost))) from UserStory u where u.manager.id = :managerId")
	Double deviationEstimatedCost(int managerId);

	@Query("select min(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	double minimumEstimatedCost(int managerId);

	@Query("select max(u.estimatedCost) from UserStory u where u.manager.id = :managerId")
	double maximumEstimatedCost(int managerId);

	@Query("select avg(p.cost.amount) from Project p where p.manager.id = :managerId")
	Double averageCostProject(int managerId);

	@Query("select sqrt((sum(p.cost.amount * p.cost.amount) / count(p) - avg(p.cost.amount) * avg(p.cost.amount))) from Project p where p.manager.id = :managerId")
	Double deviationCostProject(int managerId);

	@Query("select min(p.cost.amount) from Project p where p.manager.id = :managerId")
	double minimumCostProject(int managerId);

	@Query("select max(p.cost.amount) from Project p where p.manager.id = :managerId")
	double maximumCostProject(int managerId);
}
