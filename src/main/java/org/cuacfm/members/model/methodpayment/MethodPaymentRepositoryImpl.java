/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.methodpayment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class MethodPaymentRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class MethodPaymentRepositoryImpl implements MethodPaymentRepository {

	private static final Logger logger = LoggerFactory.getLogger(MethodPaymentRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public MethodPayment save(MethodPayment methodPayment) {
		entityManager.persist(methodPayment);
		return methodPayment;
	}

	@Override
	@Transactional
	public MethodPayment update(MethodPayment methodPayment) {
		return entityManager.merge(methodPayment);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		MethodPayment methodPayment = findById(id);
		if (methodPayment != null) {
			entityManager.remove(methodPayment);
		}
	}

	@Override
	public MethodPayment findById(Long id) {
		try {
			return entityManager.createQuery("select a from MethodPayment a where a.id = :id", MethodPayment.class).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public MethodPayment findByName(String name) {
		try {
			return entityManager.createQuery("select a from MethodPayment a where a.name = :name", MethodPayment.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<MethodPayment> getMethodPayments() {
		return entityManager.createQuery("select a from MethodPayment a", MethodPayment.class).getResultList();
	}
}
