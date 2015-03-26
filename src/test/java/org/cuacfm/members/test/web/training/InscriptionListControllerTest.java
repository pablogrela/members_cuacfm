package org.cuacfm.members.test.web.training;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.inscription.Inscription;
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



/** The class InscriptionListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InscriptionListControllerTest extends WebSecurityConfigurationAware {

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
     * Display TrainingView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/trainingList/inscriptions")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysTrainingView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("training/inscriptionlist"))
				.andExpect(content().string(containsString("<title>Inscriptions</title>")));
	}	
	
	/**
	 * Send redirect TrainingList Because Training Is Null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {    
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList"));
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void postUpdateTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user", "user", "email@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(get("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(content()
         .string(containsString(user.getName())));
		
		mockMvc.perform(post("/trainingList/inscriptionList/save").locale(Locale.ENGLISH).session(defaultSession)
				.param("update", "update"))
				//.param("attend", "true")
				//.param("note", "He is a good student")
				//.param("pass", "true")
				//.param("unsubscribe", "true")
				//)
		//.andExpect(view().name("redirect:/trainingList")
				;
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void postSaveTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user", "user", "email@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		List<Inscription> inscriptions = trainingService.getInscriptionsByTrainingId(training.getId());
		System.out.println("incriptions: " + inscriptions + "tamaño: " + inscriptions.size());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		
		mockMvc.perform(post("/trainingList/inscriptionList/save").locale(Locale.ENGLISH).session(defaultSession)
				.param("save", "save")
				.param("inscriptions", String.valueOf(inscriptions))
				//.param("attend", "true")
				//.param("note", "He is a good student")
				//.param("pass", "true")
				//.param("unsubscribe", "true")
				//)
		//.andExpect(view().name("redirect:/trainingList")
				);
	}
	
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void blankMessageByIncriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", ""))
				.andExpect(content()
                .string(containsString("The value may not be empty!")))
                .andExpect(view().name("training/inscriptionlist"));
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void noExistLoginByInscriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", "No exist login"))
				.andExpect(content()
                .string(containsString("No exist login")))
                .andExpect(view().name("training/inscriptionlist"));
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void loginAlreadyExistByInscriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));

		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user.getLogin()))
				.andExpect(content()
                .string(containsString("User user2 already have inscription")))
                .andExpect(view().name("training/inscriptionlist"));
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void maxInscriptionsExceptionByInscriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 1);		
		trainingService.save(training);
		Account user = new Account("user", "user", "email@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		Account user2 = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user2);
		trainingService.createInscription(user.getId(), training.getId());
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user2.getLogin()))
	            .andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}
	
	/**
	 * Send displaysInscriptionList with DateLimitExpirationException.
	 * @throws Exception the exception
	 */
	@Test
	public void createInscriptionsDateLimitExpirationExceptionByInscriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-01-01";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 1);		
		trainingService.save(training);
		Account user = new Account("user", "user", "email@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user.getLogin()))
	            .andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void newInscriptionByInscriptionListTest() throws Exception {    
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";	
		Training training = new Training (trainingType, "training1", DisplayDate.stringToDate(dateTraining),DisplayDate.stringToDate(dateTraining), 
				"description", "place", Float.valueOf((float) 2.3), 10);		
		trainingService.save(training);
		Account user = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
		
		mockMvc.perform(post("/trainingList/inscriptionList/"+training.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/trainingList/inscriptionList"));
		
		mockMvc.perform(post("/trainingList/inscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user.getLogin()))
                .andExpect(view().name("redirect:/trainingList/inscriptionList"));
	}
}