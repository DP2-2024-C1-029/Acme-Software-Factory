
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findMany();

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT r from AuditRecord r where r.codeAudit.id = :id")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int id);
}
