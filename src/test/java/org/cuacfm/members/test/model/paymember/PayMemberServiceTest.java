package org.cuacfm.members.test.model.paymember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

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
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class PayMemberServiceTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayMemberServiceTest extends
		WebSecurityConfigurationAware {

	/** The training service. */
	@Inject
	private FeeMemberService payInscriptionService;

	/** The training service. */
	@Inject
	private PayMemberService payMemberService;

	/** The account service. */
	@Inject
	private AccountService accountService;

	/** The account type service. */
	@Inject
	private AccountTypeService accountTypeService;

	/** The method payment service. */
	@Inject
	private MethodPaymentService methodPaymentService;

	/**
	 * Save and update user pay inscription test.
	 * @throws UniqueException 
	 */
	@Test
	public void SaveAndUpdatePayMemberTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayMember payMember = new PayMember(user,
				payInscription, payInscription.getPrice(), 1,
				user.getInstallments());
		payMemberService.save(payMember);

		// Update
		payMember.setDatePay(new Date());
		payMember.setHasPay(true);
		payMember.setInstallment(3);
		payMember.setInstallments(3);
		payMember.setPrice(Double.valueOf(30));
		payMemberService.update(payMember);

		// Assert
		PayMember payMemberSearch = payMemberService
				.findById(payMember.getId());
		assertEquals(payMember, payMemberSearch);
		assertEquals(payMember.getAccount(),
				payMemberSearch.getAccount());
		assertEquals(payMember.getDatePay(),
				payMemberSearch.getDatePay());
		assertEquals(payMember.isHasPay(),
				payMemberSearch.isHasPay());
		assertEquals(payMember.getInstallment(),
				payMemberSearch.getInstallment());
		assertEquals(payMember.getInstallments(),
				payMemberSearch.getInstallments());
		assertEquals(payMember.getPrice(),
				payMemberSearch.getPrice());
	}

	/**
	 * Gets the user pay inscription list by pay inscription test.
	 *
	 * @return the user pay inscription list by pay inscription test
	 * @throws UniqueException 
	 */
	@Test
	public void getPayMemberListByFeeMemberTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
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

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<PayMember> payMembers = payMemberService
				.getPayMemberListByFeeMemberId(payInscription
						.getId());
		assertEquals(payMembers.size(), 3);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}

		payMembers = payMemberService
				.getPayMemberListByAccountId(user.getId());
		assertEquals(payMembers.size(), 1);

		payMembers = payMemberService
				.getPayMemberListByAccountId(account.getId());
		assertEquals(payMembers.size(), 2);

		payMembers = payMemberService
				.getPayMemberList();
		assertEquals(payMembers.size(), 3);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}

		payMembers = payMemberService
				.findByPayMemberIds(account.getId(),
						payInscription.getId());
		assertEquals(payMembers.size(), 2);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}
	}

	/**
	 * Gets the user pay inscription list by account id test.
	 *
	 * @return the user pay inscription list by account id test
	 * @throws UniqueException 
	 */
	@Test
	public void getPayMemberListByAccountIdTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		Account account = new Account("user2", "55555555D", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
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

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20),DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<PayMember> payMembers = payMemberService
				.getPayMemberListByAccountId(user.getId());
		assertEquals(payMembers.size(), 1);

		payMembers = payMemberService
				.getPayMemberListByAccountId(account.getId());
		assertEquals(payMembers.size(), 2);
	}

	/**
	 * Gets the user pay inscription list test.
	 *
	 * @return the user pay inscription list test
	 * @throws UniqueException 
	 */
	@Test
	public void getPayMemberListTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
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

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<PayMember> payMembers = payMemberService
				.getPayMemberList();
		assertEquals(payMembers.size(), 3);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}

		payMembers = payMemberService
				.findByPayMemberIds(account.getId(),
						payInscription.getId());
		assertEquals(payMembers.size(), 2);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}
	}

	/**
	 * Find by user pay inscription ids.
	 * @throws UniqueException 
	 */
	@Test
	public void findByPayMemberIds() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
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

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<PayMember> payMembers = payMemberService
				.findByPayMemberIds(account.getId(),
						payInscription.getId());
		assertEquals(payMembers.size(), 2);
		for (PayMember payMember : payMembers) {
			assertEquals(payMember.getFeeMember(), payInscription);
		}
	}

	/**
	 * Pay user pay inscription test.
	 * @throws UniqueException 
	 */
	@Test
	public void payPayMemberTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		List<PayMember> payMembers = payMemberService
				.getPayMemberListByFeeMemberId(payInscription
						.getId());
		payMemberService.pay(payMembers.get(0));

		// Assert
		assertEquals(payMembers.get(0).isHasPay(), true);
	}

	/**
	 * Gets the pay inscription list test.
	 *
	 * @return the pay inscription list test
	 * @throws UniqueException 
	 */
	@Test
	public void getFeeMemberListTest() throws UniqueException {

		// getFeeMemberList, no FeeMembers
		List<FeeMember> payInscriptionList = payInscriptionService
				.getFeeMemberList();
		// Assert
		assertTrue(payInscriptionList.isEmpty());

		// Save
		FeeMember payInscription = new FeeMember("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);
		FeeMember payInscription2 = new FeeMember("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// getFeeMemberList
		payInscriptionList = payInscriptionService.getFeeMemberList();
		// Assert
		assertEquals(payInscriptionList.size(), 2);
	}
	
	
	  /**
    * Gets the user pay inscription list by pay inscription test.
    *
    * @return the user pay inscription list by pay inscription test
    * @throws UniqueException 
    */
   @Test
   public void getUsernamesByFeeMember() throws UniqueException {

      // Save
      FeeMember payInscription = new FeeMember("pay of 2015", 2015,
            Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
      payInscriptionService.save(payInscription);
      
      AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
      accountTypeService.save(accountType);
      MethodPayment methodPayment = new MethodPayment("cash", "cash");
      methodPaymentService.save(methodPayment);
      Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
      accountService.save(user);
      Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
      accountService.save(account);
      Account account3 = new Account("user3", "55555555F", "London", "user3", "user3@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
      accountService.save(account3);
      account3.setNickName("terminataror");
      accountService.update(account3, false);

      // Assert
      List<String> payMembers = payMemberService.getUsernamesByFeeMember(payInscription
                  .getId());
      assertEquals(payMembers.size(), 3);
   }
}
