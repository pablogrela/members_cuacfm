package org.cuacfm.members.model.paymember;

import java.util.List;

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
    * Gets the pay member no pay list by direct Debit.
    *
    * @return the pay member no pay list by direct Debit
    */
   public List<PayMember> getPayMemberNoPayListByDirectDebit();

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
