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
	 * Find by id.
	 *
	 * @param id the id
	 * @return the direct debit
	 */
	public DirectDebit findById(Long id);

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
	public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId);
}
