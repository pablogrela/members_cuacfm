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



/** The class ProfileControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingEditControllerTest extends WebSecurityConfigurationAware {

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
     * Display TrainingList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayTrainingEditPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingList/trainingEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send redirect TrainingList Because Training Is Null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {    
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(get("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(get("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/trainingedit"))
				.andExpect(content().string(containsString("<title>Edit Training</title>")));
	}

	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void postTrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "2015-12-05")
				.param("timeTraining", "10:30")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
		.andExpect(view().name("redirect:/trainingList"));
	}

	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersTrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "111111111111111111111111111111111111111111111111111111111111111")
				.param("timeLimit", "")
				.param("dateLimit", "")
				.param("timeTraining", "")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "11111111111111111111111111111111111111111111111111111111111111")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("Maximum 30 characters")))
                		.andExpect(view().name("training/trainingedit"));
	}
	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlankTrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "")
				.param("dateLimit", "")
				.param("timeTraining", "")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingedit"));
	}
	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlank2TrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "")
				.param("timeLimit", "")
				.param("dateLimit", "2015-12-05")
				.param("timeTraining", "")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingedit"));
	}
	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlank3TrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "")
				.param("timeTraining", "10:30")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingedit"));
	}
	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dateLimitTrainingEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "2015-13-05")
				.param("timeTraining", "10:30")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The date limit should be before or equal to date training.")))
                		.andExpect(view().name("training/trainingedit"));
	}
	
	
	/**
	 * Send displaysTrainingEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void countPlaceExceptionTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		
		mockMvc.perform(post("/trainingList/trainingEdit/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingEdit"));
		
		mockMvc.perform(post("/trainingList/trainingEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "2015-12-05")
				.param("timeTraining", "10:30")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6")
				.param("countPlaces", "12")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The number of reservations can not exceed 10")))
                		.andExpect(view().name("training/trainingedit"));
	}
}