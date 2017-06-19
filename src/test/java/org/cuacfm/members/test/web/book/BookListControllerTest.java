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
package org.cuacfm.members.test.web.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.bookservice.BookService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class BookListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BookListControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;
	private Account user;
	private List<Account> accounts;
	private Element element;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;

	@Autowired
	private BookService bookService;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSessionTest() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555A", "London", "admin", "admin@test.es", "666666666", "666666666", "admin",
				roles.ROLE_ADMIN);
		admin.addPermissions(permissions.ROLE_BOOK);
		accountService.save(admin);
		defaultSession = getDefaultSession(admin.getEmail());

		user = new Account("test", "2", "11111111H", "London", "test", "user@test.es", "666666666", "666666666", "test", roles.ROLE_USER);
		accountService.save(user);
		accounts = new ArrayList<Account>();
		accounts.add(user);
		element = new Element("element 1", "element 1", true, true);
		elementService.save(element);
	}

	/**
	 * Display BookList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayBookListPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/bookList")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysBookList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysBookListTest() throws Exception {
		mockMvc.perform(get("/bookList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/booklist"));
		mockMvc.perform(get("/bookList").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/booklist"));
		mockMvc.perform(get("/bookUserList").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail()))).andExpect(view().name("book/bookuserlist"));
	}

	/**
	 * Displays program list close.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysBookListCloseTest() throws Exception {
		mockMvc.perform(get("/bookList/close").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/booklistclose"));
	}

	/**
	 * Send displaysBookList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysUserBookListTest() throws Exception {
		mockMvc.perform(get("/bookList").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail()))).andExpect(view().name("book/booklist"));
	}

	/**
	 * Send displaysBookList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void bookCreateTest() throws Exception {
		mockMvc.perform(get("/bookList/bookCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Book down.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void bookDenyTest() throws Exception {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(post("/bookList/bookDeny/" + book.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertFalse(book.isActive());
	}

	@Test
	public void bookAcceptTest() throws Exception {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(post("/bookList/bookAccept/" + book.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertFalse(book.isActive());
	}
	
	/**
	 * Book up.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void bookUpTest() throws Exception {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(post("/bookList/bookUp/" + book.getId()).locale(Locale.ENGLISH).session(defaultSession));
		assertTrue(book.isActive());
	}

	/**
	 * Display program list.
	 *
	 * @return the book list
	 * @throws Exception the exception
	 */
	@Test
	public void getBookListTest() throws Exception {
		mockMvc.perform(get("/bookList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/bookUserList/").locale(Locale.ENGLISH).session(defaultSession));

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(get("/bookList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/bookList/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
		mockMvc.perform(get("/bookUserList/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
	}

	/**
	 * Display program list close.
	 *
	 * @return the book list close
	 * @throws Exception the exception
	 */
	@Test
	public void getBookListCloseTest() throws Exception {
		mockMvc.perform(get("/bookList/close/").locale(Locale.ENGLISH).session(defaultSession));

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(get("/bookList/close/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/bookList/close/").locale(Locale.ENGLISH).session(getDefaultSession(user.getEmail())));
	}

	/**
	 * Notification.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void bookAnswerTest() throws Exception {

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, 1);
		Date startDate = new Date(cal.getTimeInMillis());

		cal.add(Calendar.MONTH, 1);
		Date endDate = new Date(cal.getTimeInMillis());

		Book book = new Book(user, element, "book 1", startDate, endDate);
		bookService.save(book);

		mockMvc.perform(post("/bookList/bookAnswer/" + book.getId()).locale(Locale.ENGLISH).session(defaultSession).param("answer", "answer"));
		mockMvc.perform(post("/bookUserList/bookAnswer/" + book.getId()).locale(Locale.ENGLISH).session(defaultSession).param("answer", "answer"));
	}
}