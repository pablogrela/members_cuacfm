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
package org.cuacfm.members.model.feemember;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class FeeMemberRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class FeeMemberRepositoryImpl implements FeeMemberRepository {

	private static final Logger logger = LoggerFactory.getLogger(FeeMemberRepository.class);
	
	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public FeeMember save(FeeMember payMember) {
		entityManager.persist(payMember);
		return payMember;
	}

	@Override
	@Transactional
	public FeeMember update(FeeMember payMember) {
		return entityManager.merge(payMember);
	}

	@Override
	public FeeMember findById(Long id) {
		try {
			return entityManager.createQuery("select a from FeeMember a where a.id = :id", FeeMember.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public FeeMember findByName(String name) {
		try {
			return entityManager.createQuery("select a from FeeMember a where a.name = :name", FeeMember.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public FeeMember findByYear(int year) {
		try {
			return entityManager.createQuery("select a from FeeMember a where a.year = :year", FeeMember.class).setParameter("year", year)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("NoResult" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<FeeMember> getFeeMemberList() {
		return entityManager.createQuery("select a from FeeMember a order by a.name", FeeMember.class).getResultList();
	}

	@Override
	public FeeMember getLastFeeMember() {
		try {
		return entityManager.createQuery("select a from FeeMember a where a.year = (select max(year) from FeeMember)", FeeMember.class)
				.getSingleResult();
		} catch (Exception e) {
			logger.info("getLastFeeMember" + "No exist FeeMembers");
			return null;
		}
	}
}
