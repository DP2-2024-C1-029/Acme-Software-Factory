
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
public class ClientProgressLogsListService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

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
		Collection<ProgressLogs> objects;
		int contractId;

		contractId = super.getRequest().getData("contractId", int.class);
		objects = this.repository.findProgressLogsByContractId(contractId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		String payload;
		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "draftMode");
		payload = String.format("%s", object.getResponsiblePerson());
		dataset.put("payload", payload);
		super.getResponse().addData(dataset);

	}

	@Override
	public void unbind(final Collection<ProgressLogs> objects) {
		assert objects != null;

		int contractId;

		contractId = super.getRequest().getData("contractId", int.class);

		super.getResponse().addGlobal("contractId", contractId);
	}

}
