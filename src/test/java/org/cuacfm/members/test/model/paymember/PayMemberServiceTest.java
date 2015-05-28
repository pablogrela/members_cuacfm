package org.cuacfm.members.test.model.paymember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayMemberServiceTest extends WebSecurityConfigurationAware {

   /** The fee member service. */
   @Autowired
   private FeeMemberService feeMemberService;

   /** The pay member service. */
   @Autowired
   private PayMemberService payMemberService;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The account type service. */
   @Autowired
   private AccountTypeService accountTypeService;

   /** The method payment service. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /**
    * Save and update user pay inscription test.
    *
    * @throws UniqueException
    *            the unique exception
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   @Test
   public void SaveAndUpdatePayMemberTest() throws UniqueException, ExistTransactionIdException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(2);
      accountService.update(user, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);
      PayMember payMember = new PayMember(user, feeMember, feeMember.getPrice(), 1,
            user.getInstallments(), DisplayDate.stringToDate2("2015-01-01"));
      payMemberService.save(payMember);

      // Update
      payMember.setDatePay(new Date());
      payMember.setHasPay(true);
      payMember.setInstallment(3);
      payMember.setInstallments(3);
      payMember.setPrice(Double.valueOf(30));
      payMember.setDatePay(DisplayDate.stringToDate("2015-12-04"));
      payMember.setDateCharge(DisplayDate.stringToDate("2015-12-04"));
      payMemberService.update(payMember);

      // Assert
      PayMember payMemberSearch = payMemberService.findById(payMember.getId());
      assertEquals(payMember, payMemberSearch);
      assertEquals(payMember.getAccount(), payMemberSearch.getAccount());
      assertEquals(payMember.getDatePay(), payMemberSearch.getDatePay());
      assertEquals(payMember.isHasPay(), payMemberSearch.isHasPay());
      assertEquals(payMember.getInstallment(), payMemberSearch.getInstallment());
      assertEquals(payMember.getInstallments(), payMemberSearch.getInstallments());
      assertEquals(payMember.getPrice(), payMemberSearch.getPrice());
      assertEquals(payMember.getDateCharge(), payMemberSearch.getDateCharge());
   }

   /**
    * Gets the user pay inscription list by pay inscription test.
    *
    * @return the user pay inscription list by pay inscription test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getPayMemberListByFeeMemberTest() throws UniqueException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      account.setAccountType(accountType);
      account.setMethodPayment(methodPayment);
      account.setInstallments(2);
      accountService.update(account, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // Assert
      List<PayMember> payMembers = payMemberService
            .getPayMemberListByFeeMemberId(feeMember.getId());
      assertEquals(payMembers.size(), 3);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }

      payMembers = payMemberService.getPayMemberListByAccountId(user.getId());
      assertEquals(payMembers.size(), 1);

      payMembers = payMemberService.getPayMemberListByAccountId(account.getId());
      assertEquals(payMembers.size(), 2);

      payMembers = payMemberService.getPayMemberList();
      assertEquals(payMembers.size(), 3);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }

      payMembers = payMemberService.findByPayMemberIds(account.getId(), feeMember.getId());
      assertEquals(payMembers.size(), 2);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }
   }

   /**
    * Gets the user pay inscription list by account id test.
    *
    * @return the user pay inscription list by account id test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getPayMemberListByAccountIdTest() throws UniqueException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555D", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      account.setAccountType(accountType);
      account.setMethodPayment(methodPayment);
      account.setInstallments(2);
      accountService.update(account, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // Assert
      List<PayMember> payMembers = payMemberService.getPayMemberListByAccountId(user.getId());
      assertEquals(payMembers.size(), 1);

      payMembers = payMemberService.getPayMemberListByAccountId(account.getId());
      assertEquals(payMembers.size(), 2);
   }

   /**
    * Gets the user pay inscription list test.
    *
    * @return the user pay inscription list test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getPayMemberListTest() throws UniqueException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      account.setAccountType(accountType);
      account.setMethodPayment(methodPayment);
      account.setInstallments(2);
      accountService.update(account, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // Assert
      List<PayMember> payMembers = payMemberService.getPayMemberList();
      assertEquals(payMembers.size(), 3);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }

      payMembers = payMemberService.findByPayMemberIds(account.getId(), feeMember.getId());
      assertEquals(payMembers.size(), 2);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }
   }

   /**
    * Find by user pay inscription ids.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void findByPayMemberIds() throws UniqueException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      account.setAccountType(accountType);
      account.setMethodPayment(methodPayment);
      account.setInstallments(2);
      accountService.update(account, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // Assert
      List<PayMember> payMembers = payMemberService.findByPayMemberIds(account.getId(),
            feeMember.getId());
      assertEquals(payMembers.size(), 2);
      for (PayMember payMember : payMembers) {
         assertEquals(payMember.getFeeMember(), feeMember);
      }
   }

   /**
    * Pay user pay inscription test.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void payPayMemberTest() throws UniqueException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(2);
      accountService.update(user, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      List<PayMember> payMembers = payMemberService
            .getPayMemberListByFeeMemberId(feeMember.getId());
      payMemberService.pay(payMembers.get(0));

      // Assert
      assertEquals(payMembers.get(0).isHasPay(), true);
   }

   /**
    * Exist transaction id test.
    *
    * @throws UniqueException
    *            the unique exception
    * @throws ExistTransactionIdException
    *            the exist transaction id exception
    */
   @Test(expected = ExistTransactionIdException.class)
   public void existTransactionIdTest() throws UniqueException, ExistTransactionIdException {

      // Save
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);

      // Update Account
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(2);
      accountService.update(user, false);

      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      PayMember payMember = payMemberService.getPayMemberListByFeeMemberId(feeMember.getId())
            .get(0);
      payMember.setIdTxn("a");
      payMemberService.update(payMember);
      payMemberService.update(payMember);

      Account user2 = new Account("user2", "255555555C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      PayMember payMember2 = new PayMember(user2, feeMember, Double.valueOf(24), 1, 1, new Date());
      payMember2.setIdTxn("a");
      payMemberService.update(payMember2);
   }

   /**
    * Gets the pay inscription list test.
    *
    * @return the pay inscription list test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getFeeMemberListTest() throws UniqueException {

      // getFeeMemberList, no FeeMembers
      List<FeeMember> feeMemberList = feeMemberService.getFeeMemberList();
      // Assert
      assertTrue(feeMemberList.isEmpty());

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);
      FeeMember feeMember2 = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember2);

      // getFeeMemberList
      feeMemberList = feeMemberService.getFeeMemberList();
      // Assert
      assertEquals(feeMemberList.size(), 2);
   }

   /**
    * Gets the user pay inscription list by pay inscription test.
    *
    * @return the user pay inscription list by pay inscription test
    * @throws UniqueException
    *            the unique exception
    */
   @Test
   public void getUsernamesByFeeMember() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      Account account3 = new Account("user3", "55555555F", "London", "user3", "user3@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account3);
      account3.setNickName("terminataror");
      accountService.update(account3, false);

      // Assert
      List<String> payMembers = payMemberService.getUsernamesByFeeMember(feeMember.getId());
      assertEquals(payMembers.size(), 3);
   }
}
