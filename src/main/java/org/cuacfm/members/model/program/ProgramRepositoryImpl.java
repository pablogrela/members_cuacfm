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

import java.util.Date;
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

	@Override
	public List<Program> getProgramListActiveWhitoutPays(Date month) {
		return entityManager.createQuery("select p from Program p where p.active = true "
				+ "and p not in (select program from PayProgram pay join pay.program program join pay.feeProgram fee where program.active = true and month(fee.date) = month(:month)) "
				+ "order by p.name", Program.class).setParameter("month", month).getResultList();
	}

	@Override
	public List<ProgramType> findProgramTypeList() {
		return entityManager.createQuery("select p from ProgramType p order by p.name", ProgramType.class).getResultList();
	}

	@Override
	public ProgramType findProgramTypeById(int id) {
		try {
			return entityManager.createQuery("select p from ProgramType p where p.id = :id", ProgramType.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ProgramThematic> findProgramThematicList() {
		return entityManager.createQuery("select p from ProgramThematic p order by p.name", ProgramThematic.class).getResultList();
	}

	@Override
	public ProgramThematic findProgramThematicById(int id) {
		try {
			return entityManager.createQuery("select p from ProgramThematic p where p.id = :id", ProgramThematic.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ProgramCategory> findProgramCategoryList() {
		return entityManager.createQuery("select p from ProgramCategory p order by p.name", ProgramCategory.class).getResultList();
	}

	@Override
	public ProgramCategory findProgramCategoryById(int id) {
		try {
			return entityManager.createQuery("select p from ProgramCategory p where p.id = :id", ProgramCategory.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ProgramLanguage> findProgramLanguageList() {
		return entityManager.createQuery("select p from ProgramLanguage p order by p.name", ProgramLanguage.class).getResultList();
	}

	@Override
	public ProgramLanguage findProgramLanguageById(int id) {
		try {
			return entityManager.createQuery("select p from ProgramLanguage p where p.id = :id", ProgramLanguage.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
