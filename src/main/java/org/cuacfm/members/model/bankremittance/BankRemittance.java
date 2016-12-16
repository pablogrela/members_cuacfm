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
package org.cuacfm.members.model.bankremittance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.cuacfm.members.model.util.Constants.states;

/** The Class BankRemittance. */
@Entity
public class BankRemittance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private Date dateDebit;
	private Date dateCharge;
	private Date monthCharge;
	private states state;

	/**
	 * Instantiates a new bank remittance.
	 */
	protected BankRemittance() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new bank remittance.
	 *
	 * @param dateCharge the date charge
	 * @param monthCharge the month charge
	 */
	public BankRemittance(Date dateCharge, Date monthCharge) {
		super();
		this.dateDebit = new Date();
		this.monthCharge = monthCharge;
		this.dateCharge = dateCharge;
		this.state = states.NO_PAY;
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
	 * Gets the date debit.
	 *
	 * @return the date debit
	 */
	public Date getDateDebit() {
		return dateDebit;
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
	 * Gets the month charge.
	 *
	 * @return the month charge
	 */
	public Date getMonthCharge() {
		return monthCharge;
	}
}
