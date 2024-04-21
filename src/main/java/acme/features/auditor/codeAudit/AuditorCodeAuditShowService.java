
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.AuditType;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		Auditor auditor;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(auditor);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> auditRecords;
		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices types;
		Dataset dataset;

		types = SelectChoices.from(AuditType.class, object.getType());

		projects = this.repository.findManyProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		auditRecords = this.repository.findAllAuditRecordsByCodeAuditId(object.getId());

		EnumMap<Mark, Integer> modeMap = new EnumMap<>(Mark.class);

		for (AuditRecord record : auditRecords) {
			Mark mode = record.getMark();
			modeMap.put(mode, modeMap.getOrDefault(mode, 0) + 1);
		}

		Mark mode = null;
		int maxFrequency = 0;
		for (Map.Entry<Mark, Integer> entry : modeMap.entrySet())
			if (entry.getValue() == maxFrequency) { // si empata a la frecuencia máx
				if (mode != null && mode.ordinal() > entry.getKey().ordinal()) // nos quedamos con la de menor nota
					mode = entry.getKey();
			} else if (entry.getValue() > maxFrequency) { // si la frecuencia es mayor nos quedamos con esa
				maxFrequency = entry.getValue();
				mode = entry.getKey();
			}

		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "link", "draftMode");
		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);
		dataset.put("mark", mode);
		dataset.put("isPassing", mode != null && mode.compareTo(Mark.C) <= 0);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
