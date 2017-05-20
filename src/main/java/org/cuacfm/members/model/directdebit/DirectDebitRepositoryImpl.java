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
package org.cuacfm.members.model.directdebit;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class DirectDebitRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class DirectDebitRepositoryImpl implements DirectDebitRepository {

	private static final Logger logger = LoggerFactory.getLogger(DirectDebitRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public DirectDebit save(DirectDebit directDebit) {
		entityManager.persist(directDebit);
		return directDebit;
	}

	@Override
	@Transactional
	public DirectDebit update(DirectDebit directDebit) {
		return entityManager.merge(directDebit);
	}

	@Override
	@Transactional
	public void remove(DirectDebit directDebit) {
		try {
			entityManager.remove(directDebit);
		} catch (Exception e) {
			logger.error("save: ", e);
		}
	}

	@Override
	public DirectDebit findById(String id) {
		try {
			return entityManager.createQuery("select d from DirectDebit d where d.id = :id", DirectDebit.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public String findLastId() {
		try {
			String id = entityManager.createQuery("select MAX(d.id) from DirectDebit d", String.class).getSingleResult();
			if (id != null) {
				return id;
			} else {
				return "_0";
			}
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return "_0";
		}
	}

	@Override
	public List<DirectDebit> findAll() {
		return entityManager.createQuery("select d from DirectDebit d order by d.id asc ", DirectDebit.class).getResultList();
	}

	@Override
	public List<DirectDebit> findAllOpen() {
		return entityManager
				.createQuery("select d from DirectDebit d where d.state = :state1 or d.state = :state2 or d.state = :state3 order by d.id desc",
						DirectDebit.class)
				.setParameter("state1", states.NO_PAY).setParameter("state2", states.MANAGEMENT).setParameter("state3", states.RETURN_BILL)
				.getResultList();
	}

	@Override
	public List<DirectDebit> findAllClose() {
		return entityManager
				.createQuery("select d from DirectDebit d where d.state = :state1 or d.state = :state2 order by d.id desc", DirectDebit.class)
				.setParameter("state1", states.PAY).setParameter("state2", states.CANCEL).getResultList();
	}

	@Override
	public List<DirectDebit> findAllByAccountId(Long accountId) {
		return entityManager.createQuery("select d from DirectDebit d where d.account.id = :accountId order by d.id desc", DirectDebit.class)
				.setParameter("accountId", accountId).getResultList();
	}

	@Override
	public List<DirectDebit> findAllOpenByAccountId(Long accountId) {
		return entityManager
				.createQuery(
						"select d from DirectDebit d where d.account.id = :accountId and (d.state != :state1 or d.state != :state2) order by d.id desc",
						DirectDebit.class)
				.setParameter("accountId", accountId).setParameter("state1", states.PAY).setParameter("state2", states.CANCEL).getResultList();
	}

	@Override
	public DirectDebit getLastDirectDebit(Long accountId) {
		try {
			return entityManager.createQuery("select d from DirectDebit d where d.account.id = :accountId and d.state = :state", DirectDebit.class)
					.setParameter("accountId", accountId).setParameter("state", states.NO_PAY).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public String isRcurOrFRST(Long accountId) {
		// Si tienes pagos ese usario y son con remesada bancara y ya han sido cobrados
		List<DirectDebit> directDebits = entityManager
				.createQuery("select d from DirectDebit d where d.account.id = :id and d.state = :state and d.method = :method", DirectDebit.class)
				.setParameter("id", accountId).setParameter("state", states.PAY).setParameter("method", methods.DIRECTDEBIT).getResultList();
		if (directDebits.isEmpty()) {
			return "FRST";
		}
		return "RCUR";
	}

	@Override
	public List<DirectDebit> findAllByBankRemittanceId(Long bankRemittanceId) {
		return entityManager.createQuery("select d from DirectDebit d where d.bankRemittance.id = :bankRemittanceId", DirectDebit.class)
				.setParameter("bankRemittanceId", bankRemittanceId).getResultList();
	}

	@Override
	public DirectDebit findByIdTxn(String idTxn) {
		try {
			return entityManager.createQuery("select d from DirectDebit d where d.idTxn = :idTxn", DirectDebit.class).setParameter("idTxn", idTxn)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}
}
