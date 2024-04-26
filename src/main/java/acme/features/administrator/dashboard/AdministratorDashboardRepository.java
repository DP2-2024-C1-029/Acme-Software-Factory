
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select r.probability from Risk r")
	Collection<Double> findAllValueOfRisk();

	@Query("select case when count(n) > 0 then count(n) else null end from Notice n where n.email is not null and n.link is not null")
	Double findTotalNoticesWithEmailAndLink();

	@Query("select case when count(n) > 0 then count(n) else null end from Notice n")
	Integer findTotalNotices();

	@Query("select case when count(o) > 0 then count(o) else null end from Objective o where o.isCritical = true")
	Double findTotalCriticalObjectives();

	@Query("select case when count(o) > 0 then count(o) else null end from Objective o where o.isCritical = false")
	Double findTotalNonCriticalObjectives();

	@Query("select case when count(o) > 0 then count(o) else null end from Objective o")
	Integer findTotalObjectives();

	@Query("select count(a) from Administrator a")
	Integer totalNumberOfPrincipalsWithAdministrator();

	@Query("select count(m) from Manager m")
	Integer totalNumberOfPrincipalsWithManager();

	@Query("select count(d) from Developer d")
	Integer totalNumberOfPrincipalsWithDeveloper();

	@Query("select count(s) from Sponsor s")
	Integer totalNumberOfPrincipalsWithSponsor();

	@Query("select count(a) from Auditor a")
	Integer totalNumberOfPrincipalsWithAuditor();

	@Query("select count(c) from Client c")
	Integer totalNumberOfPrincipalsWithClient();

}
