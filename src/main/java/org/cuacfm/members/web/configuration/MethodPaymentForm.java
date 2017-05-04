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
package org.cuacfm.members.web.configuration;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.util.Constants;
import org.hibernate.validator.constraints.NotBlank;

/** The Class MethodPaymentForm. */
public class MethodPaymentForm {

	/** The name. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	/** The direct debit. */
	private boolean directDebit;

	/** The description. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	/** Instantiates a new method payment form. */
	public MethodPaymentForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDirectDebit() {
		return directDebit;
	}

	public void setDirectDebit(boolean directDebit) {
		this.directDebit = directDebit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Creates the account type.
	 *
	 * @return the account type
	 */
	public MethodPayment createMethodPayment() {
		return new MethodPayment(getName(), isDirectDebit(), getDescription());
	}

	/**
	 * Update account type.
	 *
	 * @param methodPayment the account type
	 * @return the account type
	 */
	public MethodPayment updateMethodPayment(MethodPayment methodPayment) {
		methodPayment.setName(getName());
		methodPayment.setDirectDebit(isDirectDebit());
		methodPayment.setDescription(getDescription());
		return methodPayment;
	}
}
