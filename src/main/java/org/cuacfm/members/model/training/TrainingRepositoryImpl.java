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
    * Get all trainings with close = true.
    *
    * @return List<Training>
    */
   @Override
   public List<Training> getTrainingListClose() {
      return entityManager.createQuery(
            "select t from Training t where t.close = true order by t.name", Training.class)
            .getResultList();
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
