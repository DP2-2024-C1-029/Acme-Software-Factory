
package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService<Auditor, AuditRecord> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		CodeAudit codeAudit;
		AuditRecord auditRecord;
		Auditor auditor;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(auditRecordId);
		codeAudit = this.repository.findOneCodeAuditByAuditRecordId(auditRecordId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && auditRecord.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "mark", "link");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.auditRecord.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimunMoment = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");

			super.state(MomentHelper.isPresentOrPast(object.getStartPeriod()), "startPeriod", "auditor.auditRecord.form.error.not-past");
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minimunMoment), "startPeriod", "auditor.auditRecord.form.error.too-early");
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), object.getCodeAudit().getExecutionDate()), "startPeriod", "auditor.auditRecord.form.error.before-audit");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minimunMoment = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");

			super.state(MomentHelper.isPresentOrPast(object.getEndPeriod()), "endPeriod", "auditor.auditRecord.form.error.not-past");
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minimunMoment), "endPeriod", "auditor.auditRecord.form.error.too-early");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriod") && !super.getBuffer().getErrors().hasErrors("endPeriod"))
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "auditor.auditRecord.form.error.end-after-start");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod") && !super.getBuffer().getErrors().hasErrors("endPeriod"))
			super.state(MomentHelper.isLongEnough(object.getStartPeriod(), object.getEndPeriod(), 1, ChronoUnit.HOURS), "endPeriod", "auditor.auditRecord.form.error.too-short");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;
		Dataset dataset;

		marks = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "link", "draftMode");
		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);

		super.getResponse().addData(dataset);
	}
}
