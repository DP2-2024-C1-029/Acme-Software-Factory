
package acme.features.authenticated.developer.traniningmodule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByDeveloperId(int id);

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select t from TrainingModule t where t.code = :code")
	TrainingModule findOneJobByCode(String code);

	//@Query("select p from Project p where p.trainingmodule.id = :id")
	//Collection<Project> findManyProjectsByTrainingModuleId(int id); ME LA TENGO QUE TRAER DE LA TABLA

}
