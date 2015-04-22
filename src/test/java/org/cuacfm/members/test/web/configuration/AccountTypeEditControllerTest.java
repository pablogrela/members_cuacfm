package org.cuacfm.members.test.web.configuration;

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
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
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
public class AccountTypeEditControllerTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;
	
	/** The account Type service. */
	@Inject
	private AccountTypeService accountTypeService;
	
	private AccountType accountType;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
    	Account admin = new Account("admin", "55555555C", "London", "admin", "admin@udc.es", 666666666, 666666666, "admin", "ROLE_ADMIN");
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
		accountType = new AccountType("Adult", "Tax for Adult", 0);
		accountTypeService.save(accountType);
    }

	
	
    /**
     * Display AccountTypeList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayAccountTypeEditPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/configuration/accountTypeEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
  
    
	/**
	 * Send null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectAccountListBecauseAccountTypeIsNullTest() throws Exception {    
		mockMvc.perform(post("/configuration/accountTypeEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(get("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration"));
	}
	
	/**
	 * Send displaysAccountTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysAccountTypeEditTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(get("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("configuration/accountypedit"))
				.andExpect(content().string(containsString("<title>Modify Account Type</title>")));
	}


	/**
	 * Send displaysAccountTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountTypeEditTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Adult")
				.param("description", "Tax for adult")
				.param("discount", "0"))
		.andExpect(view().name("redirect:/configuration"));
	}

	/**
	 * Send displaysAccountTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void nameAlreadExistTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Adult")
				.param("description", "Tax for adult")
				.param("discount", "0"))
				.andExpect(content()
                        		.string(containsString("Already exist account type with name "+ accountType.getName() + ", please chose other")))
                        		.andExpect(view().name("configuration/accountypedit"));

	}
	
	/**
	 * Send displaysAccountTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void updateAccountTypeTheSameParamsTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Adult")
				.param("description", "Tax for adult")
				.param("discount", "0"))
				.andExpect(view().name("redirect:/configuration"));

	}
	/**
	 * notBlankMessage.
	 * @throws Exception the exception
	 */
	@Test
	public void notBlankMessageInAccountTypeEditTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(content()
                        		.string(containsString("The value may not be empty!")))
                        		.andExpect(view().name("configuration/accountypedit"));

	}
	
	/**
	 * "Already exist type of account with name "+ accountType.getName() + ", please chose other"
	 * Send displaysAccountTypeList.
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInAccountTypeEditTest() throws Exception {    
		
		mockMvc.perform(post("/configuration/accountTypeEdit/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration/accountTypeEdit"));
		
		mockMvc.perform(post("/configuration/accountTypeEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("name", "Adulttttttttttttttttttttttttttttttttttttttttttttttttt")
				.param("description", "Tax for adultttttttttttttttttttttttttttttttttt")
				.param("discount", "0"))
				.andExpect(content()
                        		.string(containsString("Maximum 30 characters")))
                        		.andExpect(view().name("configuration/accountypedit"));

	}
}