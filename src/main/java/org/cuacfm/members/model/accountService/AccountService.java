package org.cuacfm.members.model.accountService;

import org.cuacfm.members.model.account.Account;

/** The Class AccountService. */

public interface AccountService {

	/**
	 * Save, saves an account into database.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */
	public Account save(Account account);

	/**
	 * Update, updates an user registered into bd depending if he wants to
	 * update his password or not.
	 *
	 * @param account
	 *            the account
	 * @param passwordUpdate
	 *            the passwordUpdate
	 * @return the account
	 */
	public Account update(Account account, boolean newPassword);

	/**
	 * Find by email returns user which has this email.
	 *
	 * @param email
	 *            the email
	 * @return the account
	 */
	public Account findByEmail(String email);

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the account
	 */
	public Account findByLogin(String login);

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the account
	 */
	public Account findById(Long id);

	/**
	 * Match password check if password match with the user.
	 *
	 * @param account
	 *            the account
	 * @param rawPassword
	 *            the raw password
	 * @return true, if successful
	 */
	public boolean matchPassword(Account account, String rawPassword);

}
