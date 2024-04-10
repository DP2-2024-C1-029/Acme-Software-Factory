
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findMany();

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("SELECT r from AuditRecord r where r.codeAudit.id = :id")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int id);

	@Query("SELECT a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("SELECT c from CodeAudit c where c.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("SELECT p from Project p")
	Collection<Project> findManyProjects();

	@Query("SELECT r from AuditRecord r where r.codeAudit.id = :id")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int id);

	@Query("SELECT c from CodeAudit c where c.auditor.id = :id")
	Collection<CodeAudit> findMine(int id);
}
