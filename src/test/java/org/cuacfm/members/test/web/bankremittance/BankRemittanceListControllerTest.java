package org.cuacfm.members.test.web.bankremittance;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
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

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.States.states;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankRemittanceListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankRemittanceListControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /** The direct debit service. */
   @Autowired
   private BankRemittanceService directDebitService;

   /** The fee member service. */
   @Autowired
   private FeeMemberService feeMemberService;

   /** The account service. */
   @Autowired
   private AccountService accountService;
   
   /** The account type service. */
   @Autowired
   private AccountTypeService accountTypeService;
   
   /** The method payment service. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /** The program service. */
   @Autowired
   private ProgramService programService;

   /** The fee program service. */
   @Autowired
   private FeeProgramService feeProgramService;

   /** The bank remittance service. */
   @Autowired
   private BankRemittanceService bankRemittanceService;
   
   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "55555555B", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      MethodPayment methodPayment = new MethodPayment("name", true, "domiciliacion");
      methodPaymentService.save(methodPayment);

      // Create User
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user1", "11111111C", "London", "user11", "user1@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account);
      account.setMethodPayment(methodPayment);
      List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
      
      BankAccount bankAccount = new BankAccount(account, "Banco", "BSCHESMMXXX", "ES7620770024003102575766");
      accountService.saveBankAccount(bankAccount);
      bankAccounts.add(bankAccount);
      account.setBankAccounts(bankAccounts);
      
      accountService.update(account, false);
      accounts.add(account);

      Account account2 = new Account("user2", "12222222C", "London", "user2", "user2@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account2);
      account2.setMethodPayment(methodPayment);
      List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();
      BankAccount bankAccount2 = new BankAccount(account2, "Banco", "BSCHESMMXXX", "ES7620770024003102575766");
      accountService.saveBankAccount(bankAccount2);
      bankAccounts2.add(bankAccount2);
      account2.setBankAccounts(bankAccounts2);
      accountService.update(account2, false);
      accounts.add(account2);

      Account account3 = new Account("user3", "33333333C", "London", "user3", "user3@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_USER);
      accountService.save(account3);
      account3.setMethodPayment(methodPayment);
      
      AccountType accountType = new AccountType("Organization", true, "Organization", 0);
      accountTypeService.save(accountType);
      account3.setAccountType(accountType);
      
      List<BankAccount> bankAccounts3 = new ArrayList<BankAccount>();
      BankAccount bankAccount3 = new BankAccount(account3, "Banco", "BSCHESMMXXX", "ES7620770024003102575766");
      accountService.saveBankAccount(bankAccount3);
      bankAccounts3.add(bankAccount3);
      account3.setBankAccounts(bankAccounts3);
      
      accountService.update(account3, false);

      FeeMember feeMember = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember);

      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());
      program.setAccountPayer(account);
      programService.update(program);

      Program program2 = new Program("Pepe2", Float.valueOf(1), "Very interesting", 9,
            new ArrayList<Account>());
      programService.save(program2);
      programService.up(program2.getId());

      List<Account> accounts2 = new ArrayList<Account>();
      accounts2.add(account3);
      Program program3 = new Program("Pepe3", Float.valueOf(1), "Very interesting", 9, accounts2);
      programService.save(program3);
      programService.up(program3.getId());
      program3.setAccountPayer(account2);
      programService.update(program3);

      // Save
      Date date = DisplayDate.stringToMonthOfYear("2015-01");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
   }

   /**
    * Display PayProgramList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayPayProgramEditPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/bankRemittanceList")).andExpect(redirectedUrl("http://localhost/signin"));
   }

   /**
    * Displays user pay inscription edit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysBankRemittance() throws Exception {

      mockMvc.perform(get("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("bankremittance/bankremittancelist"))
            .andExpect(model().attributeExists("bankRemittanceForm"))
            .andExpect(content().string(containsString("<title>Bank remittances</title>")));
   }

   /**
    * Bank remittance test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void createBankRemittanceTest() throws Exception {

      mockMvc.perform(
            post("/bankRemittanceList").locale(Locale.ENGLISH).session(defaultSession)
                  .param("dateCharge", "2015-07-05")
                  .param("monthCharge", "2015-07"))
                  .andExpect(view().name("redirect:/bankRemittanceList"));
   }

   /**
    * Pay direct debit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void payBankRemittanceTest() throws Exception {

      bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
      BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

      mockMvc.perform(
            post("/bankRemittanceList/pay/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/bankRemittanceList"));
      assertTrue(bankRemittance.getState().equals(states.PAY));
      
      Date date = DisplayDate.stringToMonthOfYear("2015-04");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
      bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-04-01"));
      
      BankRemittance bankRemittance2 = bankRemittanceService.getBankRemittanceList().get(1);

      mockMvc.perform(
            post("/bankRemittanceList/pay/" + bankRemittance2.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/bankRemittanceList"));
      assertTrue(bankRemittance2.getState().equals(states.PAY));
   }
   
   @Test
   public void managementBankRemittanceTest() throws Exception {

      bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
      BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

      mockMvc.perform(
            post("/bankRemittanceList/management/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/bankRemittanceList"));
      assertTrue(bankRemittance.getState().equals(states.MANAGEMENT));
      
      Date date = DisplayDate.stringToMonthOfYear("2015-04");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);
      bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-04-01"));
      
      BankRemittance bankRemittance2 = bankRemittanceService.getBankRemittanceList().get(1);

      mockMvc.perform(
            post("/bankRemittanceList/management/" + bankRemittance2.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/bankRemittanceList"));
      assertTrue(bankRemittance2.getState().equals(states.MANAGEMENT));
   }
   
   /**
    * Download bank remittance test.
    *
    * @throws Exception the exception
    */
   @Test
   public void downloadBankRemittanceTest() throws Exception {

      bankRemittanceService.createBankRemittance(new Date(), DisplayDate.stringToDate2("2015-01-01"));
      BankRemittance bankRemittance = bankRemittanceService.getBankRemittanceList().get(0);

      mockMvc.perform(
            post("/bankRemittanceList/downloadBankRemittance/" + bankRemittance.getId()).locale(Locale.ENGLISH).session(
                  defaultSession));
   }
}