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
package org.cuacfm.members.web.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.util.PushService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class PushController.
 */
@Controller
public class PushController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	public static final String PUSH_VIEW_NAME = "push/pushform";
	public static final String REDIRECT_PUSH = "redirect:/push";

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	@RequestMapping(value = "push")
	public String getView() {
		return PUSH_VIEW_NAME;
	}

	/**
	 * List all users.
	 *
	 * @return the response entity
	 */
	@RequestMapping(value = "push/accountList/", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> getAccounts() {

		accountService.getAccountsWithDeviceToken();

		List<AccountDTO> accountsDTO = accountService.getAccountsDTO(accountService.getAccountsOrderByActive());

		if (accountsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
	}

	/**
	 * Push.
	 *
	 * @param title the title
	 * @param body the body
	 * @param accounts the accounts
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "push/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> push(@RequestParam(value = "title") String title, @RequestParam(value = "body") String body,
			@RequestBody List<AccountDTO> accounts, RedirectAttributes ra) {

		// Add devices token to send push
		List<String> devicesToken = new ArrayList<>();

		if (accounts != null && !accounts.isEmpty()) {
			for (AccountDTO account : accounts) {
				devicesToken.addAll(accountService.findById(account.getId()).getDevicesToken());
			}
		} else {
			for (Account account : accountService.getAccountsWithDeviceToken()) {
				devicesToken.addAll(account.getDevicesToken());
			}
		}

		if (PushService.sendPushNotificationToDevice(devicesToken, title, body)) {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("push.send.success", null, Locale.getDefault()));
		} else {
			MessageHelper.addInfoAttribute(ra, messageSource.getMessage("push.send.error", null, Locale.getDefault()));
		}

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

}