
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
import acme.entities.projects.ProjectUserStory;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId ")
	Collection<Project> findAllByManager(int managerId);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(final int projectId);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(final String code);

	@Query("select pu from ProjectUserStory pu where pu.project.id = :projectId and pu.userStory.draftMode = false")
	Collection<ProjectUserStory> findUserStoryByProjectPublished(final int projectId);

	@Query("select c from CodeAudit c where c.project.id = :projectId")
	Collection<CodeAudit> findCodeAuditByProject(final int projectId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findContractByProject(final int projectId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findSponsorshipByProject(final int projectId);

	@Query("select t from TrainingModule t where t.project.id = :projectId")
	Collection<TrainingModule> findTrainingModuleByProject(final int projectId);

	@Query("select ps from ProjectUserStory ps where ps.project.id = :projectId")
	Collection<ProjectUserStory> findProjectUserStoryByProject(final int projectId);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id IN :codeAuditsId")
	Collection<AuditRecord> findAuditRecordByCodeAuditsId(final List<Integer> codeAuditsId);

	@Query("select pl from ProgressLogs pl where pl.contract.id IN :contractsId")
	Collection<ProgressLogs> findProgressLogsByContractsId(final List<Integer> contractsId);

	@Query("select i from Invoice i where i.sponsorship.id IN :sponsoshipsId")
	Collection<Invoice> findInvoiceBySponsorshipsId(final List<Integer> sponsoshipsId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :trainingModulesId")
	Collection<TrainingSession> findTrainingSessionByTrainingModulesId(final List<Integer> trainingModulesId);
}
