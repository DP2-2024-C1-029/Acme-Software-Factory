/*
 * AuthenticatedConsumerRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.notice;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.notices.Notice;

@Repository
public interface AuthenticatedNoticeRepository extends AbstractRepository {

	@Query("select n from Notice n")
	Collection<Notice> findMany();

	@Query("SELECT n FROM Notice n WHERE n.instantiationMoment >= :oneMonthAgo")
	Collection<Notice> findRecentNotices(Date oneMonthAgo);

	@Query("select n from Notice n where n.id = :id")
	Notice findOneNoticeById(int id);

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findOneUserById(int id);

}
