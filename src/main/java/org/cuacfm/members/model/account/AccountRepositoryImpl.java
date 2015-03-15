package org.cuacfm.members.model.account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	public Account save(Account account) throws PersistenceException {
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
	public Account update(Account account) throws PersistenceException {
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
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceException();
		}
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
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_LOGIN, Account.class)
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceException();
		}
	}
}
