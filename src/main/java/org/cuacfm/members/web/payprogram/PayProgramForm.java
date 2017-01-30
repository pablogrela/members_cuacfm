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
package org.cuacfm.members.web.payprogram;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.web.support.DisplayDate;

/** The Class PayProgramForm. */
public class PayProgramForm {

	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The price. */
	@Digits(fraction = 2, integer = 2)
	@DecimalMin("0.00")
	private Double price;

	/** The state. */
	private String state;

	/** The state list. */
	private List<states> stateList;

	/** The method. */
	private String method;

	/** The method list. */
	private List<methods> methodList;

	/** The account Payer. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String accountPayer;

	/** The id payer. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String idPayer;

	/** The id txn. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String idTxn;

	/** The email payer. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String emailPayer;

	/** The status pay. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String statusPay;

	/** The date pay. */
	@Size(max = 50, message = PayProgramForm.MAX_CHARACTERS)
	private String datePay;

	/** Instantiates a new user pay inscription form. */
	public PayProgramForm() {
		// Default empty constructor.
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Constants.states> getStateList() {
		return stateList;
	}

	public void setStateList(List<Constants.states> stateList) {
		this.stateList = stateList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Constants.methods> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<Constants.methods> methodList) {
		this.methodList = methodList;
	}

	public String getAccountPayer() {
		return accountPayer;
	}

	public void setAccountPayer(String accountPayer) {
		this.accountPayer = accountPayer;
	}

	public String getIdPayer() {
		return idPayer;
	}

	public void setIdPayer(String idPayer) {
		this.idPayer = idPayer;
	}

	public String getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(String idTxn) {
		this.idTxn = idTxn;
	}

	public String getEmailPayer() {
		return emailPayer;
	}

	public void setEmailPayer(String emailPayer) {
		this.emailPayer = emailPayer;
	}

	public String getStatusPay() {
		return statusPay;
	}

	public void setStatusPay(String statusPay) {
		this.statusPay = statusPay;
	}

	public String getDatePay() {
		return datePay;
	}

	public void setDatePay(String datePay) {
		this.datePay = datePay;
	}

	/**
	 * Update pay program.
	 *
	 * @param payProgram the pay program
	 * @return the pay program
	 */
	public PayProgram updatePayProgram(PayProgram payProgram) {

		payProgram.setPrice(getPrice());
		payProgram.setState(states.valueOf(getState()));
		payProgram.setMethod(methods.valueOf(getMethod()));
		if (getAccountPayer() != "") {
			payProgram.setAccountPayer(getAccountPayer());
		}
		if (getIdTxn() != "") {
			payProgram.setIdTxn(getIdTxn());
		}
		if (getIdPayer() != "") {
			payProgram.setIdPayer(getIdPayer());
		}
		if (getEmailPayer() != "") {
			payProgram.setEmailPayer(getEmailPayer());
		}
		if (getIdPayer() != "") {
			payProgram.setIdPayer(getIdPayer());
		}
		if (getDatePay() != "") {
			payProgram.setDatePay(DisplayDate.stringToDateTime(getDatePay()));
		}
		return payProgram;
	}

}
