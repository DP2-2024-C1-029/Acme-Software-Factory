
package acme.features.administrator.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.objectives.Objective;

public interface AdministratorObjectiveRepository extends AbstractRepository {

	@Query("select o from Objective o where o.id = :id")
	Objective findOneObjectiveById(int id);

	@Query("select o from Objective o")
	Collection<Objective> findManyObjectives();

}
