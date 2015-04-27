package org.cuacfm.members.test.model.userPayInscription;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
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
	 * @throws UniqueException 
	 */
	@Test
	public void SaveAndUpdateUserPayInscriptionTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void getUserPayInscriptionListByPayInscriptionTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
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
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void getUserPayInscriptionListByAccountIdTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "55555555D", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
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
				Double.valueOf(20),DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),  "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void getUserPayInscriptionListTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
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
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void findByUserPayInscriptionIds() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);
		Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
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
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void payUserPayInscriptionTest() throws UniqueException {

		// Save
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", "ROLE_USER");
		accountService.save(user);

		// Update Account
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(2);
		accountService.update(user, false);

		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
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
	 * @throws UniqueException 
	 */
	@Test
	public void getPayInscriptionListTest() throws UniqueException {

		// getPayInscriptionList, no PayInscriptions
		List<PayInscription> payInscriptionList = payInscriptionService
				.getPayInscriptionList();
		// Assert
		assertEquals(payInscriptionList, null);

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016",
				2016, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// getPayInscriptionList
		payInscriptionList = payInscriptionService.getPayInscriptionList();
		// Assert
		assertEquals(payInscriptionList.size(), 2);
	}
}
