package org.cuacfm.members.model.training;

import java.util.List;

import org.cuacfm.members.model.training.Training;

/** TrainingRepository Class */
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
    * @param training
    *           the training
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
    * Get all trainings with close = true.
    *
    * @return List<Training>
    */
   public List<Training> getTrainingListClose();

   /**
    * Get all trainings by trainingTypeId.
    *
    * @return List<Training> pertain at trainingType
    */
   public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId);
}
