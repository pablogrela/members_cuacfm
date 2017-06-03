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
package org.cuacfm.members.web.account;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.util.Constants.typePush;
import org.cuacfm.members.model.util.PushService;
import org.cuacfm.members.model.util.SpringEmailService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class AccountListController.
 */
//@RestController
@Controller
public class AccountListController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SpringEmailService emailService;
	
	@Autowired
	private PushService pushService;

	public static final String ACCOUNT_VIEW_NAME = "account/accountlist";
	public static final String REDIRECT_ACCOUNT = "redirect:/accountList";

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	@RequestMapping(value = "accountList")
	public String getView() {
		return ACCOUNT_VIEW_NAME;
	}

	/**
	 * List all users.
	 *
	 * @return the response entity
	 */
	@RequestMapping(value = "accountList/", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> getAccounts() {

		List<AccountDTO> accountsDTO = accountService.getAccountsDTO(accountService.getAccountsOrderByActive());

		if (accountsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
	}

	/**
	 * Unsubscribe.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "accountList/unsubscribe/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> unsubscribe(@PathVariable("id") Long id, RedirectAttributes ra) {

		Account account = accountService.findById(id);

		// Only unbuscribe roles no admin
		if (!account.getRole().equals(roles.ROLE_ADMIN)) {
			accountService.unsubscribe(account);
			Object[] arguments = { account.getName() + " " + account.getSurname() };
			String messageI18n = messageSource.getMessage("account.successUnsubscribe", arguments, Locale.getDefault());
			MessageHelper.addInfoAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Subscribe.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "accountList/subscribe/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> subscribe(@PathVariable("id") Long id, RedirectAttributes ra) {

		Account account = accountService.findById(id);
		accountService.subscribe(account);

		Object[] arguments = { account.getName() + " " + account.getSurname() };
		String messageI18n = messageSource.getMessage("account.successSubscribe", arguments, Locale.getDefault());
		MessageHelper.addInfoAttribute(ra, messageI18n);

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Push.
	 *
	 * @param id the id
	 * @param title the title
	 * @param body the body
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "accountList/push/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> push(@PathVariable("id") Long id, @RequestParam(value = "title") String title,
			@RequestParam(value = "body") String body, RedirectAttributes ra) {

		Account account = accountService.findById(id);
		Object[] arguments = { account.getName() + " " + account.getSurname() };

		if (pushService.sendPushNotificationToDevice(account.getDevicesToken(), title, body, typePush.DEFAULT, null)) {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("account.push.success", arguments, Locale.getDefault()));
		} else {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("account.push.error", arguments, Locale.getDefault()));
		}

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Email.
	 *
	 * @param id the id
	 * @param title the title
	 * @param body the body
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "accountList/email/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> email(@PathVariable("id") Long id, @RequestParam(value = "title") String title,
			@RequestParam(value = "body") String body, RedirectAttributes ra) {

		Account account = accountService.findById(id);
		Object[] arguments = { account.getName() + " " + account.getSurname() };

		if (emailService.sendMail(account.getEmail(), title, body)) {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("account.push.success", arguments, Locale.getDefault()));
		} else {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("account.push.error", arguments, Locale.getDefault()));
		}

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

}