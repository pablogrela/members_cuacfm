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
package org.cuacfm.members.test.model.bookservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.book.BookDTO;
import org.cuacfm.members.model.bookservice.BookService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.exceptions.UserAlreadyBookException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class BookServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BookServiceTest extends WebSecurityConfigurationAware {

	@Inject
	private BookService bookService;

	@Inject
	private AccountService accountService;

	@Inject
	private ElementService elementService;

	private Account account;
	private Account account2;
	private Element element;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		List<Account> accounts = new ArrayList<Account>();
		account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account);
		accounts.add(account);
		account2 = new Account("user2", "2", "25555555C", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(account2);
		accounts.add(account2);
		element = new Element("element 1", "element 1", true, true);
		elementService.save(element);
	}

	/**
	 * Save and find book test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void saveAndFindBookTest() throws UniqueException, DatesException, UserAlreadyBookException {

		// Save
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book);

		// Assert
		Book bookSearched = bookService.findById(book.getId());
		assertEquals(book, bookSearched);
	}

	/**
	 * Save book exception test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test(expected = DatesException.class)
	public void saveBookDatesExceptionTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Book book = new Book(account, element, "book", new Date(), new Date());
		bookService.save(book);
		bookService.save(book);
	}

	/**
	 * Save book dates exception 2 test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test(expected = DatesException.class)
	public void saveBookDatesException2Test() throws UniqueException, DatesException, UserAlreadyBookException {

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		// Save
		Book book = new Book(account, element, "book", startDate, endDate);
		bookService.save(book);
		bookService.save(book);
	}

	/**
	 * Update book dates exception test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test(expected = DatesException.class)
	public void updateBookDatesExceptionTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book);
		cal.add(Calendar.MONTH, -3);
		startDate = new Date(cal.getTimeInMillis());
		book.setDateStart(startDate);
		bookService.update(book);
	}

	/**
	 * Update book dates exception 2 test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test(expected = DatesException.class)
	public void updateBookDatesException2Test() throws UniqueException, DatesException, UserAlreadyBookException {

		// Save
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date failDate = new Date(cal.getTimeInMillis());
		
		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book);
		book.setDateEnd(failDate);
		bookService.update(book);
	}

	/**
	 * Save book user already book exception.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test(expected = UserAlreadyBookException.class)
	public void saveBookUserAlreadyBookException() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book);

		Book book2 = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book2);
	}

	/**
	 * Update book test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void updateBookTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Book book = new Book(account, element, "book 1", new Date(), new Date());
		bookService.save(book);

		// Update
		book.setAccount(account);
		book.setElement(element);
		book.setDateStart(new Date());
		book.setDateEnd(new Date());
		book.setAnswer("");
		book.setDescription("");
		book.setDateCreate(book.getDateStart());
		book.setDateRevision(book.getDateEnd());
		Book bookUpdate = bookService.update(book);

		// Assert
		assertEquals(book, bookUpdate);
		assertEquals(book.getAccount(), bookUpdate.getAccount());
		assertEquals(book.getDescription(), bookUpdate.getDescription());
		assertEquals(book.getAnswer(), bookUpdate.getAnswer());
		assertEquals(book.getDateCreate(), bookUpdate.getDateCreate());
		assertEquals(book.getDateRevision(), bookUpdate.getDateRevision());
		assertEquals(book.getDateEnd(), bookUpdate.getDateEnd());
		assertEquals(book.getDateStart(), bookUpdate.getDateStart());
		book.toString();

		BookDTO bookDTO = bookService.getBookDTO(bookUpdate);
		bookDTO.setAccount(accountService.getAccountDTO(account));
		bookDTO.setElement(elementService.getElementDTO(element));
		bookDTO.setDateStart(new Date());
		bookDTO.setDateEnd(new Date());
		bookDTO.setAnswer("");
		bookDTO.setDescription("");
		bookDTO.setDateCreate(new Date());
		bookDTO.setDateRevision(new Date());

		// Assert
		assertEquals(book.getId(), bookUpdate.getId());
		assertEquals(bookDTO.getDescription(), bookUpdate.getDescription());
		assertEquals(bookDTO.getAnswer(), bookUpdate.getAnswer());
	}

	/**
	 * Gets the book test.
	 *
	 * @return the book test
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void getBookTest() throws UniqueException, DatesException, UserAlreadyBookException {
		Book book = new Book(account, element, "book 1", new Date(), new Date());
		bookService.save(book);
		BookDTO bookDTO = bookService.getBookDTO(book);

		Book bookAux1 = bookService.getBook(bookDTO, null);
		Book bookAux2 = bookService.getBook(bookDTO, account);

		assertEquals(bookAux1.getId(), bookAux2.getId());
	}

	/**
	 * Book states test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void bookStatesTest() throws UniqueException, DatesException, UserAlreadyBookException {
		Book book = new Book(account, element, "book 1", new Date(), new Date());
		bookService.save(book);

		bookService.up(book);
		assertTrue(book.isActive());

		bookService.accept(book);
		assertFalse(book.isActive());

		bookService.answer(book, account, "", null);
		assertTrue(book.getAnswer().contains("user"));

		bookService.answer(book, account, "", true);
		assertTrue(book.getAnswer().contains("user"));
		assertFalse(book.isActive());

		bookService.answer(book, account, "", false);
		assertTrue(book.getAnswer().contains("user"));
		assertFalse(book.isActive());

		bookService.answer(book, account2, "", null);
		assertTrue(book.getAnswer().contains("user2"));
	}

	/**
	 * Delete book test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void deleteBookTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Book book = new Book(account, element, "book 1", new Date(), new Date());
		bookService.save(book);

		// Assert
		Book bookSearched = bookService.findById(book.getId());
		assertNotNull(bookSearched);

		// Delete
		bookService.delete(book);

		// Assert, no exist Book
		bookSearched = bookService.findById(book.getId());
		assertNotNull(bookSearched.getId());
	}

	/**
	 * Delete null book test.
	 */
	@Test
	public void deleteNullBookTest() {
		// Delete
		bookService.delete(null);
	}

	/**
	 * Save and find account type test.
	 *
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void saveAndFindByNameBookTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Save
		Book book = new Book(account, element, "book 1", new Date(), new Date());
		bookService.save(book);

		// Assert
		Book bookSearched = bookService.findById(book.getId());
		assertEquals(book, bookSearched);
	}

	/**
	 * Gets the books test.
	 *
	 * @return the books test
	 * @throws UniqueException the unique exception
	 * @throws DatesException the dates exception
	 * @throws UserAlreadyBookException the user already book exception
	 */
	@Test
	public void getBooksTest() throws UniqueException, DatesException, UserAlreadyBookException {
		// Assert
		List<Book> books = bookService.getBookList();
		assertEquals(books.size(), 0);

		// Save
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());
		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date startDate2 = new Date(cal.getTimeInMillis());
		cal.add(Calendar.MONTH, 1);
		Date endDate2 = new Date(cal.getTimeInMillis());

		Book book = new Book(account, element, "book 1", startDate, endDate);
		bookService.save(book);
		Book book2 = new Book(account2, element, "book 2", startDate, endDate);
		bookService.save(book2);
		Book book3 = new Book(account, element, "book 3", startDate2, endDate2);
		bookService.save(book3);

		// Assert
		books = bookService.getBookList();
		assertEquals(books.size(), 3);
		assertTrue(books.contains(book));
		assertTrue(books.contains(book2));
		assertTrue(books.contains(book3));

		books = bookService.getBookListActive();
		assertEquals(books.size(), 3);
		assertTrue(books.contains(book));
		assertTrue(books.contains(book2));
		assertTrue(books.contains(book3));

		book3.setActive(false);
		bookService.update(book3);
		books = bookService.getBookListClose();
		assertEquals(books.size(), 1);
		assertTrue(books.contains(book3));

		books = bookService.getBookListByUser(account);
		assertEquals(books.size(), 2);
		assertTrue(books.contains(book));
		assertTrue(books.contains(book3));

		books = bookService.getBookListActiveByUser(account);
		assertEquals(books.size(), 1);
		assertTrue(books.contains(book));

		List<BookDTO> booksDTO = bookService.getBooksDTO(bookService.getBookList());
		assertEquals(booksDTO.size(), 3);
		BookDTO bookDTO = bookService.getBookDTO(book);
		assertEquals(bookService.findById(bookDTO.getId()).getId(), book.getId());
		assertNull(bookService.findById(Long.valueOf(0)));
		BookDTO book2DTO = bookService.getBookDTO(null);
		assertNull(book2DTO);
		bookDTO.toString();
		bookDTO.setDateCreate(bookDTO.getDateRevision());
		bookDTO.setDateApproval(bookDTO.getDateApproval());
		bookDTO.setActive(bookDTO.isActive());
		bookDTO.setState(bookDTO.getState());
	}
}
