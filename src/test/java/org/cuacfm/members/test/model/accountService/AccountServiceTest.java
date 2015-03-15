package org.cuacfm.members.test.model.accountService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/** The Class AccountServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountServiceTest extends WebSecurityConfigurationAware {

	/** The account service. */
	@Inject
	private AccountService accountService;

	/**
	 * Assert equal accounts.
	 *
	 * @param account1
	 *            the account1
	 * @param account2
	 *            the account2
	 */
	private void assertEqualAccounts(Account account1, Account account2) {
		assertEquals(account1.getName(), account2.getName());
		assertEquals(account1.getEmail(), account2.getEmail());
		assertEquals(account1.getLogin(), account2.getLogin());
		assertEquals(account1.getPassword(), account2.getPassword());
		assertEquals(account1.getRole(), account2.getRole());
	}

	/**
	 * Save and find by email account test.
	 */
	@Test
	public void saveAndFindByEmailAccountTest() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");
		Account accountSaved = accountService.save(account);

		assertEqualAccounts(accountSaved, account);
		Assert.notNull(account.getEmail());

		Account account2 = accountService.findByEmail("user@example.com");
		assertEqualAccounts(account, account2);
	}

	/**
	 * Find by id.
	 */
	@Test
	public void findById() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");
		Account accountSaved = accountService.save(account);
		Account accountSearch = accountService.findById(accountSaved.getId());

		assertEquals(accountSearch, account);
	}

	/**
	 * Find by id without user.
	 */
	@Test
	public void findByIDWithoutUser() {
		Account account = accountService.findById(Long.valueOf(1));
		assertEquals(null, account);
	}

	/**
	 * insert Exception the same login.
	 */
	@Test(expected = PersistenceException.class)
	public void insertUserTheSameLoginException() {
		Account account = new Account("user", "user.new", "user1@example.com",
				"demo", "ROLE_USER");
		accountService.save(account);

		Account account2 = new Account("user", "user.new", "user2@example.com",
				"demo", "ROLE_USER");
		accountService.save(account2);
	}
	
	/**
	 * insert Exception the same email.
	 */
	@Test(expected = PersistenceException.class)
	public void insertUserTheSameEmailException() {
		Account account = new Account("user", "user.new1", "user@example.com",
				"demo", "ROLE_USER");
		accountService.save(account);

		Account account2 = new Account("user", "user.new2", "user@example.com",
				"demo", "ROLE_USER");
		accountService.save(account2);
	}

	/**
	 * update Exception the same login.
	 */
	@Test(expected = PersistenceException.class)
	public void updateUserTheSameLoginException() {
		Account account = new Account("user1", "user1", "user1@example.com",
				"demo", "ROLE_USER");
		Account accountSaved = accountService.save(account);

		Account account2 = new Account("user2", "user2", "user2@example.com",
				"demo", "ROLE_USER");
		accountService.save(account2);

		accountSaved.setLogin("user2");
		accountService.update(accountSaved, false);
	}
	

	/**
	 * update Exception the same email.
	 */
	@Test(expected = PersistenceException.class)
	public void updateUserTheSameEmailException() {
		Account account = new Account("user1", "user.new1",
				"user1@example.com", "demo", "ROLE_USER");
		Account accountSaved = accountService.save(account);

		Account account2 = new Account("user1", "user.new2",
				"user2@example.com", "demo", "ROLE_USER");
		accountService.save(account2);

		accountSaved.setEmail("user2@example.com");
		accountService.update(accountSaved, false);
	}


	/**
	 * Save and find by login account test.
	 */
	@Test
	public void saveAndFindByLoginAccountTest() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");
		accountService.save(account);
		Account account2 = accountService.findByLogin("user.new");
		assertEqualAccounts(account, account2);
	}

	/**
	 * Find by login without user.
	 */
	@Test
	public void findByLoginWithoutUser() {
		Account account = accountService.findByLogin("user1");
		assertEquals(null, account);
	}

	/**
	 * Find by email without user.
	 */
	@Test
	public void findByEmailWithoutUser() {
		Account account = accountService.findByEmail("user@user.es");
		assertEquals(null, account);
	}

	/**
	 * Update user data without password.
	 */
	@Test
	public void updateUserDataWithoutPassword() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");
		Account savedAccount = accountService.save(account);
		assertEqualAccounts(account, savedAccount);
		/* changing properties */
		savedAccount.setEmail("user2@user2.es");
		savedAccount.setRole("ROLE_ADMIN");
		savedAccount.setLogin("user2");
		/* updating */
		Account updatedAccount = accountService.update(savedAccount, false);
		assertEqualAccounts(updatedAccount, savedAccount);
		Account searchedAccount = accountService.findByEmail("user2@user2.es");
		assertEqualAccounts(searchedAccount, updatedAccount);
		/* check that the previous properties are not in the db */
		Account notExistentAccount = accountService.findByEmail("user@user.es");
		assertEquals(null, notExistentAccount);
		Account notExistentAccount2 = accountService.findByLogin("user1");
		assertEquals(null, notExistentAccount2);

	}

	/**
	 * Update user data with password.
	 */
	@Test
	public void updateUserDataWithPassword() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");
		Account savedAccount = accountService.save(account);
		assertEqualAccounts(account, savedAccount);

		/* changing properties */
		savedAccount.setEmail("user2@user2.es");
		savedAccount.setRole("ROLE_ADMIN");
		savedAccount.setLogin("user2");
		savedAccount.setPassword("rawPassword2");

		/* updating */
		Account updatedAccount = accountService.update(savedAccount, true);

		assertEquals(updatedAccount.getEmail(), savedAccount.getEmail());
		assertEquals(updatedAccount.getLogin(), savedAccount.getLogin());
		assertEquals(updatedAccount.getRole(), savedAccount.getRole());

		/* checking that the password has changed */
		assertTrue(accountService.matchPassword(updatedAccount, "rawPassword2"));
		Account searchedAccount = accountService.findByEmail("user2@user2.es");
		assertEqualAccounts(searchedAccount, updatedAccount);

		/* check that the previous properties are not in the db */
		Account notExistentAccount = accountService.findByEmail("user@user.es");
		assertEquals(null, notExistentAccount);
		Account notExistentAccount2 = accountService.findByLogin("user1");
		assertEquals(null, notExistentAccount2);

	}

	/**
	 * Match password test.
	 */
	@Test
	public void matchPasswordTest() {
		Account account = new Account("user", "user.new", "user@example.com",
				"demo", "ROLE_USER");

		Account savedAccount = accountService.save(account);

		assertTrue(accountService.matchPassword(savedAccount, "demo"));
		assertFalse(accountService.matchPassword(savedAccount,
				"otherRawPassword"));
	}

}
