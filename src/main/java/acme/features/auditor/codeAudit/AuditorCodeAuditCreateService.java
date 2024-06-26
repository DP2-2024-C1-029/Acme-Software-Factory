
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeaudits.AuditType;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;

		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setExecutionDate(new Date(MomentHelper.getCurrentMoment().getTime() - 1));
		object.setDraftMode(true);
		object.setAuditor(auditor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null, "code", "auditor.codeAudit.form.error.duplicated");
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
		object.setId(0);
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

		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "link", "draftMode");
		dataset.put("type", types.getSelected().getKey());
		dataset.put("types", types);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
