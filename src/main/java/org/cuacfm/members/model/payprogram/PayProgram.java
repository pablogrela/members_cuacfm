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
package org.cuacfm.members.model.payprogram;

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

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Class PayProgram. */
@Entity
public class PayProgram implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "programId")
	private Program program;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "feeProgramId")
	private FeeProgram feeProgram;

	private Double price;

	@Enumerated(EnumType.STRING)
	private states state;

	@Enumerated(EnumType.STRING)
	private methods method;

	private String accountPayer;

	private String idPayer;

	//@Column(unique = true)
	// May have been paid with the same directDebit transaction
	private String idTxn;

	/** The email payer. */
	private String emailPayer;

	/** The date pay. */
	private Date datePay;

	/**
	 * Instantiates a new pay program.
	 */
	public PayProgram() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new pay program.
	 *
	 * @param program the program
	 * @param feeProgram the fee program
	 * @param price the price
	 */
	public PayProgram(Program program, FeeProgram feeProgram, Double price) {
		super();
		this.program = program;
		this.feeProgram = feeProgram;
		this.price = price;
		this.state = states.NO_PAY;
	}

	public Long getId() {
		return id;
	}

	public Program getProgram() {
		return program;
	}

	public FeeProgram getFeeProgram() {
		return feeProgram;
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

	public methods getMethod() {
		return method;
	}

	public void setMethod(methods method) {
		this.method = method;
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

	public Date getDatePay() {
		return datePay;
	}

	public void setDatePay(Date datePay) {
		this.datePay = datePay;
	}

}
