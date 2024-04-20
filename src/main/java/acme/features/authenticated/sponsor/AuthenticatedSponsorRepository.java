
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Sponsor;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select s from Sponsor s where s.userAccount.id = :id")
	Sponsor findOneSponsorByUserAccountId(int id);
}
