
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClientId(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findContractByCode(String code);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p")
	Collection<Project> findProjects();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findPublishedProjects();

	@Query("select p from ProgressLogs p where p.contract.id = :id")
	Collection<ProgressLogs> findManyProgressLogsByContractId(int id);

	@Query("select p from ProgressLogs p where p.contract.id = :id and p.draftMode = true")
	Collection<ProgressLogs> findManyProgressLogsNotPublishedByContractId(int id);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findContractsByProjectId(int projectId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("SELECT pl FROM ProgressLogs pl WHERE pl.contract.id = :id AND pl.registrationMoment = (SELECT MIN(pl2.registrationMoment) FROM ProgressLogs pl2 WHERE pl2.contract.id = :id)")
	ProgressLogs findProgressLogEarliestRegistrationMomentByContractId(int id);

	default double currencyTransformerUsd(final Money initial) {
		double res = initial.getAmount();

		if (initial.getCurrency().equals("USD"))
			res = initial.getAmount();

		else if (initial.getCurrency().equals("EUR"))
			res = initial.getAmount() * 1.07;

		else
			res = initial.getAmount() * 1.25;

		return res;
	}

}
