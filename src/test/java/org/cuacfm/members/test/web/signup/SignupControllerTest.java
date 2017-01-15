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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class SignupControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SignupControllerTest extends WebAppConfigurationAware {

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
		Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666, Double.valueOf(24),
				Double.valueOf(25), "Rul");
		configurationService.save(configuration);
	}

	/**
	 * Send sign up form.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displaysSignupForm() throws Exception {
		mockMvc.perform(get("/signup").locale(Locale.ENGLISH)).andExpect(model().attributeExists("signupForm"))
				.andExpect(view().name("signup/signup"))
				.andExpect(content().string(allOf(containsString("<title>Sign Up</title>"),
						containsString("<legend>Please Sign Up</legend>"))));
	}

	/**
	 * Send sign up form with blank messages.
	 * 
	 * @throws Exception
	 *             the exception
	 **/
	@Test
	public void blankMessages() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", " ").param("login", " ")
				.param("email", " ").param("password", " ").param("rePassword", " ").param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))

		.andExpect(content().string(containsString("The value may not be empty!")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with dni existent.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void dniAlreadyExists() throws Exception {
		Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666",
				"demo", roles.ROLE_USER);
		accountServiceMock.save(demoUser);

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C")
				.param("address", "London").param("login", "user").param("email", "user@example.es")
				.param("phone", "12356789").param("mobile", "12356789").param("programName", "12356789")
				.param("password", "1234").param("rePassword", "1234").param("rule", "true").param("student", "true").param("emitProgram", "true")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(content().string(containsString("Already existent dni 55555555C, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with email existent.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void emailAlreadyExists() throws Exception {
		Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666",
				"demo", roles.ROLE_USER);
		accountServiceMock.save(demoUser);

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "11111111F")
				.param("address", "London").param("login", "user2").param("email", "user@udc.es")
				.param("phone", "12356789").param("mobile", "12356789").param("programName", "12356789")
				.param("password", "1234").param("rePassword", "1234").param("rule", "true").param("student", "true").param("emitProgram", "true")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(
						content().string(containsString("Already existent email user@udc.es, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Incorrect email format.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void incorrectEmailFormat() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C")
				.param("login", "login").param("email", "email").param("password", "1234").param("rePassword", "1234")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(content().string(containsString("The value must be a valid email!")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Incorrect email format.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void incorrectEmailFormatDirect() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login")
				.param("email", "email").param("password", "1234").param("rePassword", "1234").param("rule", "true")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(content().string(containsString("The value must be a valid email!")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with login existent.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void loginAlreadyExist() throws Exception {
		Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666",
				"demo", roles.ROLE_USER);

		accountServiceMock.save(demoUser);
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "11111111F")
				.param("address", "London").param("login", "user").param("email", "email@example.es")
				.param("phone", "12356789").param("mobile", "12356789").param("programName", "12356789")
				.param("password", "1234").param("rePassword", "1234").param("rule", "true").param("student", "true").param("emitProgram", "true")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(content().string(containsString("Already existent login user, please choose another")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with more of 30 characters.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void maximumCharacters() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "1234567890123456789012345678903453444444444444444445345345345345345351")
				.param("login", "1234567890123456789012345678903453453534534535345345345341")
				.param("email", "1234567890123456789012345678901@example.es")
				.param("password", "1234567890123456789012345678901")
				.param("rePassword", "1234567890123456789012345678901").param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))
				.andExpect(content().string(containsString("Maximum 50 characters")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with insuficient characters in password.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void insuficientCharactersPasswords() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login")
				.param("email", "email@example.es").param("password", "12").param("rePassword", "12")
				.param("g-recaptcha-response",
						"03AHJ_VuvpwN_rZWFIqT34HNbDM0NWvTupj7zpEPoEtZ_enmgoGvYQjZEEAbJt80m9so_yAo3e6J9frhLs-5hrLxpmCE-1Xwk0vaAtaK9eoKO2cNGl-Boz4Tx8-4icsnQCbjCf-bRreNjSp1SicP1NV6iGy_Tb6zwT2Zy6tpNZCSCZ7O0RmjeAwmrGYO794A3slNu_6MafCbDe_32GzboC2ggJ7udtteQwe5V5FwRLvkPWD_2zGBaGstnUemwU07gXTRv5Nl7tnxmvxXDrkG7QjLbZdZJZ5N2EmUd6evaQhKLCdnF4N9BVdeaVIXIKjo9SVs4OOlWkNFHSnzuCWplW15YgAx10GWhyPoFWU2mTD3Jms12KTa_PWUXCd1nm4uXDvK72VTJ0JgnQ2jcPvs_NckMU61RHOUZYCeBmtfe0RJ06I_E8BfssiPx0dy_Rh808O5KYUIy97tenpN00UEmHcakQCblxEM7hlarB9KoN0jmJUcA5s5Zve7Ipd32xm2NevM5BjzmcGa-f_YeyCdBeYiDVfcFSxg8bKxbV0X32FoOUuJqHOcRTw2UzmxZk86wXRWmoKKyp8_jua2Uwb4JVoJjiXRO_PNwQKYsgQQC1O1NrAKzM5fXXI5Y0umxFmj5K3vmj0dxEV-YmVfsJ4shryUMSS7QJ9ZOpnIL7rx2p5bimhmGbbcjYiS8M-8se0PhYVLE_hiQsYfXz5x-nJ0WBVU_c5kQN3B03d9RCIaJmkaypFTtbHq9bEIc"))

		.andExpect(content().string(containsString("The password should be to have between 4 and 20 characters.")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Send sign up form with diferent password.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void diferentPasswords() throws Exception {

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("login", "login")
				.param("email", "email@example.es").param("password", "1234").param("rePassword", "1233")
				.param("g-recaptcha-response",
						"03AHJ_Vuvvra51nw9MNKKHKlqizfquXnc3EaRnkEEG1vqmQB0EyOP14G8CI_ySDURHAgrEJzyxYmuFZP93inLceuREhmj2dSw0T4cp3vyfAw4PyDFdo1c_Kq7zDI578DjOsH45uDmGGkPU7UCryBArGYb1NPkgI45ypo0N4Vl4Sz1O2JNmH_07oGoHesal5sJhxAuQ8ExIrJeiWUfx2kmEaHftpwuGaOqgpjDul6ceqBRduEuoP-TJgkd04A3syny60Ng4eu4-OikQH9jieLy7VMLwKwFbkfaM8hSAHWldqlzpM_aF9RFxpzlc2OTS4bYS0f5Gtj9oHgSdgizMnYxy0Bltgxa1l0Sp3I30KbLJNyEvpkBntdWRvcxAG6hp8tFY8NgsNGKVsjWB30nNDTGZgJr6JeVKgJLpyADDYJvSAUUCTGRg-vo9odWI-fHSE8BxFV72pPiK_u42E7X-9TtA5EaViDFo98jQQ0uN_ggp-U85BOqWS0-pnz1W0HgZP-jPNoW074pvKmWqSCQqg6UR-ctPGx-liJdRV8bf3ldA34dHxIkroP7pKa2M1U2drf8KbBtqHiSXhaQB-jr2RmKmh79N6fQjkmWtKD9l0595wDS5IueBvO3kT0-xHIZiHm54lIE3uEKV3nCBQcC6GzEFCzasYBtFKUdO6T-0o01UOO0-wKsxq4R4JNHToOYJflNxi6FoiOswkbHD"))
				.andExpect(content().string(containsString("Passwords are not equal")))
				.andExpect(view().name("signup/signup"));
	}

	/**
	 * Succesful sign up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void succesfulSignUp() throws Exception {

		mockMvc.perform(post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C")
				.param("address", "London").param("login", "login").param("email", "email@example.es")
				.param("phone", "12356789").param("mobile", "12356789").param("programName", "12356789")
				.param("password", "1234").param("rePassword", "1234").param("rule", "true").param("student", "true").param("emitProgram", "true")
				.param("g-recaptcha-response",
						"03AHJ_VuswDtrYsfRwWDSklt6tL-YD1auPU976yaltsP5gC2zt5PCLifA6naJJ3uF56IGMnm006Qqw7PgHcwfujkR0ia_YIyGGnBiI0yzPA3I7VIK3wVHhuxrGFhbCL7fF-JqBymsU67uhVVHer1bplhX-8Nj6RceE5iGhbknkfIIdQUPSmak9ef2Pi2OkYNomqtFd20Xnh-xwSpQj3hKyc6ndSCvCCjhU0Lg75DJH1GkuPq1tMI0w4iYRFqfQ3xna146LQ0EF3fPlG_Nz3KUQl8rZGFoIAbbHIOTMwLcCStQEHeYXtY7odp1K6cmJUfKs49PaOEmMBezqs_eK4csZd2ihN9PDM8bePHxMCahJbfVOKYeqO3HwAtAdxKlNncBXfl0biuPWYLSs1R9BwACfuJiyq9ZbanXoW18fHZyMhep7QGDP5ni-Nv-D23gljB6xO5ffGMkR52CjuEw3OTC4gOiT35UZwBb8kBi38Rx1io-Qr1GVp36sBPSq9Nh3P-4SEo1OBoys5ay9zpYRCFaEr43iIbUh983Fkg7awmhcsek7xNnhdzBzJ4CYx3LIBczdzocvkB9gInVKbjN2XuY-k75wSZGbiCTUbny0guBrLjdTCXeMCxPkPaYW2Fm4Mnv50gtcF2mHFoPNsgDp202IL_FqBXwfCG2mCZqXxpeXFjPOdowDsuhsEJtTAdimo9mbEIfmBMtzNxxPM1pu1dZ6G6GikA6pje4tcA"))
				.andExpect(redirectedUrl("/"));
	}

	/**
	 * Appear Error Page.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displaysErrorPage() throws Exception {
		mockMvc.perform(post("/signup").locale(Locale.ENGLISH))
				.andExpect(content().string(containsString("<title>Error page</title>")));
	}
}