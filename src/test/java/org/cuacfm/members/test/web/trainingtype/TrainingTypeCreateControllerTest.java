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
package org.cuacfm.members.test.web.trainingtype;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ProfileControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingTypeCreateControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The training Type service. */
	@Inject
	private TrainingTypeService trainingTypeService;

	/**
	 * Initialize default session.
	 * 
	 * @throws UniqueException
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account trainer = new Account("trainer", "", "55555555C", "London", "trainer", "trainer@udc.es", "666666666", "666666666", "trainer", roles.ROLE_USER);
		trainer.addPermissions(permissions.ROLE_TRAINER);
		accountService.save(trainer);
		defaultSession = getDefaultSession("trainer@udc.es");
	}

	/**
	 * Display TrainingTypeList page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayTrainingCreateCreatePageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/trainingTypeList/trainingTypeCreate")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysTrainingTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingTypeCreateTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		mockMvc.perform(get("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("trainingtype/trainingtypecreate"))
				.andExpect(content().string(containsString("<title>Create training type</title>")));
	}

	/**
	 * Send displaysTrainingTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postTrainingTypeCreateTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		mockMvc.perform(post("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession).param("name", "Locution2")
				.param("required", "false").param("description", "Very interesting2").param("place", "livingRoom2").param("duration", "90"))
				.andExpect(view().name("redirect:/trainingTypeList"));
	}

	/**
	 * Send displaysTrainingTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void nameAlreadExistTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		mockMvc.perform(post("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession).param("name", "Locution")
				.param("required", "false").param("description", "Very interesting2").param("place", "livingRoom2").param("duration", "90"))
				.andExpect(content()
						.string(containsString("Already exist type of formation with name " + trainingType.getName() + ", please choose another.")))
				.andExpect(view().name("trainingtype/trainingtypecreate"));

	}

	/**
	 * notBlankMessage.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		mockMvc.perform(post("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content().string(containsString("The value may not be empty!"))).andExpect(view().name("trainingtype/trainingtypecreate"));

	}

	/**
	 * "Already exist type of formation with name "+ trainingType.getName() + ", please chose other" Send displaysTrainingTypeList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		mockMvc.perform(post("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "1111111111111111111111111111111111111111111111111111111").param("required", "false")
				.param("place", "111111111111111111111111111111111111111111111111111111").param("duration", "90"))
				.andExpect(content().string(containsString("Maximum 50 characters"))).andExpect(view().name("trainingtype/trainingtypecreate"));

	}
}