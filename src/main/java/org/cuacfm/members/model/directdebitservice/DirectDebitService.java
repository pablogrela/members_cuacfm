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
package org.cuacfm.members.model.directdebitservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitDTO;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;

/** The Interface DirectDebitService. */
public interface DirectDebitService {

	/**
	 * Save.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	public DirectDebit save(DirectDebit directDebit);

	/**
	 * Save.
	 *
	 * @param account the account
	 * @return the direct debit
	 */
	public DirectDebit save(Account account);

	/**
	 * Refresh.
	 *
	 * @return the string
	 */
	public String refresh();

	/**
	 * Update state direct debit.
	 *
	 * @param directDebit the direct debit
	 * @param state the state
	 * @param method the method
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void updateDirectDebit(DirectDebit directDebit, states state, methods method, Date datePay);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the direct debit
	 */
	public DirectDebit findById(String id);

	/**
	 * Gets the dto.
	 *
	 * @param directDebits the direct debits
	 * @return the dto
	 */
	public List<DirectDebitDTO> getDTO(List<DirectDebit> directDebits);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<DirectDebit> findAll();

	/**
	 * Find all open.
	 *
	 * @return the list
	 */
	public List<DirectDebit> findAllOpen();

	/**
	 * Find all close.
	 *
	 * @return the list
	 */
	public List<DirectDebit> findAllClose();

	/**
	 * Find allby account id.
	 *
	 * @param accountId the account id
	 * @return the list
	 */
	public List<DirectDebit> findAllByAccountId(Long accountId);

	/**
	 * Find all activeby account id.
	 *
	 * @param accountId the account id
	 * @return the list
	 */
	public List<DirectDebit> findAllOpenByAccountId(Long accountId);

	/**
	 * Gets the last direct debit, if state is NO_PAY.
	 *
	 * @param accountId the account id
	 * @return the last direct debit
	 */
	public DirectDebit getLastDirectDebit(Long accountId);

	/**
	 * Checks if is rcur or frst.
	 *
	 * @param accountId the account id
	 * @return the boolean
	 */
	public String isRcurOrFRST(Long accountId);

	/**
	 * Gets the direct debit list by bank remittance id.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the direct debit list by bank remittance id
	 */
	public List<DirectDebit> findAllByBankRemittanceId(Long bankRemittanceId);

	/**
	 * Pay direct debit direct debit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String directDebit(DirectDebit directDebit, Account account);

	/**
	 * Bank deposit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String markBankDeposit(DirectDebit directDebit, Account account);

	/**
	 * Cancel bank deposit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String cancelBankDeposit(DirectDebit directDebit, Account account);

	/**
	 * Confirm bankDeposit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String confirmBankDeposit(DirectDebit directDebit, Account account);

	/**
	 * Confirm pay by paypal.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String confirmPaypal(DirectDebit directDebit, Account account);

	/**
	 * Cash.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String cash(DirectDebit directDebit, Account account);

	/**
	 * Paypal.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @param idTxn the id txn
	 * @param idPayer the id payer
	 * @param emailPayer the email payer
	 * @param statusPay the status pay
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void paypal(DirectDebit directDebit, Account account, String idTxn, String idPayer, String emailPayer, String statusPay, String datePay)
			throws ExistTransactionIdException;

	/**
	 * Return bill direct debit.
	 *
	 * @param directDebi the direct debi
	 * @param accountt the accountt
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String returnBill(DirectDebit directDebi, Account accountt);

	/**
	 * Management direct debit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String management(DirectDebit directDebit, Account account);

	/**
	 * Cancel direct debit.
	 *
	 * @param directDebit the direct debit
	 * @param account the account
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String cancel(DirectDebit directDebit, Account account);
}
