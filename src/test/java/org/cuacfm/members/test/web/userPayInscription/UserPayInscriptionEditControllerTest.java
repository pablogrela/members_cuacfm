package org.cuacfm.members.test.web.userPayInscription;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class UserPayInscriptionEditControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserPayInscriptionEditControllerTest extends WebSecurityConfigurationAware {

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
	
	/** The training service. */
	private UserPayInscription pay;
	
	
    /**
     * Initialize default session.
     */
    @Before
    public void initializeDefaultSession() {
		accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		
		Account admin = new Account("admin", "admin", "admin@udc.es", "admin", "ROLE_ADMIN");
		accountService.save(admin);
        admin.setAccountType(accountType);
        admin.setMethodPayment(methodPayment);
        admin.setInstallments(1);
		accountService.update(admin, false);
		defaultSession = getDefaultSession("admin");
		
        
        // Create User
		user = new Account("user", "user", "email1@udc.es", "demo",
				"ROLE_USER");
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
		
		//Create Payment
		payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
		
		pay = userPayInscriptionService.getUserPayInscriptionListByAccountId(user.getId()).get(0);
    }

	
	
    /**
     * Display UserPayInscriptionList page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayUserPayInscriptionEditPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
  
    
	/**
	 * Send null.
	 * @throws Exception the exception
	 */
	@Test
	public void redirectTrainingListBecauseUserPayInscriptionIsNullTest() throws Exception {    
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit/"+Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit"));
		
		mockMvc.perform(get("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
	}
	

	/**
	 * Displays user pay inscription edit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displaysUserPayInscriptionEditTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit/"+pay.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit"));
		
		
		mockMvc.perform(get("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("userpayinscription/userpayinscriptionedit"))
				.andExpect(model().attributeExists("userPayInscriptionForm"))
				.andExpect(content().string(containsString("<title>Edit User Payment</title>")));
	}


	/**
	 * Send displaysUserPayInscriptionList.
	 * @throws Exception the exception
	 */
	@Test
	public void postUserPayInscriptionEditTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit/"+pay.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("price", "24")
				.param("hasPay", "true")
				.param("installment", "1")
				.param("installments", "1")
				.param("idPayer", "1G3210")
				.param("idTxn", "1G3210")
				.param("emailPayer", "user@hotmail.com")				
				.param("statusPay", "Completed")
				.param("datePay", "10:10 10/10/2015"))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList"));
	}
	

	/**
	 * Max characters in user pay inscription edit test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void maxCharactersInUserPayInscriptionEditTest() throws Exception {    
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit/"+pay.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit"));
		
		mockMvc.perform(post("/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit").locale(Locale.ENGLISH).session(defaultSession)
				.param("price", "24")
				.param("hasPay", "true")
				.param("installment", "1")
				.param("installments", "1")
				.param("idPayer", "1G3210111111111111111111111111111111111111111")
				.param("idTxn", "1G321011111111111111111111111111111111111111111")
				.param("emailPayer", "user@hotmail.com11111111111111111111111111")				
				.param("statusPay", "Completed1111111111111111111111111111111111")
				.param("datePay", "10:10 10/10/2015"))
				.andExpect(content()
                        		.string(containsString("Maximum 30 characters")))
                        		.andExpect(view().name("userpayinscription/userpayinscriptionedit"));

	}
}