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
package org.cuacfm.members.model.training;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TrainingRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class TrainingRepositoryImpl implements TrainingRepository {

   /** The entity manager. */
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Save Training.
    *
    * @param training
    *           the training
    * @return the training
    */
   @Override
   @Transactional
   public Training save(Training training) {
      entityManager.persist(training);
      return training;
   }

   /**
    * Update Training.
    *
    * @param training
    *           the training
    * @return the training
    */
   @Override
   @Transactional
   public Training update(Training training) {
      return entityManager.merge(training);
   }

   /**
    * Delete Training.
    *
    * @param training
    *           the training
    * @return the training
    */
   @Override
   @Transactional
   public void delete(Long id) {
      Training training = findById(id);
      if (training != null) {
         entityManager.remove(training);
      }
   }

   /**
    * Find by id.
    *
    * @param id
    *           the id of training
    * @return the training
    */
   @Override
   public Training findById(Long id) {
      try {
         return entityManager
               .createQuery("select t from Training t where t.id = :id", Training.class)
               .setParameter("id", id).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Find by name.
    *
    * @param name
    *           the name of training
    * @return the training
    */
   @Override
   public Training findByName(String name) {
      try {
         return entityManager
               .createQuery("select t from Training t where t.name = :name", Training.class)
               .setParameter("name", name).getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * Get all trainings.
    *
    * @return List<Training>
    */
   @Override
   public List<Training> getTrainingList() {
      return entityManager.createQuery("select t from Training t order by t.name", Training.class)
            .getResultList();
   }

   /**
    * Get all trainings with close = false.
    *
    * @return List<Training>
    */
   @Override
   public List<Training> getTrainingListOpen() {
      return entityManager.createQuery(
            "select t from Training t where t.close = false order by t.name", Training.class)
            .getResultList();
   }

   /**
    * Gets the training list close with close = true.
    *
    * @param year
    *           the year
    * @return the training list close
    */
   @Override
   public List<Training> getTrainingListClose(int year) {
      return entityManager.createQuery(
            "select t from Training t where t.close = true and year(t.dateTraining) = :year order by t.name ", Training.class)
            .setParameter("year", year).getResultList();
   }

   /**
    * Get all trainings by trainingTypeId.
    *
    * @return List<Training> pertains at trainingType
    */
   @Override
   public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId) {
      return entityManager
            .createQuery("select t from Training t where t.trainingType.id = :trainingTypeId",
                  Training.class).setParameter("trainingTypeId", trainingTypeId).getResultList();

   }
}
