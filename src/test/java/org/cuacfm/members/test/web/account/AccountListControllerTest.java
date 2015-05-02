package org.cuacfm.members.test.web.account;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

/** The class accountListControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountListControllerTest extends WebSecurityConfigurationAware {

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

	/** The admin. */
	private Account admin;
	
	/** The user. */
	private Account user;
	
	/** The account type. */
	private AccountType accountType;
	
	/** The method payment. */
	private MethodPayment methodPayment;
	
	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException {
		accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		
		
		admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es", 666666666, 666666666,"demo", roles.ROLE_ADMIN);
		accountService.save(admin);
		admin.setAccountType(accountType);
		admin.setMethodPayment(methodPayment);
		admin.setInstallments(1);
		accountService.update(admin, false);
		defaultSession = getDefaultSession("admin");

		
		user = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);

		
	}

	/**
	 * Display account page without sigin in test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displayaccountPageWithoutSiginInTest() throws Exception {
		mockMvc.perform(get("/accountlist")).andExpect(
				redirectedUrl("http://localhost/signin"));
	}



	/**
	 * Send account form.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displaysaccountFormTest() throws Exception {
		mockMvc.perform(
				get("/accountList").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("account/accountlist"))
				.andExpect(
						content()
								.string(allOf(
										containsString("<title>Accounts</title>"),
										containsString("Account List</h2>"))));
	}
	

	/**
	 * Post account unsubscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountUnsubscribeTest() throws Exception {
		mockMvc.perform(post("/accountList/accountUnsubscribe/"+user.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/accountList"));
		
		//User is Unsubscribe
		assertFalse(user.isActive());
	}
	
	/**
	 * Post account unsubscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountUnsubscribeAdminTest() throws Exception {
		mockMvc.perform(post("/accountList/accountUnsubscribe/"+admin.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/accountList"));
		
		// Admin not unsubscribe
		assertTrue(admin.isActive());
	}

	/**
	 * Post account subscribe test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void postAccountSubscribeTest() throws Exception {
		mockMvc.perform(post("/accountList/accountUnsubscribe/"+user.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/accountList"));
		
		mockMvc.perform(post("/accountList/accountSubscribe/"+user.getId()).locale(Locale.ENGLISH).session(defaultSession))
		.andExpect(view().name("redirect:/accountList"));
		
		//Admin already subscribe
		assertTrue(user.isActive());
	}
	

}