package org.cuacfm.members.model.accountService;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountRepository;
import org.cuacfm.members.model.accountTypeService.AccountTypeService;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPayment.MethodPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** The Class AccountService. */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	/** The account repository. */
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private MethodPaymentRepository methodPaymentService;

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
	 * @throws UniqueException 
	 */
	@Override
	public Account save(Account account) throws UniqueException {

		// It is verified that there is not exist dni
		if (accountRepository.findByDni(account.getDni()) != null) {
			throw new UniqueException("Dni", account.getDni());
		}
		
		// It is verified that there is not exist login
		if (accountRepository.findByLogin(account.getLogin()) != null) {
			throw new UniqueException("Login", account.getLogin());
		}

		// It is verified that there is not exist email
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			throw new UniqueException("Email", account.getEmail());
		}

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
	 * @throws UniqueException 
	 */
	@Override
	public Account update(Account account, boolean newPassword)
			throws UniqueException {
		
		// It is verified that there is not exist dni
		Account accountSearch = accountRepository.findByDni(account
				.getDni());
		if (accountSearch != null) {
			if (accountSearch.getId() != account.getId()) {
				throw new UniqueException("Dni", account.getDni());
			}
		}
		
		// It is verified that there is not exist login
		accountSearch = accountRepository.findByLogin(account
				.getLogin());
		if (accountSearch != null) {
			if (accountSearch.getId() != account.getId()) {
				throw new UniqueException("Login", account.getLogin());
			}
		}

		// It is verified that there is not exist email
		accountSearch = accountRepository.findByEmail(account.getEmail());
		if (accountSearch != null) {
			if (accountSearch.getId() != account.getId()) {
				throw new UniqueException("Email", account.getEmail());
			}
		}

		if (newPassword) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		return accountRepository.update(account);
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @throws ExistInscriptionsException
	 *             the exist inscriptions exception
	 */
	@Override
	public void delete(Long id) {
		accountRepository.delete(id);
	}

	/**
	 * Subscribe Account.
	 *
	 * @param id
	 *            the id
	 */
	public void Subscribe(Long id) {
		
		Account account = accountRepository.findById(id);
		account.setActive(true);
		accountRepository.update(account);		
	}

	/**
	 * Unsubscribe Account.
	 *
	 * @param id
	 *            the id
	 */
	public void Unsubscribe(Long id) {
		
		Account account = accountRepository.findById(id);
		account.setActive(false);
		accountRepository.update(account);		
	}
	
	/**
	 * Find by dni.
	 *
	 * @param dni
	 *            the dni
	 * @return the account
	 */
	@Override
	public Account findByDni(String dni){
		return accountRepository.findByDni(dni);
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

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<Account> getUsers() {
		return accountRepository.getUsers();
	}

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accountRepository.getAccounts();
	}
	
	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	@Override
	public List<String> getRoles() {
		return accountRepository.getRoles();
	}
}
