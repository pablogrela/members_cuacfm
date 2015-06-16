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
package org.cuacfm.members.model.trainingtype;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TrainingRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save.
    *
    * @param trainingType
    *           the trainingType
    * @return trainingType
    */
   @Override
   @Transactional
   public TrainingType save(TrainingType trainingType) {
      entityManager.persist(trainingType);
      return trainingType;
   }

   /**
    * Update.
    *
    * @param training
    *           the trainingType
    * @return trainingType
    */
   @Override
   @Transactional
   public TrainingType update(TrainingType trainingType) {
      return entityManager.merge(trainingType);
   }

   /**
    * Delete.
    *
    * @param trainingType
    *           the trainingType
    */
   @Override
   @Transactional
   public void delete(Long id) {
      TrainingType trainingType = findById(id);
      if (trainingType != null) {
         entityManager.remove(trainingType);
      }
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of trainingType
    * @return trainingType
    */
   @Override
   public TrainingType findById(Long id) {
      try {
         return entityManager
               .createQuery("select a from TrainingType a where a.id = :id", TrainingType.class)
               .setParameter("id", id).getSingleResult();
      } catch (PersistenceException e) {
         return null;
      }
   }

   /**
    * Find by name.
    *
    * @param name
    *           the name of trainingType
    * @return TrainingType
    */
   @Override
   public TrainingType findByName(String name) {
      try {
         return entityManager
               .createQuery("select a from TrainingType a where a.name = :name", TrainingType.class)
               .setParameter("name", name).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Get all trainingTypes.
    *
    * @return List<TrainingType>
    */
   @Override
   public List<TrainingType> getTrainingTypeList() {
      return entityManager.createQuery("select a from TrainingType a order by a.name",
            TrainingType.class).getResultList();
   }
}
