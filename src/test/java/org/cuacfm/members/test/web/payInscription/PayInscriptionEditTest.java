package org.cuacfm.members.test.web.payInscription;

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
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class PayInscriptionListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayInscriptionEditTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;

	/** The pay Inscription service. */
	@Inject
	private PayInscriptionService payInscriptionService;
	
	/** The pay inscription. */
	private PayInscription payInscription;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", 666666666, 666666666,"demo", "ROLE_ADMIN");
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
		//Create Payment
		payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
		payInscriptionService.save(payInscription);
    }

	
    /**
     * Display TrainingView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayPayInscriptionEditViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/payInscriptionList/payInscriptionEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysPayInscriptionEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void postDisplaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
	}	
	
	/**
	 * Send displaysPayInscriptionEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
		
		mockMvc.perform(get("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("payinscription/payinscriptionedit"))
				.andExpect(content().string(containsString("<title>Edit Pay Inscription</title>")));
	}	
	
	/**
	 * Send displaysPayInscriptionEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectPayInscriptionListBecausePayInscriptionIsNullTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
		
		mockMvc.perform(get("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/payInscriptionList"));
	}	
	
	/**
	 * Send displaysPayInscriptionEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void postPayInscriptionEditSuccesfulTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
		
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Pay 2015")
				.param("year", "2016")
				.param("price", "24")
				.param("dateLimit1", "2015-04-05")
				.param("dateLimit2", "2015-07-05")
				.param("description", "Pay of inscription 2015"))
		.andExpect(view().name("redirect:/payInscriptionList"));
	}
	
	/**
	 * Send maxCharactersPayInscriptionEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersPayInscriptionEditTest() throws Exception {  
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
		
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "111111111111111111111111111111111111111111111111111")
				.param("year", "2015")
				.param("price", "24")
				.param("description", "Pay of inscription 2015"))
					.andExpect(content()
                		.string(containsString("Maximum 30 characters")))
                		.andExpect(view().name("payinscription/payinscriptionedit"));
	}
	
	/**
	 * Send dataBlankPayInscriptionEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlankPayInscriptionEditTest() throws Exception {  
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/payInscriptionEdit"));
		
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", " ")
				.param("year", "2015")
				.param("price", "24")
				.param("description", " "))
					.andExpect(content()
						.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("payinscription/payinscriptionedit"));
	}
	
	/**
	 * Send dataBlankPayInscriptionEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void yearAlreadyExistPayInscriptionEditTest() throws Exception {
		
		//Edit Payment
		PayInscription payInscription2 = new PayInscription("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription2);
		
		mockMvc.perform(post("/payInscriptionList/payInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "pay of 2015")
				.param("year", "2016")
				.param("price", "24")
				.param("dateLimit1", "2015-04-05")
				.param("dateLimit2", "2015-07-05")
				.param("description", "pay of 2015"))
					.andExpect(content()
						.string(containsString("Year repeated")))
                		.andExpect(view().name("payinscription/payinscriptionedit"));
	}
}