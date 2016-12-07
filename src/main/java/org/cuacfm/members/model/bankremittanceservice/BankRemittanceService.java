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
package org.cuacfm.members.model.bankremittanceservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.springframework.http.ResponseEntity;

/** The Interface BankRemittanceService. */
public interface BankRemittanceService {

	/**
	 * Creates the bank remittance.
	 *
	 * @param dateCharge the date charge
	 * @param monthCharge the month charge
	 */
	public void createBankRemittance(Date dateCharge, Date monthCharge);

	/**
	 * Update.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the bank remittance
	 */
	public BankRemittance update(BankRemittance bankRemittance);

	/**
	 * Pay bank remittance.
	 *
	 * @param bankRemittance the bank remittance
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void payBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException;

	/**
	 * Management bank remittance.
	 *
	 * @param bankRemittanc the bank remittanc
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void managementBankRemittance(BankRemittance bankRemittanc) throws ExistTransactionIdException;

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the bank remittance
	 */
	public BankRemittance findById(Long id);

	/**
	 * Gets the bank remittance list.
	 *
	 * @return the bank remittance list
	 */
	public List<BankRemittance> getBankRemittanceList();

	/**
	 * Update direct debit.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	public DirectDebit updateDirectDebit(DirectDebit directDebit);

	/**
	 * Find by direct debit id.
	 *
	 * @param directDebitid the direct debitid
	 * @return the direct debit
	 */
	public DirectDebit findByDirectDebitId(Long directDebitid);

	/**
	 * Pay direct debit.
	 *
	 * @param directDebitId the direct debit id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void payDirectDebit(DirectDebit directDebit) throws ExistTransactionIdException;

	/**
	 * Return bill.
	 *
	 * @param directDebit the direct debit
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void returnBill(DirectDebit directDebit) throws ExistTransactionIdException;

	/**
	 * Management direct debit.
	 *
	 * @param directDebitId the direct debit id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void managementDirectDebit(DirectDebit directDebit) throws ExistTransactionIdException;

	/**
	 * Gets the direct debit list by bank remittance id.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the direct debit list by bank remittance id
	 */
	public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId);

	/**
	 * create Txt Bank Remittance.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the response entity
	 */
	public ResponseEntity<byte[]> createTxtBankRemittance(Long bankRemittanceId);
}
