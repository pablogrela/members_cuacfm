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
package org.cuacfm.members.model.event;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class EventRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class EventRepositoryImpl implements EventRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Event save(Event event) {
		entityManager.persist(event);
		return event;
	}

	@Override
	@Transactional
	public Event update(Event event) {
		return entityManager.merge(event);
	}

	@Override
	public Event findById(Long id) {
		try {
			return entityManager.createQuery("select e from Event e where e.id = :id", Event.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Event> findAllByAccountId(Long accountId) {
		return entityManager.createQuery("select e from Event e where e.account.id = :accountId order by e.dateEvent desc", Event.class)
				.setParameter("accountId", accountId).getResultList();
	}

	@Override
	public List<Event> findAllOpen() {
		return entityManager.createQuery("select e from Event e where e.priority!=0 order by e.priority, e.dateEvent desc", Event.class).getResultList();
	}
	
	@Override
	public List<Event> findAllClose() {
		return entityManager.createQuery("select e from Event e where e.priority=0 order by e.priority, e.dateEvent desc", Event.class).getResultList();
	}
	
	@Override
	public List<Event> findAll() {
		return entityManager.createQuery("select e from Event e order by e.priority, e.dateEvent desc", Event.class).getResultList();
	}
}
