package org.cuacfm.members.model.paymember;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.cuacfm.members.model.account.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class PayMemberRepositoryImpl implements PayMemberRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param userFeeMember
    *           the training type
    * @return userFeeMember
    * @throws PersistenceException
    *            the persistence exception
    */
   @Override
   @Transactional
   public PayMember save(PayMember userFeeMember) {
      entityManager.persist(userFeeMember);
      return userFeeMember;
   }

   /**
    * Update.
    *
    * @param userFeeMember
    *           the training type
    * @return userFeeMember
    * @throws PersistenceException
    *            the persistence exception
    */
   @Override
   @Transactional
   public PayMember update(PayMember userFeeMember) {
      return entityManager.merge(userFeeMember);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of userFeeMember
    * @return userFeeMember
    */
   @Override
   public PayMember findById(Long id) {
      try {
         return entityManager
               .createQuery("select a from PayMember a where a.id = :id", PayMember.class)
               .setParameter("id", id).getSingleResult();
      } catch (PersistenceException e) {
         return null;
      }
   }

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the pay member
    */
   @Override
   public PayMember findByIdTxn(String idTxn) {
      try {
         return entityManager
               .createQuery("select a from PayMember a where a.idTxn = :idTxn", PayMember.class)
               .setParameter("idTxn", idTxn).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by pay member ids.
    *
    * @param accountId
    *           the account id
    * @param feeMemberId
    *           the fee member id
    * @return List<FeeMember>
    */
   @Override
   public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId) {
      return entityManager
            .createQuery(
                  "select a from PayMember a where a.account.id = :accountId and a.feeMember.id = :feeMemberId",
                  PayMember.class).setParameter("accountId", accountId)
            .setParameter("feeMemberId", feeMemberId).getResultList();
   }

   /**
    * Get all userFeeMembers.
    *
    * @return List<FeeMember>
    */
   @Override
   public List<PayMember> getPayMemberList() {
      return entityManager.createQuery("select a from PayMember a order by a.account.login",
            PayMember.class).getResultList();
   }

   /**
    * Gets the pay member no pay list by direct debit.
    *
    * @return the pay member no pay list by direct debit
    */
   @Override
   public List<PayMember> getPayMemberNoPayListByDirectDebit() {
      return entityManager.createQuery(
            "select p from PayMember p where p.hasPay = false "
                  + "and p.account.methodPayment.directDebit = true "
                  + "and p.account.iban <> '' "
                  + "and p.account.bic <> ''  "
                  + "and p.dateCharge <= CURRENT_DATE()  "
                  + "order by p.account.name",
            PayMember.class).getResultList();
   }

   /**
    * Gets the pay member list by fee member id.
    *
    * @param feeMemberId
    *           the fee member id
    * @return the pay member list by fee member id
    */
   @Override
   public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId) {
      return entityManager
            .createQuery("select a from PayMember a where a.feeMember.id = :feeMemberId",
                  PayMember.class).setParameter("feeMemberId", feeMemberId).getResultList();
   }

   /**
    * Gets the pay member list by pay account id.
    *
    * @param accountId
    *           the fee member id
    * @return the pay member list by account id
    */
   @Override
   public List<PayMember> getPayMemberListByAccountId(Long accountId) {
      return entityManager
            .createQuery("select a from PayMember a where a.account.id = :accountId",
                  PayMember.class).setParameter("accountId", accountId).getResultList();

   }

   /**
    * Gets the name users by fee member with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the fee member id
    * @return the name users by fee member
    */
   @Override
   public List<String> getUsernamesByFeeMember(Long feeMemberId) {
      // No running Concat(a.name, ' - ', a.nickname)
      List<Account> accounts = entityManager
            .createQuery(
                  "select a from Account a "
                        + "where a.role in ('ROLE_USER', 'ROLE_TRAINER', 'ROLE_PREREGISTERED')"
                        + "and a.active = true " + "and a.id not in "
                        + "(select c.id from Account c, PayMember p "
                        + "where p.feeMember.id = :feeMemberId and p.account.id = c.id) "
                        + "order by a.login", Account.class)
            .setParameter("feeMemberId", feeMemberId).getResultList();

      List<String> usernames = new ArrayList<String>();
      for (Account account : accounts) {
         if (account.getNickName() != null) {
            usernames.add(account.getId() + ": " + account.getName() + " - "
                  + account.getNickName());
         } else {
            usernames.add(account.getId() + ": " + account.getName());
         }
      }
      return usernames;
   }
}
