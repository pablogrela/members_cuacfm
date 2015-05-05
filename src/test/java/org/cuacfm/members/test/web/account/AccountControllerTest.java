package org.cuacfm.members.test.web.account;

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
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class accountControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountControllerTest extends WebSecurityConfigurationAware {

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

   /** The user. */
   private Account user;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /**
    * Initialize default session.
    * 
    * @throws UniqueException
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
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
   }

   /**
    * Display account page without sigin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayaccountPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/account")).andExpect(redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send account form.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postDisplaysAccountFormTest() throws Exception {
      mockMvc.perform(
            post("/account/" + defaultSession.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/account"));
   }

   /**
    * Send account form.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysAccount2FormTest() throws Exception {

      Account user2 = new Account("user2", "11111111D", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);

      mockMvc.perform(
            post("/account/" + user2.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(get("/account").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(model().attributeExists("profileForm"))
            .andExpect(view().name("account/account"))
            .andExpect(
                  content()
                        .string(
                              allOf(containsString("<title>Accounts</title>"),
                                    containsString("<legend>Would you like to change any of the information?</legend>"))));
   }

   /**
    * Send account form.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysaccountFormTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(get("/account").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(model().attributeExists("profileForm"))
            .andExpect(view().name("account/account"))
            .andExpect(
                  content()
                        .string(
                              allOf(containsString("<title>Accounts</title>"),
                                    containsString("<legend>Would you like to change any of the information?</legend>"))));
   }

   /**
    * Send redirect AccountList Because Account Is Null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectAccountListBecauseAccountIsNullTest() throws Exception {
      mockMvc.perform(
            post("/account/" + +Long.valueOf(0)).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(get("/account").locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/accountList"));
   }

   public void aqui() {

   }

   /**
    * Send account form with dni existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postdniAlreadyExistsTest() throws Exception {

      Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      /*
       * mockMvc.perform( post("/account/" + user.getId()).locale(
       * Locale.ENGLISH).session(defaultSession))
       * .andExpect(view().name("redirect:/account"));
       */

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("onName", "true").param("name", "name").param("onDni", "true")
                        .param("dni", "55555555B").param("onLogin", "true").param("login", "user")
                        .param("onEmail", "true").param("email", "email@udc.es")
                        .param("onPassword", "true").param("password", "1234")
                        .param("rePassword", "1234").param("onInstallments", "true")
                        .param("installments", "1"))
            .andExpect(
                  content().string(
                        containsString("Already existent dni 55555555B, please chose other")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Send account form with login existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void loginAlreadyExistsTest() throws Exception {

      Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      /*
       * mockMvc.perform( post("/account/" + user.getId()).locale(
       * Locale.ENGLISH).session(defaultSession))
       * .andExpect(view().name("redirect:/account"));
       */

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("onName", "true").param("name", "name").param("onDni", "true")
                        .param("dni", "dni").param("onLogin", "true").param("login", "user2")
                        .param("onEmail", "true").param("email", "email@udc.es")
                        .param("onPassword", "true").param("password", "1234")
                        .param("rePassword", "1234").param("onInstallments", "true")
                        .param("installments", "1"))
            .andExpect(
                  content().string(
                        containsString("Already existent login user2, please chose other")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Send account form with email existent.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void emailAlreadyExists() throws Exception {

      Account user2 = new Account("user2", "55555555B", "London", "user2", "email2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      /*
       * mockMvc.perform( post("/account/" + user.getId()).locale(
       * Locale.ENGLISH).session(defaultSession))
       * .andExpect(view().name("redirect:/account"));
       */
      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("onName", "true").param("name", "name").param("onDni", "true")
                        .param("dni", "dni").param("onLogin", "true").param("login", "login")
                        .param("onEmail", "true").param("email", "email2@udc.es")
                        .param("onInstallments", "true").param("installments", "1"))
            .andExpect(
                  content().string(
                        containsString("Already existent email email2@udc.es, please chose other")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Incorrect email format.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void incorrectEmailFormat() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("name", "name").param("login", "login").param("email", "email")
                        .param("password", "1234").param("rePassword", "1234")
                        .param("onInstallments", "true").param("installments", "1"))
            .andExpect(content().string(containsString("The value must be a valid email!")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Send account form with more of 30 characters.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("name", "1234567890123456789012345678901")
                        .param("login", "1234567890123456789012345678901")
                        .param("email", "1234567890123456789012345678901@example.es")
                        .param("password", "1234567890123456789012345678901")
                        .param("rePassword", "1234567890123456789012345678901")
                        .param("onInstallments", "true").param("installments", "1"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Update account blank message test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateaccountBlankMessageTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true")
                  .param("name", " ").param("onLogin", "true").param("login", " ")
                  .param("onEmail", "true").param("email", " ").param("onPassword", "true")
                  .param("password", " ").param("rePassword", " ").param("onInstallments", "true")
                  .param("installments", "1")).andExpect(view().name("account/account"));
   }

   /**
    * update Profile The Same Params Test.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateProfileTheSameParamsTest() throws Exception {

      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true")
                  .param("name", "user").param("onDni", "true").param("dni", "55555555C")
                  .param("onAddress", "true").param("address", "London").param("onLogin", "true")
                  .param("login", "user").param("onEmail", "true").param("email", "user@udc.es")
                  .param("onProgramName", "true").param("programName", "programName")
                  .param("onPhone", "true").param("phone", "12356789").param("onMobile", "true")
                  .param("mobile", "12356789").param("onStudent", "true").param("student", "true")
                  .param("onDateBirth", "true").param("dateBirth", "1990-05-02")
                  .param("installments", "1")).andExpect(view().name("redirect:/accountList"));
   }

   /**
    * Update account succesfullwithout login.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateaccountSuccesfullwithoutLogin() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true")
                  .param("name", "name").param("onEmail", "true").param("email", "email2@udc.es")
                  .param("onPassword", "true").param("password", "1234")
                  .param("rePassword", "1234").param("onInstallments", "true")
                  .param("installments", "1")).andExpect(view().name("redirect:/accountList"));
   }

   /**
    * Update account succesfull test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateaccountSuccesfullTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession).param("onName", "true")
                  .param("name", "name").param("onNickName", "true").param("nickName", "nickName")
                  .param("onLogin", "true").param("login", "login").param("onDni", "true")
                  .param("dni", "6666666666666666C").param("onAddress", "true")
                  .param("address", "France").param("onEmail", "true")
                  .param("email", "email2@udc.es").param("onPhone", "true")
                  .param("phone", "12356789").param("onMobile", "true").param("mobile", "12356789")
                  .param("onStudent", "true").param("student", "true").param("onDateBirth", "true")
                  .param("dateBirth", "1990-05-02").param("onInstallments", "true")
                  .param("installments", "1").param("onAccountType", "true")
                  .param("accountTypeId", "1").param("onMethodPayment", "true")
                  .param("methodPaymentId", "1").param("onInstallments", "true")
                  .param("installments", "1").param("onObservations", "true")
                  .param("Observations", "Good partner").param("onRole", "true")
                  .param("Role", "ROLE_TRAINER")).andExpect(view().name("redirect:/accountList"));

   }

   /**
    * Send profile form with insufficient characters in password.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void insuficientCharactersPasswordsTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("onName", "true").param("name", "name").param("onNickName", "true")
                        .param("nickName", "nickName").param("onLogin", "true")
                        .param("login", "login").param("onDni", "true")
                        .param("dni", "6666666666666666C").param("onAddress", "true")
                        .param("address", "France").param("onEmail", "true")
                        .param("email", "email2@udc.es").param("onPhone", "true")
                        .param("phone", "12356789").param("onMobile", "true")
                        .param("mobile", "12356789").param("onStudent", "true")
                        .param("student", "true").param("onDateBirth", "true")
                        .param("dateBirth", "1990-05-02").param("onPassword", "true")
                        .param("password", "12").param("rePassword", "12")
                        .param("onInstallments", "true").param("installments", "1")
                        .param("onAccountType", "true").param("accountTypeId", "1")
                        .param("onMethodPayment", "true").param("methodPaymentId", "1")
                        .param("onInstallments", "true").param("installments", "1")
                        .param("onObservations", "true").param("Observations", "Good partner")
                        .param("onRole", "true").param("Role", "ROLE_TRAINER"))
            .andExpect(
                  content()
                        .string(
                              containsString("The password should be to have between 4 and 20 characters.")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Send profile form with different password.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void diferentPasswordsTest() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc
            .perform(
                  post("/account").locale(Locale.ENGLISH).session(defaultSession)
                        .param("onName", "true").param("name", "name").param("onNickName", "true")
                        .param("nickName", "nickName").param("onLogin", "true")
                        .param("login", "login").param("onDni", "true")
                        .param("dni", "6666666666666666C").param("onAddress", "true")
                        .param("address", "France").param("onEmail", "true")
                        .param("email", "email2@udc.es").param("onPhone", "true")
                        .param("phone", "12356789").param("onMobile", "true")
                        .param("mobile", "12356789").param("onStudent", "true")
                        .param("student", "true").param("onDateBirth", "true")
                        .param("dateBirth", "1990-05-02").param("onPassword", "true")
                        .param("password", "1234").param("rePassword", "1233")
                        .param("onInstallments", "true").param("installments", "1")
                        .param("onAccountType", "true").param("accountTypeId", "1")
                        .param("onMethodPayment", "true").param("methodPaymentId", "1")
                        .param("onInstallments", "true").param("installments", "1")
                        .param("onObservations", "true").param("Observations", "Good partner")
                        .param("onRole", "true").param("Role", "ROLE_TRAINER"))
            .andExpect(content().string(containsString("Passwords are not equal")))
            .andExpect(view().name("account/account"));
   }

   /**
    * Not on and blanck message.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void notOnAndBlanckMessage() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession)
                  .param("onName", "false").param("name", " ").param("onLogin", "false")
                  .param("login", " ").param("onEmail", "false").param("email", " ")
                  .param("onPassword", "false").param("password", " ")
                  .param("onInstallments", "false").param("installments", "1")).andExpect(
            view().name("account/account"));
   }

   /**
    * Not on and message.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void notOnAndMessage() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession)
                  .param("onName", "false").param("name", "name").param("onLogin", "false")
                  .param("login", "login").param("onEmail", "false")
                  .param("email", "email@example.es").param("onPassword", "false")
                  .param("password", "1234").param("rePassword", "1233")
                  .param("onInstallments", "false").param("installments", "1")).andExpect(
            view().name("redirect:/accountList"));
   }

   /**
    * Update account successful not changed.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateaccountSuccesfullNotChanged() throws Exception {
      mockMvc.perform(
            post("/account/" + user.getId()).locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/account"));

      mockMvc.perform(
            post("/account").locale(Locale.ENGLISH).session(defaultSession)
                  .param("onInstallments", "true").param("installments", "1")).andExpect(
            view().name("redirect:/accountList"));
   }

}