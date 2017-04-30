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
package org.cuacfm.members.test.web.signup;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
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
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.test.config.WebAppConfigurationAware;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class SignupControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SignupControllerTest extends WebAppConfigurationAware {

	// Puede dar fallo de capthca erroreno
	private static final String KEY = "03AHJ_VuvqYXuoskARGRCMeWTSNE-IOq7iC9wPVuVjDdEaQdzQTz6vCOSIki14anHed50uYq2iu5Q7-uS579V3GpZkv1LFq3nVk4HosQNI4Kq-jD3WmhlcXDRuOeiwtFC_rVpTQLMZNamCvDoKi4CscN8rBGPKwogAFXO_0NTUePTd98LMOh1e6dkbK0aNbQPmB8jd6wSlC8UnTVLLyHD3ml_3pRe6VWMjqZdnhz2Y5LhQXlwSavdQt0Ew0XeP1phNAzLEMcKYLGw9qQVM4qankfurhYOy5Tjo54Xkz5jpax1LsJFeNZOCpVMSHF8MzY5K_Iy4qWFFcshZ4tMrIfk3MDukLrXjeVwpZnSjCq9VCkcwq1hBeEJ3C9ZqNfa4DbCfPzqqO4fPIfGdmu8SgrM5azZTze3inaSK-NJJ0aEgjIt4N3SZk1tQ25_9TT74Gx8PES_eSLIrVRKej9yp9nM_9G_NwNiWnu0M-azAMituf3w-CcI_SWC2M6iEX1WqnRNLzlrgufbWYJ_kCBM1hcveSRgghgs9M9VU4XuiriDp8XHfILI7GhfKDgVhrJyvihRcqy56qQ_1TqyoTZtuNNHMrjqY48R9tVO99V9QhNkp7IKZhPyPtcrxLlLRrY9nVOOujma9gkRXLzOpHGUoeRXN7a_6PlPrkDYga8vvkSbWcy5QWnh922HGs1CmdUNcvmyMjZMsJBXWTqyvRy8FBMEk1ibaUZFGxYO-yS61AaOxDg7fmNrtiRGr_3sA9M4K7msa7de8NQjsJWYEXDvJcp3ExbFweTyQji934zFUwTtbl6PaPyWuGuDFjwJELyp_cOKxOOS3r0KXR5cpYAiFgX3aZaqpBMgqnhfd7BJrxHeQC0d_lhf6XSOe9Iaw8Kojm0xMX2ZD-LtyZb28Spey3M_Qdm22vOduvTY0XfKMTPBp0CT-k-KRT0SiKqcuCK7eu8axx1J1PHjFIkEihQ0DRG8CsSnb0FNX35QEPwvrTQzpOZB3jWem3aIB1-mpyKr93ncIG4vzLc3SVwCNdcTb9qgOCdoibNV2hMJ4DhmWAIsHD-tuKXZGx6pSshs";
	
	/** The configuration service. */
	@Inject
	private ConfigurationService configurationService;

	/** The account service mock. */
	@Inject
	private AccountService accountServiceMock;

	/**
	 * Initialize default session.
	 */
	@Before
	public void initializeDefaultSession() {
		Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666, Double.valueOf(24), Double.valueOf(25), "Rul");
		configurationService.save(configuration);
	}

	/**
	 * Send sign up form.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysSignupForm() throws Exception {
		mockMvc.perform(get("/signup").locale(Locale.ENGLISH)).andExpect(model().attributeExists("signupForm"))
				.andExpect(view().name("signup/signup"))
				.andExpect(content().string(allOf(containsString("<title>Sign Up</title>"), containsString("<legend>Please Sign Up</legend>"))));
	}

	/**
	 * Send sign up form with blank messages.
	 * 
	 * @throws Exception the exception
	 **/
	@Test
	public void blankMessages() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", " ").param("login", " ").param("email", " ").param("password", " ")
				.param("rePassword", " ").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("The value may not be empty!"))).andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with dni existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Falla el validar el recaptcha
	public void dniAlreadyExists() throws Exception {
		Account demoUser = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountServiceMock.save(demoUser);

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C").param("address", "London")
				.param("login", "user").param("email", "user@example.es").param("phone", "12356789").param("mobile", "12356789")
				.param("programName", "12356789").param("password", "123456").param("rePassword", "123456").param("rule", "true")
				.param("student", "true").param("emitProgram", "true").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("Already existent dni 55555555C, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with email existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Falla el validar el recaptcha
	public void emailAlreadyExists() throws Exception {
		Account demoUser = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountServiceMock.save(demoUser);

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "11111111F").param("address", "London")
				.param("login", "user2").param("email", "user@udc.es").param("phone", "12356789").param("mobile", "12356789")
				.param("programName", "12356789").param("password", "123456").param("rePassword", "123456").param("rule", "true")
				.param("student", "true").param("emitProgram", "true").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("Already existent email user@udc.es, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Incorrect email format.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void incorrectEmailFormat() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C").param("login", "login")
				.param("email", "email").param("password", "123456").param("rePassword", "123456").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("The value must be a valid email!"))).andExpect(view().name("signup/signup"));
	}

	/**
	 * Incorrect email format.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void incorrectEmailFormatDirect() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login").param("email", "email")
				.param("password", "123456").param("rePassword", "123456").param("rule", "true").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("The value must be a valid email!"))).andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with login existent.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Falla el validar el recaptcha
	public void loginAlreadyExist() throws Exception {
		Account demoUser = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);

		accountServiceMock.save(demoUser);
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "11111111F").param("address", "London")
				.param("login", "user").param("email", "email@example.es").param("phone", "12356789").param("mobile", "12356789")
				.param("programName", "12356789").param("student", "true").param("emitProgram", "true").param("password", "123456")
				.param("rePassword", "123456").param("rule", "true").param("student", "true").param("emitProgram", "true")
				.param("g-recaptcha-response", KEY)).andExpect(content().string(containsString("Already existent login user, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with more of 30 characters.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maximumCharacters() throws Exception {
		mockMvc.perform(
				post("/signup").locale(Locale.ENGLISH).param("name", "1234565678901234565678901234565678903453444444444444444445345345345345345351")
						.param("login", "1234565678901234565678901234565678903453453534534535345345345341")
						.param("email", "1234565678901234565678901234565678901@example.es").param("password", "1234565678901234565678901234565678901")
						.param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("Maximum 50 characters"))).andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with insuficient characters in password.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void insuficientCharactersPasswords() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login").param("email", "email@example.es")
				.param("password", "12").param("rePassword", "12").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("The password should be to have between 6 and 20 characters.")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with diferent password.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void diferentPasswords() throws Exception {

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login").param("email", "email@example.es")
				.param("password", "123456").param("rePassword", "1233").param("g-recaptcha-response", KEY))
				.andExpect(content().string(containsString("Passwords are not equal"))).andExpect(view().name("signup/signup"));
	}

	/**
	 * Succesful sign up.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Ignore
	// Falla el validar el recaptcha
	public void succesfulSignUp() throws Exception {

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C").param("address", "London")
				.param("login", "login").param("email", "email@example.es").param("phone", "12356789").param("mobile", "12356789")
				.param("programName", "12356789").param("password", "123456").param("rePassword", "123456").param("rule", "true")
				.param("student", "true").param("emitProgram", "true").param("g-recaptcha-response", KEY)).andExpect(redirectedUrl("/"));
	}

	/**
	 * Appear Error Page.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysErrorPage() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH)).andExpect(content().string(containsString("<title>Error page</title>")));
	}
}