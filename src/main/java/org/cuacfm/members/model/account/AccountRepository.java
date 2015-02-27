package org.cuacfm.members.model.account;

public interface AccountRepository {

	/**
	 * Save.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */
	public Account save(Account account);

	/**
	 * Update.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */

	public Account update(Account account);

	/**
	 * Match password.
	 *
	 * @param account
	 *            the account
	 * @param rawPassword
	 *            the raw password
	 * @return true, if successful
	 */
	boolean matchPassword(Account account, String rawPassword);

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id of user
	 * @return the account
	 */
	public Account findById(Long id);

	/**
	 * Find by email.
	 *
	 * @param email
	 *            the email of user
	 * @return the account
	 */
	public Account findByEmail(String email);

	/**
	 * Find by login.
	 *
	 * @param login
	 *            the login of user
	 * @return the account
	 */
	public Account findByLogin(String login);

}
