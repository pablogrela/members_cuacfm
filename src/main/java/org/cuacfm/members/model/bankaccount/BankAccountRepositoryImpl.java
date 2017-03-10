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
package org.cuacfm.members.model.bankaccount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankAccountRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class BankAccountRepositoryImpl implements BankAccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public BankAccount save(BankAccount bankAccount) {
		entityManager.createQuery("update from BankAccount b set b.active = false where b.account.id = :accountId")
				.setParameter("accountId", bankAccount.getAccount().getId()).executeUpdate();

		entityManager.persist(bankAccount);
		return bankAccount;
	}

	@Override
	public BankAccount activeBankAccountByAccountId(Long accountId) {
		return entityManager.createQuery("select b from BankAccount b where b.account.id = :accountId and b.active = true", BankAccount.class)
				.setParameter("accountId", accountId).getSingleResult();
	}
}
