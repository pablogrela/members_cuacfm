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
package org.cuacfm.members.model.bankaccount;

/** The Interface BankAccountRepository. */
public interface BankAccountRepository {

	/**
	 * Save.
	 *
	 * @param bankAccount the bank account
	 * @return the bank bankAccount
	 */
	public BankAccount save(BankAccount bankAccount);

	/**
	 * Active bank account by account id.
	 *
	 * @param accountId the account id
	 * @return the bank account
	 */
	public BankAccount activeBankAccountByAccountId(Long accountId);
}
