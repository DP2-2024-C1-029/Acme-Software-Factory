
package acme.features.auditor.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.AuditType.STATIC and c.auditor.id = :id and c.draftMode = false")
	int totalStaticCodeAudits(int id);

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.codeaudits.AuditType.DYNAMIC and c.auditor.id = :id and c.draftMode = false")
	int totalDynamicCodeAudits(int id);

	@Query("select count(r) from AuditRecord r where r.draftMode = false and r.codeAudit.draftMode = false and r.codeAudit.auditor.id = :id group by r.codeAudit")
	List<Integer> findRecordCountsByAudit(int id);

	@Query("select r.startPeriod, r.endPeriod from AuditRecord r where r.draftMode = false and r.codeAudit.draftMode = false and r.codeAudit.auditor.id = :id")
	List<Object[]> findPeriods(int id);

}
