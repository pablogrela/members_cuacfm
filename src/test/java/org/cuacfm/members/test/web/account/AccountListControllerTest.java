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
package org.cuacfm.members.test.web.account;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class accountListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountListControllerTest extends WebSecurityConfigurationAware {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private MethodPaymentService methodPaymentService;

	private MockHttpSession defaultSession;
	private Account admin;
	private Account user;
	private AccountType accountType;
	private MethodPayment methodPayment;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		accountType = new AccountType("Adult", false, "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", false, "cash");
		methodPaymentService.save(methodPayment);

		admin = new Account("admin", "", "55555555D", "London", "admin", "admin@udc.es", "666666666", "666666666", "demo", roles.ROLE_ADMIN);
		accountService.save(admin);
		admin.setAccountType(accountType);
		admin.setMethodPayment(methodPayment);
		admin.setInstallments(1);
		accountService.update(admin, false, true);
		defaultSession = getDefaultSession("admin@udc.es");

		user = new Account("user", "1", "55555555C", "London", "user", "email1@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.addDeviceToken("deviceToken");
		user.setInstallments(1);
		accountService.update(user, false, true);
	}

	/**
	 * Display account page without sigin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayaccountPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/accountlist")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send account form.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysaccountFormTest() throws Exception {
		CsrfToken token = new DefaultCsrfToken("headerName", "parameterName", "token");
		mockMvc.perform(get("/accountList").locale(Locale.ENGLISH).session(defaultSession).sessionAttr("_csrf", token))
				.andExpect(view().name("account/accountlist"))
				.andExpect(content().string(allOf(containsString("<title>Accounts</title>"), containsString("Account List</h1>"))));
	}

	/**
	 * Post account unsubscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountUnsubscribeTest() throws Exception {
		mockMvc.perform(post("/accountList/unsubscribe/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession));
		// .andExpect(view().name("redirect:/accountList"));

		//User is Unsubscribe
		assertFalse(user.isActive());
	}

	/**
	 * Post account unsubscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountUnsubscribeAdminTest() throws Exception {
		mockMvc.perform(post("/accountList/unsubscribe/" + admin.getId()).locale(Locale.ENGLISH).session(defaultSession));
		// .andExpect(view().name("redirect:/accountList"));

		// Admin not unsubscribe
		assertTrue(admin.isActive());
	}

	/**
	 * Post account subscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountSubscribeTest() throws Exception {
		mockMvc.perform(post("/accountList/unsubscribe/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//		.andExpect(view().name("redirect:/accountList"));

		mockMvc.perform(post("/accountList/subscribe/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession));
		//		.andExpect(view().name("redirect:/accountList"));

		//Admin already subscribe
		assertTrue(user.isActive());
	}

	/**
	 * Display account list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayAccountListTest() throws Exception {
		mockMvc.perform(get("/accountList/").locale(Locale.ENGLISH).session(defaultSession));
		accountService.delete(user);
		mockMvc.perform(get("/accountList/").locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Push.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void pushTest() throws Exception {
		mockMvc.perform(post("/accountList/push/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession).param("title", "title").param("body",
				"body"));
	}

	/**
	 * Email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void emailTest() throws Exception {
		mockMvc.perform(post("/accountList/email/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession).param("title", "title")
				.param("body", "body"));
	}
}