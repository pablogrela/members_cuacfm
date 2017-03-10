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
package org.cuacfm.members.model.directdebit;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class ReturnReasonRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ReturnReasonRepositoryImpl implements ReturnReasonRepository {

	private static final Logger logger = LoggerFactory.getLogger(ReturnReasonRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public ReturnReason save(ReturnReason returnReason) {
		entityManager.persist(returnReason);
		return returnReason;
	}

	@Override
	@Transactional
	public ReturnReason update(ReturnReason returnReason) {
		return entityManager.merge(returnReason);
	}

	@Override
	@Transactional
	public void remove(ReturnReason returnReason) {
		entityManager.remove(returnReason);
	}

	@Override
	public ReturnReason findById(String id) {
		try {
			return entityManager.createQuery("select d from ReturnReason d where d.id = :id", ReturnReason.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnReason> findAll() {
		return entityManager.createQuery("select d from ReturnReason d order by d.id asc ", ReturnReason.class).getResultList();
	}
}
