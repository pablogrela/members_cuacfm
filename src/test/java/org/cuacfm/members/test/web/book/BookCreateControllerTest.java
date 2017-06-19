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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class BookCreateControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BookCreateControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Inject
	private AccountService accountService;

	@Inject
	private ElementService elementService;

	private Element element;
	private Account admin;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account user = new Account("user", "", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "user", roles.ROLE_USER);
		accountService.save(user);
		defaultSession = getDefaultSession(user.getEmail());

		admin = new Account("admin", "", "55555555A", "London", "admin", "admin@test.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		admin.addPermissions(permissions.ROLE_BOOK);
		accountService.save(admin);

		element = new Element("element 1", "element 1", true, true);
		elementService.save(element);
	}

	/**
	 * Display BookList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayBookCreatePageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/bookList/bookCreate")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Gets the book create test.
	 *
	 * @return the book create test
	 * @throws Exception the exception
	 */
	@Test
	public void getBookCreateTest() throws Exception {
		mockMvc.perform(get("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Gets the user book create test.
	 *
	 * @return the user book create test
	 * @throws Exception the exception
	 */
	@Test
	public void getUserBookCreateTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Post book create test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookCreateTest() throws Exception {
		mockMvc.perform(get("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail()))
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("redirect:/bookList"));
	}

	/**
	 * Post book user create test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookUserCreateTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("redirect:/bookUserList"));
	}

	/**
	 * Post book create fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookCreateFailTest() throws Exception {
		mockMvc.perform(get("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())).param("elementId", "-1")
				.param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30").param("dateEnd", "2030-12-05")
				.param("description", "Book")).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Post book create warning user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookCreateWarningUserTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("redirect:/bookUserList"));

		mockMvc.perform(get("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail())))
				.andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookList/bookCreate").locale(Locale.ENGLISH).session(getDefaultSession(admin.getEmail()))
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("redirect:/bookList"));
	}

	/**
	 * Post book user create same element test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookUserCreateSameElementTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("redirect:/bookUserList"));

		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2030-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Post book user create exception date test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookUserCreateExceptionDateTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "10:30").param("dateStart", "2010-12-05").param("timeEnd", "11:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Post book user create exception date 2 test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookUserCreateExceptionDate2Test() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "11:30").param("dateStart", "2030-12-05").param("timeEnd", "10:30")
				.param("dateEnd", "2030-12-05").param("description", "Book")).andExpect(view().name("book/bookcreate"));
	}

	/**
	 * Post book user create no time test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postBookUserCreateNoTimeTest() throws Exception {
		mockMvc.perform(get("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("book/bookcreate"));

		mockMvc.perform(post("/bookUserList/bookUserCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("elementId", element.getId().toString()).param("timeStart", "11:30").param("dateStart", "2030-12-05").param("timeEnd", "10:30")
				.param("dateEnd", "2030-12-05").param("description",
						"BookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBook"
						+ "BookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBook"
						+ "BookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBookBook"
						+ "BookBookBookBookBookBookBookBookBookBookBookBook"))
				.andExpect(view().name("book/bookcreate"));
	}

}