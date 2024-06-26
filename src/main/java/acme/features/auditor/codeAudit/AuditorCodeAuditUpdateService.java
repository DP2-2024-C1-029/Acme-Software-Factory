
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.AuditType;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditUpdateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;
		Auditor auditor;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

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
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
		object.setAuditor(auditor);

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.codeAudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("executionDate")) {
			Date minimunMoment = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");

			super.state(MomentHelper.isPresentOrPast(object.getExecutionDate()), "executionDate", "auditor.codeAudit.form.error.too-close");
			super.state(MomentHelper.isAfterOrEqual(object.getExecutionDate(), minimunMoment), "executionDate", "auditor.codeAudit.form.error.too-early");
		}

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isDraftMode(), "project", "auditor.codeAudit.form.error.drafted-project");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices types;
		Dataset dataset;

		types = SelectChoices.from(AuditType.class, object.getType());

		projects = this.repository.findManyPublishedProjects();
		choices = SelectChoices.from(projects, "title", object.getProject());

		// CÁLCULO DE LA MARK MEDIANTE LA MODA

		Collection<AuditRecord> auditRecords;
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
				if (mode != null && mode.ordinal() < entry.getKey().ordinal()) // nos quedamos con la de menor nota
					mode = entry.getKey();
			} else if (entry.getValue() > maxFrequency) { // si la frecuencia es mayor nos quedamos con esa
				maxFrequency = entry.getValue();
				mode = entry.getKey();
			}

		// ----

		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "link", "draftMode");
		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);
		dataset.put("mark", mode);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
