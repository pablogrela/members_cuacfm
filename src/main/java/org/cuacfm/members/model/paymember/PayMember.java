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
package org.cuacfm.members.model.paymember;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Class PayMember. */
@Entity
public class PayMember implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId")
	private Account account;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "feeMemberId")
	private FeeMember feeMember;

	private Double price;

	@Enumerated(EnumType.STRING)
	private states state;

	@Enumerated(EnumType.STRING)
	private methods method;

	private int installment;
	private int installments;
	private String idPayer;

	//@Column(unique = true)
	// May have been paid with the same directDebit transaction
	private String idTxn;
	private String emailPayer;
	private Date datePay;
	private Date dateCharge;

	/**
	 * Instantiates a pay fee member.
	 */
	public PayMember() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new user fee member.
	 *
	 * @param account the account
	 * @param feeMember the fee member
	 * @param price the price
	 * @param installment the installment
	 * @param installments the installments
	 * @param dateCharge the date charge
	 */
	public PayMember(Account account, FeeMember feeMember, Double price, int installment, int installments, Date dateCharge) {
		super();
		this.account = account;
		this.feeMember = feeMember;
		this.price = price;
		this.installment = installment;
		this.installments = installments;
		this.dateCharge = dateCharge;
		this.state = states.NO_PAY;
	}

	public Account getAccount() {
		return account;
	}

	public FeeMember getFeeMember() {
		return feeMember;
	}

	public Long getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public states getState() {
		return state;
	}

	public void setState(states state) {
		this.state = state;
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

	public methods getMethod() {
		return method;
	}

	public void setMethod(methods method) {
		this.method = method;
	}

	public Date getDatePay() {
		return datePay;
	}

	public void setDatePay(Date datePay) {
		this.datePay = datePay;
	}

	public Date getDateCharge() {
		return dateCharge;
	}

	public void setDateCharge(Date dateCharge) {
		this.dateCharge = dateCharge;
	}

	@Override
	public String toString() {
		return "PayMember [id=" + id + ", account=" + account + ", feeMember=" + feeMember + ", price=" + price + ", state=" + state + ", method="
				+ method + ", installment=" + installment + ", installments=" + installments + ", idPayer=" + idPayer + ", idTxn=" + idTxn
				+ ", emailPayer=" + emailPayer + ", datePay=" + datePay + ", dateCharge=" + dateCharge + "]";
	}

}
