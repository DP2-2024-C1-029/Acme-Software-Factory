
package acme.features.client.progresslogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogsDeleteService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public ClientProgressLogsRepository repository;

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
	public void load() {
		ProgressLogs progressLogs;
		int progressLogsId;

		progressLogsId = super.getRequest().getData("id", int.class);
		progressLogs = this.repository.findProgressLogById(progressLogsId);

		super.getBuffer().addData(progressLogs);
	}

	@Override
	public void bind(final ProgressLogs object) {
		assert object != null;

		super.bind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLogs object) {
		assert object != null;
	}

	@Override
	public void perform(final ProgressLogs object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
		super.getResponse().addData(dataset);
	}
}
