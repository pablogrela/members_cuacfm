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
package org.cuacfm.members.web.configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.util.Constants;
import org.hibernate.validator.constraints.NotBlank;

/** The Class AccountTypeForm. */
public class AccountTypeForm {

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	private boolean organization;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	@Min(0)
	@Max(100)
	private int discount;

	/**
	 * Instantiates a new account type form.
	 */
	public AccountTypeForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOrganization() {
		return organization;
	}

	public void setOrganization(boolean organization) {
		this.organization = organization;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * Creates the account type.
	 *
	 * @return the account type
	 */
	public AccountType createAccountType() {
		return new AccountType(getName(), isOrganization(), getDescription(), getDiscount());
	}

	/**
	 * Update account type.
	 *
	 * @param accountType the account type
	 * @return the account type
	 */
	public AccountType updateAccountType(AccountType accountType) {
		accountType.setName(getName());
		accountType.setOrganization(isOrganization());
		accountType.setDescription(getDescription());
		accountType.setDiscount(getDiscount());
		return accountType;
	}
}
