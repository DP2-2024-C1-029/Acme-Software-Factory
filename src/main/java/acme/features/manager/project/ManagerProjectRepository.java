
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId ")
	Collection<Project> findAllByManager(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(final int projectId);

	@Query("select p from Project p where p.id = :projectId and p.manager.id = :managerId")
	Project findOneProjectByIdAndManagerId(final int projectId, final int managerId);

	@Query("select p from Project p where p.id = :projectId and p.draftMode = true")
	Project findOneProjectByIdAndNotPublished(final int projectId);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(final String code);

	@Query("SELECT CASE WHEN COUNT(pu) > 0 THEN true ELSE false END FROM ProjectUserStory pu WHERE pu.project.id = :projectId AND pu.userStory.draftMode = true")
	boolean containUserStoryNotPublished(final int projectId);

	@Query("SELECT CASE WHEN COUNT(pu) > 0 THEN true ELSE false END FROM ProjectUserStory pu WHERE pu.project.id = :projectId")
	boolean containUserStory(final int projectId);

	@Query("select c from CodeAudit c where c.project.id = :projectId")
	Collection<CodeAudit> findCodeAuditByProject(final int projectId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findContractByProject(final int projectId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findSponsorshipByProject(final int projectId);

	@Query("select t from TrainingModule t where t.project.id = :projectId")
	Collection<TrainingModule> findTrainingModuleByProject(final int projectId);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id IN :codeAuditsId")
	Collection<AuditRecord> findAuditRecordByCodeAuditsId(final List<Integer> codeAuditsId);

	@Query("select pl from ProgressLogs pl where pl.contract.id IN :contractsId")
	Collection<ProgressLogs> findProgressLogsByContractsId(final List<Integer> contractsId);

	@Query("select i from Invoice i where i.sponsorship.id IN :sponsoshipsId")
	Collection<Invoice> findInvoiceBySponsorshipsId(final List<Integer> sponsoshipsId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :trainingModulesId")
	Collection<TrainingSession> findTrainingSessionByTrainingModulesId(final List<Integer> trainingModulesId);
}
