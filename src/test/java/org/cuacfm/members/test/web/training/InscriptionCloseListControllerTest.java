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
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
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
public class InscriptionCloseListControllerTest extends WebSecurityConfigurationAware {

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
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account trainer = new Account("trainer", "55555555C", "London", "trainer", "trainer@udc.es", "666666666", "666666666", "trainer", roles.ROLE_TRAINER);
		accountService.save(trainer);
        defaultSession = getDefaultSession("trainer");
    }

	
    /**
     * Display InscriptionCloseList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayInscriptionCloseListViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingList/inscriptioncloseList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send InscriptionCloseList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionCloseListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		LocalDateTime dateTraining = LocalDateTime.now().plusMonths(10);
	    Date expiryDate = Date.from(dateTraining.toInstant(ZoneOffset.UTC));
	    
		Training training = new Training (trainingType, "training1", expiryDate, expiryDate, 
				"description", "place", 90, 10);		
		trainingService.save(training);
		Account user = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionCloseList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionCloseList"));
		
		mockMvc.perform(get("/trainingList/inscriptionCloseList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/inscriptioncloselist"))
				.andExpect(content().string(containsString("<title>Inscriptions close</title>")));
	}	
	
	/**
	 * Send redirect TrainingCloseList Because Training Is Null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingCloseListBecauseTrainingIsNullTest() throws Exception {    
		
		mockMvc.perform(post("/trainingList/inscriptionCloseList/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionCloseList"));
		
		mockMvc.perform(get("/trainingList/inscriptionCloseList").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingCloseList"));
	}
}