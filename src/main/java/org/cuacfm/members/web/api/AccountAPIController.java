/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.api;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.userservice.UserService;
import org.cuacfm.members.web.home.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** The Class AccountController. */
@Controller
public class AccountAPIController {

	private static final Logger logger = LoggerFactory.getLogger(AccountAPIController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	/**
	 * Instantiates a new Profile controller.
	 */
	public AccountAPIController() {
		super();
	}

	/**
	 * Gets the account API.
	 *
	 * @param token the token
	 * @param deviceToken the device token
	 * @return the account API
	 * @throws UniqueListException the unique list exception
	 */
	@RequestMapping(value = "api/accountList/account")
	public ResponseEntity<AccountDTO> getAccountAPI(@RequestParam(value = "token") String token,
			@RequestParam(value = "deviceToken", required = false) String deviceToken) throws UniqueListException {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null && !email.isEmpty()) {
			try {
				Account account = accountService.findByEmail(email);
				if (deviceToken != null && !deviceToken.isEmpty()) {
					account.addDeviceToken(deviceToken);
					accountService.update(account, false, false);
					return new ResponseEntity<>(accountService.getAccountDTO(account), HttpStatus.OK);
				}
			} catch (Exception e) {
				logger.error("error", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Signin API.
	 *
	 * @param token the token
	 * @return the string
	 */
	@RequestMapping(value = "api/", method = RequestMethod.GET)
	public String signinAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null && !email.isEmpty()) {
			userService.signin(accountService.findByEmail(email));
		}
		return HomeController.REDIRECT_HOME;
	}
}
