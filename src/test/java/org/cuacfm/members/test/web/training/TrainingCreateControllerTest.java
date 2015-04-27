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
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
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
public class TrainingCreateControllerTest extends WebSecurityConfigurationAware {

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
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account trainer = new Account("trainer", "55555555C", "London", "trainer", "trainer@udc.es", 666666666, 666666666, "trainer", roles.ROLE_TRAINER);
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
    public void displayTrainingCreatePageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingList/trainingCreate")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send redirect TrainingList Because Training Is Null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {    		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(get("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send displaysTrainingList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(get("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/trainingcreate"))
				.andExpect(content().string(containsString("<title>Create Training</title>")));
	}

	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void postTrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "2015-12-05")
				.param("timeTraining", "10:30")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
		.andExpect(view().name("redirect:/trainingList"));
	}

	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersTrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "111111111111111111111111111111111111111111111111111111111111111")
				.param("timeLimit", "")
				.param("dateLimit", "")
				.param("timeTraining", "")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "11111111111111111111111111111111111111111111111111111111111111")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("Maximum 30 characters")))
                		.andExpect(view().name("training/trainingcreate"));
	}
	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlankTrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "")
				.param("dateLimit", "")
				.param("timeTraining", "")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingcreate"));
	}
	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlank2TrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "")
				.param("timeLimit", "")
				.param("dateLimit", "2015-12-05")
				.param("timeTraining", "")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingcreate"));
	}
	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlank3TrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "")
				.param("timeTraining", "10:30")
				.param("dateTraining", "")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("training/trainingcreate"));
	}
	
	/**
	 * Send displaysTrainingCreate.
	 * @throws Exception the exception
	 */
	@Test
	public void dataLimitExceptionTrainingCreateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingList/trainingLoad").locale(Locale.ENGLISH).session(defaultSession)
				.param("trainingTypeId", String.valueOf(trainingType.getId())))
		.andExpect(view().name("redirect:/trainingList/trainingCreate"));
		
		mockMvc.perform(post("/trainingList/trainingCreate").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("timeLimit", "10:30")
				.param("dateLimit", "2015-13-05")
				.param("timeTraining", "10:30")
				.param("dateTraining", "2015-12-05")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "90")
				.param("countPlaces", "2")
				.param("maxPlaces", "10")
				.param("close", "false"))
				.andExpect(content()
                		.string(containsString("The date limit should be before or equal to date training.")))
                		.andExpect(view().name("training/trainingcreate"));
	}
}