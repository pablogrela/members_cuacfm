package org.cuacfm.members.test.web.userpayments;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
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
public class UserPaymentsTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;
   
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
   private FeeMemberService feeMemberService;

   /** The training service. */
   @Inject
   private PayMemberService userFeeMemberService;

   /** The program service. */
   @Inject
   private ProgramService programService;

   /** The fee program service. */
   @Inject
   private FeeProgramService feeProgramService;

   /** The pay program service. */
   @Inject
   private PayProgramService payProgramService;

   /** The user. */
   private Account user;

   /** The program */
   private Program program;

   /** The fee program. */
   private FeeProgram feeProgram;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /** The pay inscription. */
   private FeeMember feeMember;

   /**
    * Initialize default session.
    * 
    * @throws UniqueException
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Configuration configuration = new Configuration("CuacFM", "cuacfm@hotmail.com", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
      
      // Create User
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

      // Create Payment
      feeMember = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember);


      // Create Program and Payments
      List<Account> accounts = new ArrayList<Account>();
      List<Program> programs = new ArrayList<Program>();
      accounts.add(user);
      program = new Program("Program 1", Float.valueOf(1), "About program", 1, accounts);
      programs.add(program);
      programService.save(program);
      programService.up(program.getId());

      feeProgram = new FeeProgram("Fee March 2015", Double.valueOf(25),
            DisplayDate.stringToMonthOfYear("2016-03"), DisplayDate.stringToMonthOfYear("2016-03"),
            "Fee for program");
      feeProgramService.save(feeProgram);

      
      user.setPrograms(programs);
      accountService.update(user, false);

   }

   /**
    * Display TrainingView page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayuserPaymentsViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/userPayments")).andExpect(redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysTrainingView.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayUserPaymentsTest() throws Exception {

      // Charge csrf in pay member
      PayMember userFeeMember = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Charge csrf in pay program
      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());
      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      mockMvc
            .perform(
                  get("/userPayments").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("userpayments/userpayments"))
            .andExpect(content().string(containsString("<title>My payments</title>")));
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payPayMemberTest() throws Exception {

      PayMember userFeeMember = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
      // Assert no pay
      assertEquals(userFeeMember.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(userFeeMember.isHasPay(), true);
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payProgressPayMemberTest() throws Exception {

      PayMember userFeeMember = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
      // Assert no pay
      assertEquals(userFeeMember.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Progress").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(userFeeMember.isHasPay(), false);
      assertEquals(userFeeMember, userFeeMemberService.findByIdTxn("txn"));
   }

   /**
    * Pay Exist TransactionId Exception Test
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payMemberExistTransactionIdExceptionTest() throws Exception {

      PayMember userFeeMember = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
      
      
      FeeMember feeMember2 = new FeeMember("pay of 2017", 2017, Double.valueOf(20),
            DisplayDate.stringToDate2("2017-04-05"), DisplayDate.stringToDate2("2017-07-05"),
            "pay of 2017");
      feeMemberService.save(feeMember2);
      
      PayMember userFeeMember2 = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember2.getId()).get(0);
      
      // Assert no pay
      assertEquals(userFeeMember.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));
      
      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMember2.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn"))
                  .andExpect(view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(userFeeMember.isHasPay(), true);
   }

   /**
    * Pay Other Account PayMember Test
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payOtherAccountPayMemberTest() throws Exception {

      PayMember userFeeMember = userFeeMemberService
            .findByPayMemberIds(user.getId(), feeMember.getId()).get(0);
      // Assert no pay
      assertEquals(userFeeMember.isHasPay(), false);
      Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      feeMemberService.savePayMember(user2, feeMember);
      PayMember userFeeMemberProbe = userFeeMemberService
            .getPayMemberListByAccountId(user2.getId()).get(0);

      mockMvc.perform(
            post("/userPayments/payMember/" + userFeeMemberProbe.getId())
                  .locale(Locale.ENGLISH).session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(userFeeMemberProbe.isHasPay(), false);
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payProgramPayProgramTest() throws Exception {

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());
      // Assert no pay
      assertEquals(payProgram.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(payProgram.isHasPay(), true);
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payProgramProgressUserPayProgramTest() throws Exception {

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());
      // Assert no pay
      assertEquals(payProgram.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Progress").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(payProgram.isHasPay(), false);
      assertEquals(payProgram, payProgramService.findByIdTxn("txn"));
   }

   /**
    * Pay Exist TransactionId Exception Test
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payProgramExistTransactionIdExceptionTest() throws Exception {

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());

      FeeProgram feeProgram2 = new FeeProgram("Fee April 2015", Double.valueOf(25),
            DisplayDate.stringToMonthOfYear("2016-04"), DisplayDate.stringToMonthOfYear("2016-04"),
            "Fee for program");
      feeProgramService.save(feeProgram2);
      PayProgram payProgram2 = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram2.getId());
      
      // Assert no pay
      assertEquals(payProgram.isHasPay(), false);

      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));
      
      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgram2.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn"))
                        .andExpect(view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(payProgram.isHasPay(), true);
   }

   /**
    * Pay Other Account PayMember Test
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payProgramOtherAccountPayProgramTest() throws Exception {

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());
      // Assert no pay
      assertEquals(payProgram.isHasPay(), false);

      Account user2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user2.setAccountType(accountType);
      user2.setMethodPayment(methodPayment);
      user2.setInstallments(1);
      accountService.update(user2, false);

      // Create Program and Payments
      List<Account> accounts = new ArrayList<Account>();
      accounts.add(user2);
      Program program2 = new Program("Program 2", Float.valueOf(1), "About program", 1, accounts);
      programService.save(program2);
      programService.up(program2.getId());

      FeeProgram feeProgram2 = new FeeProgram("Fee Jun 2015", Double.valueOf(25),
            DisplayDate.stringToMonthOfYear("2016-06"), DisplayDate.stringToMonthOfYear("2016-08"),
            "Fee for program");
      feeProgramService.save(feeProgram2);

      PayProgram payProgramProbe = payProgramService.findByPayProgramIds(program2.getId(),
            feeProgram2.getId());

      mockMvc.perform(
            post("/userPayments/payProgram/" + payProgramProbe.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession).sessionAttr("_csrf", "csrf")
                  .param("payer_email", "email").param("payer_id", "id")
                  .param("payment_date", "10:10:10 Jun 10, 2015")
                  .param("payment_status", "Completed").param("txn_id", "txn")).andExpect(
            view().name("redirect:/userPayments"));

      // Assert Pay
      assertEquals(payProgramProbe.isHasPay(), false);
   }
}