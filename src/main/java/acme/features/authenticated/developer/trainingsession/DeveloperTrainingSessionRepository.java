
package acme.features.authenticated.developer.trainingsession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingSession t where t.trainingModule.developer.id = :id")
	Collection<TrainingSession> findManyTrainingSessionByDeveloperId(int id);

	@Query("select t from TrainingSession t where t.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("SELECT DISTINCT s.trainingModule FROM TrainingSession s WHERE s.trainingModule.developer.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByDeveloperId(int id);

}
