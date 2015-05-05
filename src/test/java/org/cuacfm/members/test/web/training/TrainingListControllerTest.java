package org.cuacfm.members.test.web.training;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



/** The class TrainingTypeListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingListControllerTest extends WebSecurityConfigurationAware {

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
		Account trainer = new Account("trainer", "55555555C", "London", "trainer", "trainer@udc.es", 666666666, 666666666, "trainer", roles.ROLE_TRAINER);
		accountService.save(trainer);
        defaultSession = getDefaultSession("trainer");
    }

	
	
    /**
     * Display TrainingTypeList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayTrainingListPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingListTest() throws Exception {    
		mockMvc.perform(get("/trainingList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/traininglist"))
				.andExpect(content().string(containsString("<title>Trainings</title>")));
	}


	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingListwithDatabaseTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 10);		
		trainingService.save(training);
		
		mockMvc.perform(get("/trainingList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/traininglist"))
				.andExpect(
						content()
								.string(containsString("<title>Trainings</title>")));
	}
	
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingDelete/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));

		//Assert, it remove training
		assertEquals(trainingService.findById(training.getId()), null);
	}
	
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteExistInscriptionsInTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 10);		
		trainingService.save(training);
		Account user = new Account("user", "55555555B", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/trainingDelete/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));

		//Assert, it don´t remove training
		assertEquals(trainingService.findById(training.getId()), training);
	}
	
	/**
	 * Send trainingJoin.
	 * @throws Exception the exception
	 */
	@Test
	public void createInscriptionTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 10);		
		trainingService.save(training);
		Account user = new Account("user", "55555555B", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		
		mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	  /**
    * Send trainingJoin.
    * @throws Exception the exception
    */
   @Test
   public void userAlreadyJoinedExceptionTest() throws Exception {    
      TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
      trainingTypeService.save(trainingType);
      String dateTraining = "10:30,2015-12-05"; 
      Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
            "description", "place", 90, 10);    
      trainingService.save(training);
      Account user = new Account("user", "55555555B", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
      accountService.save(user);
      
      mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
      .andExpect(view().name("redirect:/trainingList"));
      
      mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
      .andExpect(view().name("redirect:/trainingList"));
   }
   
	/**
	 * Send trainingJoin MaxInscriptions.
	 * @throws Exception the exception
	 */
	@Test
	public void joinInscriptionTrainingListMaxInscriptionsTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 1);		
		trainingService.save(training);
		Account user = new Account("user", "55555555B", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send trainingJoin MaxInscriptions.
	 * @throws Exception the exception
	 */
	@Test
	public void JoinInscriptionMaxInscriptionsExceptionTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 1);		
		trainingService.save(training);

		mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
		
		mockMvc.perform(post("/trainingUserList/trainingRemoveJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * end trainingJoin DateLimitExpirationException.
	 * @throws Exception the exception
	 */
	@Test
	public void createInscriptionsDateLimitExpirationExceptionInTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-01-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 1);		
		trainingService.save(training);

		mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	/**
	 * Send trainingJoin MaxInscriptions UnsubscribeException.
	 * @throws Exception the exception
	 */
	@Test
	public void removeJoinInscriptionTrainingListUnsubscribeExceptionTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 1);		
		trainingService.save(training);

		mockMvc.perform(post("/trainingUserList/trainingJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
		
		mockMvc.perform(post("/trainingUserList/trainingRemoveJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
		
		mockMvc.perform(post("/trainingUserList/trainingRemoveJoin/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void TrainingEditFromTrainingListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", 90, 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
	}
}