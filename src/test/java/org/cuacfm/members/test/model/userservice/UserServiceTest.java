package org.cuacfm.members.test.model.userservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountServiceImpl;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.userservice.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
	public void shouldInitializeWithTwoDemoUsers() throws UniqueException {
		// act
		accountRepositoryMock.save(new Account("user", "55555555C", "London", "user", "user@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER));
		accountRepositoryMock.save(new Account("admin", "55555555B", "London", "admin", "admin@udc.es", 666666666, 666666666,"demo", roles.ROLE_ADMIN));
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
	public void shouldReturnUserDetails() {
		// arrange
		Account demoUser = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666, 666666666,"demo", roles.ROLE_USER);
		when(accountRepositoryMock.findByLogin("usuario.nuevo")).thenReturn(demoUser);

		// act
		UserDetails userDetails = userService.loadUserByUsername("usuario.nuevo");

		// assert
		assertThat(demoUser.getLogin()).isEqualTo(userDetails.getUsername());
		assertThat(demoUser.getPassword()).isEqualTo(userDetails.getPassword());
        assertThat(hasAuthority(userDetails, String.valueOf(demoUser.getRole())));
	}

	private boolean hasAuthority(UserDetails userDetails, String role) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
