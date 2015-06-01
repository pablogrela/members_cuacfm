package org.cuacfm.members.model.bankaccount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankAccountRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class BankAccountRepositoryImpl implements BankAccountRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param bankAccount
    *           the bankAccount
    * @return the bankAccount
    */
   @Override
   @Transactional
   public BankAccount save(BankAccount bankAccount) {
      entityManager.createQuery("update from BankAccount b set b.active = false where b.account.id = :accountId")
      .setParameter("accountId", bankAccount.getAccount().getId()).executeUpdate();

      entityManager.persist(bankAccount);
      return bankAccount;
   }

   /**
    * Active bank account by account id.
    *
    * @param accountId
    *           the account id
    * @return the bank account
    */
   @Override
   public BankAccount activeBankAccountByAccountId(Long accountId) {
         return entityManager
               .createQuery("select b from BankAccount b where b.account.id = :accountId and b.active = true",
                     BankAccount.class).setParameter("accountId", accountId).getSingleResult();
   }
}
