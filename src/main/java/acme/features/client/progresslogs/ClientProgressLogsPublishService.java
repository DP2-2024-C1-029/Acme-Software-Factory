
package acme.features.client.progresslogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
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

		int progressLogId;
		ProgressLogs progressLog;
		int clientId;
		boolean isValid;

		progressLogId = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findProgressLogById(progressLogId);
		clientId = super.getRequest().getPrincipal().getActiveRoleId();

		isValid = clientId == progressLog.getContract().getClient().getId() && progressLog.isDraftMode() == true;

		super.getResponse().setAuthorised(isValid);
	}

	@Override
	public void bind(final ProgressLogs progressLog) {
		assert progressLog != null;

		super.bind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLogs object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLogs existing;
			existing = this.repository.findProgressLogByRecordId(object.getRecordId());
			super.state(existing.getId() == object.getId(), "recordId", "client.contract.form.error.duplicatedRecordId");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationMoment"))
			super.state(object.getRegistrationMoment().after(object.getContract().getInstantiationMoment()), "registrationMoment", "client.progress-log.form.error.registration-moment-must-be-later");

		if (!super.getBuffer().getErrors().hasErrors("publishedContract")) {
			Integer contractId;
			Contract contract;
			int progressLogId;

			progressLogId = super.getRequest().getData("id", int.class);
			contractId = this.repository.findProgressLogById(progressLogId).getContract().getId();
			contract = this.repository.findContractById(contractId);

			super.state(contract.isDraftMode(), "*", "client.progress-log.form.error.published-contract");
		}
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

		dataset = super.unbind(progressLog, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");

		super.getResponse().addData(dataset);
	}
}
