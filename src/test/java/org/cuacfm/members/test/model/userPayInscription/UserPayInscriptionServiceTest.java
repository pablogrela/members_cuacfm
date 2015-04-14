package org.cuacfm.members.test.model.userPayInscription;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/** The Class UserPayInscriptionServiceTest.*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserPayInscriptionServiceTest extends
		WebSecurityConfigurationAware {

	/** The training service. */
	@Inject
	private PayInscriptionService payInscriptionService;

	/** The training service. */
	@Inject
	private UserPayInscriptionService userPayInscriptionService;

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
	 */
	@Test
	public void SaveAndUpdateUserPayInscriptionTest() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
		UserPayInscription userPayInscription = new UserPayInscription(user,
				payInscription, payInscription.getPrice(), 1,
				user.getInstallments());
		userPayInscriptionService.save(userPayInscription);

		// Update
		userPayInscription.setDatePay(new Date());
		userPayInscription.setHasPay(true);
		userPayInscription.setInstallment(3);
		userPayInscription.setInstallments(3);
		userPayInscription.setPrice(Double.valueOf(30));
		userPayInscriptionService.update(userPayInscription);

		// Assert
		UserPayInscription userPayInscriptionSearch = userPayInscriptionService
				.findById(userPayInscription.getId());
		assertEquals(userPayInscription, userPayInscriptionSearch);
		assertEquals(userPayInscription.getAccount(),
				userPayInscriptionSearch.getAccount());
		assertEquals(userPayInscription.getDatePay(),
				userPayInscriptionSearch.getDatePay());
		assertEquals(userPayInscription.isHasPay(),
				userPayInscriptionSearch.isHasPay());
		assertEquals(userPayInscription.getInstallment(),
				userPayInscriptionSearch.getInstallment());
		assertEquals(userPayInscription.getInstallments(),
				userPayInscriptionSearch.getInstallments());
		assertEquals(userPayInscription.getPrice(),
				userPayInscriptionSearch.getPrice());
	}

	/**
	 * Gets the user pay inscription list by pay inscription test.
	 *
	 * @return the user pay inscription list by pay inscription test
	 */
	@Test
	public void getUserPayInscriptionListByPayInscriptionTest() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "user2", "user2@email.com",
				"demo", "ROLE_USER");
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

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByPayInscriptionId(payInscription
						.getId());
		assertEquals(userPayInscriptions.size(), 3);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}

		userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByAccountId(user.getId());
		assertEquals(userPayInscriptions.size(), 1);

		userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByAccountId(account.getId());
		assertEquals(userPayInscriptions.size(), 2);

		userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionList();
		assertEquals(userPayInscriptions.size(), 3);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}

		userPayInscriptions = userPayInscriptionService
				.findByUserPayInscriptionIds(account.getId(),
						payInscription.getId());
		assertEquals(userPayInscriptions.size(), 2);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}
	}

	/**
	 * Gets the user pay inscription list by account id test.
	 *
	 * @return the user pay inscription list by account id test
	 */
	@Test
	public void getUserPayInscriptionListByAccountIdTest() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "user2", "user2@email.com",
				"demo", "ROLE_USER");
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

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByAccountId(user.getId());
		assertEquals(userPayInscriptions.size(), 1);

		userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByAccountId(account.getId());
		assertEquals(userPayInscriptions.size(), 2);
	}

	/**
	 * Gets the user pay inscription list test.
	 *
	 * @return the user pay inscription list test
	 */
	@Test
	public void getUserPayInscriptionListTest() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "user2", "user2@email.com",
				"demo", "ROLE_USER");
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

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionList();
		assertEquals(userPayInscriptions.size(), 3);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}

		userPayInscriptions = userPayInscriptionService
				.findByUserPayInscriptionIds(account.getId(),
						payInscription.getId());
		assertEquals(userPayInscriptions.size(), 2);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}
	}

	/**
	 * Find by user pay inscription ids.
	 */
	@Test
	public void findByUserPayInscriptionIds() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "user2", "user2@email.com",
				"demo", "ROLE_USER");
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

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Assert
		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.findByUserPayInscriptionIds(account.getId(),
						payInscription.getId());
		assertEquals(userPayInscriptions.size(), 2);
		for (UserPayInscription userPayInscription : userPayInscriptions) {
			assertEquals(userPayInscription.getPayInscription(), payInscription);
		}
	}

	/**
	 * Pay user pay inscription test.
	 */
	@Test
	public void payUserPayInscriptionTest() {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "user", "user@email.com", "demo",
				"ROLE_USER");
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		List<UserPayInscription> userPayInscriptions = userPayInscriptionService
				.getUserPayInscriptionListByPayInscriptionId(payInscription
						.getId());
		userPayInscriptionService.pay(userPayInscriptions.get(0));

		// Assert
		assertEquals(userPayInscriptions.get(0).isHasPay(), true);
	}

	/**
	 * Gets the pay inscription list test.
	 *
	 * @return the pay inscription list test
	 */
	@Test
	public void getPayInscriptionListTest() {

		// getPayInscriptionList, no PayInscriptions
		List<PayInscription> payInscriptionList = payInscriptionService
				.getPayInscriptionList();
		// Assert
		assertEquals(payInscriptionList, null);

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016",
				2016, Double.valueOf(20), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// getPayInscriptionList
		payInscriptionList = payInscriptionService.getPayInscriptionList();
		// Assert
		assertEquals(payInscriptionList.size(), 2);
	}
}
