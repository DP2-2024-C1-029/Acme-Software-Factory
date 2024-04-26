
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT r from AuditRecord r where r.codeAudit.id = :id")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int id);

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("SELECT r from AuditRecord r where r.codeAudit.id = :id")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int id);

	@Query("SELECT a.codeAudit from AuditRecord a where a.id = :id")
	CodeAudit findOneCodeAuditByAuditRecordId(int id);

	@Query("SELECT a from AuditRecord a where a.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);
}
