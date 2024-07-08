
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select i from Invoice i where i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("select i.sponsorship from Invoice i where i.id = :id")
	Sponsorship findOneSponsorshipByInvoiceId(int id);

	@Query("select count(i) = 0 from Invoice i where i.code = :code")
	boolean notExistsDuplicatedCodeLike(String code);

	@Query("select count(i) = 0 from Invoice i where i.code = :code and i.id <> :id")
	boolean notExistsDuplicatedCodeExceptThisIdLike(String code, int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findManyInvoicesByMasterId(int id);

	@Query("select sum(i.quantity.amount * (100.0 + i.tax) / 100) from Invoice i where i.isPublished = true and i.sponsorship.id = :idSponsorship")
	Double sumOfQuantitiesOfInvoicesOfSponsorship(int idSponsorship);

	@Query("select count(c) > 0 from Configuration c where c.acceptedCurrencies like concat('%',:currency,'%')")
	boolean isAmongAcceptedCurrencies(String currency);
}
