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
package org.cuacfm.members.model.paymemberservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.springframework.http.ResponseEntity;

/** The Interface PayMemberService. */
public interface PayMemberService {

	/**
	 * Save an pay Member into database.
	 *
	 * @param paymember the pay member
	 * @return the training
	 */
	public PayMember save(PayMember paymember);

	/**
	 * Update Pay Member.
	 *
	 * @param payMember the pay member
	 * @return PayMember
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public PayMember update(PayMember payMember);

	/**
	 * Pay the PayMember.
	 *
	 * @param paymember the pay member
	 */
	public void pay(PayMember paymember);

	/**
	 * Pay pay pal.
	 *
	 * @param paymember the pay member
	 * @param idTxn the id txn
	 * @param idPayer the id payer
	 * @param emailPayer the email payer
	 * @param statusPay the status pay
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void payPayPal(PayMember paymember, String idTxn, String idPayer, String emailPayer, String statusPay, String datePay)
			throws ExistTransactionIdException;

	/**
	 * Find by id returns pay member which has this identifier.
	 *
	 * @param id the id
	 * @return paymember
	 */
	public PayMember findById(Long id);

	/**
	 * Find by id txn.
	 *
	 * @param idTxn the id txn
	 * @return the pay member
	 */
	public PayMember findByIdTxn(String idTxn);

	/**
	 * Find by pay member ids.
	 *
	 * @param accountId the account id
	 * @param feeMemberId the fee member id
	 * @return List<PayMember>
	 */
	public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId);

	/**
	 * Get all pay members.
	 *
	 * @return List<PayMember>
	 */
	public List<PayMember> getPayMemberList();

	/**
	 * Find no pay list by account id.
	 *
	 * @param accountId the account id
	 * @return the list
	 */
	public List<PayMember> findNoPayListByAccountId(Long accountId);

	/**
	 * Gets the pay member no pay list by direct debit.
	 *
	 * @param monthCharge the month charge
	 * @return the pay member no pay list by direct debit
	 */
	public Map<Account, List<PayMember>> getPayMemberNoPayListByDirectDebit(Date monthCharge);

	/**
	 * Gets the pay member list by fee member id.
	 *
	 * @param feeMemberId the fee member id
	 * @return the pay member list by fee member id
	 */
	public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId);

	/**
	 * Gets the pay member list by pay account id.
	 *
	 * @param accountId the fee member id
	 * @return the pay member list by account id
	 */
	public List<PayMember> getPayMemberListByAccountId(Long accountId);

	/**
	 * Gets the name users by fee member with role=ROLE_USER an active=true.
	 *
	 * @param feeMemberId the fee member id
	 * @return the name users by fee member
	 */
	public List<String> getUsernamesByFeeMember(Long feeMemberId);

	/**
	 * Creates the pdf fee member.
	 *
	 * @param feeMemberId the fee member id
	 * @param option the option
	 * @return the response entity
	 */
	public ResponseEntity<byte[]> createPdfFeeMember(Long feeMemberId, String option);
}
