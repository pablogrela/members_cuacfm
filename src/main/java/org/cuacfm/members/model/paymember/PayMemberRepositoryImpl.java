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
package org.cuacfm.members.model.paymember;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.cuacfm.members.model.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class PayMemberRepositoryImpl implements PayMemberRepository {

	private static final Logger logger = LoggerFactory.getLogger(PayMemberRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public PayMember save(PayMember payMember) {
		entityManager.persist(payMember);
		return payMember;
	}

	@Override
	@Transactional
	public PayMember update(PayMember payMember) {
		return entityManager.merge(payMember);
	}

	@Override
	public PayMember findById(Long id) {
		try {
			return entityManager.createQuery("select a from PayMember a where a.id = :id", PayMember.class).setParameter("id", id).getSingleResult();
		} catch (PersistenceException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public PayMember findByIdTxn(String idTxn) {
		try {
			return entityManager.createQuery("select a from PayMember a where a.idTxn = :idTxn", PayMember.class).setParameter("idTxn", idTxn)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId) {
		return entityManager
				.createQuery("select a from PayMember a where a.account.id = :accountId and a.feeMember.id = :feeMemberId", PayMember.class)
				.setParameter("accountId", accountId).setParameter("feeMemberId", feeMemberId).getResultList();
	}

	@Override
	public List<PayMember> getPayMemberList() {
		return entityManager.createQuery("select a from PayMember a order by a.account.login", PayMember.class).getResultList();
	}

	@Override
	public List<PayMember> findNoPayListByAccountId(Long accountId) {
		return entityManager.createQuery("select p from PayMember p where p.state <> 'PAY' and p.dateCharge <= :date and p.account.id = :accountId",
				PayMember.class).setParameter("date", new Date()).setParameter("accountId", accountId).getResultList();
	}

	@Override
	public Map<Account, List<PayMember>> getPayMemberNoPayListByDirectDebit(Date monthCharge) {

		TypedQuery<Object[]> q = entityManager.createQuery(
				"select p.account, p from PayMember p where p.state <> 'PAY' " + "and p.account.methodPayment.directDebit = true "
						+ "and month(p.dateCharge) = month(:month) " + "and year(p.dateCharge) = year(:month) " + "order by p.account.name",
				Object[].class).setParameter("month", monthCharge);

		List<Object[]> resultList = q.getResultList();
		Map<Account, List<PayMember>> userPayMembers = new HashMap<Account, List<PayMember>>();

		for (Object[] result : resultList) {
			Account account = (Account) result[0];

			// If exist Account in userPayMembers
			if (userPayMembers.containsKey(account)) {
				List<PayMember> payMembersAux = userPayMembers.get(account);
				payMembersAux.add((PayMember) result[1]);
				userPayMembers.putIfAbsent(account, payMembersAux);
			}
			// If no exist Account in userPayPrograms
			else {
				List<PayMember> payMembers = new ArrayList<PayMember>();
				payMembers.add((PayMember) result[1]);
				userPayMembers.put(account, payMembers);
			}
		}
		return userPayMembers;
	}

	@Override
	public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId) {
		return entityManager.createQuery("select a from PayMember a where a.feeMember.id = :feeMemberId", PayMember.class)
				.setParameter("feeMemberId", feeMemberId).getResultList();
	}

	@Override
	public List<PayMember> getPayMemberListByAccountId(Long accountId) {
		return entityManager.createQuery("select a from PayMember a where a.account.id = :accountId", PayMember.class)
				.setParameter("accountId", accountId).getResultList();
	}

	@Override
	public List<String> getUsernamesByFeeMember(Long feeMemberId) {
		// No running Concat(a.name, ' - ', a.nickname)
		List<Account> accounts = entityManager
				.createQuery("select a from Account a " + "where a.role in ('ROLE_USER', 'ROLE_PREREGISTERED')"
						+ "and a.active = true " + "and a.id not in " + "(select c.id from Account c, PayMember p "
						+ "where p.feeMember.id = :feeMemberId and p.account.id = c.id) " + "order by a.login", Account.class)
				.setParameter("feeMemberId", feeMemberId).getResultList();

		List<String> usernames = new ArrayList<>();
		for (Account account : accounts) {
			if (account.getNickName() != null && !account.getNickName().isEmpty()) {
				usernames.add(account.getId() + ": " + account.getName() + " " + account.getSurname() + " - " + account.getNickName());
			} else {
				usernames.add(account.getId() + ": " + account.getName() + " " + account.getSurname());
			}
		}
		return usernames;
	}
}
