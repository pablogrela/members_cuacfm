package org.cuacfm.members.test.web.paymember;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.util.States.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayMemberListControllerTest extends WebSecurityConfigurationAware {

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

   /** The fee member service. */
   @Inject
   private FeeMemberService feeMemberService;

   /** The pay member service. */
   @Inject
   private PayMemberService payMemberService;

   /** The user. */
   private Account user;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /** The fee member. */
   private FeeMember feeMember;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      // Create User
      user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666",
            "666666666", "demo", roles.ROLE_USER);
      accountService.save(user);
      accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      // Create Payment
      feeMember = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember);
   }

   /**
    * Display PayMemberList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeMemberList/payMemberList")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send PayMemberList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysInscriptionsTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc
            .perform(
                  get("/feeMemberList/payMemberList").locale(Locale.ENGLISH)
                        .session(defaultSession))
            .andExpect(view().name("paymember/paymemberlist"))
            .andExpect(
                  content().string(containsString("<title>Payments of user´s inscription</title>")));
   }

   /**
    * Send redirect FeeMemberList Because FeeMember Is Null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc.perform(
            get("/feeMemberList/payMemberList").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/feeMemberList"));
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payPayMemberTest() throws Exception {

      PayMember payMember = payMemberService.findByPayMemberIds(user.getId(), feeMember.getId())
            .get(0);
      // Assert no pay
      assertTrue(payMember.getState().equals(states.NO_PAY));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/pay/" + payMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      // Assert Pay
      assertTrue(payMemberService.findByPayMemberIds(user.getId(), feeMember.getId()).get(0)
            .getState().equals(states.PAY));
   }

   /**
    * Send displaysInscriptionList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void blankMessageLoginTest() throws Exception {
      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc
            .perform(
                  get("/feeMemberList/payMemberList").locale(Locale.ENGLISH)
                        .session(defaultSession))
            .andExpect(view().name("paymember/paymemberlist"))
            .andExpect(
                  content().string(containsString("<title>Payments of user´s inscription</title>")));

      mockMvc
            .perform(
                  post("/feeMemberList/payMemberList").locale(Locale.ENGLISH)
                        .session(defaultSession).param("login", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("paymember/paymemberlist"));
   }

   /**
    * no Exist Login.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void noExistLoginTest() throws Exception {
      mockMvc
            .perform(
                  post("/feeMemberList/payMemberList").locale(Locale.ENGLISH)
                        .session(defaultSession).param("login", "No exist login"))
            .andExpect(content().string(containsString("No exist login")))
            .andExpect(view().name("paymember/paymemberlist"));
   }

   /**
    * login Already Exist By payMemberList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void loginAlreadyExistTest() throws Exception {
      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc
            .perform(
                  post("/feeMemberList/payMemberList").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("login", user.getId() + ": " + user.getLogin()))
            .andExpect(content().string(containsString("It has already created to user")))
            .andExpect(view().name("paymember/paymemberlist"));
   }

   /**
    * addUserToFeeMember.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void addUserToFeeMemberTest() throws Exception {
      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      mockMvc.perform(
            post("/feeMemberList/payMemberList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("login", user2.getId() + ": " + user.getLogin())).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));
   }

   /**
    * Creates the pdf all.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfALL() throws Exception {
      Account user2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user2);
      Account user3 = new Account("user3", "35555555C", "London", "user3", "user3@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      user3.setInstallments(2);
      accountService.save(user3);
      Account user4 = new Account("user4", "45555555C", "London", "user4", "user4@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user4);
      FeeMember feeMember2 = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember2);
      List<PayMember> payMembers = payMemberService.findByPayMemberIds(user2.getId(),
            feeMember2.getId());
      payMemberService.pay(payMembers.get(0));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember2.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc.perform(post("/feeMemberList/payMemberList/createPdf/" + feeMember.getId())
            .locale(Locale.ENGLISH).session(defaultSession).param("createPdf", "ALL"));
   }

   /**
    * Creates the pdf pay.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfPAy() throws Exception {
      Account user2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user2);
      Account user3 = new Account("user3", "35555555C", "London", "user3", "user3@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      user3.setInstallments(2);
      accountService.save(user3);
      Account user4 = new Account("user4", "45555555C", "London", "user4", "user4@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user4);
      FeeMember feeMember2 = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember2);
      List<PayMember> payMembers = payMemberService.findByPayMemberIds(user2.getId(),
            feeMember2.getId());
      payMemberService.pay(payMembers.get(0));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember2.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc.perform(post("/feeMemberList/payMemberList/createPdf/" + feeMember.getId())
            .locale(Locale.ENGLISH).session(defaultSession).param("createPdf", "PAY"));
   }

   /**
    * Creates the pdf no pay.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfNoPAy() throws Exception {
      Account user2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user2);
      Account user3 = new Account("user3", "35555555C", "London", "user3", "user3@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      user3.setInstallments(2);
      accountService.save(user3);
      Account user4 = new Account("user4", "45555555C", "London", "user4", "user4@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(user4);
      FeeMember feeMember2 = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember2);
      List<PayMember> payMembers = payMemberService.findByPayMemberIds(user2.getId(),
            feeMember2.getId());
      payMemberService.pay(payMembers.get(0));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/" + feeMember2.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));

      mockMvc.perform(post("/feeMemberList/payMemberList/createPdf/" + feeMember.getId())
            .locale(Locale.ENGLISH).session(defaultSession).param("createPdf", "NOPAY"));
   }

}