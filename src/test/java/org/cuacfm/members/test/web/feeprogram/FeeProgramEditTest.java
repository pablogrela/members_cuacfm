package org.cuacfm.members.test.web.feeprogram;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class FeeProgramListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeProgramEditTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;

	/** The pay Inscription service. */
	@Inject
	private FeeProgramService feeProgramService;
	
	/** The pay inscription. */
	private FeeProgram feeProgram;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", 666666666, 666666666,"demo", roles.ROLE_ADMIN);
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
		//Create Payment
        Date date = DisplayDate.stringToMonthOfYear("2015-12");
        feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
        feeProgramService.save(feeProgram);
    }

	
    /**
     * Display TrainingView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayFeeProgramEditViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/feeProgramList/feeProgramEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysFeeProgramEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void postDisplaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
	}	
	
	/**
	 * Send displaysFeeProgramEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
		
		mockMvc.perform(get("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("feeprogram/feeprogramedit"))
				.andExpect(content().string(containsString("<title>Edit fee programs</title>")));
	}	
	
	/**
	 * Send displaysFeeProgramEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectFeeProgramListBecauseFeeProgramIsNullTest() throws Exception {    
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
		
		mockMvc.perform(get("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeProgramList"));
	}	
	
	/**
	 * Send displaysFeeProgramEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void postFeeProgramEditSuccesfulTest() throws Exception {    
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
		
		mockMvc.perform(post("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Pay april 2015")
				.param("price", "24")
				.param("date", "2015-04")
				.param("dateLimit", "2015-07")
				.param("description", "Pay of inscription april 2015"))
		.andExpect(view().name("redirect:/feeProgramList"));
	}
	
	/**
	 * Send maxCharactersFeeProgramEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersFeeProgramEditTest() throws Exception {  
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
		
		mockMvc.perform(post("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "111111111111111111111111111111111111111111111111111")
				.param("price", "24")
				.param("description", "Pay of inscription 2015"))
					.andExpect(content()
                		.string(containsString("Maximum 30 characters")))
                		.andExpect(view().name("feeprogram/feeprogramedit"));
	}
	
	/**
	 * Send dataBlankFeeProgramEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlankFeeProgramEditTest() throws Exception {  
		mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));
		
		mockMvc.perform(post("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", " ")
				.param("year", "2015")
				.param("description", " "))
					.andExpect(content()
						.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("feeprogram/feeprogramedit"));
	}
	
	/**
	 * Send dataBlankFeeProgramEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void postdateAlreadyExistFeeProgramEditTest() throws Exception {

      Date date = DisplayDate.stringToMonthOfYear("2015-10");
      FeeProgram feeProgram2 = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram2);
      
      mockMvc.perform(post("/feeProgramList/feeProgramEdit/"+feeProgram2.getId()).locale(Locale.ENGLISH).session(defaultSession))
      .andExpect(view().name("redirect:/feeProgramList/feeProgramEdit"));

		mockMvc.perform(post("/feeProgramList/feeProgramEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "pay of april 2016")
				.param("price", "24")
				.param("date", "2015-12")
				.param("dateLimit", "2015-12")
				.param("description", "pay of april 2016"));
					//.andExpect(content().string(containsString("Date repeated")))
                		//.andExpect(view().name("feeprogram/feeprogramedit"));
	}
}