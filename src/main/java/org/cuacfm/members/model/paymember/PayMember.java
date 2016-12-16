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

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
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
		this.method = methods.NO_PAY;
	}

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Gets the fee member.
	 *
	 * @return the fee member
	 */
	public FeeMember getFeeMember() {
		return feeMember;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public states getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(states state) {
		this.state = state;
	}

	/**
	 * Gets the installment.
	 *
	 * @return the installment
	 */
	public int getInstallment() {
		return installment;
	}

	/**
	 * Sets the installment.
	 *
	 * @param installment the new installment
	 */
	public void setInstallment(int installment) {
		this.installment = installment;
	}

	/**
	 * Gets the installments.
	 *
	 * @return the installments
	 */
	public int getInstallments() {
		return installments;
	}

	/**
	 * Sets the installments.
	 *
	 * @param installments the new installments
	 */
	public void setInstallments(int installments) {
		this.installments = installments;
	}

	/**
	 * Gets the id payer.
	 *
	 * @return the id payer
	 */
	public String getIdPayer() {
		return idPayer;
	}

	/**
	 * Sets the id payer.
	 *
	 * @param idPayer the new id payer
	 */
	public void setIdPayer(String idPayer) {
		this.idPayer = idPayer;
	}

	/**
	 * Gets the id txn.
	 *
	 * @return the id txn
	 */
	public String getIdTxn() {
		return idTxn;
	}

	/**
	 * Sets the id txn.
	 *
	 * @param idTxn the new id txn
	 */
	public void setIdTxn(String idTxn) {
		this.idTxn = idTxn;
	}

	/**
	 * Gets the email payer.
	 *
	 * @return the email payer
	 */
	public String getEmailPayer() {
		return emailPayer;
	}

	/**
	 * Sets the email payer.
	 *
	 * @param emailPayer the new email payer
	 */
	public void setEmailPayer(String emailPayer) {
		this.emailPayer = emailPayer;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public methods getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod(methods method) {
		this.method = method;
	}

	/**
	 * Gets the date pay.
	 *
	 * @return the date pay
	 */
	public Date getDatePay() {
		return datePay;
	}

	/**
	 * Sets the date pay.
	 *
	 * @param datePay the new date pay
	 */
	public void setDatePay(Date datePay) {
		this.datePay = datePay;
	}

	/**
	 * Gets the date charge.
	 *
	 * @return the date charge
	 */
	public Date getDateCharge() {
		return dateCharge;
	}

	/**
	 * Sets the date charge.
	 *
	 * @param dateCharge the new date charge
	 */
	public void setDateCharge(Date dateCharge) {
		this.dateCharge = dateCharge;
	}

}
