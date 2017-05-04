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
package org.cuacfm.members.web.paymember;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Class PayMemberForm. */
public class PayMemberForm {

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
	/** The installment. */
	@Min(1)
	@Max(12)
	private int installment;

	/** The installments. */
	@Min(1)
	@Max(12)
	private int installments;

	/** The id payer. */
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String idPayer;

	/** The id txn. */
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String idTxn;

	/** The email payer. */
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String emailPayer;

	/** The status pay. */
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String statusPay;

	/** The date pay. */
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String datePay;

	/** Instantiates a new user fee member form. */
	public PayMemberForm() {
		super();
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

	public int getInstallment() {
		return installment;
	}

	public void setInstallment(int installment) {
		this.installment = installment;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
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
	 * Update user fee member.
	 *
	 * @param payMember the pay member
	 * @return the user fee member
	 */
	public PayMember updatePayMember(PayMember payMember) {

		payMember.setPrice(getPrice());
		payMember.setState(states.valueOf(getState()));
		payMember.setMethod(methods.valueOf(getMethod()));
		payMember.setInstallment(getInstallment());
		payMember.setInstallments(getInstallments());
		if (getIdTxn() != "") {
			payMember.setIdTxn(getIdTxn());
		}
		if (getIdPayer() != "") {
			payMember.setIdPayer(getIdPayer());
		}
		if (getEmailPayer() != "") {
			payMember.setEmailPayer(getEmailPayer());
		}
		if (getIdPayer() != "") {
			payMember.setIdPayer(getIdPayer());
		}
		if (getDatePay() != "") {
			payMember.setDatePay(DateUtils.format(getDatePay(), DateUtils.FORMAT_DISPLAY));
		}
		return payMember;
	}

}
