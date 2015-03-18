package org.cuacfm.members.test.web.trainingType;

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
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingService.TrainingService;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
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
public class TrainingTypeListControllerTest extends WebSecurityConfigurationAware {

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
     */
    @Before
    public void initializeDefaultSession() {
		Account trainer = new Account("trainer", "trainer", "trainer@udc.es", "trainer", "ROLE_TRAINER");
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
    public void displayTrainingTypeListPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingTypeList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingTypeList() throws Exception {    
		mockMvc.perform(get("/trainingTypeList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("trainingtype/trainingtypelist"))
				.andExpect(content().string(containsString("<title>Types trainings</title>")));
	}


	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingTypeListWithDatabase() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(get("/trainingTypeList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("trainingtype/trainingtypelist"))
				.andExpect(content().string(containsString("<title>Types trainings</title>")));
	}
	
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteTrainingTypeList() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeDelete/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList"));

		//Assert, it remove trainingType
		assertEquals(trainingTypeService.findById(trainingType.getId()), null);
	}
	
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteExistTrainingsInTrainingTypeList() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeDelete/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList"));

		//Assert, it don´t remove trainingType
		assertEquals(trainingTypeService.findById(trainingType.getId()), trainingType);
	}
	
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void TrainingTypeEdit() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
	}
	
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void TrainingTypeCreate() throws Exception {    	
		mockMvc.perform(post("/trainingTypeList/trainingTypeCreate").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("trainingtype/trainingtypecreate"));
	}
}