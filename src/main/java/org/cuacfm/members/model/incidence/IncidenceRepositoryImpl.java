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
package org.cuacfm.members.model.incidence;

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

/** The Class IncidenceRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class IncidenceRepositoryImpl implements IncidenceRepository {

	private static final Logger logger = LoggerFactory.getLogger(IncidenceRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Incidence save(Incidence incidence) {
		entityManager.persist(incidence);
		return incidence;
	}

	@Override
	@Transactional
	public Incidence update(Incidence incidence) {
		return entityManager.merge(incidence);
	}

	@Override
	@Transactional
	public void delete(Incidence incidence) {
		entityManager.remove(incidence);
	}

	@Override
	public Incidence findById(Long id) {
		try {
			return entityManager.createQuery("select a from Incidence a where a.id = :id", Incidence.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public Incidence findByName(String name) {
		try {
			return entityManager.createQuery("select a from Incidence a where a.name = :name", Incidence.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Incidence> getIncidenceList() {
		return entityManager.createQuery("select p from Incidence p order by p.name", Incidence.class).getResultList();
	}

	@Override
	public List<Incidence> getIncidenceListActive() {
		return entityManager.createQuery("select p from Incidence p where p.active = true order by p.dateCreate desc", Incidence.class)
				.getResultList();
	}

	@Override
	public List<Incidence> getIncidenceListByUser(Account account) {
		return entityManager.createQuery("select p from Incidence p where p.account.id = :accountId order by p.dateCreate desc", Incidence.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

	@Override
	public List<Incidence> getIncidenceListClose() {
		return entityManager.createQuery("select p from Incidence p where p.active = false order by p.dateCreate desc", Incidence.class)
				.getResultList();
	}
}
