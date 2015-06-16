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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class BankRemittanceRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class BankRemittanceRepositoryImpl implements BankRemittanceRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   @Override
   @Transactional
   public BankRemittance save(BankRemittance bankRemittance) {
      entityManager.persist(bankRemittance);
      return bankRemittance;
   }

   /**
    * Update.
    *
    * @param bankRemittance
    *           the bank remittance
    * @return the bank remittance
    */
   @Override
   @Transactional
   public BankRemittance update(BankRemittance bankRemittance) {
      return entityManager.merge(bankRemittance);
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id
    * @return the bank remittance
    */
   @Override
   public BankRemittance findById(Long id) {
      try {
         return entityManager
               .createQuery("select d from BankRemittance d where d.id = :id", BankRemittance.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Gets the bank remittance list.
    *
    * @return the bank remittance list
    */
   @Override
   public List<BankRemittance> getBankRemittanceList() {
      return entityManager.createQuery("select d from BankRemittance d",
            BankRemittance.class).getResultList();
   }
}
