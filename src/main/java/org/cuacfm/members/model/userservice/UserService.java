/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.userservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
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

/** The Class UserService. */
public class UserService implements UserDetailsService {

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new user service.
	 */
	public UserService() {
		super();
	}

	/**
	 * Load user in the system by email.
	 *
	 * @param email the email
	 * @param account the account
	 * @return the UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String email) {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	/**
	 * Signin login an user in the system.
	 *
	 * @param account the account
	 */
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}

	/**
	 * Authenticate.
	 *
	 * @param account the account
	 * @return the authentication
	 */
	private Authentication authenticate(Account account) {
//		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));
		return new UsernamePasswordAuthenticationToken(createUser(account), null, createAuthority(account));
	}

	/**
	 * Creates the user, create user in the system.
	 *
	 * @param account the account
	 * @return the user
	 */
	private User createUser(Account account) {
		// If account.active = false throw exception errorUserDisabled in view signin
		boolean enabled = account.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

//		return new User(account.getLogin(), account.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
//				Collections.singleton(createAuthority(account)));
		
		return new User(account.getLogin(), account.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				createAuthority(account));
	}

	/**
	 * Creates the authority.
	 *
	 * @param account the account
	 * @return the granted authority
	 */
//	private GrantedAuthority createAuthority(Account account) {
//		return new SimpleGrantedAuthority(String.valueOf(account.getRole()));
//	}

	/**
	 * Creates the authority.
	 *
	 * @param account the account
	 * @return the granted authority
	 */
	private List<SimpleGrantedAuthority> createAuthority(Account account) {
	    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	    String[] authStrings = account.getRole().toString().split(", ");
	    for(String authString : authStrings) {
	        authorities.add(new SimpleGrantedAuthority(authString));
	    }
	    return authorities;
	}
	

}
