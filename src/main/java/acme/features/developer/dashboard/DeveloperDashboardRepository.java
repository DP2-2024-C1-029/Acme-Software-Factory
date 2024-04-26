
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(t) from TrainingModule t where t.updateMoment <> null and t.developer.id = :id and t.draftMode = false")
	Integer totalTrainingModuleWithUpdateMoment(int id);

	@Query("select count(t) from TrainingSession t where t.furtherInformationLink <> '' and t.furtherInformationLink is not null and t.trainingModule.developer.id = :id and t.draftMode = false")
	Integer totalNumberOfTrainingSessionsWithLink(int id);

	@Query("select t.estimatedTotalTime from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Collection<Integer> findAllTimeOfTrainingModule(int id);

}
