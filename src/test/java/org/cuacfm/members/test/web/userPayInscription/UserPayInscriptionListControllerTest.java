package org.cuacfm.members.test.web.userPayInscription;

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
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class UserPayInscriptionListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserPayInscriptionListControllerTest extends WebSecurityConfigurationAware {

    /** The default session. */
    private MockHttpSession defaultSession;

    /** The account service. */
	@Inject
	private AccountService accountService;

	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;
	
	/** The training service. */
	@Inject
	private PayInscriptionService payInscriptionService;

	/** The training service. */
	@Inject
	private UserPayInscriptionService userPayInscriptionService;
	
	/** The user. */
	private Account user;
	
	/** The account type. */
	private AccountType accountType;
	
	/** The method payment. */
	private MethodPayment methodPayment;
	
	/** The pay inscription. */
	private PayInscription payInscription;

    /**
     * Initialize default session.
     * @throws UniqueException 
     */
    @Before
    public void initializeDefaultSession() throws UniqueException {
		Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", 666666666, 666666666,"demo", roles.ROLE_ADMIN);
		accountService.save(admin);
        defaultSession = getDefaultSession("admin");
        
        // Create User
		user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
		
		//Create Payment
		payInscription = new PayInscription("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription);
    }

	
    /**
     * Display UserPayInscriptionList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/payInscriptionList/userPayInscriptionList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send UserPayInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(get("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("userpayinscription/userpayinscriptionlist"))
				.andExpect(content().string(containsString("<title>Payments of user´s inscription</title>")));
	}	
	
	/**
	 * Send redirect PayInscriptionList Because PayInscription Is Null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(get("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList"));
	}
		
	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payUserPayInscriptionTest() throws Exception {    
		
		UserPayInscription userPayInscription = userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0);
		//Assert no pay
		assertEquals(userPayInscription.isHasPay(), false);
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		// Assert Pay
		assertEquals(userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0).isHasPay(), true);
	}
	
	/**
	 * Send displaysInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void blankMessageLoginTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", ""))
				.andExpect(content()
                .string(containsString("The value may not be empty!")))
                .andExpect(view().name("userpayinscription/userpayinscriptionlist"));
	}
	
	/**
	 * no Exist Login.
	 * @throws Exception the exception
	 */
	@Test
	public void noExistLoginTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", "No exist login"))
				.andExpect(content()
                .string(containsString("No exist login")))
                .andExpect(view().name("userpayinscription/userpayinscriptionlist"));
	}
	
	/**
	 * login Already Exist By userPayInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void loginAlreadyExistTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user.getLogin()))
				.andExpect(content()
                .string(containsString("It has already created to user")))
                .andExpect(view().name("userpayinscription/userpayinscriptionlist"));
	}
	
	/**
	 * addUserToPayInscription.
	 * @throws Exception the exception
	 */
	@Test
	public void addUserToPayInscriptionTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/"+payInscription.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
		
		Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false);
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList").locale(Locale.ENGLISH).session(defaultSession)
				.param("login", user2.getLogin()))
                .andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
	}
}