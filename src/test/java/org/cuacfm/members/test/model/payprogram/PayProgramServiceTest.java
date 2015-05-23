package org.cuacfm.members.test.model.payprogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayProgramServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayProgramServiceTest extends WebSecurityConfigurationAware {

   /** The program service. */
   @Inject
   private ProgramService programService;

   @Inject
   private FeeProgramService feeProgramService;

   @Inject
   private PayProgramService payProgramService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /**
    * Save and update user pay inscription test.
    * 
    * @throws UniqueException
    * @throws ExistTransactionIdException 
    */
   @Test
   public void SaveAndUpdatePayProgramTest() throws UniqueException, ExistTransactionIdException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);
      accounts.add(account2);

      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());

      // Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(),
            feeProgram.getId());

      // Update
      payProgram.setDatePay(new Date());
      payProgram.setHasPay(true);
      payProgram.setAccountPayer("Pepe");
      payProgram.setEmailPayer("user2@udc.es");
      payProgram.setIdPayer("idPayer");
      payProgram.setIdTxn("IDTXT");
      payProgram.setStatusPay("Waiting");
      payProgram.setPrice(Double.valueOf(30));
      payProgramService.update(payProgram);

      // Assert
      PayProgram payProgramSearch = payProgramService.findById(payProgram.getId());
      assertEquals(payProgram, payProgramSearch);
      assertEquals(payProgram.getAccountPayer(), payProgramSearch.getAccountPayer());
      assertEquals(payProgram.getDatePay(), payProgramSearch.getDatePay());
      assertEquals(payProgram.isHasPay(), payProgramSearch.isHasPay());
      assertEquals(payProgram.getIdPayer(), payProgramSearch.getIdPayer());
      assertEquals(payProgram.getIdTxn(), payProgramSearch.getIdTxn());
      assertEquals(payProgram.getStatusPay(), payProgramSearch.getStatusPay());
      assertEquals(payProgram.getEmailPayer(), payProgramSearch.getEmailPayer());
      assertEquals(payProgram.getPrice(), payProgramSearch.getPrice());
   }

   /**
    * Gets the user pay inscription list by pay inscription test.
    *
    * @return the user pay inscription list by pay inscription test
    * @throws UniqueException
    */
   @Test
   public void getPayProgramListByFeeProgramTest() throws UniqueException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);
      accounts.add(account2);

      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());

      // Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      // Assert
      List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram
            .getId());
      assertEquals(payPrograms.size(), 1);
      for (PayProgram payProgram : payPrograms) {
         assertEquals(payProgram.getFeeProgram(), feeProgram);
      }

      payPrograms = payProgramService.getPayProgramListByAccountId(account.getId());
      assertEquals(payPrograms.size(), 0);

      payPrograms = payProgramService.getPayProgramListByAccountId(account2.getId());
      assertEquals(payPrograms.size(), 0);

      payPrograms = payProgramService.getPayProgramList();
      assertEquals(payPrograms.size(), 1);
      for (PayProgram payProgram : payPrograms) {
         assertEquals(payProgram.getFeeProgram(), feeProgram);
      }
   }

   /**
    * Gets the user pay inscription list by account id test.
    *
    * @return the user pay inscription list by account id test
    * @throws UniqueException
    */
   @Test
   public void getPayProgramListByAccountIdTest() throws UniqueException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      List<Program> programs = new ArrayList<Program>();

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);

      Program program = new Program("Pepe", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);
      programService.up(program.getId());

      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);
      accounts.add(account2);

      Program program2 = new Program("Pepe2", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program2);
      programService.up(program2.getId());

      programs.add(program2);
      account2.setPrograms(programs);
      accountService.update(account2, false);

      programs.add(program);
      account.setPrograms(programs);
      accountService.update(account, false);

      Program program3 = new Program("Pepe3", Float.valueOf(1), "Very interesting", 9,
            new ArrayList<Account>());
      programService.save(program3);
      programService.up(program3.getId());

      // Save
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      // Assert
      List<PayProgram> payPrograms = payProgramService
            .getPayProgramListByAccountId(account.getId());
      assertEquals(payPrograms.size(), 2);

      payPrograms = payProgramService.getPayProgramListByAccountId(account2.getId());
      assertEquals(payPrograms.size(), 2);
   }

   /**
    * Gets the user pay inscription list test.
    *
    * @return the user pay inscription list test
    * @throws UniqueException
    */
   @Test
   public void getPayProgramListTest() throws UniqueException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
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
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      // Assert
      List<PayProgram> payPrograms = payProgramService.getPayProgramList();
      assertEquals(payPrograms.size(), 2);
      for (PayProgram payProgram : payPrograms) {
         assertEquals(payProgram.getFeeProgram(), feeProgram);
      }
   }

   /**
    * Pay user pay inscription test.
    * 
    * @throws UniqueException
    */
   @Test
   public void payPayProgramTest() throws UniqueException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
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
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram
            .getId());
      payProgramService.pay(payPrograms.get(0));

      // Assert
      assertEquals(payPrograms.get(0).isHasPay(), true);
   }

   @Test
   public void payPayPalProgramTest() throws UniqueException, ExistTransactionIdException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
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
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      List<PayProgram> payPrograms = payProgramService.getPayProgramListByFeeProgramId(feeProgram
            .getId());

      payProgramService.payPayPal(payPrograms.get(0), "accountPayer", "idTxn", "idPayer",
            "emailPayer", "statusPay", "12:12:12 Jun 12, 2015");
      assertFalse(payPrograms.get(0).isHasPay());

      payProgramService.payPayPal(payPrograms.get(0), "accountPayer", "idTxn", "idPayer",
            "emailPayer", "Completed", "12:12:12 Jun 12, 2015");
      assertTrue(payPrograms.get(0).isHasPay());
   }

   
   @Test (expected = ExistTransactionIdException.class)
   public void existTransactionIdTest() throws UniqueException, ExistTransactionIdException {

      // Save
      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
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
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      PayProgram payProgram = payProgramService.findByPayProgramIds(program.getId(), feeProgram.getId());
      payProgram.setIdTxn("a");
      payProgramService.update(payProgram);
      payProgramService.update(payProgram);

      PayProgram payProgram2 = new PayProgram(program2, feeProgram, Double.valueOf(25));
      payProgram2.setIdTxn("a");
      payProgramService.update(payProgram2);
   }
   /**
    * Gets the pay inscription list test.
    *
    * @return the pay inscription list test
    * @throws UniqueException
    */
   @Test
   public void getFeeProgramListTest() throws UniqueException {

      // getFeeProgramList, no FeePrograms
      List<FeeProgram> feeProgramList = feeProgramService.getFeeProgramList();
      // Assert
      assertTrue(feeProgramList.isEmpty());

      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      Account account2 = new Account("user2", "25555555C", "London", "user2", "user2@udc.es",
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
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      // getFeeProgramList
      feeProgramList = feeProgramService.getFeeProgramList();
      // Assert
      assertEquals(feeProgramList.size(), 1);
   }
}
