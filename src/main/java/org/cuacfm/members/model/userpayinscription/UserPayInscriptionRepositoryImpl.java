package org.cuacfm.members.model.userpayinscription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.cuacfm.members.model.account.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class UserPayInscriptionRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class UserPayInscriptionRepositoryImpl implements UserPayInscriptionRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param userPayInscription
    *           the training type
    * @return userPayInscription
    * @throws PersistenceException
    *            the persistence exception
    */
   @Override
   @Transactional
   public UserPayInscription save(UserPayInscription userPayInscription) {
      entityManager.persist(userPayInscription);
      return userPayInscription;
   }

   /**
    * Update.
    *
    * @param userPayInscription
    *           the training type
    * @return userPayInscription
    * @throws PersistenceException
    *            the persistence exception
    */
   @Override
   @Transactional
   public UserPayInscription update(UserPayInscription userPayInscription) {
      return entityManager.merge(userPayInscription);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of userPayInscription
    * @return userPayInscription
    */
   @Override
   public UserPayInscription findById(Long id) {
      try {
         return entityManager
               .createQuery("select a from UserPayInscription a where a.id = :id",
                     UserPayInscription.class).setParameter("id", id).getSingleResult();
      } catch (PersistenceException e) {
         return null;
      }
   }

   /**
    * Find by id txn.
    *
    * @param idTxn
    *           the id txn
    * @return the user pay inscription
    */
   @Override
   public UserPayInscription findByIdTxn(String idTxn) {
      try {
         return entityManager
               .createQuery("select a from UserPayInscription a where a.idTxn = :idTxn",
                     UserPayInscription.class).setParameter("idTxn", idTxn).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by user pay inscription ids.
    *
    * @param accountId
    *           the account id
    * @param payInscriptionId
    *           the pay inscription id
    * @return List<PayInscription>
    */
   @Override
   public List<UserPayInscription> findByUserPayInscriptionIds(Long accountId, Long payInscriptionId) {
      return entityManager
            .createQuery(
                  "select a from UserPayInscription a where a.account.id = :accountId and a.payInscription.id = :payInscriptionId",
                  UserPayInscription.class).setParameter("accountId", accountId)
            .setParameter("payInscriptionId", payInscriptionId).getResultList();
   }

   /**
    * Get all userPayInscriptions.
    *
    * @return List<PayInscription>
    */
   @Override
   public List<UserPayInscription> getUserPayInscriptionList() {
      return entityManager
            .createQuery("select a from UserPayInscription a order by a.account.login",
                  UserPayInscription.class).getResultList();
   }

   /**
    * Gets the user pay inscription list by pay inscription id.
    *
    * @param payInscriptionId
    *           the pay inscription id
    * @return the user pay inscription list by pay inscription id
    */
   @Override
   public List<UserPayInscription> getUserPayInscriptionListByPayInscriptionId(Long payInscriptionId) {
      return entityManager
            .createQuery(
                  "select a from UserPayInscription a where a.payInscription.id = :payInscriptionId",
                  UserPayInscription.class).setParameter("payInscriptionId", payInscriptionId)
            .getResultList();
   }

   /**
    * Gets the user pay inscription list by pay account id.
    *
    * @param accountId
    *           the pay inscription id
    * @return the user pay inscription list by account id
    */
   @Override
   public List<UserPayInscription> getUserPayInscriptionListByAccountId(Long accountId) {
      return entityManager
            .createQuery("select a from UserPayInscription a where a.account.id = :accountId",
                  UserPayInscription.class).setParameter("accountId", accountId).getResultList();

   }

   /**
    * Gets the name users by Pay Inscription with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the pay inscription id
    * @return the name users by pay inscription
    */
   @Override
   public List<String> getUsernamesByPayInscription(Long payInscriptionId) {
      // No running Concat(a.name, ' - ', a.nickname)
      List<Account> accounts = entityManager
            .createQuery(
                  "select a from Account a "
                        + "where a.role in ('ROLE_USER', 'ROLE_TRAINER', 'ROLE_PREREGISTERED')"
                        + "and a.active = true " + "and a.id not in "
                        + "(select c.id from Account c, UserPayInscription p "
                        + "where p.payInscription.id = :payInscriptionId and p.account.id = c.id) "
                        + "order by a.login", Account.class)
            .setParameter("payInscriptionId", payInscriptionId).getResultList();

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
