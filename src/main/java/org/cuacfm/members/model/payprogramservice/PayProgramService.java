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
package org.cuacfm.members.model.payprogramservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.springframework.http.ResponseEntity;

/** The Interface PayProgramService. */
public interface PayProgramService {

	/**
	 * Save an payProgram into database.
	 *
	 * @param payProgram the pay program
	 * @return the training
	 */
	public PayProgram save(PayProgram payProgram);

	/**
	 * Update PayProgram.
	 *
	 * @param payProgram the pay program
	 * @return PayProgram
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public PayProgram update(PayProgram payProgram) throws ExistTransactionIdException;

	/**
	 * Pay the PayProgram.
	 *
	 * @param payProgram the pay program
	 */
	public void pay(PayProgram payProgram);

	/**
	 * Pay pay pal.
	 *
	 * @param payProgram the pay program
	 * @param accountPayer the account payer
	 * @param idTxn the id txn
	 * @param idPayer the id payer
	 * @param emailPayer the email payer
	 * @param statusPay the status pay
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void payPayPal(PayProgram payProgram, String accountPayer, String idTxn, String idPayer, String emailPayer, String statusPay,
			String datePay) throws ExistTransactionIdException;

	/**
	 * Find by id returns pay program which has this identifier.
	 *
	 * @param id the id
	 * @return payProgram
	 */
	public PayProgram findById(Long id);

	/**
	 * Find by id txn.
	 *
	 * @param idTxn the id txn
	 * @return the pay program
	 */
	public PayProgram findByIdTxn(String idTxn);

	/**
	 * Find by pay program ids.
	 *
	 * @param programId the program id
	 * @param feeProgramId the fee program id
	 * @return the pay program
	 */
	public PayProgram findByPayProgramIds(Long programId, Long feeProgramId);

	/**
	 * Get all payPrograms.
	 *
	 * @return List<PayProgram>
	 */
	public List<PayProgram> getPayProgramList();

	/**
	 * Find no pay list by account id.
	 *
	 * @param accountId the account id
	 * @return the list
	 */
	public List<PayProgram> findNoPayListByAccountId(Long accountId);

	/**
	 * Gets the pay program no pay list by direct debit.
	 *
	 * @param monthCharge the month charge
	 * @return the pay program no pay list by direct debit
	 */
	public Map<Account, List<PayProgram>> getPayProgramNoPayListByDirectDebit(Date monthCharge);

	/**
	 * Gets the pay program list by fee program id.
	 *
	 * @param feeProgramId the fee program id
	 * @return the pay program list by fee program id
	 */
	public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId);

	/**
	 * Gets the pay program list by pay program id.
	 *
	 * @param programId the pay inscription id
	 * @return the pay program list by program id
	 */
	public List<PayProgram> getPayProgramListByProgramId(Long programId);

	/**
	 * Gets the pay program list by account id.
	 *
	 * @param accountId the account id
	 * @return the pay program list by account id
	 */
	public List<PayProgram> getPayProgramListByAccountId(Long accountId);

	/**
	 * Creates the pdf fee program.
	 *
	 * @param feeProgramId the fee program id
	 * @param option the option
	 * @return the response entity
	 */
	public ResponseEntity<byte[]> createPdfFeeProgram(Long feeProgramId, String option);
}
