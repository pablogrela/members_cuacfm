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
package org.cuacfm.members.test.web.training;

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
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class TrainingViewControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingViewControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The training Type service. */
	@Inject
	private TrainingTypeService trainingTypeService;

	/** The training service. */
	@Inject
	private TrainingService trainingService;

	/**
	 * Initialize default session.
	 * 
	 * @throws UniqueException
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account trainer = new Account("trainer", "", "55555555C", "London", "trainer", "trainer@udc.es", "666666666", "666666666", "trainer",
				roles.ROLE_USER);
		trainer.addPermissions(permissions.ROLE_TRAINER);
		accountService.save(trainer);
		defaultSession = getDefaultSession("trainer@udc.es");
	}

	/**
	 * Display TrainingView page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayTrainingViewPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/trainingList/trainingView")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysTrainingView.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingViewTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "2015-12-05 10:30";
		Training training = new Training(trainingType, "training1", DateUtils.format(dateTraining, DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format(dateTraining, DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		mockMvc.perform(post("/trainingList/trainingView/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/trainingView"));

		mockMvc.perform(get("/trainingList/trainingView").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/trainingview")).andExpect(content().string(containsString("<title>View session</title>")));
	}

	/**
	 * Send redirect TrainingList Because Training Is Null.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {
		mockMvc.perform(post("/trainingList/trainingView/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/trainingView"));

		mockMvc.perform(get("/trainingList/trainingView").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList"));
	}

}