package org.cuacfm.members.model.accountService;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** The Class AccountService. */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	/** The account repository. */
	@Autowired
	private AccountRepository accountRepository;

	/** The password encoder. */
	@Inject
	private PasswordEncoder passwordEncoder;

	/** Instantiates a new account service. */
	public AccountServiceImpl() {
		// Default empty constructor.
	}

	/**
	 * Save, saves an account into database.
	 *
	 * @param account
	 *            the account
	 * @return the account
	 */
	@Override
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}

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
	@Override
	public Account update(Account account, boolean newPassword) {
		if (newPassword) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		return accountRepository.update(account);
	}

	/**
	 * Find by email returns user which has this email.
	 *
	 * @param email
	 *            the email
	 * @return the account
	 */
	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the account
	 */
	@Override
	public Account findByLogin(String login) {
		return accountRepository.findByLogin(login);
	}

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the account
	 */
	@Override
	public Account findById(Long id) {
		return accountRepository.findById(id);
	}

	/**
	 * Match password check if password match with the user.
	 *
	 * @param account
	 *            the account
	 * @param rawPassword
	 *            the raw password
	 * @return true, if successful
	 */
	@Override
	public boolean matchPassword(Account account, String rawPassword) {
		return accountRepository.matchPassword(account, rawPassword);
	}

}
