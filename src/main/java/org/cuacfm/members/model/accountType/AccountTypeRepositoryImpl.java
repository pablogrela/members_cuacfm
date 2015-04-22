package org.cuacfm.members.model.accountType;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class AccountTypeRepositoryImpl implements AccountTypeRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save.
	 *
	 * @param accountType
	 *            the accountType
	 * @return the account type
	 */
	@Override
	@Transactional
	public AccountType save(AccountType accountType)
			throws PersistenceException {
		entityManager.persist(accountType);
		return accountType;
	}

	/**
	 * Update.
	 *
	 * @param accountType
	 *            the accountType
	 * @return the account type
	 */
	@Override
	@Transactional
	public AccountType update(AccountType accountType)
			throws PersistenceException {
		return entityManager.merge(accountType);
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		AccountType accountType = findById(id);
		if (accountType != null) {
			entityManager.remove(accountType);
		}
	};
	
	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the account type
	 */
	@Override
	public AccountType findById(Long id) {
		try {
			return entityManager
					.createQuery(
							"select a from AccountType a where a.id = :id",
							AccountType.class).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name of AccountType
	 * @return AccountType
	 */
	@Override
	public AccountType findByName(String name) {
		try {
			return entityManager
					.createQuery(
							"select a from AccountType a where a.name = :name",
							AccountType.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceException();
		}
	}
	
	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	@Override
	public List<AccountType> getAccountTypes() {
		try {
			return entityManager.createQuery("select a from AccountType a",
					AccountType.class).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
