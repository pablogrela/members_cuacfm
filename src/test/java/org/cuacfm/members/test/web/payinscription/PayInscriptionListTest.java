package org.cuacfm.members.test.web.payinscription;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
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
public class PayInscriptionListTest extends WebSecurityConfigurationAware {

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
	
	/** The pay Inscription service. */
	@Inject
	private PayInscriptionService payInscriptionService;

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
     * Display PayInscriptionListView page without signin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayPayInscriptionViewPageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/payInscriptionList")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send displaysPayInscriptionView.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysInscriptionsTest() throws Exception {    
		
		mockMvc.perform(get("/payInscriptionList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("payinscription/payinscriptionlist"))
				.andExpect(content().string(containsString("<title>Payments Inscription</title>")));
	}	
}