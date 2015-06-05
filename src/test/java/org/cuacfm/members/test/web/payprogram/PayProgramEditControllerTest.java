package org.cuacfm.members.test.web.payprogram;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
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

/** The Class PayProgramEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayProgramEditControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The program service. */
   @Inject
   private ProgramService programService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The account type service. */
   @Inject
   private AccountTypeService accountTypeService;

   /** The method payment service. */
   @Inject
   private MethodPaymentService methodPaymentService;

   /** The fee program service. */
   @Inject
   private FeeProgramService feeProgramService;

   /** The pay program service. */
   @Inject
   private PayProgramService payProgramService;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /** The fee program. */
   private FeeProgram feeProgram;

   /** The pay. */
   private PayProgram pay;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);

      Account admin = new Account("admin", "55555555B", "London", "admin", "admin@udc.es",
            666666666, 666666666, "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      admin.setAccountType(accountType);
      admin.setMethodPayment(methodPayment);
      admin.setInstallments(1);
      accountService.update(admin, false);
      defaultSession = getDefaultSession("admin");

      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "22222222C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "33333333C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);
      accounts.add(account2);

      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());

      Program program2 = new Program("Pepe2", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program2);
      programService.up(program2.getId());

      // Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      pay = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
   }

   /**
    * Display PayProgramList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayPayProgramEditPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeProgramList/payProgramList/payProgramEdit")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectTrainingListBecausePayProgramIsNullTest() throws Exception {
      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit/" + Long.valueOf(0)).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList/payProgramEdit"));

      mockMvc.perform(
            get("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH).session(
                  defaultSession))
            .andExpect(view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Displays user pay inscription edit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysPayProgramEditTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit/" + pay.getId()).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList/payProgramEdit"));

      mockMvc
            .perform(
                  get("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                        .session(defaultSession))
            .andExpect(view().name("payprogram/payprogramedit"))
            .andExpect(model().attributeExists("payProgramForm"))
            .andExpect(content().string(containsString("<title>Edit pay program</title>")));
   }

   /**
    * Send displaysPayProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postPayProgramEditTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit/" + pay.getId()).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList/payProgramEdit"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("AccountPayer", "pepe").param("idPayer", "1G3210")
                  .param("idTxn", "1G3210").param("emailPayer", "user@hotmail.com")
                  .param("datePay", "10:10 10/10/2015")).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Send displaysPayProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postBlankPayProgramEditTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit/" + pay.getId()).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList/payProgramEdit"));

      mockMvc
            .perform(
                  get("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                        .session(defaultSession))
            .andExpect(view().name("payprogram/payprogramedit"))
            .andExpect(model().attributeExists("payProgramForm"))
            .andExpect(content().string(containsString("<title>Edit pay program</title>")));

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("AccountPayer", "").param("idPayer", "").param("idTxn", "")
                  .param("emailPayer", "").param("datePay", "")).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Max characters in user pay inscription edit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersInPayProgramEditTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit/" + pay.getId()).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList/payProgramEdit"));

      mockMvc
            .perform(
                  post("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                        .session(defaultSession).param("price", "24").param("hasPay", "true")
                        .param("installment", "1").param("installments", "1")
                        .param("idPayer", "1G3210111111111111111111111111111111111111111")
                        .param("idTxn", "1G321011111111111111111111111111111111111111111")
                        .param("emailPayer", "user@hotmail.com11111111111111111111111111")
                        .param("statusPay", "Completed1111111111111111111111111111111111")
                        .param("datePay", "10:10 10/10/2015"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("payprogram/payprogramedit"));

   }

   /**
    * Exist transaction id test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void existTransactionIdTest() throws Exception {

      pay.setIdTxn("1G3210");
      payProgramService.update(pay);

      mockMvc.perform(
            post("/feeProgramList/payProgramList/payProgramEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("AccountPayer", "pepe").param("idPayer", "1G3210")
                  .param("idTxn", "1G3210").param("emailPayer", "user@hotmail.com")
                  .param("datePay", "10:10 10/10/2015")).andExpect(
            view().name("payprogram/payprogramedit"));

   }
}