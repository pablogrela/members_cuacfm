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
package org.cuacfm.members.model.report;

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

/** The Class ReportRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ReportRepositoryImpl implements ReportRepository {

	private static final Logger logger = LoggerFactory.getLogger(ReportRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Report save(Report report) {
		entityManager.persist(report);
		return report;
	}

	@Override
	@Transactional
	public Report update(Report report) {
		return entityManager.merge(report);
	}

	@Override
	@Transactional
	public void delete(Report report) {
		entityManager.remove(report);
	}

	@Override
	public Report findById(Long id) {
		try {
			return entityManager.createQuery("select a from Report a where a.id = :id", Report.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Report> getReportList() {
		return entityManager.createQuery("select p from Report p order by p.dateCreate desc", Report.class).getResultList();
	}

	@Override
	public List<Report> getReportListActive() {
		return entityManager.createQuery("select p from Report p where p.active = true order by p.dateCreate desc", Report.class).getResultList();
	}

	@Override
	public List<Report> getReportListClose() {
		return entityManager.createQuery("select p from Report p where p.active = false order by p.dateCreate desc", Report.class).getResultList();
	}

	@Override
	public List<Report> getReportListByUser(Account account) {
		return entityManager.createQuery("select p from Report p where p.account.id = :accountId order by p.dateCreate desc", Report.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

	@Override
	public List<Report> getReportListActiveByUser(Account account) {
		return entityManager
				.createQuery("select p from Report p where p.account.id = :accountId and p.active = true order by p.dateCreate desc", Report.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

}
