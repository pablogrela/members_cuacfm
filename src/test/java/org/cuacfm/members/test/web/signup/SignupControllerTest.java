package org.cuacfm.members.test.web.signup;

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
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.test.config.WebAppConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class SignupControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SignupControllerTest extends WebAppConfigurationAware {

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;

   /** The account service mock. */
   @Inject
   private AccountService accountServiceMock;

   /**
    * Initialize default session.
    */
   @Before
   public void initializeDefaultSession() {
      Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
   }

   /**
    * Send sign up form.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysSignupForm() throws Exception {
      mockMvc
            .perform(get("/signup").locale(Locale.ENGLISH))
            .andExpect(model().attributeExists("signupForm"))
            .andExpect(view().name("signup/signup"))
            .andExpect(
                  content().string(
                        allOf(containsString("<title>Sign Up</title>"),
                              containsString("<legend>Please Sign Up</legend>"))));
   }

   /**
    * Send sign up form with blank messages.
    * 
    * @throws Exception
    *            the exception
    **/
   @Test
   public void blankMessages() throws Exception {
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", " ").param("login", " ")
                        .param("email", " ").param("password", " ").param("rePassword", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with dni existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void dniAlreadyExists() throws Exception {
      Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountServiceMock.save(demoUser);

      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("dni", "55555555C").param("address", "London")
                        .param("login", "user").param("email", "user@example.es")
                        .param("phone", "12356789").param("mobile", "12356789")
                        .param("programName", "12356789").param("password", "1234")
                        .param("rePassword", "1234").param("rule", "true"))
            .andExpect(
                  content().string(
                        containsString("Already existent dni 55555555C, please chose other")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with email existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void emailAlreadyExists() throws Exception {
      Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountServiceMock.save(demoUser);

      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("dni", "11111111F").param("address", "London")
                        .param("login", "user2").param("email", "user@udc.es")
                        .param("phone", "12356789").param("mobile", "12356789")
                        .param("programName", "12356789").param("password", "1234")
                        .param("rePassword", "1234").param("rule", "true"))
            .andExpect(
                  content().string(
                        containsString("Already existent email user@udc.es, please chose other")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Incorrect email format.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void incorrectEmailFormat() throws Exception {
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("dni", "55555555C").param("login", "login").param("email", "email")
                        .param("password", "1234").param("rePassword", "1234"))
            .andExpect(content().string(containsString("The value must be a valid email!")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Incorrect email format.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void incorrectEmailFormatDirect() throws Exception {
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("login", "login").param("email", "email").param("password", "1234")
                        .param("rePassword", "1234").param("rule", "true"))
            .andExpect(content().string(containsString("The value must be a valid email!")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with login existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void loginAlreadyExist() throws Exception {
      Account demoUser = new Account("user", "55555555C", "London", "user", "user@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);

      accountServiceMock.save(demoUser);
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("dni", "11111111F").param("address", "London")
                        .param("login", "user").param("email", "email@example.es")
                        .param("phone", "12356789").param("mobile", "12356789")
                        .param("programName", "12356789").param("password", "1234")
                        .param("rePassword", "1234").param("rule", "true"))
            .andExpect(
                  content().string(
                        containsString("Already existent login user, please chose other")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with more of 30 characters.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maximumCharacters() throws Exception {
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH)
                        .param("name", "1234567890123456789012345678901")
                        .param("login", "1234567890123456789012345678901")
                        .param("email", "1234567890123456789012345678901@example.es")
                        .param("password", "1234567890123456789012345678901")
                        .param("rePassword", "1234567890123456789012345678901"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with insuficient characters in password.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void insuficientCharactersPasswords() throws Exception {
      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("login", "login").param("email", "email@example.es")
                        .param("password", "12").param("rePassword", "12"))
            .andExpect(
                  content()
                        .string(
                              containsString("The password should be to have between 4 and 20 characters.")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Send sign up form with diferent password.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void diferentPasswords() throws Exception {

      mockMvc
            .perform(
                  post("/signup").locale(Locale.ENGLISH).param("name", "name")
                        .param("login", "login").param("email", "email@example.es")
                        .param("password", "1234").param("rePassword", "1233"))
            .andExpect(content().string(containsString("Passwords are not equal")))
            .andExpect(view().name("signup/signup"));
   }

   /**
    * Succesful sign up.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void succesfulSignUp() throws Exception {

      mockMvc.perform(
            post("/signup").locale(Locale.ENGLISH).param("name", "name").param("dni", "55555555C")
                  .param("address", "London").param("login", "login")
                  .param("email", "email@example.es").param("phone", "12356789")
                  .param("mobile", "12356789").param("programName", "12356789")
                  .param("password", "1234").param("rePassword", "1234").param("rule", "true"))
            .andExpect(redirectedUrl("/"));
   }

   /**
    * Appear Error Page.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysErrorPage() throws Exception {
      mockMvc.perform(post("/signup").locale(Locale.ENGLISH)).andExpect(
            content().string(containsString("<title>Error Page</title>")));
   }
}