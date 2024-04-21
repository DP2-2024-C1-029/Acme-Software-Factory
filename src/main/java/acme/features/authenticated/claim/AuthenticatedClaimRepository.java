
package acme.features.authenticated.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claims.Claim;

@Repository
public interface AuthenticatedClaimRepository extends AbstractRepository {

	@Query("select c from Claim c")
	Collection<Claim> findAllClaims();

	@Query("select c from Claim c where c.id = :claimId")
	Claim findOneClaimById(final int claimId);
}
