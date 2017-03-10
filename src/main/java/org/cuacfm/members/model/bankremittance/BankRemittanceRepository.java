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

import java.util.List;

/** The Interface BankRemittanceRepository. */
public interface BankRemittanceRepository {

	/**
	 * Save.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the bank remittance
	 */
	public BankRemittance save(BankRemittance bankRemittance);

	/**
	 * Update.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the bank remittance
	 */
	public BankRemittance update(BankRemittance bankRemittance);

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
}
