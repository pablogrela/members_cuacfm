package org.cuacfm.members.model.accountType;

import java.util.List;

/** The Interface AccountTypeRepository. */
public interface AccountTypeRepository {

	/**
	 * Save.
	 *
	 * @param accountType
	 *            the accountType
	 * @return the account type
	 */
	public AccountType save(AccountType accountType);

	/**
	 * Update.
	 *
	 * @param accountType
	 *            the accountType
	 * @return the account type
	 */
	public AccountType update(AccountType accountType);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 */
	public void delete(Long id);

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the account type
	 */
	public AccountType findById(Long id);

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name of AccountType
	 * @return AccountType
	 */
	public AccountType findByName(String name);
	
	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	public List<AccountType> getAccountTypes();

}
