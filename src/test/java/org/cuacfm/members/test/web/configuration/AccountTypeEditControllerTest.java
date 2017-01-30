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
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
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

/** The Class AccountTypeEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTypeEditControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The account Type service. */
	@Autowired
	private AccountTypeService accountTypeService;

	/** The account type. */
	private AccountType accountType;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "55555555C", "London", "admin", "admin@udc.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@udc.es");

		accountType = new AccountType("Adult", false, "Tax for Adult", 0);
		accountTypeService.save(accountType);
	}

	/**
	 * Display AccountTypeList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayAccountTypeEditPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/configuration/accountTypeEdit")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send null.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void redirectAccountListBecauseAccountTypeIsNullTest() throws Exception {
		mockMvc.perform(post("/configuration/accountTypeEdit/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(get("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Send displaysAccountTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysAccountTypeEditTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(get("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("configuration/accountypedit"))
				.andExpect(content().string(containsString("<title>Edit account type</title>")));
	}

	/**
	 * Send displaysAccountTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountTypeEditTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "Adult")
				.param("description", "Tax for adult").param("discount", "0")).andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Send displaysAccountTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void nameAlreadyExistTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "Adult")
				.param("description", "Tax for adult").param("discount", "0"))
				.andExpect(
						content().string(containsString("Already exist account type with name " + accountType.getName() + ", please choose another")))
				.andExpect(view().name("configuration/accountypedit"));

	}

	/**
	 * Send update Account Type List.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void updateAccountTypeTheSameParamsTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession).param("name", "Adult")
				.param("description", "Tax for adult").param("discount", "0")).andExpect(view().name("redirect:/configuration"));

	}

	/**
	 * notBlankMessage.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageInAccountTypeEditTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content().string(containsString("The value may not be empty!"))).andExpect(view().name("configuration/accountypedit"));

	}

	/**
	 * "Already exist type of account with name "+ accountType.getName() + ", please chose other" Send displaysAccountTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInAccountTypeEditTest() throws Exception {

		mockMvc.perform(post("/configuration/accountTypeEdit/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration/accountTypeEdit"));

		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Adultttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
				.param("description", "Tax for adultttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt").param("discount", "0"))
				.andExpect(content().string(containsString("Maximum 50 characters"))).andExpect(view().name("configuration/accountypedit"));

	}
}