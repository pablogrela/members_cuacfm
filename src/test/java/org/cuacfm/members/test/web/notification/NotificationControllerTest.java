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
package org.cuacfm.members.test.web.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

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
import org.cuacfm.members.model.util.Constants.typeDestinataries;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class accountListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NotificationControllerTest extends WebSecurityConfigurationAware {

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

		admin = new Account("admin", "", "55555555D", "London", "admin", "admin@test.es", "666666666", "666666666", "demo", roles.ROLE_ADMIN);
		admin.setAccountType(accountType);
		admin.setMethodPayment(methodPayment);
		admin.setInstallments(1);
		accountService.save(admin);
		defaultSession = getDefaultSession(admin.getEmail());

		user = new Account("user", "1", "55555555C", "London", "user", "email1@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.addDeviceToken("deviceToken");
		user.setInstallments(1);
		accountService.save(user);
	}

	/**
	 * Display account page without sigin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayaccountPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/notification")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Display account list test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayAccountListTest() throws Exception {
		mockMvc.perform(get("/notification").locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Display account list test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getAccountListTest() throws Exception {
		mockMvc.perform(get("/notification/accountList/").locale(Locale.ENGLISH).session(defaultSession));
		accountService.delete(user);
		mockMvc.perform(get("/notification/accountList/").locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Notification test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void notificationTest() throws Exception {
		mockMvc.perform(post("/accountList/push/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.param("destinataries", typeDestinataries.ALL.toString()).param("title", "title").param("body", "body")
				.contentType(MediaType.APPLICATION_JSON).content("[]"));
	}

}