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
package org.cuacfm.members.web.account;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.web.support.DisplayDate;
import org.hibernate.validator.constraints.NotBlank;

/** The Class BankAccountForm. */
public class BankAccountForm {

	/** The bank. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String bank;

	/** The bic. */
	@Size(max = 11, message = Constants.MAX_CHARACTERS)
	private String bic;

	/** The iban. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 34, message = Constants.MAX_CHARACTERS)
	private String iban;

	/** The date mandate. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateMandate;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getDateMandate() {
		return dateMandate;
	}

	public void setDateMandate(String dateMandate) {
		this.dateMandate = dateMandate;
	}

	/**
	 * Creates the bank account.
	 *
	 * @param account the account
	 * @return the bank account
	 */
	public BankAccount createBankAccount(Account account) {
		return new BankAccount(account, getBank(), getBic(), getIban(), DisplayDate.format(dateMandate, "yyyy-MM-dd"));
	}

}
