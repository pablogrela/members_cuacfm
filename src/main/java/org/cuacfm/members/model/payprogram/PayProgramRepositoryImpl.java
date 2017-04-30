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
package org.cuacfm.members.model.payprogram;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.cuacfm.members.model.account.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayProgramRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class PayProgramRepositoryImpl implements PayProgramRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public PayProgram save(PayProgram payProgram) {
		entityManager.persist(payProgram);
		return payProgram;
	}

	@Override
	@Transactional
	public PayProgram update(PayProgram payProgram) {
		return entityManager.merge(payProgram);
	}

	@Override
	@Transactional
	public void remove(PayProgram payProgram) {
		entityManager.remove(payProgram);
	}

	@Override
	public PayProgram findById(Long id) {
		try {
			return entityManager.createQuery("select p from PayProgram p where p.id = :id", PayProgram.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public PayProgram findByIdTxn(String idTxn) {
		try {
			return entityManager.createQuery("select p from PayProgram p where p.idTxn = :idTxn", PayProgram.class).setParameter("idTxn", idTxn)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public PayProgram findByPayProgramIds(Long programId, Long feeProgramId) {
		return entityManager
				.createQuery("select p from PayProgram p where p.program.id = :programId and p.feeProgram.id = :feeProgramId", PayProgram.class)
				.setParameter("programId", programId).setParameter("feeProgramId", feeProgramId).getSingleResult();
	}

	@Override
	public List<PayProgram> getPayProgramList() {
		return entityManager.createQuery("select p from PayProgram p order by p.program.name", PayProgram.class).getResultList();
	}

	@Override
	public List<PayProgram> findNoPayListByAccountId(Long accountId) {
		return entityManager.createQuery(
				"select p from PayProgram p where p.state <> 'PAY' and p.feeProgram.date <= :date and p.program.accountPayer.id = :accountId order by p.program.name",
				PayProgram.class).setParameter("date", new Date()).setParameter("accountId", accountId).getResultList();

	}

	/**
	 * Gets the pay program no pay list by direct debit.
	 *
	 * @param monthCharge the month charge
	 * @return the pay program no pay list by direct debit
	 */
	@Override
	public Map<Account, List<PayProgram>> getPayProgramNoPayListByDirectDebit(Date monthCharge) {

		TypedQuery<Object[]> q = entityManager
				.createQuery(
						"select p.program.accountPayer, p from PayProgram p where p.state <> 'PAY' " + "and month(p.feeProgram.date) = month(:month) "
								+ "and year(p.feeProgram.date) = year(:month) " + "order by p.program.accountPayer.name, p.program.name",
						Object[].class)
				.setParameter("month", monthCharge);

		List<Object[]> resultList = q.getResultList();
		Map<Account, List<PayProgram>> userPayPrograms = new HashMap<>();

		for (Object[] result : resultList) {
			Account account = (Account) result[0];

			// If exist Account in userPayPrograms
			if (userPayPrograms.containsKey(account)) {
				List<PayProgram> payProgramsAux = userPayPrograms.get(account);
				payProgramsAux.add((PayProgram) result[1]);
				userPayPrograms.putIfAbsent(account, payProgramsAux);
			}
			// If no exist Account in userPayPrograms
			else {
				List<PayProgram> payPrograms = new ArrayList<PayProgram>();
				payPrograms.add((PayProgram) result[1]);
				userPayPrograms.put(account, payPrograms);
			}
		}
		return userPayPrograms;
	}

	@Override
	public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId) {
		return entityManager.createQuery("select p from PayProgram p where p.feeProgram.id = :feeProgramId", PayProgram.class)
				.setParameter("feeProgramId", feeProgramId).getResultList();
	}

	@Override
	public List<PayProgram> getPayProgramListByProgramId(Long programId) {
		return entityManager.createQuery("select p from PayProgram p where p.program.id = :programId", PayProgram.class)
				.setParameter("programId", programId).getResultList();
	}

	@Override
	public List<PayProgram> getPayProgramListByAccountId(Long accountId) {
		return entityManager

				// .createQuery("select p from PayProgram p where (select a from Account a where a.id = :accountId) in p.program.accounts ",
				// .createQuery("select p from PayProgram p where p.program.id in (select a.programs.id from Account a where a.id = :accountId)",
				// .createQuery("select p from PayProgram p where p.program.id in (select a.id from UserPrograms a where a.id = :accountId)",
				.createQuery("select p from PayProgram p ", PayProgram.class)
				// .setParameter("accountId", accountId)
				.getResultList();
	}
}
