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

import java.util.List;

/** The Interface DirectDebitRepository. */
public interface DirectDebitRepository {

	/**
	 * Save.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	public DirectDebit save(DirectDebit directDebit);

	/**
	 * Update.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	public DirectDebit update(DirectDebit directDebit);

	/**
	 * Save.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	public void remove(DirectDebit directDebit);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the direct debit
	 */
	public DirectDebit findById(String id);

	/**
	 * Find last id.
	 *
	 * @return the string
	 */
	public String findLastId();

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
	 * Gets the direct debit list by bank remittance id.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the direct debit list by bank remittance id
	 */
	public List<DirectDebit> findAllByBankRemittanceId(Long bankRemittanceId);

	/**
	 * Gets the last direct debit, with state NO_PAY.
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
	 * Find by id txn.
	 *
	 * @param idTxn the id txn
	 * @return the direct debit
	 */
	public DirectDebit findByIdTxn(String idTxn);
}
