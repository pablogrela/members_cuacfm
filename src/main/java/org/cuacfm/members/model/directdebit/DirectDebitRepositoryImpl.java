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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class DirectDebitRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class DirectDebitRepositoryImpl implements DirectDebitRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param directDebit
    *           the direct debit
    * @return the direct debit
    */
   @Override
   @Transactional
   public DirectDebit save(DirectDebit directDebit) {
      entityManager.persist(directDebit);
      return directDebit;
   }

   /**
    * Update.
    *
    * @param directDebit
    *           the direct debit
    * @return the direct debit
    */
   @Override
   @Transactional
   public DirectDebit update(DirectDebit directDebit) {
      return entityManager.merge(directDebit);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the direct debit
    */
   @Override
   public DirectDebit findById(Long id) {
      try {
         return entityManager
               .createQuery("select d from DirectDebit d where d.id = :id", DirectDebit.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Checks if is rcur or frst.
    *
    * @param accountId
    *           the account id
    * @return the boolean
    */
   @Override
   public String isRcurOrFRST(Long accountId) {
      List<DirectDebit> directDebits = entityManager
            .createQuery("select d from DirectDebit d where d.account.id = :id", DirectDebit.class)
            .setParameter("id", accountId).getResultList();
      if (directDebits.isEmpty()) {
         return "FSRT";
      }
      return "RCUR";
   }

   /**
    * Gets the direct debit list by bank remittance id.
    *
    * @param bankRemittanceId
    *           the bank remittance id
    * @return the direct debit list by bank remittance id
    */
   @Override
   public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId) {
      return entityManager
            .createQuery(
                  "select d from DirectDebit d where d.bankRemittance.id = :bankRemittanceId",
                  DirectDebit.class).setParameter("bankRemittanceId", bankRemittanceId)
            .getResultList();
   }
}