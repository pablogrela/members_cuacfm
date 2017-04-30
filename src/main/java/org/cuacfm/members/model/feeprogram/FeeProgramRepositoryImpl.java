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
package org.cuacfm.members.model.feeprogram;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cuacfm.members.model.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class FeeProgramRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class FeeProgramRepositoryImpl implements FeeProgramRepository {

	private static final Logger logger = LoggerFactory.getLogger(FeeProgramRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public FeeProgram save(FeeProgram feeProgram) {
		entityManager.persist(feeProgram);
		return feeProgram;
	}

	@Override
	@Transactional
	public FeeProgram update(FeeProgram feeProgram) {
		return entityManager.merge(feeProgram);
	}

	@Override
	public FeeProgram findById(Long id) {
		try {
			return entityManager.createQuery("select a from FeeProgram a where a.id = :id", FeeProgram.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public FeeProgram findByName(String name) {
		try {
			return entityManager.createQuery("select a from FeeProgram a where a.name = :name", FeeProgram.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public FeeProgram findByDate(Date date) {
		try {
			return entityManager
					.createQuery("select a from FeeProgram a where month(a.date) = month(:date) and year(a.date) = year(:date)", FeeProgram.class)
					.setParameter("date", date).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<FeeProgram> getFeeProgramList() {
		return entityManager.createQuery("select a from FeeProgram a order by a.name", FeeProgram.class).getResultList();
	}
}
