package org.cuacfm.members.model.account;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AccountRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class AccountRepositoryImpl implements AccountRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/** The password encoder. */
	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Save.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */
	@Override
	@Transactional
	public Account save(Account account) {

		// It is verified that there is not exist login
		if (findByLogin(account.getLogin()) != null) {
			throw new PersistenceException("Already exist login: "
					+ account.getLogin());
		}

		// It is verified that there is not exist email
		if (findByEmail(account.getEmail()) != null) {
			throw new PersistenceException("Already exist email: "
					+ account.getEmail());
		}

		entityManager.persist(account);
		return account;
	}

	/**
	 * Update.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */	
	@Override
	@Transactional
	public Account update(Account account) {
		
		// It is verified that there is not exist login
		Account accountSearch = findByLogin(account.getLogin());
		if (accountSearch != null) {
			if (accountSearch.getId() != account.getId()) {
				throw new PersistenceException("Already exist login: "
						+ account.getLogin());
			}
		}
		
		// It is verified that there is not exist email
		accountSearch = findByEmail(account.getEmail());
		if (accountSearch != null) {
			if (accountSearch.getId() != account.getId()) {
				throw new PersistenceException("Already exist email: "
						+ account.getEmail());
			}
		}

		return entityManager.merge(account);
	}

	/**
	 * Match password.
	 *
	 * @param account
	 *            the account
	 * @param rawPassword
	 *            the raw password
	 * @return true, if successful
	 */
	@Override
	public boolean matchPassword(Account account, String rawPassword) {
		return passwordEncoder.matches(rawPassword, account.getPassword());
	}

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id of user
	 * @return the account
	 */
	@Override
	public Account findById(Long id) {
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_ID, Account.class)
					.setParameter("id", id).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find by email.
	 *
	 * @param email
	 *            the email of user
	 * @return the account
	 */
	@Override
	public Account findByEmail(String email) {
		List<Account> accounts = entityManager
				.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
				.setParameter("email", email).getResultList();
		if (accounts.isEmpty()) {
			return null;
		}
		return accounts.get(0);
	}

	/**
	 * Find by login.
	 *
	 * @param login
	 *            the login of user
	 * @return the account
	 */
	@Override
	public Account findByLogin(String login) {
		List<Account> accounts = entityManager
				.createNamedQuery(Account.FIND_BY_LOGIN, Account.class)
				.setParameter("login", login).getResultList();
		if (accounts.isEmpty()) {
			return null;
		}
		return accounts.get(0);
	}
}
