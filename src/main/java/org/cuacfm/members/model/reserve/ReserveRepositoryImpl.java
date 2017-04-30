/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.reserve;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class ReserveRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ReserveRepositoryImpl implements ReserveRepository {

	private static final Logger logger = LoggerFactory.getLogger(ReserveRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Reserve save(Reserve reserve) {
		entityManager.persist(reserve);
		return reserve;
	}

	@Override
	@Transactional
	public Reserve update(Reserve reserve) {
		return entityManager.merge(reserve);
	}

	@Override
	@Transactional
	public void delete(Reserve reserve) {
		entityManager.remove(reserve);
	}

	@Override
	public Reserve findById(Long id) {
		try {
			return entityManager.createQuery("select r from Reserve r where r.id = :id", Reserve.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Reserve> getReserveList() {
		return entityManager.createQuery("select r from Reserve r order by r.dateCreate desc", Reserve.class).getResultList();
	}

	@Override
	public List<Reserve> getReserveListConflicts(Reserve reserve) {
		return entityManager
				.createQuery(
						"select r from Reserve r where r.element.id = :elementId and ((:dateStart between r.dateStart and r.dateEnd) or (:dateEnd between r.dateStart and r.dateEnd)) order by r.dateCreate desc",
						Reserve.class)
				.setParameter("elementId", reserve.getElement().getId()).setParameter("dateStart", reserve.getDateStart()).setParameter("dateEnd", reserve.getDateEnd()).getResultList();
	}

	@Override
	public List<Reserve> getReserveListActive() {
		return entityManager.createQuery("select r from Reserve r where r.active = true order by r.dateCreate desc", Reserve.class).getResultList();
	}

	@Override
	public List<Reserve> getReserveListClose() {
		return entityManager.createQuery("select r from Reserve r where r.active = false order by r.dateCreate desc", Reserve.class).getResultList();
	}

	@Override
	public List<Reserve> getReserveListByUser(Account account) {
		return entityManager.createQuery("select r from Reserve r where r.account.id = :accountId order by r.dateCreate desc", Reserve.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

	@Override
	public List<Reserve> getReserveListActiveByUser(Account account) {
		return entityManager
				.createQuery("select r from Reserve r where r.account.id = :accountId and r.active = true order by r.dateCreate desc", Reserve.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

}
