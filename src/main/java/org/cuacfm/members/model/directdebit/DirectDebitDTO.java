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
package org.cuacfm.members.model.directdebit;

import java.util.Date;

import org.cuacfm.members.model.account.AccountDTO;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Class DirectDebit. */
public class DirectDebitDTO {

	private String id;
	private AccountDTO account;
	private Date bankRemittance;
	private String concept;
	private Double price;
	private Date dateCreate;
	private Date dateUpdate;
	private Date datePay;
	private states state;
	private methods method;
	private String returnReason;
	private String secuence;
	private String idPayer;
	private String idTxn;
	private String emailPayer;

	/** Instantiates a direct debit. */
	protected DirectDebitDTO() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new direct debit DTO.
	 *
	 * @param id the id
	 * @param account the account
	 * @param concept the concept
	 * @param price the price
	 * @param dateUpdate the date update
	 * @param datePay the date pay
	 * @param state the state
	 * @param method the method
	 * @param secuence the secuence
	 * @param idPayer the id payer
	 * @param idTxn the id txn
	 * @param emailPayer the email payer
	 */
	public DirectDebitDTO(String id, AccountDTO account, String concept, Double price, Date dateCreate, Date dateUpdate, Date datePay, states state,
			methods method, String secuence, String idPayer, String idTxn, String emailPayer) {
		super();
		this.id = id;
		this.account = account;
		this.concept = concept;
		this.price = price;
		this.dateCreate = dateCreate;
		this.dateUpdate = dateUpdate;
		this.datePay = datePay;
		this.state = state;
		this.method = method;
		this.secuence = secuence;
		this.idPayer = idPayer;
		this.idTxn = idTxn;
		this.emailPayer = emailPayer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public Date getBankRemittance() {
		return bankRemittance;
	}

	public void setBankRemittance(Date bankRemittance) {
		this.bankRemittance = bankRemittance;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Date getDatePay() {
		return datePay;
	}

	public void setDatePay(Date datePay) {
		this.datePay = datePay;
	}

	public states getState() {
		return state;
	}

	public void setState(states state) {
		this.state = state;
	}

	public methods getMethod() {
		return method;
	}

	public void setMethod(methods method) {
		this.method = method;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getSecuence() {
		return secuence;
	}

	public void setSecuence(String secuence) {
		this.secuence = secuence;
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

}
