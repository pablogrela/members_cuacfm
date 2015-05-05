package org.cuacfm.members.model.trainingtype;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.trainingtype.TrainingType;

public interface TrainingTypeRepository {
   /**
    * Save.
    *
    * @param training
    *           the trainingType
    * @return trainingType
    */
   public TrainingType save(TrainingType trainingType);

   /**
    * Update.
    *
    * @param trainingType
    *           the trainingType
    * @return trainingType
    */
   public TrainingType update(TrainingType trainingType);

   /**
    * Delete.
    *
    * @param trainingType
    *           the trainingType
    */
   public void delete(Long id) throws ExistTrainingsException;

   /**
    * Find by id.
    *
    * @param id
    *           the id of trainingType
    * @return the trainingType
    */
   public TrainingType findById(Long id);

   /**
    * Find by login.
    *
    * @param name
    *           the name of trainingType
    * @return TrainingType
    */
   public TrainingType findByName(String name);

   /**
    * Get all trainingTypes.
    *
    * @return List<TrainingType>
    */
   public List<TrainingType> getTrainingTypeList();
}
