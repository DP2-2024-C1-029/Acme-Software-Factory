
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i where i.tax <= 21.0 and i.sponsorship.sponsor.id = :id and i.isPublished = true")
	Integer totalInvoicesWithTaxLowerTo21(int id);

	@Query("select count(s) from Sponsorship s where s.link <> null and s.sponsor.id = :id and s.isPublished = true")
	Integer totalSponsorshipsWithLink(int id);

	@Query("select s.amount from Sponsorship s where s.sponsor.id = :id and s.isPublished = true")
	Collection<Money> findAllAmountsOfSponsorships(int id);

	@Query("select i.quantity from Invoice i where i.sponsorship.sponsor.id = :id and i.isPublished = true")
	Collection<Money> findAllQuantitiesOfInvoices(int id);
	//
	//	@Query("select avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.isPublished = true")
	//	Double averageAmountSponsorships(int id);
	//
	//	//	Double deviationAmountSponsorships;
	//
	//	@Query("select min(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.isPublished = true")
	//	Double minimumAmountSponsorships(int id);
	//
	//	@Query("select max(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.isPublished = true")
	//	Double maximumAmountSponsorships(int id);
	//
	//	@Query("select avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.isPublished = true")
	//	Double averageQuantityInvoices(int id);
	//
	//	//	Double deviationQuantityInvoices;
	//
	//	@Query("select min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.isPublished = true")
	//	Double minimumQuantityInvoices(int id);
	//
	//	@Query("select max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.isPublished = true")
	//	Double maximumQuantityInvoices(int id);

}
