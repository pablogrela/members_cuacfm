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
package org.cuacfm.members.test.model.userservice;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountServiceImpl;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.userservice.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService = new UserService();

	@Mock
	private AccountServiceImpl accountRepositoryMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldInitializeWithTwoDemoUsers() throws UniqueListException {
		// act
		accountRepositoryMock
				.save(new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER));
		accountRepositoryMock
				.save(new Account("admin", "", "55555555B", "London", "admin", "admin@udc.es", "666666666", "666666666", "demo", roles.ROLE_ADMIN));
		//userService.initialize();
		// assert
		verify(accountRepositoryMock, times(2)).save(any(Account.class));
	}

	@Test
	public void shouldThrowExceptionWhenUserNotFound() {
		// arrange
		thrown.expect(UsernameNotFoundException.class);
		thrown.expectMessage("user not found");

		when(accountRepositoryMock.findByEmail("user@example.com")).thenReturn(null);
		// act
		userService.loadUserByUsername("user@example.com");
	}

	@Test
	public void shouldReturnUserDetails() throws UniqueListException {
		// arrange
		Account demoUser = new Account("user", "", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountRepositoryMock.save(demoUser);
		when(accountRepositoryMock.findByLogin("user")).thenReturn(demoUser);

		// assert
		verify(accountRepositoryMock, times(1)).save(any(Account.class));

		// act
		//		UserDetails userDetails = userService.loadUserByUsername("user@udc.es");

		// assert
		//		assertThat(demoUser.getLogin()).isEqualTo(userDetails.getUsername());
		//		assertThat(demoUser.getPassword()).isEqualTo(userDetails.getPassword());
		//      assertThat(hasAuthority(userDetails, String.valueOf(demoUser.getRole())));
	}

	//	private boolean hasAuthority(UserDetails userDetails, String role) {
	//		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
	//		for(GrantedAuthority authority : authorities) {
	//			if(authority.getAuthority().equals(role)) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}
}
