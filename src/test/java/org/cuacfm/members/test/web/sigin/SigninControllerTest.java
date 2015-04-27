package org.cuacfm.members.test.web.sigin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SigninControllerTest extends WebSecurityConfigurationAware {

    /** The account service. */
	@Inject
	private AccountService accountService;	
	
	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;
	
	/** The user. */
	private Account user;
	
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
		accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		
        
        // Create User
		user = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
    }
    
    /**
     * Show page sigin
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displaySignInPage() throws Exception {
        mockMvc.perform(get("/signin")).andExpect(view().name("signin/signin"));
    }
    
	/**
	 * Send null.
	 * @throws Exception the exception
	 */
	@Test
	public void downUserTest() throws Exception {    
		user.setActive(false);
		accountService.update(user, false);
		
		mockMvc.perform(post("/signin").locale(Locale.ENGLISH)
				.param("username", "user")
				.param("password", "demo"))
		.andExpect(view().name("signin/signin"));
	}
}