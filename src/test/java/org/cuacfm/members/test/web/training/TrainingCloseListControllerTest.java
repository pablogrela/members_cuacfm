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

import java.util.Locale;

import org.cuacfm.members.model.account.Account;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class TrainingCloseListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingCloseListControllerTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Autowired
	private AccountService accountService;
	
	/** The training Type service. */
	@Autowired
	private TrainingTypeService trainingTypeService;
	
	/** The training service. */
	@Autowired
	private TrainingService trainingService;
	
    /**
     * Initialize default session.
     *
     * @throws UniqueException the unique exception
     */
    @Before
    public void initializeDefaultSession() throws UniqueException, UniqueListException {
		Account trainer = new Account("trainer", "", "55555555C", "London", "trainer", "trainer@udc.es", "666666666", "666666666", "trainer", roles.ROLE_TRAINER);
		accountService.save(trainer);
        defaultSession = getDefaultSession("trainer@udc.es");
    }

	
	
    /**
     * Display TrainingTypeList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayTrainingCloseListPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingCloseList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingCloseListTest() throws Exception {    
		mockMvc.perform(get("/trainingList/trainingCloseList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/trainingcloselist"))
				.andExpect(content().string(containsString("<title>Sessions close</title>")));
	}
	
   /**
    * Displays training close list by year test.
    *
    * @throws Exception the exception
    */
   @Test
   public void displaysTrainingCloseListByYearTest() throws Exception {    
      mockMvc.perform(post("/trainingList/trainingCloseList").locale(Locale.ENGLISH).session(defaultSession)
            .param("year", "2015"))
            .andExpect(view().name("redirect:/trainingList/trainingCloseList"));
   }

	/**
	 * Send displaysTrainingCloseList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingCloseListwithDatabaseTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DateUtils.stringToDate(dateTraining),DateUtils.stringToDate(dateTraining), 
				"description", "place", 90, 10);
		training.setClose(true);
		trainingService.save(training);
		
		mockMvc.perform(get("/trainingList/trainingCloseList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/trainingcloselist"))
				.andExpect(
						content()
								.string(containsString("<title>Sessions close</title>")));
	}
}