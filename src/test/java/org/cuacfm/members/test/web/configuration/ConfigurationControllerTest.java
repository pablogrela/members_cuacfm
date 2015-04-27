package org.cuacfm.members.test.web.configuration;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
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
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



/** The class ConfigurationControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationControllerTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;
	
	/** The account Type service. */
	@Inject
	private AccountTypeService accountTypeService;
	
	/** The account Type service. */
	@Inject
	private MethodPaymentService methodPaymentService;
	
	/** The account type. */
	private AccountType accountType;
	
	/** The method payment. */
	private MethodPayment methodPayment;
	
    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
    	Account admin = new Account("admin", "55555555C", "London", "admin", "admin@udc.es", 666666666, 666666666, "admin", roles.ROLE_ADMIN);
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
		accountType = new AccountType("Adult", "Tax for Adult", 0);
		accountTypeService.save(accountType);
		
		methodPayment = new MethodPayment("Paypal", "Pay by Paypal");
		methodPaymentService.save(methodPayment);
    }
	
    /**
     * Display Configuration page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayConfigurationPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/configuration")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysConfiguration.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysConfiguration() throws Exception {    
		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));
	}


	/**
	 * Send displaysConfiguration.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysConfigurationWithDatabase() throws Exception {    
		mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("configuration/configuration"))
				.andExpect(content().string(containsString("<title>Configuration</title>")));
	}
	
	/**
	 * Send displaysConfiguration.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteAccountType() throws Exception {    

		mockMvc.perform(post("/configuration/accountTypeDelete/"+accountType.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration"));

		//Assert, it remove accountType
		assertEquals(accountTypeService.findById(accountType.getId()), null);
	}
	
	/**
	 * Send displaysConfiguration.
	 * @throws Exception the exception
	 */
	@Test
	public void deleteMethodPayment() throws Exception {    

		mockMvc.perform(post("/configuration/methodPaymentDelete/"+methodPayment.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/configuration"));

		//Assert, it remove methodPayment
		assertEquals(methodPaymentService.findById(methodPayment.getId()), null);
	}
}