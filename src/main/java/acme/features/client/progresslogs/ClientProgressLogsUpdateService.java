
package acme.features.client.progresslogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogsUpdateService extends AbstractService<Client, ProgressLogs> {

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

		status = progressLog != null && progressLog.isDraftMode() && super.getRequest().getPrincipal().hasRole(Client);

		super.getResponse().setAuthorised(status);
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
	public void bind(final ProgressLogs object) {
		assert object != null;

		super.bind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLogs object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLogs existing;
			existing = this.repository.findProgressLogByRecordId(object.getRecordId());
			if (existing != null)
				super.state(existing.getId() == object.getId(), "recordId", "client.progress-log.form.error.duplicatedRecordId");
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

		if (!super.getBuffer().getErrors().hasErrors("publishedProgressLog"))
			super.state(object.isDraftMode(), "*", "client.progress-log.form.error.published-progress-log");
	}

	@Override
	public void perform(final ProgressLogs object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");
		super.getResponse().addData(dataset);
	}
}
