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
package org.cuacfm.members.model.accounttype;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class AccountTypeRepositoryImpl implements AccountTypeRepository {

	private static final Logger logger = LoggerFactory.getLogger(AccountTypeRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public AccountType save(AccountType accountType) {
		entityManager.persist(accountType);
		return accountType;
	}

	@Override
	@Transactional
	public AccountType update(AccountType accountType) {
		return entityManager.merge(accountType);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		AccountType accountType = findById(id);
		if (accountType != null) {
			entityManager.remove(accountType);
		}
	}

	@Override
	public AccountType findById(Long id) {
		try {
			return entityManager.createQuery("select a from AccountType a where a.id = :id", AccountType.class).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public AccountType findByName(String name) {
		try {
			return entityManager.createQuery("select a from AccountType a where a.name = :name", AccountType.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AccountType> getAccountTypes() {
		return entityManager.createQuery("select a from AccountType a", AccountType.class).getResultList();
	}
}
