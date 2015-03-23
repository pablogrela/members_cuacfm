package org.cuacfm.members.test.web.home;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class HomeControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HomeControllerTest extends WebSecurityConfigurationAware {

	/** The default session. */
	private MockHttpSession defaultSession;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/**
	 * Initialize default session.
	 */
	@Before
	public void initializeDefaultSession() {
		Account user = new Account("user", "user", "email1@udc.es", "demo",
				"ROLE_USER");
		accountService.save(user);
		defaultSession = getDefaultSession("user");
	}

	/**
	 * Show page homeNotSignedIn
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displayHomeNotSignedIn() throws Exception {
		mockMvc.perform(get("/").locale(Locale.ENGLISH)).andExpect(
				view().name("home/homeNotSignedIn"));
	}

	/**
	 * Show page homeSignedIn
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void displayHomeSignedIn() throws Exception {
		mockMvc.perform(get("/").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(view().name("home/homeSignedIn"));
	}

}