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
package org.cuacfm.members.model.payprogram;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cuacfm.members.model.account.Account;

/** The Interface PayProgramRepository. */
public interface PayProgramRepository {

	/**
	 * Save.
	 *
	 * @param payProgram the pay inscription
	 * @return the pay program
	 */
	public PayProgram save(PayProgram payProgram);

	/**
	 * Update.
	 *
	 * @param payProgram the pay inscription
	 * @return the pay program
	 */
	public PayProgram update(PayProgram payProgram);

	/**
	 * Removes the.
	 *
	 * @param payProgram the pay program
	 * @return the pay program
	 */
	public void remove(PayProgram payProgram);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the pay program
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
	 * @param feeProgram the fee program
	 * @return the pay program
	 */
	public PayProgram findByPayProgramIds(Long programId, Long feeProgram);

	/**
	 * Gets the pay program list.
	 *
	 * @return the pay program list
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
}
