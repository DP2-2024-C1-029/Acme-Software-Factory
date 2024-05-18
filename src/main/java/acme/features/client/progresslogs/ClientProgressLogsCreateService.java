
package acme.features.client.progresslogs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogsCreateService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int Id;
		Contract Contract;
		Client Client;

		Id = super.getRequest().getData("contractId", int.class);
		Contract = this.repository.findContractById(Id);
		Client = Contract == null ? null : Contract.getClient();
		status = Contract != null && super.getRequest().getPrincipal().hasRole(Client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Contract contract;
		Integer contractId;
		ProgressLogs progressLog;

		contractId = super.getRequest().getData("contractId", int.class);
		contract = this.repository.findContractById(contractId);
		progressLog = new ProgressLogs();
		progressLog.setContract(contract);
		progressLog.setDraftMode(true);
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
				super.state(existing.getId() == object.getId(), "recordId", "client.progress-log.form.error.duplicated-record-id");
		}
		if (!super.getBuffer().getErrors().hasErrors("registrationMoment"))
			super.state(object.getRegistrationMoment().after(object.getContract().getInstantiationMoment()), "registrationMoment", "client.progress-log.form.error.registration-moment-must-be-later");

		if (!super.getBuffer().getErrors().hasErrors("publishedContract")) {
			Integer contractId;
			Contract contract;

			contractId = super.getRequest().getData("contractId", int.class);
			contract = this.repository.findContractById(contractId);

			super.state(!contract.isDraftMode(), "*", "client.progress-log.form.error.published-contract");
		}

		if (!super.getBuffer().getErrors().hasErrors("completeness")) {

			Double maxCompleteness = this.repository.findMaxCompletnessProgressLog(object.getContract().getId());

			if (maxCompleteness != null)
				super.state(maxCompleteness < object.getCompleteness(), "completeness", "client.progress-log.form.error.completeness");

		}

		if (!super.getBuffer().getErrors().hasErrors("registrationMoment")) {

			Collection<ProgressLogs> sameDate = this.repository.findProgressLogsByContractIdDate(object.getContract().getId(), object.getId(), object.getRegistrationMoment());
			super.state(sameDate.isEmpty(), "registrationMoment", "client.progress-log.form.error.same-moment");
		}
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

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
		dataset.put("contractId", super.getRequest().getData("contractId", int.class));
		super.getResponse().addData(dataset);
	}
}
