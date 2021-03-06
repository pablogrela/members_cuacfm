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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.training.InscriptionsForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class InscriptionListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InscriptionListControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Inject
	private AccountService accountService;

	@Inject
	private TrainingTypeService trainingTypeService;

	@Inject
	private TrainingService trainingService;

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
	 * Display TrainingView page without signin in test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/trainingList/inscriptions")).andExpect(redirectedUrl("http://localhost/signin"));
	}

	/**
	 * Send displaysTrainingView.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/inscriptionlist")).andExpect(content().string(containsString("<title>Inscriptions</title>")));
	}

	/**
	 * Send redirect TrainingList Because Training Is Null.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {

		mockMvc.perform(post("/trainingList/inscriptionList/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList"));
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postUpdateTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content().string(containsString(user.getName())));

		List<Inscription> inscriptions = trainingService.getInscriptionsByTrainingId(training.getId());
		InscriptionsForm inscriptionsForm = new InscriptionsForm();
		inscriptionsForm.setInscriptions(inscriptions);

		mockMvc.perform(post("/trainingList/inscriptionList/save").locale(Locale.ENGLISH).session(defaultSession).param("submit", "update")
		.param("inscriptionsForm", String.valueOf(inscriptionsForm))
		.param("inscriptions", String.valueOf(inscriptions))
		.param("attend", "true")
		.param("note", "He is a good student")
		.param("pass", "true")
		.param("unsubscribe", "true")
		//)
		//.andExpect(view().name("redirect:/trainingList")
		);
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void postSaveTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		for(Inscription inscription : trainingService.getInscriptionsAll()){
			trainingService.deleteInscription(inscription.getAccount().getId(), inscription.getTraining().getId());
		}
		
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);
		List<Inscription> inscriptions = trainingService.getInscriptionsByTrainingId(training.getId());

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList/save").locale(Locale.ENGLISH).session(defaultSession).param("submit", "save")
		.param("inscriptions", String.valueOf(inscriptions))
		.param("attend", "true")
		.param("note", "He is a good student")
		.param("pass", "true")
		.param("unsubscribe", "true")
		//)
		//.andExpect(view().name("redirect:/trainingList")
		);
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void blankMessageByIncriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login", ""))
				.andExpect(content().string(containsString("The value may not be empty!"))).andExpect(view().name("training/inscriptionlist"));
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void noExistLoginByInscriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login", "No exist login"))
				.andExpect(content().string(containsString("No exist login"))).andExpect(view().name("training/inscriptionlist"));
	}

	/**
	 * login Already Exist By InscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void loginAlreadyExistByInscriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/inscriptionlist")).andExpect(content().string(containsString("<title>Inscriptions</title>")));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login",
				user.getId() + ": " + user.getLogin())).andExpect(content().string(containsString("User user already has an inscription.")))
				.andExpect(view().name("training/inscriptionlist"));
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void maxInscriptionsExceptionByInscriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		Account user2 = new Account("user2", "2", "55555555A", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user2);
		trainingService.createInscription(user, training);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login",
				user2.getId() + ": " + user.getLogin())).andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}

	/**
	 * Send displaysInscriptionList with DateLimitExpirationException.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void createInscriptionsDateLimitExpirationExceptionByInscriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login",
				user.getId() + ": " + user.getLogin())).andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}

	/**
	 * Send displaysInscriptionList.
	 * 
	 * @throws Exception the exception
	 */
	@Test
	public void newInscriptionByInscriptionListTest() throws Exception {
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);
		Account user = new Account("user", "1", "55555555B", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);

		mockMvc.perform(post("/trainingList/inscriptionList/" + training.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession).param("login",
				user.getId() + ": " + user.getLogin())).andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}
}