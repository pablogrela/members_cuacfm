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
package org.cuacfm.members.model.inscription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cuacfm.members.model.account.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class InscriptionRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class InscriptionRepositoryImpl implements InscriptionRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save Inscription.
    *
    * @param inscription
    *           the inscription
    * @return the inscription
    */
   @Override
   @Transactional
   public void save(Inscription inscription) {
         entityManager.persist(inscription);
   }

   /**
    * Update.
    * 
    * @param inscription
    *           the inscription
    */
   @Override
   @Transactional
   public void update(Inscription inscription) {
         entityManager.merge(inscription);
   }

   /**
    * Delete Inscription.
    *
    * @param inscription
    *           the inscription
    * @return the inscription
    */
   @Override
   @Transactional
   public void delete(Long accountId, Long trainingId) {
      Inscription inscription = findByInscriptionIds(accountId, trainingId);
      if (inscription != null) {
         entityManager.remove(inscription);
      }
   }

   /**
    * Find by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Inscription> pertain to user
    */
   @Override
   public List<Inscription> getByAccountId(Long accountId) {
      return entityManager
            .createQuery("select i from Inscription i where i.account.id = :accountId",
                  Inscription.class).setParameter("accountId", accountId).getResultList();

   }

   /**
    * Find by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Long> pertain to user
    */
   @Override
   public List<Long> getIdsByAccountId(Long accountId) {

      return entityManager
            .createQuery("select training.id from Inscription i where i.account.id = :accountId",
                  Long.class).setParameter("accountId", accountId).getResultList();

   }

   /**
    * Find by traningId.
    *
    * @param traningId
    *           the id of training
    * @return the List<Inscription> pertain to training
    */
   @Override
   public List<Inscription> getByTrainingId(Long trainingId) {
      return entityManager
            .createQuery("select i from Inscription i where i.training.id = :trainingId",
                  Inscription.class).setParameter("trainingId", trainingId).getResultList();
   }

   /**
    * Find Inscription by accountId and trainingId.
    *
    * @param accountId
    *           the id of user
    * @param traningId
    *           the id of training
    * @return inscription
    */
   @Override
   public Inscription findByInscriptionIds(Long accountId, Long trainingId) {
      try {
         return entityManager
               .createQuery(
                     "select i from Inscription i where i.account.id = :accountId and i.training.id = :trainingId",
                     Inscription.class).setParameter("accountId", accountId)
               .setParameter("trainingId", trainingId).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Get all Inscriptions by trainingId.
    *
    * @return List<Inscription>
    */
   @Override
   public List<Inscription> getInscriptionListByTrainingId(Long trainingId) {
      return entityManager
            .createQuery("select i from Inscription i where i.training.id = :trainingId",
                  Inscription.class).setParameter("trainingId", trainingId).getResultList();
   }

   /**
    * Get inscriptionsId by accountId with unsubscribe = true.
    *
    * @param accountId
    *           the id of account
    * @return List<Long> pertain to account
    */
   @Override
   public List<Long> getUnsubscribeIdsByAccountId(Long accountId) {

      return entityManager
            .createQuery(
                  "select training.id from Inscription i where i.account.id = :accountId and i.unsubscribe = true",
                  Long.class).setParameter("accountId", accountId).getResultList();

   }

   /**
    * Gets the name users by inscription with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the training id
    * @return the name users by inscription
    */
   @Override
   public List<String> getUsernamesByInscription(Long trainingId) {

      // No running Concat(a.name, ' - ', a.nickname)
      List<Account> accounts = entityManager
            .createQuery(
                  "select a from Account a " + "where a.role <> 'ROLE_ADMIN' "
                        + "and a.active = true " + "and a.id not in "
                        + "(select c.id from Account c, Inscription i "
                        + "where i.training.id = :trainingId and i.account.id = c.id) "
                        + "order by a.login", Account.class).setParameter("trainingId", trainingId)
            .getResultList();

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
