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

import org.cuacfm.members.model.account.Account;

/**
 * The Interface BookRepository.
 */
public interface BookRepository {

	/**
	 * Save.
	 *
	 * @param book the book
	 * @return book
	 */
	public Book save(Book book);

	/**
	 * Update.
	 *
	 * @param book the book
	 * @return book
	 */
	public Book update(Book book);

	/**
	 * Delete.
	 *
	 * @param book the book
	 */
	public void delete(Book book);

	/**
	 * Find by id.
	 *
	 * @param id the id of book
	 * @return the book
	 */
	public Book findById(Long id);

	/**
	 * Get all books.
	 *
	 * @return List<Book>
	 */
	public List<Book> getBookList();

	/**
	 * Gets the book list conflicts.
	 *
	 * @param book the book
	 * @return the book list conflicts
	 */
	public List<Book> getBookListConflicts(Book book);

	/**
	 * Get all active books.
	 *
	 * @return List<Book>
	 */
	public List<Book> getBookListActive();

	/**
	 * Gets the book list close.
	 *
	 * @return the book list close
	 */
	public List<Book> getBookListClose();

	/**
	 * Gets the book list by user.
	 *
	 * @param account the account
	 * @return the book list by user
	 */
	public List<Book> getBookListByUser(Account account);

	/**
	 * Gets the book list by user.
	 *
	 * @param account the account
	 * @return the book list by user
	 */
	public List<Book> getBookListActiveByUser(Account account);

}
