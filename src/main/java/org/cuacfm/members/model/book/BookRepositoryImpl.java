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
package org.cuacfm.members.model.book;

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

/** The Class BookRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepository {

	private static final Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Book save(Book book) {
		entityManager.persist(book);
		return book;
	}

	@Override
	@Transactional
	public Book update(Book book) {
		return entityManager.merge(book);
	}

	@Override
	@Transactional
	public void delete(Book book) {
		entityManager.remove(book);
	}

	@Override
	public Book findById(Long id) {
		try {
			return entityManager.createQuery("select r from Book r where r.id = :id", Book.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			logger.info(Constants.NO_RESULT + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Book> getBookList() {
		return entityManager.createQuery("select r from Book r order by r.dateCreate desc", Book.class).getResultList();
	}

	@Override
	public List<Book> getBookListConflicts(Book book) {
		return entityManager
				.createQuery(
						"select r from Book r where r.element.id = :elementId and ((:dateStart between r.dateStart and r.dateEnd) or (:dateEnd between r.dateStart and r.dateEnd)) order by r.dateCreate desc",
						Book.class)
				.setParameter("elementId", book.getElement().getId()).setParameter("dateStart", book.getDateStart()).setParameter("dateEnd", book.getDateEnd()).getResultList();
	}

	@Override
	public List<Book> getBookListActive() {
		return entityManager.createQuery("select r from Book r where r.active = true order by r.dateCreate desc", Book.class).getResultList();
	}

	@Override
	public List<Book> getBookListClose() {
		return entityManager.createQuery("select r from Book r where r.active = false order by r.dateCreate desc", Book.class).getResultList();
	}

	@Override
	public List<Book> getBookListByUser(Account account) {
		return entityManager.createQuery("select r from Book r where r.account.id = :accountId order by r.dateCreate desc", Book.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

	@Override
	public List<Book> getBookListActiveByUser(Account account) {
		return entityManager
				.createQuery("select r from Book r where r.account.id = :accountId and r.active = true order by r.dateCreate desc", Book.class)
				.setParameter("accountId", account.getId()).getResultList();
	}

}
