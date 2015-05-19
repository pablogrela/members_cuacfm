package org.cuacfm.members.model.training;

import java.util.List;

import org.cuacfm.members.model.training.Training;

/** TrainingRepository Class.*/
public interface TrainingRepository {

   /**
    * Save training.
    *
    * @param training
    *           the training
    * @return the training
    */
   public Training save(Training training);

   /**
    * Update training.
    *
    * @param training
    *           the training
    * @return the training
    */

   public Training update(Training training);

   /**
    * Delete training.
    *
    * @param id
    *           the id
    * @return the training
    */

   public void delete(Long id);

   /**
    * Find by id.
    *
    * @param id
    *           the id of user
    * @return the training
    */
   public Training findById(Long id);

   /**
    * Find by name.
    *
    * @param name
    *           the name of training
    * @return the name
    */
   public Training findByName(String name);

   /**
    * Get all trainings.
    *
    * @return List<Training>
    */
   public List<Training> getTrainingList();

   /**
    * Get all trainings with close = false.
    *
    * @return List<Training>
    */
   public List<Training> getTrainingListOpen();

   /**
    * Gets the training list close with close = true.
    *
    * @param year
    *           the year
    * @return the training list close
    */
   public List<Training> getTrainingListClose(int year);

   /**
    * Get all trainings by trainingTypeId.
    *
    * @param trainingTypeId
    *           the training type id
    * @return List<Training> pertain at trainingType
    */
   public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId);
}
