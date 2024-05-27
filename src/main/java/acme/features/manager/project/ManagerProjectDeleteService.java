
package acme.features.manager.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
import acme.features.manager.projectUserStory.ManagerProjectUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository			repository;

	@Autowired
	private ManagerProjectUserStoryRepository	managerProjectUserStoryRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findOneProjectByIdAndNotPublished(projectId);
		Manager manager = project == null ? null : project.getManager();
		boolean status = manager != null && super.getRequest().getPrincipal().hasRole(manager) && project != null && project.getManager().getId() == manager.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Project object = this.repository.findOneProjectByIdAndNotPublished(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractText", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(object.isDraftMode(), "published", "manager.project.form.error.detele.published");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		Collection<CodeAudit> codeAudits = this.repository.findCodeAuditByProject(object.getId());
		Collection<Contract> contracts = this.repository.findContractByProject(object.getId());
		Collection<Sponsorship> sponsorships = this.repository.findSponsorshipByProject(object.getId());
		Collection<TrainingModule> trainingModules = this.repository.findTrainingModuleByProject(object.getId());
		Collection<ProjectUserStory> projectUserStory = this.managerProjectUserStoryRepository.findProjectUserStoryByProjectId(object.getId());
		if (codeAudits != null && !codeAudits.isEmpty()) {
			List<Integer> codeAuditsId = new ArrayList<>();
			for (CodeAudit ca : codeAudits)
				codeAuditsId.add(ca.getId());

			Collection<AuditRecord> auditRecords = this.repository.findAuditRecordByCodeAuditsId(codeAuditsId);
			if (auditRecords != null && !auditRecords.isEmpty())
				this.repository.deleteAll(auditRecords);

			this.repository.deleteAll(codeAudits);
		}

		if (contracts != null && !contracts.isEmpty()) {
			List<Integer> contractsId = new ArrayList<>();
			for (Contract c : contracts)
				contractsId.add(c.getId());

			Collection<ProgressLogs> progressLogs = this.repository.findProgressLogsByContractsId(contractsId);
			if (progressLogs != null && !progressLogs.isEmpty())
				this.repository.deleteAll(progressLogs);
			this.repository.deleteAll(contracts);
		}

		if (sponsorships != null && !sponsorships.isEmpty()) {
			List<Integer> sponsorshipsId = new ArrayList<>();
			for (Sponsorship s : sponsorships)
				sponsorshipsId.add(s.getId());

			Collection<Invoice> invoices = this.repository.findInvoiceBySponsorshipsId(sponsorshipsId);
			if (invoices != null && !invoices.isEmpty())
				this.repository.deleteAll(invoices);
			this.repository.deleteAll(sponsorships);
		}

		if (trainingModules != null && !trainingModules.isEmpty()) {
			List<Integer> trainingModulesId = new ArrayList<>();
			for (TrainingModule tm : trainingModules)
				trainingModulesId.add(tm.getId());

			Collection<TrainingSession> trainingSessions = this.repository.findTrainingSessionByTrainingModulesId(trainingModulesId);
			if (trainingSessions != null && !trainingSessions.isEmpty())
				this.repository.deleteAll(trainingSessions);
			this.repository.deleteAll(trainingModules);
		}

		if (projectUserStory != null && !projectUserStory.isEmpty())
			this.repository.deleteAll(projectUserStory);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstractText", "indication", "cost", "link");
		dataset.put("published", !object.isDraftMode());

		super.getResponse().addData(dataset);
	}
}
