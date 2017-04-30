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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.account.Account;

/** The Class Account. */
@Entity
public class BankAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "accountId")
	private Account account;

	private String bank;

	private String bic;

	private String iban;

	/** Identificador unico, para cada cuenta de cada usuario. */
	private String mandate;

	/** Fecha en la que se firma el pdf del mandato. */
	private Date dateMandate;

	private Date dateCreate;

	private boolean active;

	/**
	 * Instantiates a new bank account.
	 */
	protected BankAccount() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new bank account.
	 *
	 * @param account the account
	 * @param bank the bank
	 * @param bic the bic
	 * @param iban the iban
	 * @param dateMandate the date mandate
	 */
	public BankAccount(Account account, String bank, String bic, String iban, Date dateMandate) {
		super();
		this.account = account;
		this.bank = bank;
		this.bic = bic;
		this.iban = iban;
		this.dateMandate = dateMandate;
		this.dateCreate = new Date();
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public String getBank() {
		return bank;
	}

	public String getBic() {
		return bic;
	}

	public String getIban() {
		return iban;
	}

	public String getMandate() {
		return mandate;
	}

	public void setMandate(String mandate) {
		this.mandate = mandate;
	}

	public Date getDateMandate() {
		return dateMandate;
	}

	public void setDateMandate(Date dateMandate) {
		this.dateMandate = dateMandate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "BankAccount [id=" + id + ", account=" + account + ", bank=" + bank + ", bic=" + bic + ", iban=" + iban + ", mandate=" + mandate
				+ ", dateMandate=" + dateMandate + ", dateCreate=" + dateCreate + ", active=" + active + "]";
	}

}
