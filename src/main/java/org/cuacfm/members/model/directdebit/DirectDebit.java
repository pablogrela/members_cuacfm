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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Class DirectDebit. */
@Entity
public class DirectDebit implements Serializable {

	private static final long serialVersionUID = 1L;

	// LA CLAVE SERA ddMMyyy_1, donde uno es el numero de pago creado ese dia

	//	@GeneratedValue
	//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directDebit_generator")
	//	@SequenceGenerator(name="book_generator", sequenceName = "book_seq", initialValue=1, allocationSize=50)
	@Id
	private String id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "accountId")
	private Account account;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "bankRemittanceId")
	private BankRemittance bankRemittance;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DirectDebitPayMembers", joinColumns = { @JoinColumn(name = "directDebitId") }, inverseJoinColumns = {
			@JoinColumn(name = "payMemberId") })
	private List<PayMember> payMembers;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DirectDebitPayPrograms", joinColumns = { @JoinColumn(name = "directDebitId") }, inverseJoinColumns = {
			@JoinColumn(name = "payProgramId") })
	private List<PayProgram> payPrograms;

	private Date dateCreate;

	private Date dateUpdate;

	private Date datePay;

	@Enumerated(EnumType.STRING)
	private states state;

	@Enumerated(EnumType.STRING)
	private methods method;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnReasonId")
	private ReturnReason returnReason;

	//FRST(first) o RCUR(Recursive)
	private String secuence;

	private String idPayer;

	@Column(unique = true)
	private String idTxn;

	private String emailPayer;

	/** Instantiates a directDebit. */
	protected DirectDebit() {
		super();
	}

	/**
	 * Instantiates a new directDebit.
	 *
	 * @param account the account
	 * @param id the id
	 */
	public DirectDebit(Account account, String id) {
		super();
		this.account = account;
		this.dateCreate = new Date();
		this.state = states.NO_PAY;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public BankRemittance getBankRemittance() {
		return bankRemittance;
	}

	public void setBankRemittance(BankRemittance bankRemittance) {
		this.bankRemittance = bankRemittance;
	}

	public List<PayMember> getPayMembers() {
		return payMembers;
	}

	public List<PayProgram> getPayPrograms() {
		return payPrograms;
	}

	public void setPayMembers(List<PayMember> payMembers) {
		this.payMembers = payMembers;
	}

	public void setPayPrograms(List<PayProgram> payPrograms) {
		this.payPrograms = payPrograms;
	}

	public Date getDateCreate() {
		return dateCreate;
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

	public ReturnReason getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(ReturnReason returnReason) {
		this.returnReason = returnReason;
	}

	public String getSecuence() {
		return secuence;
	}

	public void setSecuence(String secuence) {
		this.secuence = secuence;
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

	// BigDecimal
	public Double getPrice() {
		Double price = (double) 0;

		if (getPayMembers() != null) {
			for (PayMember payMember : getPayMembers()) {
				price = price + payMember.getPrice();
			}
		}

		if (getPayPrograms() != null) {
			for (PayProgram payProgram : getPayPrograms()) {
				price = price + payProgram.getPrice();
			}
		}

		return price;
	}

	public String getConcept() {
		String concept = account.getName() + " " + account.getSurname() + ": ";

		if (getPayMembers() != null) {
			for (PayMember payMember : getPayMembers()) {
				concept = concept + payMember.getFeeMember().getName() + ", ";
			}
		}

		if (getPayPrograms() != null) {
			for (PayProgram payProgram : getPayPrograms()) {
				concept = concept + payProgram.getProgram().getName() + " - " + payProgram.getFeeProgram().getName() + ", ";
			}
		}

		// Se quita la coma del final
		concept = concept.substring(0, concept.length() - 2);
		return concept;
	}

	public String getConceptShort() {
		String conceptShort = getConcept();
		if (conceptShort.isEmpty() && conceptShort.length() > 140) {
			conceptShort = conceptShort.substring(0, 140);
		}
		return conceptShort;
	}

	@Override
	public String toString() {
		return "DirectDebit [id=" + id + ", account=" + account + ", bankRemittance=" + bankRemittance + ", payMembers=" + payMembers
				+ ", payPrograms=" + payPrograms + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + ", datePay=" + datePay + ", state="
				+ state + ", method=" + method + ", returnReason=" + returnReason + ", secuence=" + secuence + ", idPayer=" + idPayer + ", idTxn="
				+ idTxn + ", emailPayer=" + emailPayer + "]";
	}
}
