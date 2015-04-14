package org.cuacfm.members.test.model.payInscription;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.accountType.AccountType;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPaymentService.MethodPaymentService;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
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
	 */
	@Test
	public void savePayInscriptionTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * Save and find by PayInscription test.
	 */
	@Test(expected = PersistenceException.class)
	public void savePayInscriptionExceptionTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016",
				2015, Double.valueOf(20), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * Save and find by PayInscription test.
	 */
	@Test
	public void saveUserPayInscriptionTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

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
		user.setInstallments(1);
		accountService.update(user, false);
		
		payInscriptionService.saveUserPayInscription(user, payInscription);
		
		// findById
		PayInscription payInscriptionSearch = payInscriptionService
				.findById(payInscription.getId());
		assertEquals(payInscription, payInscriptionSearch);
	}
	
	/**
	 * Update Inscription
	 */
	@Test
	public void UpdatePayInscriptionTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
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
	 */
	@Test(expected = PersistenceException.class)
	public void UpdatePayInscriptionExceptionTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);
		PayInscription payInscription2 = new PayInscription("pay of 2016", 2016,
				Double.valueOf(20), "pay of 2016");
		payInscriptionService.save(payInscription2);

		// Update
		payInscription.setYear(2016);
		payInscriptionService.update(payInscription);
	}

	/**
	 * findByName test.
	 */
	@Test
	public void findByNameTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findByName
		PayInscription payInscriptionSearch = payInscriptionService
				.findByName(payInscription.getName());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * findByYear test.
	 */
	@Test
	public void findByYearTest() {

		// Save
		PayInscription payInscription = new PayInscription("pay of 2015", 2015,
				Double.valueOf(20), "pay of 2015");
		payInscriptionService.save(payInscription);

		// findByName
		PayInscription payInscriptionSearch = payInscriptionService
				.findByYear(payInscription.getYear());
		assertEquals(payInscription, payInscriptionSearch);
	}

	/**
	 * getPayInscriptionList test.
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
