
package acme.features.developer.trainingmodule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainingmodules.TrainingModule;
import acme.entities.trainingsessions.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByDeveloperId(int id);

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select distinct p from Project p where p.draftMode = false")
	Collection<Project> findManyProjects();

	@Query("select p from Project p where p.id = :id and p.draftMode = false")
	Project findOneProjectById(int id);

	@Query("select t from TrainingSession t where t.trainingModule.id = :id")
	Collection<TrainingSession> findManyTrainingSessionsByTrainingModuleId(int id);

	@Query("SELECT t FROM TrainingModule t")
	Collection<TrainingModule> findAllTrainingModules();

	@Query("select t from TrainingModule t where t.draftMode = false")
	Collection<TrainingModule> findManyTrainingModulePublished();

}
