
package acme.features.auditor.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.AuditType.STATIC and c.draftMode = false")
	int totalStaticCodeAudits();

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.AuditType.DYNAMIC and c.draftMode = false")
	int totalDynamicCodeAudits();

	@Query("select count(r) from AuditRecord r where r.draftMode = false group by r.codeAudit")
	List<Integer> findRecordCountsByAudit();

	@Query("select r.startPeriod, r.endPeriod from AuditRecord r where r.draftMode = false")
	List<Object[]> findPeriods();

}
