package org.cuacfm.members.test.model.feemember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class rainingTypeServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeMemberServiceTest extends WebSecurityConfigurationAware {

   /** The training service. */
   @Autowired
   private FeeMemberService feeMemberService;

   /** The training service. */
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
    * Save and find by FeeMember test.
    * 
    * @throws UniqueException
    */
   @Test
   public void saveFeeMemberTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // findById
      FeeMember feeMemberSearch = feeMemberService.findById(feeMember.getId());
      assertEquals(feeMember, feeMemberSearch);
   }

   /**
    * Save and find by FeeMember test.
    * 
    * @throws UniqueException
    */
   @Test
   public void saveFeeMemberWithUsersTest() throws UniqueException {
      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);

      Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      Account user2 = new Account("user2", "11111111C", "London", "user2", "email2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // findById
      FeeMember feeMemberSearch = feeMemberService.findById(feeMember.getId());
      assertEquals(feeMember, feeMemberSearch);

      assertEquals(payMemberService.getPayMemberListByFeeMemberId(feeMember.getId()).size(), 2);
   }

   /**
    * Save and find by FeeMember test.
    * 
    * @throws UniqueException
    */
   @Test(expected = UniqueException.class)
   public void saveFeeMemberExceptionTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);
      FeeMember feeMember2 = new FeeMember("pay of 2016", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember2);

      // findById
      FeeMember feeMemberSearch = feeMemberService.findById(feeMember.getId());
      assertEquals(feeMember, feeMemberSearch);
   }

   /**
    * Save and find by FeeMember test.
    * 
    * @throws UniqueException
    */
   @Test
   public void savePayMemberTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      AccountType accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);

      Account user = new Account("user", "11111111C", "London", "user", "email1@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);
      feeMemberService.savePayMember(user, feeMember);

      Account user2 = new Account("user2", "22222222C", "London", "user2", "email2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user2);
      user.setInstallments(2);
      accountService.update(user2, false);
      feeMemberService.savePayMember(user2, feeMember);

      Account user3 = new Account("user3", "33333333C", "London", "user3", "email3@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user3);
      user.setInstallments(3);
      accountService.update(user3, false);
      feeMemberService.savePayMember(user3, feeMember);

      Account user4 = new Account("user4", "44444444C", "London", "user4", "email4@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user4);
      user.setInstallments(4);
      accountService.update(user4, false);
      feeMemberService.savePayMember(user4, feeMember);
      
      Account user5 = new Account("user5", "55555555C", "London", "user5", "email5@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user5);
      user.setInstallments(5);
      accountService.update(user5, false);
      feeMemberService.savePayMember(user5, feeMember);
      
      Account user6 = new Account("user6", "66666666C", "London", "user6", "email6@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user6);
      user.setInstallments(6);
      accountService.update(user6, false);
      feeMemberService.savePayMember(user6, feeMember);
      
      Account user7 = new Account("user7", "77777777C", "London", "user7", "email7@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user7);
      user.setInstallments(7);
      accountService.update(user7, false);
      feeMemberService.savePayMember(user7, feeMember);
      
      Account user0 = new Account("user0", "00000000C", "London", "user0", "email0@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(user0);
      user.setInstallments(0);
      accountService.update(user7, false);
      feeMemberService.savePayMember(user0, feeMember);

      // findById

      FeeMember feeMemberSearch = feeMemberService.findById(feeMember.getId());
      assertEquals(feeMember, feeMemberSearch);
   }

   /**
    * Update
    * 
    * @throws UniqueException
    */
   @Test
   public void UpdateFeeMemberTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // Update
      feeMember.setName("pay of 1016");
      feeMember.setYear(2016);
      feeMember.setPrice(Double.valueOf(25));
      feeMember.setDescription("pay of 2016, tax for members");
      feeMemberService.update(feeMember);

      // Assert
      FeeMember feeMemberSearch = feeMemberService.findById(feeMember.getId());
      assertEquals(feeMember, feeMemberSearch);
      assertEquals(feeMember.getName(), feeMemberSearch.getName());
      assertEquals(feeMember.getYear(), feeMemberSearch.getYear());
      assertEquals(feeMember.getPrice(), feeMemberSearch.getPrice());
      assertEquals(feeMember.getDescription(), feeMemberSearch.getDescription());

      FeeMember feeMember2 = new FeeMember("pay of 2018", 2018, Double.valueOf(20),
            DisplayDate.stringToDate2("2018-04-05"), DisplayDate.stringToDate2("2018-07-05"),
            "pay of 2018");
      feeMemberService.update(feeMember2);
   }

   /**
    * Update Inscription Exception
    * 
    * @throws UniqueException
    */
   @Test(expected = UniqueException.class)
   public void UpdateFeeMemberExceptionTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);
      FeeMember feeMember2 = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.save(feeMember2);

      // Update
      FeeMember feeMember3 = new FeeMember("pay of 2016", 2016, Double.valueOf(20),
            DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"),
            "pay of 2016");
      feeMemberService.update(feeMember3);
   }

   /**
    * findByName test.
    * 
    * @throws UniqueException
    */
   @Test
   public void findByNameTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // findByName
      FeeMember feeMemberSearch = feeMemberService.findByName(feeMember.getName());
      assertEquals(feeMember, feeMemberSearch);

      feeMemberService.findByName(feeMember.getName());
      assertNull(feeMemberService.findByName("do not exist"));
   }

   /**
    * findByYear test.
    * 
    * @throws UniqueException
    */
   @Test
   public void findByYearTest() throws UniqueException {

      // Save
      FeeMember feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      // findByName
      FeeMember feeMemberSearch = feeMemberService.findByYear(feeMember.getYear());
      assertEquals(feeMember, feeMemberSearch);
   }

   /**
    * getFeeMemberList test.
    * 
    * @throws UniqueException
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
}
