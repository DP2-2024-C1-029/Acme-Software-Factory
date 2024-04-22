
package acme.features.client.progresslogs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogsShowService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int clientId;
		ProgressLogs ProgressLogs;

		id = super.getRequest().getData("id", int.class);
		ProgressLogs = this.repository.findProgressLogById(id);
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		status = clientId == ProgressLogs.getContract().getClient().getId() && ProgressLogs.getContract().getProject() != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLogs object;
		int ProgressLogsid;

		ProgressLogsid = super.getRequest().getData("id", int.class);
		object = this.repository.findProgressLogById(ProgressLogsid);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		Collection<Contract> contracts = this.repository.findContracts();
		choices = SelectChoices.from(contracts, "code", object.getContract());

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson", "draftMode");
		dataset.put("contract", choices.getSelected().getKey());
		dataset.put("contracts", choices);

		super.getResponse().addData(dataset);
	}
}
