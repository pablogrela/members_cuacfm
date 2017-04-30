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
package org.cuacfm.members.model.element;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cuacfm.members.model.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class ElementRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ElementRepositoryImpl implements ElementRepository {

	private static final Logger logger = LoggerFactory.getLogger(ElementRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Element save(Element report) {
		entityManager.persist(report);
		return report;
	}

	@Override
	@Transactional
	public Element update(Element report) {
		return entityManager.merge(report);
	}

	@Override
	@Transactional
	public void delete(Element report) {
		entityManager.remove(report);
	}

	@Override
	public Element findById(Long id) {
		try {
			return entityManager.createQuery("select a from Element a where a.id = :id", Element.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public Element findByName(String name) {
		try {
			return entityManager.createQuery("select a from Element a where a.name = :name", Element.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Element> getElementList() {
		return entityManager.createQuery("select p from Element p order by p.name desc", Element.class).getResultList();
	}

	@Override
	public List<Element> getElementListReservable() {
		return entityManager.createQuery("select p from Element p where p.reservable = true order by p.name desc", Element.class).getResultList();
	}

	@Override
	public List<Element> getElementListLocation() {
		return entityManager.createQuery("select p from Element p where p.location = true order by p.name desc", Element.class).getResultList();
	}

	@Override
	public List<Element> getElementListReservableLocation() {
		return entityManager
				.createQuery("select p from Element p where p.reservable = true and p.location = true order by p.name desc", Element.class)
				.getResultList();
	}

}
