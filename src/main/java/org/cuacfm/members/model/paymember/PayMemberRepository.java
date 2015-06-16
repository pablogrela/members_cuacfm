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
package org.cuacfm.members.model.paymember;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cuacfm.members.model.account.Account;

/** The Interface PayMemberRepository. */
public interface PayMemberRepository {

   /**
    * Save.
    *
    * @param userFeeMember
    *           the fee member
    * @return the pay member
    */
   public PayMember save(PayMember userFeeMember);

   /**
    * Update.
    *
    * @param userFeeMember
    *           the fee member
    * @return the pay member
    */
   public PayMember update(PayMember userFeeMember);

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the pay member
    */
   public PayMember findById(Long id);

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay member
    */
   public PayMember findByIdTxn(String idTxn);

   /**
    * Find by pay member ids.
    *
    * @param accountId
    *           the account id
    * @param feeMemberId
    *           the fee member id
    * @return List<PayMember>
    */
   public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId);

   /**
    * Gets the pay member list.
    *
    * @return the pay member list
    */
   public List<PayMember> getPayMemberList();

   /**
    * Gets the pay member no pay list by direct debit.
    *
    * @param monthCharge
    *           the month charge
    * @return the pay member no pay list by direct debit
    */
   public Map<Account, List<PayMember>> getPayMemberNoPayListByDirectDebit(Date monthCharge);

   /**
    * Gets the pay member list by fee member id.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the pay member list by fee member id
    */
   public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId);

   /**
    * Gets the pay member list by pay account id.
    *
    * @param accountId
    *           the fee member id
    * @return the pay member list by account id
    */
   public List<PayMember> getPayMemberListByAccountId(Long accountId);

   /**
    * Gets the name users by fee member with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the fee member id
    * @return the name users by fee member
    */
   public List<String> getUsernamesByFeeMember(Long feeMemberId);
}
