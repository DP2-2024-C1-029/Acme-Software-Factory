
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select case when count(t) > 0 then count(t) else null end from TrainingModule t where t.updateMoment <> null and t.developer.id = :id and t.draftMode = false")
	Integer totalTrainingModuleWithUpdateMoment(int id);

	@Query("select case when count(t) > 0 then count(t) else null end from TrainingSession t where t.furtherInformationLink <> '' and t.furtherInformationLink is not null and t.trainingModule.developer.id = :id and t.draftMode = false")
	Integer totalNumberOfTrainingSessionsWithLink(int id);

	@Query("select t.estimatedTotalTime from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Collection<Integer> findAllTimeOfTrainingModule(int id);

}
