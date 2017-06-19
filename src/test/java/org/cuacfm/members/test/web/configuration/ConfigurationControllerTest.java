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
import static org.junit.Assert.assertEquals;
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
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/** The class ConfigurationControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private MethodPaymentService methodPaymentService;

	@Autowired
	private ElementService elementService;

	private AccountType accountType;
	private MethodPayment methodPayment;
	private Element element;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account admin = new Account("admin", "", "55555555C", "London", "admin", "admin@udc.es", "666666666", "666666666", "admin", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("admin@udc.es");

		accountType = new AccountType("Adult", false, "Tax for Adult", 0);
		accountTypeService.save(accountType);

		methodPayment = new MethodPayment("Paypal", false, "Pay by Paypal");
		methodPaymentService.save(methodPayment);

		element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666, Double.valueOf(24), Double.valueOf(25), "Rul");
		configurationService.save(configuration);
		configuration.getId();
		configuration.toString();
	}

	/**
	 * Display Configuration page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayConfigurationPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/configuration")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysConfiguration.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displayConfiguration() throws Exception {
		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));
	}

	/**
	 * Send displaysConfiguration.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysConfigurationWithDatabase() throws Exception {
		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));
	}

	/**
	 * Delete account type test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteAccountTypeTest() throws Exception {
		mockMvc.perform(post("/configuration/accountTypeDelete/" + accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration"));

		// Assert, it remove accountType
		assertEquals(accountTypeService.findById(accountType.getId()), null);
	}

	/**
	 * Delete method payment test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteMethodPaymentTest() throws Exception {

		mockMvc.perform(post("/configuration/methodPaymentDelete/" + methodPayment.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration"));

		// Assert, it remove methodPayment
		assertEquals(methodPaymentService.findById(methodPayment.getId()), null);
	}

	/**
	 * Delete ement test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteEmentTest() throws Exception {
		mockMvc.perform(post("/configuration/elementDelete/" + element.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/configuration"));

		// Assert, it remove accountType
		assertEquals(accountTypeService.findById(element.getId()), null);
	}

	/**
	 * Json test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void jsonTest() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("file", "", "application/json", "{\"json\": \"someValue\"}".getBytes());

		mockMvc.perform(MockMvcRequestBuilders.fileUpload("accountList/uploadJson").file(jsonFile).locale(Locale.ENGLISH).session(defaultSession));

		mockMvc.perform(MockMvcRequestBuilders.fileUpload("programList/uploadJson").file(file).locale(Locale.ENGLISH).session(defaultSession));
	}

	/**
	 * Post configuration succesfull test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postConfigurationSuccesfullTest() throws Exception {

		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));

		mockMvc.perform(post("/configuration").locale(Locale.ENGLISH).session(defaultSession).param("name", "New Name").param("email", "email@udc.es")
				.param("phone", "111111").param("feeMember", "30").param("feeProgram", "32").param("descriptionRule", "New Description"))
				.andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Not blank message configuration test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageConfigurationTest() throws Exception {

		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));

		mockMvc.perform(post("/configuration").locale(Locale.ENGLISH).session(defaultSession).param("name", " ").param("email", " ")
				.param("phone", " ").param("descriptionRule", " ")).andExpect(view().name("configuration/configuration"));
	}

	/**
	 * Max characters in configuration test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInConfigurationTest() throws Exception {

		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));

		mockMvc.perform(post("/configuration").locale(Locale.ENGLISH).session(defaultSession).param("name", "111111111111111111111111111111111111")
				.param("email", "11111111111111111111111111111111111").param("phone", "11111111111111111111111111111111111")
				.param("descriptionRule", "1111111111111111111111111111")).andExpect(view().name("configuration/configuration"));
	}
}