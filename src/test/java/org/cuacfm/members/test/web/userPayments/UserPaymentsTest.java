package org.cuacfm.members.test.web.userPayments;

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


/** The Class UserPayInscriptionListControllerTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserPaymentsTest extends WebSecurityConfigurationAware {

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
     */
    @Before
    public void initializeDefaultSession() {
        // Create User
		user = new Account("user", "user", "email@udc.es", "demo",
				"ROLE_USER");
		accountService.save(user);
		accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
        defaultSession = getDefaultSession("user");
		
		//Create Payment
		payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
    }

	
    /**
     * Display TrainingView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayuserPaymentsViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/userPayments")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysTrainingView.
	 * @throws Exception the exception
	 */
	@Test
	public void displayUserPaymentsTest() throws Exception {  
		            
		UserPayInscription userPayInscription = userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0);
		//Assert no pay
		assertEquals(userPayInscription.isHasPay(), false);
		mockMvc.perform(post("/userPayments/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Completed")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		mockMvc.perform(get("/userPayments").locale(Locale.ENGLISH).session(defaultSession)
                .sessionAttr("_csrf", "csrf"))
				.andExpect(view().name("userpayments/userpayments"))
				.andExpect(content().string(containsString("<title>My payments</title>")));		
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

		mockMvc.perform(post("/userPayments/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Completed")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		// Assert Pay
		assertEquals(userPayInscription.isHasPay(), true);
	}
	
	/**
	 * Pay user pay inscription test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payProgressUserPayInscriptionTest() throws Exception {    
		
		UserPayInscription userPayInscription = userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0);
		//Assert no pay
		assertEquals(userPayInscription.isHasPay(), false);

		mockMvc.perform(post("/userPayments/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Progress")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		// Assert Pay
		assertEquals(userPayInscription.isHasPay(), false);
		assertEquals(userPayInscription, userPayInscriptionService.findByIdTxn("txn"));
	}
	
	/**
	 * Pay Exist TransactionId Exception Test
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payExistTransactionIdExceptionTest() throws Exception {    
		
		UserPayInscription userPayInscription = userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0);
		//Assert no pay
		assertEquals(userPayInscription.isHasPay(), false);

		mockMvc.perform(post("/userPayments/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Completed")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		mockMvc.perform(post("/userPayments/pay/"+userPayInscription.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Completed")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		// Assert Pay
		assertEquals(userPayInscription.isHasPay(), true);
	}
	
	/**
	 * Pay Other Account UserPayInscription Test
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void payOtherAccountUserPayInscriptionTest() throws Exception {    
		
		UserPayInscription userPayInscription = userPayInscriptionService.findByUserPayInscriptionIds(user.getId(), payInscription.getId()).get(0);
		//Assert no pay
		assertEquals(userPayInscription.isHasPay(), false);
		Account user2 = new Account("user2", "user2", "email2@udc.es", "demo",
				"ROLE_USER");
		accountService.save(user2);
		user2.setAccountType(accountType);
		user2.setMethodPayment(methodPayment);
		user2.setInstallments(1);
		accountService.update(user2, false);
		
		payInscriptionService.saveUserPayInscription(user2, payInscription);
		UserPayInscription userPayInscriptionProbe = userPayInscriptionService.getUserPayInscriptionListByAccountId(user2.getId()).get(0);
        
		mockMvc.perform(post("/userPayments/pay/"+userPayInscriptionProbe.getId()).locale(Locale.ENGLISH).session(defaultSession)
				.sessionAttr("_csrf", "csrf")
				.param("payer_email", "email")
				.param("payer_id", "id")
				.param("payment_date", "10:10:10 Jun 10, 2015") 
				.param("payment_status", "Completed")
				.param("txn_id", "txn"))
		.andExpect(view().name("redirect:/userPayments"));
		
		// Assert Pay
		assertEquals(userPayInscriptionProbe.isHasPay(), false);
	}
}