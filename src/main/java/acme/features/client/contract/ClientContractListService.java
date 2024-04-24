
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.roles.Client;

@Service
public class ClientContractListService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> objects;
		int clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findContractsByClientId(clientId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;
		String code = this.repository.findProjectById(object.getProject().getId()).getCode();

		dataset = super.unbind(object, "code", "project", "draftMode", "providerName", "customerName", "instantiationMoment", "budget", "goals");
		dataset.put("draftMode", object.isDraftMode() ? "❌" : "✔️");
		dataset.put("project", code);
		super.getResponse().addData(dataset);

	}

}
