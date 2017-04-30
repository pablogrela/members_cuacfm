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
package org.cuacfm.members.web.bankremittance;

import org.hibernate.validator.constraints.NotBlank;

/** The Class BankRemittanceForm. */
public class BankRemittanceForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	/** The dateCharge. */
	@NotBlank(message = BankRemittanceForm.NOT_BLANK_MESSAGE)
	private String dateCharge;

	/** The dateCharge. */
	@NotBlank(message = BankRemittanceForm.NOT_BLANK_MESSAGE)
	private String monthCharge;

	/**
	 * Instantiates a new bank remittance form.
	 */
	public BankRemittanceForm() {
		super();
	}

	public String getDateCharge() {
		return dateCharge;
	}

	public void setDateCharge(String dateCharge) {
		this.dateCharge = dateCharge;
	}

	public String getMonthCharge() {
		return monthCharge;
	}

	public void setMonthCharge(String monthCharge) {
		this.monthCharge = monthCharge;
	}

}
