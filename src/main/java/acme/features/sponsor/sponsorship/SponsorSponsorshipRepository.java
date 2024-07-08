
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select count(s) = 0 from Sponsorship s where s.code = :code")
	boolean notExistsDuplicatedCodeLike(String code);

	@Query("select count(s) = 0 from Sponsorship s where s.code = :code and s.id <> :id")
	boolean notExistsDuplicatedCodeExceptThisByIdLike(String code, int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	Collection<Sponsorship> findManySponsorshipsBySponsorId(int id);

	@Query("select distinct p from Project p where p.draftMode = false")
	Collection<Project> findManyProjects();

	@Query("select count(p) > 0 from Project p where p.draftMode = false and p.id = :id")
	boolean existsValidProject(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesBySponsorshipId(int id);

	@Query("select count(i) > 0 from Invoice i where i.sponsorship.id = :id")
	boolean existsInvoicesOfSponsorship(int id);

	@Query("select count(i) > 0 from Invoice i where i.sponsorship.id = :id and i.isPublished = true")
	boolean existsInvoicesPublishedOfSponsorship(int id);

	@Query("select count(i) = 0 from Invoice i where i.sponsorship.id = :id and i.isPublished = false")
	boolean notAllInvoicesArePublishedOfSponsorship(int id);

	@Query("select s.amount.currency = :currency from Sponsorship s where s.id = :id")
	Boolean existsSponsorshipByIdWithItsCurrencyLike(int id, String currency);

	@Query("select count(c) > 0 from Configuration c where c.acceptedCurrencies like concat('%',:currency,'%') ")
	boolean isAmongAcceptedCurrencies(String currency);

	@Query("select round(sum(i.quantity.amount * (100.0 + i.tax) / 100), 2) from Invoice i where i.sponsorship.id = :id and i.isPublished = true")
	Double sumQuantityOfInvoicesBySponsorshipId(int id);

	@Query("select distinct i.quantity.currency from Invoice i where i.sponsorship.id = :id")
	String findCurrencyOfInvoicesBySponsorshipId(int id);
}
