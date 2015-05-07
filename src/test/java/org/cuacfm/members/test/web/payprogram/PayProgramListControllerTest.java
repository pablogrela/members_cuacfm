package org.cuacfm.members.test.web.payprogram;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.cuacfm.members.model.exceptions.UniqueException;
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

/** The Class PayProgramListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayProgramListControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The program service. */
   @Inject
   private ProgramService programService;

   /** The fee program service. */
   @Inject
   private FeeProgramService feeProgramService;

   /** The training service. */
   @Inject
   private PayProgramService payProgramService;

   /** The fee program. */
   private FeeProgram feeProgram;

   /** The pay program. */
   private PayProgram payProgram;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "11111111D", "London", "admin", "admin@udc.es",
            666666666, 666666666, "demo", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      // Create User
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
      PayProgram payProgram2 = payProgramService.findByPayProgramIds(program2.getId(),
            feeProgram.getId());
      payProgramService.pay(payProgram2);

      payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
   }

   /**
    * Display PayProgramList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayInscriptionsViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeProgramList/payProgramList")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send PayProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysInscriptionsTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc
            .perform(
                  get("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("payprogram/payprogramlist"))
            .andExpect(content().string(containsString("<title>Fee program of users</title>")));
   }

   /**
    * Send redirect FeeProgramList Because FeeProgram Is Null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectTrainingListBecauseTrainingIsNullTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + Long.valueOf(0)).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            get("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/feeProgramList"));
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payPayProgramTest() throws Exception {

      // Assert no pay
      assertEquals(payProgram.isHasPay(), false);

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList/pay/" + payProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      // Assert Pay
      assertEquals(

      payProgram.isHasPay(), true);
   }

   /**
    * Creates the pdf all.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfALL() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("createPdf", "ALL").param("path", System.getProperty("user.dir"))
                  .param("file", "fileTest")).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Creates the pdf pay.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfPAy() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("createPdf", "PAY").param("path", System.getProperty("user.dir"))
                  .param("file", "fileTest")).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Creates the pdf no pay.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createPdfNoPAy() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("createPdf", "NOPAY").param("path", System.getProperty("user.dir"))
                  .param("file", "fileTest")).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));
   }

   /**
    * Blank message create pdf test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void blankMessageCreatePdfTest() throws Exception {
      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc
            .perform(
                  get("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("payprogram/payprogramlist"))
            .andExpect(content().string(containsString("<title>Fee program of users</title>")));
      mockMvc
            .perform(
                  post("/feeProgramList/payProgramList").locale(Locale.ENGLISH)
                        .session(defaultSession).param("createPdf", "ALL").param("path", " ")
                        .param("file", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("payprogram/payprogramlist"));
   }

   /**
    * Blank messag create pdf test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void pathNotFound() throws Exception {
      mockMvc.perform(
            post("/feeProgramList/payProgramList/" + feeProgram.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/feeProgramList/payProgramList"));

      mockMvc.perform(
            post("/feeProgramList/payProgramList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("createPdf", "ALL").param("path", "/path/").param("file", "file"))
            .andExpect(view().name("redirect:/feeProgramList/payProgramList"));
   }
}