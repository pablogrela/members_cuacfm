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
package org.cuacfm.members.test.web.configuration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ElementEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElementEditControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;
	
	private Element element;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException, UniqueListException the unique exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555C", "London", "admin", "admin@udc.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@udc.es");

		element = new Element("element 1", "element 1", true, true);
		elementService.save(element);
	}

	/**
	 * Display ElementList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayElementEditPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/configuration/elementEdit")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send null.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void redirectAccountListBecauseElementIsNullTest() throws Exception {
		mockMvc.perform(post("/configuration/elementEdit/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(get("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Send displaysElementList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysElementEditTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(get("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("configuration/elementedit"));
	}

	/**
	 * Send displaysElementList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postElementEditTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(post("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "Element 1").param("description",
				"Pay by Paypal")).andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Send displaysElementList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void nameAlreadyExistTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "element 1").param("description",
				"Pay by Paypal")).andExpect(view().name("configuration/elementedit"));

	}

	/**
	 * Send displaysElementList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void updateElementTheSameParamsTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(post("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "Element 1").param("description",
				"Element 1")).andExpect(view().name("redirect:/configuration"));

	}

	/**
	 * notBlankMessage.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageInElementEditTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(post("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content().string(containsString("The value may not be empty!"))).andExpect(view().name("configuration/elementedit"));

	}

	/**
	 * "Already exist type of account with name "+ element.getName() + ", please chose other" Send displaysElementList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInElementEditTest() throws Exception {

		mockMvc.perform(post("/configuration/elementEdit/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/elementEdit"));

		mockMvc.perform(post("/configuration/elementEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Element 1 tttttttttttttttttttttttttttttttttttttttttttttttt")
				.param("description", "Element 1 ttttttttttttttttttttttttttttttttt"))
				.andExpect(content().string(containsString("Maximum 50 characters"))).andExpect(view().name("configuration/elementedit"));

	}
}