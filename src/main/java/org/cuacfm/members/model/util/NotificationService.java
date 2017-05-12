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
package org.cuacfm.members.model.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.util.Constants.typeDestinataries;
import org.cuacfm.members.model.util.Constants.typePush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class PushService. */
@Service("notificationService")
public class NotificationService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SpringEmailService emailService;

	@Autowired
	private PushService pushService;

	/**
	 * Send notification.
	 *
	 * @param destinatary the destinatary
	 * @param accounts the accounts
	 * @param title the title
	 * @param body the body
	 * @param typePush the type push
	 * @param json the json
	 * @return true, if successful
	 */
	public boolean sendNotification(typeDestinataries destinatary, List<Account> accounts, String title, String body, typePush typePush,
			String json) {

		boolean result = false;
		// LinkedHashSet remove repeated accounts
		Set<Account> accountsAux = new LinkedHashSet<>();
		if (accounts != null && !accounts.isEmpty()) {
			accountsAux.addAll(accounts);
		} else {
			accountsAux.addAll(accountService.getAccountsOrderByActive());
		}

		// Add devices token to send push
		List<String> devicesToken = new ArrayList<>();
		List<Account> sendAccounts = new ArrayList<>();
		for (Account account : accountsAux) {
			if (account.getDevicesToken() != null && !account.getDevicesToken().isEmpty()) {
				devicesToken.addAll(account.getDevicesToken());
			} else {
				sendAccounts.add(account);
			}
		}

		switch (destinatary) {
		case ALL:
			result = pushService.sendPushNotificationToDevice(devicesToken, title, body, typePush, json);
			for (Account account : sendAccounts) {
				result = emailService.sendMail(account.getEmail(), title, body);
			}
			break;
		case MOBILE:
			result = pushService.sendPushNotificationToDevice(devicesToken, title, body, typePush, json);
			break;
		case EMAIL:
			for (Account account : sendAccounts) {
				result = emailService.sendMail(account.getEmail(), title, body);
			}
			break;
		}

		return result;
	}

}
