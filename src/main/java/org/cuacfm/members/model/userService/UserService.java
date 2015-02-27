package org.cuacfm.members.model.userService;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** The Class UserService.*/
public class UserService implements UserDetailsService {

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new user service.
	 */
	public UserService() {
		// Default empty constructor.
	}

	@PostConstruct
	protected void initialize() {
		// accountRepository.save(new Account("user", "user", "user@udc.es",
		// "demo", "ROLE_USER"));
		// accountRepository.save(new Account("admin", "admin", "admin@udc.es",
		// "demo", "ROLE_ADMIN"));
	}

	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		Account account = accountService.findByLogin(login);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	/**
	 * Signin login an user in the system.
	 *
	 * @param account
	 *            the account
	 */
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(
				authenticate(account));
	}

	/**
	 * Authenticate.
	 *
	 * @param account
	 *            the account
	 * @return the authentication
	 */
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account),
				null, Collections.singleton(createAuthority(account)));
	}

	/**
	 * Creates the user, create user in the system.
	 *
	 * @param account
	 *            the account
	 * @return the user
	 */
	private User createUser(Account account) {
		return new User(account.getLogin(), account.getPassword(),
				Collections.singleton(createAuthority(account)));
	}

	/**
	 * Creates the authority.
	 *
	 * @param account
	 *            the account
	 * @return the granted authority
	 */
	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
