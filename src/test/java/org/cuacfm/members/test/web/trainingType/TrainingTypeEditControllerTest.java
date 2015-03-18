package org.cuacfm.members.test.web.trainingType;

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
public class TrainingTypeEditControllerTest extends WebSecurityConfigurationAware {

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
    public void displayTrainingTypeEditPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingTypeList/trainingTypeEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
  
    
	/**
	 * Send null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingTypeIsNullTest() throws Exception {    
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
		
		mockMvc.perform(get("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysTrainingTypeEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
		
		mockMvc.perform(get("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("trainingtype/trainingtypedit"))
				.andExpect(content().string(containsString("<title>Modify type of training</title>")));
	}


	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void postTrainingTypeEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution2")
				.param("required", "false")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6"))
		.andExpect(view().name("redirect:/trainingTypeList"));
	}

	/**
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void nameAlreadExistTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Locution")
				.param("required", "false")
				.param("description", "Very interesting2")
				.param("place", "livingRoom2")
				.param("duration", "2.6"))
				.andExpect(content()
                        		.string(containsString("Already exist type of formation with name "+ trainingType.getName() + ", please chose other")))
                        		.andExpect(view().name("trainingtype/trainingtypedit"));

	}
	
	/**
	 * notBlankMessage.
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageInTrainingTypeEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content()
                        		.string(containsString("The value may not be empty!")))
                        		.andExpect(view().name("trainingtype/trainingtypedit"));

	}
	
	/**
	 * "Already exist type of formation with name "+ trainingType.getName() + ", please chose other"
	 * Send displaysTrainingTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInTrainingTypeEditTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit/"+trainingType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingTypeList/trainingTypeEdit"));
		
		mockMvc.perform(post("/trainingTypeList/trainingTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "1111111111111111111111111111111111111111111111111111111")
				.param("required", "false")
				.param("description", "111111111111111111111111111111111111111111111111")
				.param("place", "111111111111111111111111111111111111111111111111111111")
				.param("duration", "2.6"))
				.andExpect(content()
                        		.string(containsString("Maximum 30 characters")))
                        		.andExpect(view().name("trainingtype/trainingtypedit"));

	}
}