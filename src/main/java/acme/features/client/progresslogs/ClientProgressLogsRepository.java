
package acme.features.client.progresslogs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.Client;

@Repository
public interface ClientProgressLogsRepository extends AbstractRepository {

	@Query("select p from ProgressLogs p where p.contract.id = :id")
	Collection<ProgressLogs> findProgressLogsByContractId(int id);

	@Query("Select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select p from ProgressLogs p where p.id = :id")
	ProgressLogs findProgressLogById(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select c from Contract c")
	Collection<Contract> findContracts();

	@Query("select c from Client c where c.id = :id")
	Client findClientById(int id);

	@Query("select p from ProgressLogs p where p.recordId = :recordId")
	ProgressLogs findProgressLogByRecordId(String recordId);

}
