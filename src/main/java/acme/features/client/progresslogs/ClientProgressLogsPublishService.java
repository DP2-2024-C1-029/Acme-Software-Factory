
package acme.features.client.progresslogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogsPublishService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		ProgressLogs progressLog;
		Client Client;

		progressLogId = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findProgressLogById(progressLogId);
		Client = progressLog == null ? null : progressLog.getContract().getClient();

		status = progressLog != null && super.getRequest().getPrincipal().hasRole(Client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final ProgressLogs progressLog) {
		assert progressLog != null;

		super.bind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLogs progressLog) {
		assert progressLog != null;

		//Contract referencedContract = progressLog.getContract();
		//super.state(referencedContract != null, "*", "client.progressLog.form.error.invalidContract");

		//		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
		//
		//			ProgressLog progressLogWithCode = this.repository.findProgressLogByRecordId(progressLog.getRecordId());
		//
		//			super.state(progressLogWithCode == null, "recordId", "client.progress-log.form.error.recordId");
		//		}

	}

	@Override
	public void load() {

		ProgressLogs progressLog;
		int progressLogId;

		progressLogId = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findProgressLogById(progressLogId);

		super.getBuffer().addData(progressLog);
	}

	@Override
	public void perform(final ProgressLogs progressLog) {
		assert progressLog != null;

		progressLog.setDraftMode(false);
		this.repository.save(progressLog);
	}

	@Override
	public void unbind(final ProgressLogs progressLog) {
		assert progressLog != null;

		Dataset dataset;

		dataset = super.unbind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");

		super.getResponse().addData(dataset);
	}
}
