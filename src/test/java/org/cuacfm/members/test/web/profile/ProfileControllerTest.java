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
package org.cuacfm.members.test.web.profile;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.profile.ProfileForm;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ProfileControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;

	/** The account type. */
	private AccountType accountType;

	/** The method payment. */
	private MethodPayment methodPayment;

	/**
	 * Initialize default session.
	 * 
	 * @throws Exception
	 */
	@Before
	public void initializeDefaultSession() throws Exception {
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);

		accountType = new AccountType("Adult", false, "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", false, "cash");
		methodPaymentService.save(methodPayment);

		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false, true);

		defaultSession = getDefaultSession("user@udc.es");
	}

	/**
	 * Display profile page without sigin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayProfilePageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/profile")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send profile form.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysProfileFormTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession)).andExpect(model().attributeExists("profileForm"))
				.andExpect(view().name("profile/profile")).andExpect(content().string(allOf(containsString("<title>Profile</title>"),
						containsString("<legend>Would you like to change any of the information?</legend>"))));
	}

	/**
	 * Send account form.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysAccount2FormTest() throws Exception {

		Account user2 = new Account("user2", "11111111D", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);

		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession)).andExpect(model().attributeExists("profileForm"))
				.andExpect(view().name("profile/profile")).andExpect(content().string(allOf(containsString("<title>Profile</title>"),
						containsString("<legend>Would you like to change any of the information?</legend>"))));
	}

	/**
	 * update Profile The Same Params Test.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileTheSameParamsTest() throws Exception {

		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));

		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true").param("name", "user")
				.param("onDni", "true").param("dni", "55555555C").param("onAddress", "true").param("address", "London").param("name", "name")
				.param("cp", "cp").param("province", "province").param("codeCountry", "EN").param("onLogin", "true").param("login", "user")
				.param("onEmail", "true").param("email", "user@udc.es").param("onProgramName", "true").param("programName", "programName")
				.param("onPhone", "true").param("phone", "12356789").param("onMobile", "true").param("mobile", "12356789").param("onStudent", "true")
				.param("student", "true").param("onDateBirth", "true").param("dateBirth", "1990-05-02").param("onPassword", "true")
				.param("password", "1234").param("rePassword", "1234")).andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with login existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postloginAlreadyExistsTest() throws Exception {

		Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false, true);

		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "55555555K")
				.param("mobile", "12356789").param("onLogin", "true").param("login", "user2").param("name", "name").param("dni", "55555555B")
				.param("address", "address").param("cp", "cp").param("province", "province").param("codeCountry", "EN")
				.param("dateBirth", "1990-05-02").param("onEmail", "true").param("email", "usernew@udc.es"))
				//.andExpect(
				//		content()
				//				.string(containsString("Already existent login user2, please chose other")))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with email existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postDniAlreadyExistsTest() throws Exception {

		Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false, true);

		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "55555555B")
				.param("address", "address").param("mobile", "12356789").param("cp", "cp").param("province", "province").param("codeCountry", "EN")
				.param("dateBirth", "1990-05-02"))
				//.andExpect(
				//		content()
				//				.string(containsString("Already existent dni 55555555B, please chose other")))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with email existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postemailAlreadyExistsTest() throws Exception {

		Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false, true);

		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("onLogin", "true")
				.param("login", "usernew").param("dni", "dni").param("address", "address").param("cp", "cp").param("mobile", "12356789")
				.param("province", "province").param("codeCountry", "EN").param("dateBirth", "1990-05-02").param("onEmail", "true")
				.param("email", "user2@udc.es"))
				//.andExpect(
				//		content()
				//				.string(containsString("Already existent email user2@udc.es, please chose other")))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Incorrect email format.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void incorrectEmailFormatTest() throws Exception {
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("login", "login")
				.param("email", "email").param("mobile", "12356789").param("password", "1234").param("rePassword", "1234")
				.param("onInstallments", "true").param("installments", "1"))
				.andExpect(content().string(containsString("The value must be a valid email!"))).andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with insufficient characters in password.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void insuficientCharactersPasswordsTest() throws Exception {
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("onPassword", "true").param("password", "12")
				.param("rePassword", "12").param("mobile", "12356789").param("onInstallments", "true").param("installments", "1"))
				.andExpect(content().string(containsString("The password should be to have between 4 and 20 characters.")))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with different password.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void diferentPasswordsTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "95716045G")
				.param("address", "address").param("mobile", "12356789").param("cp", "cp").param("province", "province").param("codeCountry", "EN")
				.param("mobile", "11111111").param("dateBirth", "1990-05-02").param("onPassword", "true").param("password", "123456")
				.param("rePassword", "123333")).andExpect(content().string(containsString("Passwords are not equal")))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Send profile form with more of 30 characters.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maxTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "12345678901234567890123456789012343234234234234234").param("mobile", "12356789")
				.param("login", "12345678901234567890123456789012342342342342342434233")
				.param("email", "1234567890123456789012345678234234234343242901@example.es").param("password", "1234567890123456789012345678901")
				.param("rePassword", "1234567890123456789012345678901").param("installments", "1"))
				.andExpect(content().string(containsString("Maximum 50 characters"))).andExpect(view().name("profile/profile"));
	}

	/**
	 * Update profile blank message test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileBlankMessageTest() throws Exception {
		mockMvc.perform(
				post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true").param("name", " ").param("onLogin", "true")
						.param("login", " ").param("onEmail", "true").param("email", " ").param("mobile", "12356789").param("onPassword", "true")
						.param("password", " ").param("rePassword", " ").param("onInstallments", "true").param("installments", "1"))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Update profile succesfullwithout login.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullwithoutLoginTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "95716045G")
				.param("mobile", "12356789").param("address", "address").param("cp", "cp").param("province", "province").param("codeCountry", "EN")
				.param("mobile", "111111111").param("dateBirth", "1990-05-02")).andExpect(view().name("redirect:/profile"));
	}

	/**
	 * Update profile succesfull test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));

		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("nickName", "nickName")
				.param("dni", "95716045G").param("address", "address").param("cp", "cp").param("province", "province").param("codeCountry", "EN")
				.param("onLogin", "true").param("login", "login").param("onEmail", "true").param("email", "email2@udc.es").param("phone", "12356789")
				.param("mobile", "12356789").param("student", "true").param("dateBirth", "1990-05-02").param("onPassword", "true")
				.param("password", "1234").param("rePassword", "1234").param("installments", "1").param("accountTypeId", "1")
				.param("methodPaymentId", "1")).andExpect(view().name("redirect:/profile"));
	}

	/**
	 * Not on and blanck message.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ifnotOnAndBlanckMessageTest() throws Exception {
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("onName", "false").param("name", " ")
				.param("onLogin", "false").param("login", " ").param("onEmail", "false").param("email", " ").param("onPassword", "false")
				.param("password", " ").param("onDni", "false").param("dni", "95716045G").param("onAddress", "false").param("address", "address")
				.param("onPhone", "false").param("phone", "12356789").param("onMobile", "false").param("mobile", "12356789")
				.param("onStudent", "false").param("student", "true").param("onDateBirth", "false").param("dateBirth", "1990-05-02"))
				.andExpect(view().name("profile/profile"));
	}

	/**
	 * Not on and message.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ifnotOnAndMessageTest() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "95716045G")
				.param("address", "address").param("cp", "cp").param("province", "province").param("codeCountry", "EN").param("mobile", "111111111")
				.param("dateBirth", "1990-05-02").param("onName", "false").param("name", "name").param("onLogin", "false").param("login", "login")
				.param("onEmail", "false").param("email", "email@example.es").param("onPassword", "false").param("password", "1234")
				.param("rePassword", "1233")).andExpect(view().name("redirect:/profile"));
	}

	/**
	 * Update profile succesfull not changed.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullNotChanged() throws Exception {
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(post("/profile").locale(Locale.ENGLISH).session(defaultSession).param("name", "name").param("dni", "95716045G")
				.param("address", "address").param("mobile", "12356789").param("cp", "cp").param("mobile", "111111111").param("province", "province")
				.param("codeCountry", "EN").param("dateBirth", "1990-05-02")).andExpect(view().name("redirect:/profile"));
	}

	/**
	 * Not on and blanck message.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void verifiedGetAccountsMethodPaymentsTes() throws Exception {
		//Assert
		ProfileForm profileForm = new ProfileForm();
		assertNull(profileForm.getAccountTypes());
		assertNull(profileForm.getMethodPayments());

	}
}