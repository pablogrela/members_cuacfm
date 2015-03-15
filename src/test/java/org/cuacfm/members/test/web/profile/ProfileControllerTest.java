package org.cuacfm.members.test.web.profile;

import static org.hamcrest.Matchers.allOf;
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
public class ProfileControllerTest extends WebSecurityConfigurationAware {

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
		Account user = new Account("user", "user", "email1@udc.es", "demo", "ROLE_USER");
		accountService.save(user);
        defaultSession = getDefaultSession("user");
    }

	
	
    /**
     * Display profile page without sigin in test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displayProfilePageWithoutSiginInTest() throws Exception {
        mockMvc.perform(get("/profile")).andExpect(
                redirectedUrl("http://localhost/signin"));
    }
    
	/**
	 * Send profile form.
	 * @throws Exception the exception
	 */
	@Test
	public void displaysProfileFormTest() throws Exception {    
		mockMvc.perform(get("/profile").locale(Locale.ENGLISH).session(defaultSession))
				.andExpect(model().attributeExists("profileForm"))
				.andExpect(view().name("profile/profile"))
				.andExpect(
						content()
								.string(allOf(
										containsString("<title>Profile</title>"),
										containsString("<legend>Would you like to change any of the information?</legend>"))));
	}

	/**
	 * Send profile form with login existent.
	 * @throws Exception the exception
	 */
	@Test
	public void loginAlreadyExistsTest() throws Exception {

		Account user2 = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user2);
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "user2")
						.param("email", "email@udc.es")
						.param("password", "1234")
						.param("rePassword", "1234"))
                        .andExpect(content()
                        		.string(containsString("Already existent login user2, please chose other")))
                        		.andExpect(view().name("profile/profile"));
	}
	
	
	/**
	 * Send profile form with email existent.
	 * @throws Exception the exception
	 */
	@Test
	public void emailAlreadyExists() throws Exception {

		Account user2 = new Account("user2", "user2", "email2@udc.es", "demo", "ROLE_USER");
		accountService.save(user2);
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "login")
						.param("email", "email2@udc.es")
						.param("password", "1234")
						.param("rePassword", "1234"))
                        .andExpect(content()
                        		.string(containsString("Already existent email email2@udc.es, please chose other")))
                        		.andExpect(view().name("profile/profile"));
	}
	
    /**
     * Incorrect email format.
     * @throws Exception the exception
     */
    @Test
    public void incorrectEmailFormat() throws Exception {
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "login")
						.param("email", "email")
						.param("password", "1234")
						.param("rePassword", "1234"))
                        .andExpect(content()
                        		.string(containsString("The value must be a valid email!")))
                        		.andExpect(view().name("profile/profile"));
    }

    
    /**
     * Send profile form with insuficient characters in password.
     * @throws Exception the exception
     */
    @Test
    public void insuficientCharactersPasswords() throws Exception {
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "login")
						.param("email", "email@example.es")
						.param("password", "12")
						.param("rePassword", "12"))
                        .andExpect(content()
                        		.string(containsString("The password should be to have between 4 and 20 characters.")))
                        		.andExpect(view().name("profile/profile"));
    }
    
    /**
     * Send profile form with diferent password.
     * @throws Exception the exception
     */
    @Test
    public void diferentPasswords() throws Exception {
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "login")
						.param("email", "email@example.es")
						.param("password", "1234")
						.param("rePassword", "1233"))
                        .andExpect(content()
                        		.string(containsString("Passwords are not equal")))
                        		.andExpect(view().name("profile/profile"));
    }
  
    /**
     * Send profile form with more of 30 characters.
     * @throws Exception the exception
     */
    @Test
    public void maxTest() throws Exception {
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "1234567890123456789012345678901")
						.param("login", "1234567890123456789012345678901")
						.param("email", "1234567890123456789012345678901@example.es")
						.param("password", "1234567890123456789012345678901")
						.param("rePassword", "1234567890123456789012345678901"))
                        .andExpect(content()
                        		.string(containsString("Maximum 30 characters")))
                        		.andExpect(view().name("profile/profile"));
    }
    
	/**
	 * Send profile form with email existent.
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileBlankMessageTest() throws Exception {		
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", " ")
						.param("login", " ")
						.param("radioEmail", " ")
						.param("email", " ")
						.param("password", " ")
						.param("rePassword", " "))
                        		.andExpect(view().name("profile/profile"));
	}
	

	/**
	 * Send profile form with email existent.
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullwithoutLogin() throws Exception {	
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("email", "email2@udc.es")
						.param("password", "1234")
						.param("rePassword", "1234"))
                        		.andExpect(view().name("redirect:/profile"));
	}
	
	/**
	 * Send profile form with email existent.
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullTest() throws Exception {	
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession)
						.param("name", "name")
						.param("login", "login")
						.param("email", "email2@udc.es")
						.param("password", "1234")
						.param("rePassword", "1234"))
                        		.andExpect(view().name("redirect:/profile"));
	}
	
	/**
	 * Send profile form with email existent.
	 * @throws Exception the exception
	 */
	@Test
	public void updateProfileSuccesfullNotChanged() throws Exception {
		
        mockMvc.perform(
                post("/profile").locale(Locale.ENGLISH).session(defaultSession))
                	.andExpect(view().name("redirect:/profile"));
	}
}