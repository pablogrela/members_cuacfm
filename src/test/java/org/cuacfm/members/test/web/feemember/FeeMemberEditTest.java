package org.cuacfm.members.test.web.feemember;

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
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class FeeMemberListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeMemberEditTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Autowired
	private AccountService accountService;

	/** The pay Inscription service. */
	@Autowired
	private FeeMemberService feeMemberService;
	
	/** The pay inscription. */
	private FeeMember feeMember;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", "666666666", "666666666","demo", roles.ROLE_ADMIN);
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
		//Create Payment
		feeMember = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
		feeMemberService.save(feeMember);
    }

	
    /**
     * Display TrainingView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayFeeMemberEditViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/feeMemberList/feeMemberEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysFeeMemberEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void postDisplaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+feeMember.getId()).locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
	}	
	
	/**
	 * Send displaysFeeMemberEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+feeMember.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
		
		mockMvc.perform(get("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("feemember/feememberedit"))
				.andExpect(content().string(containsString("<title>Edit Pay Inscription</title>")));
	}	
	
	/**
	 * Send displaysFeeMemberEditView.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectFeeMemberListBecauseFeeMemberIsNullTest() throws Exception {    
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
		
		mockMvc.perform(get("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("redirect:/feeMemberList"));
	}	
	
	/**
	 * Send displaysFeeMemberEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void postFeeMemberEditSuccesfulTest() throws Exception {    
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+feeMember.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
		
		mockMvc.perform(post("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Pay 2015")
				.param("year", "2016")
				.param("price", "24")
				.param("dateLimit1", "2015-04-05")
				.param("dateLimit2", "2015-07-05")
				.param("description", "Pay of inscription 2015"))
		.andExpect(view().name("redirect:/feeMemberList"));
	}
	
	/**
	 * Send maxCharactersFeeMemberEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersFeeMemberEditTest() throws Exception {  
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+feeMember.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
		
		mockMvc.perform(post("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "111111111111111111111111111111111111111111111111111")
				.param("year", "2015")
				.param("price", "24")
				.param("description", "Pay of inscription 2015"))
					.andExpect(content()
                		.string(containsString("Maximum 30 characters")))
                		.andExpect(view().name("feemember/feememberedit"));
	}
	
	/**
	 * Send dataBlankFeeMemberEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void dataBlankFeeMemberEditTest() throws Exception {  
		mockMvc.perform(post("/feeMemberList/feeMemberEdit/"+feeMember.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/feeMemberList/feeMemberEdit"));
		
		mockMvc.perform(post("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", " ")
				.param("year", "2015")
				.param("price", "24")
				.param("description", " "))
					.andExpect(content()
						.string(containsString("The value may not be empty!")))
                		.andExpect(view().name("feemember/feememberedit"));
	}
	
	/**
	 * Send dataBlankFeeMemberEdit.
	 * @throws Exception the exception
	 */
	@Test
	public void yearAlreadyExistFeeMemberEditTest() throws Exception {
		
		//Edit Payment
		FeeMember feeMember2 = new FeeMember("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		feeMemberService.save(feeMember2);
		
		mockMvc.perform(post("/feeMemberList/feeMemberEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "pay of 2015")
				.param("year", "2016")
				.param("price", "24")
				.param("dateLimit1", "2015-04-05")
				.param("dateLimit2", "2015-07-05")
				.param("description", "pay of 2015"))
					.andExpect(content()
						.string(containsString("Year repeated")))
                		.andExpect(view().name("feemember/feememberedit"));
	}
}