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
package org.cuacfm.members.model.program;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class ProgramRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class ProgramRepositoryImpl implements ProgramRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Program save(Program program) {
		entityManager.persist(program);
		return program;
	}

	@Override
	@Transactional
	public Program update(Program program) {
		return entityManager.merge(program);
	}

	@Override
	@Transactional
	public void delete(Program program) {
		entityManager.remove(program);
	}

	@Override
	public Program findById(Long id) {
		try {
			return entityManager.createQuery("select a from Program a where a.id = :id", Program.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Program findByName(String name) {
		try {
			return entityManager.createQuery("select a from Program a where a.name = :name", Program.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Program> getProgramList() {
		return entityManager.createQuery("select p from Program p order by p.name", Program.class).getResultList();
	}

	@Override
	public List<Program> getProgramListActive() {
		return entityManager.createQuery("select p from Program p where p.active = true order by p.name", Program.class).getResultList();
	}
}
