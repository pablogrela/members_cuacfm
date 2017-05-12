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
package org.cuacfm.members.model.bookservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.book.BookDTO;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UserAlreadyBookException;

/** The Class BookService. */
public interface BookService {

	/**
	 * Save.
	 *
	 * @param book the book
	 * @return the book
	 */
	public Book save(Book book) throws DatesException, UserAlreadyBookException;

	/**
	 * Update Book.
	 *
	 * @param book the book
	 * @return Book
	 */
	public Book update(Book book) throws DatesException;

	/**
	 * Delete.
	 *
	 * @param book the book
	 */
	public void delete(Book book);

	/**
	 * Delete.
	 *
	 * @param book the book
	 * @param account the account
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Book book, Account account) throws ExistPaymentsException;

	/**
	 * Find by id returns book which has this identifier.
	 *
	 * @param id the id
	 * @return book
	 */
	public Book findById(Long id);

	/**
	 * Get all books.
	 *
	 * @return List<Book>
	 */
	public List<Book> getBookList();

	/**
	 * Get all active books.
	 *
	 * @return List<Book>
	 */
	public List<Book> getBookListActive();

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

	/**
	 * Gets the book list close.
	 *
	 * @return the book list close
	 */
	public List<Book> getBookListClose();

	/**
	 * Up.
	 *
	 * @param book the book
	 */
	public void up(Book book);

	/**
	 * Accept.
	 *
	 * @param book the book
	 */
	public void accept(Book book);

	/**
	 * Deny.
	 *
	 * @param book the book
	 */
	public void deny(Book book);

	/**
	 * Answer.
	 *
	 * @param book the book
	 * @param account the account
	 * @param manage the manage
	 * @param answer the answer
	 * @return the book
	 */
	public Book answer(Book book, Account account, String answer, Boolean manage);

	/**
	 * Gets the books DTO.
	 *
	 * @param books the books
	 * @return the books DTO
	 */
	public List<BookDTO> getBooksDTO(List<Book> books);

	/**
	 * Gets the book DTO.
	 *
	 * @param book the book
	 * @return the book DTO
	 */
	public BookDTO getBookDTO(Book book);

	/**
	 * Gets the book.
	 *
	 * @param bookDTO the book DTO
	 * @param account the account
	 * @return the book
	 */
	public Book getBook(BookDTO bookDTO, Account account);
}
