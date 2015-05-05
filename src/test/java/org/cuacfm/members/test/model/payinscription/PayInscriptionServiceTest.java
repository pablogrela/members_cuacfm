package org.cuacfm.members.test.model.payinscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
import org.cuacfm.members.model.userpayinscriptionservice.UserPayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class rainingTypeServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayInscriptionServiceTest extends WebSecurityConfigurationAware {

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
	 * Save and find by PayInscription test.
	 * @throws UniqueException 
	 */
	@Test
	public void savePayInscriptionTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * Save and find by PayInscription test.
	 * @throws UniqueException 
	 */
	@Test
	public void savePayInscriptionWithUsersTest() throws UniqueException {
		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		
		Account user = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
		
		Account user2 = new Account("user2", "11111111C", "London", "user2", "email2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user2);
		
		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);

		assertEquals(userPayInscriptionService.getUserPayInscriptionListByPayInscriptionId(payInscription.getId()).size(),2);
	}
	/**
	 * Save and find by PayInscription test.
	 * @throws UniqueException 
	 */
	@Test(expected = UniqueException.class)
	public void savePayInscriptionExceptionTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016",
				2015, Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * Save and find by PayInscription test.
	 * @throws UniqueException 
	 */
	@Test
	public void saveUserPayInscriptionTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		AccountType accountType = new AccountType("Adult", "Fee for adults", 0);
		accountTypeService.save(accountType);
		MethodPayment methodPayment = new MethodPayment("cash", "cash");
		methodPaymentService.save(methodPayment);
		
		Account user = new Account("user", "11111111C", "London", "user", "email1@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user);
		user.setAccountType(accountType);
		user.setMethodPayment(methodPayment);
		user.setInstallments(1);
		accountService.update(user, false);
		payInscriptionService.saveUserPayInscription(user, payInscription);
		
		Account user2 = new Account("user2", "22222222C", "London", "user2", "email2@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user2);
		payInscriptionService.saveUserPayInscription(user2, payInscription);
		
		
		Account user3 = new Account("user3", "33333333C", "London", "user3", "email3@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user3);
		user.setInstallments(2);
		accountService.update(user3, false);
		payInscriptionService.saveUserPayInscription(user3, payInscription);
		
		Account user4 = new Account("user4", "44444444C", "London", "user4", "email4@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		accountService.save(user4);
		user.setInstallments(6);
		accountService.update(user4, false);
		payInscriptionService.saveUserPayInscription(user4, payInscription);
		
		// findById
		
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}
	
	/**
	 * Update Inscription
	 * @throws UniqueException 
	 */
	@Test
	public void UpdatePayInscriptionTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// Update
		payInscription.setName("pay of 1016");
		payInscription.setYear(2016);
		payInscription.setPrice(Double.valueOf(25));
		payInscription.setDescription("pay of 2016, tax for members");
		payInscriptionService.update(payInscription);

		// Assert
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
		assertEquals(payInscription.getName(), payInscriptionSearch.getName());
		assertEquals(payInscription.getYear(), payInscriptionSearch.getYear());
		assertEquals(payInscription.getPrice(), payInscriptionSearch.getPrice());
		assertEquals(payInscription.getDescription(),
				payInscriptionSearch.getDescription());
	}

	/**
	 * Update Inscription Exception
	 * @throws UniqueException 
	 */
	@Test(expected = UniqueException.class)
	public void UpdatePayInscriptionExceptionTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016", 2016,
				Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// Update
		PayInscription payInscription3 = new PayInscription("pay of 2016", 2016,
				Double.valueOf(20), DisplayDate.stringToDate2("2016-04-05"), DisplayDate.stringToDate2("2016-07-05"), "pay of 2016");
		payInscription3.setId(payInscription.getId());
		payInscriptionService.update(payInscription3);
	}

	/**
	 * findByName test.
	 * @throws UniqueException 
	 */
	@Test
	public void findByNameTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findByName
		PayInscription payInscriptionSearch = payInscriptionService
				.findByName(payInscription.getName());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * findByYear test.
	 * @throws UniqueException 
	 */
	@Test
	public void findByYearTest() throws UniqueException {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findByName
		PayInscription payInscriptionSearch = payInscriptionService
				.findByYear(payInscription.getYear());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * getPayInscriptionList test.
	 * @throws UniqueException 
	 */
	@Test
	public void getPayInscriptionListTest() throws UniqueException {

		// getPayInscriptionList, no PayInscriptions
		List<PayInscription> payInscriptionList = payInscriptionService
				.getPayInscriptionList();
		// Assert
		assertTrue(payInscriptionList.isEmpty());

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
