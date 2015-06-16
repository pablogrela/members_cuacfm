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
package org.cuacfm.members.model.trainingtypeservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingtype.TrainingType;

/** The Class TrainingTypeService. */
public interface TrainingTypeService {

   /**
    * Save an training into database.
    *
    * @param trainingType
    *           the training
    * @return TrainingType
    * @throws UniqueException
    */
   public TrainingType save(TrainingType trainingType) throws UniqueException;

   /**
    * Update TrainingType
    *
    * @param trainingType
    *           the trainingType
    * @return TrainingType
    * @throws UniqueException
    */
   public TrainingType update(TrainingType trainingType) throws UniqueException;

   /**
    * Delete.
    *
    * @param trainingType
    *           the trainingType
    * @return TrainingType
    * @throws ExistTrainingsException
    */
   public void delete(Long id) throws ExistTrainingsException;

   /**
    * Find by Name the trainingType
    *
    * @param id
    *           the id
    * @return TrainingType
    */
   public TrainingType findByName(String name);

   /**
    * Find by id returns trainingType which has this identifier.
    *
    * @param id
    *           the id
    * @return trainingType
    */
   public TrainingType findById(Long id);

   /**
    * Get all trainingTypes.
    *
    * @return List<TrainingType>
    */
   public List<TrainingType> getTrainingTypeList();

   /**
    * Get all trainings by trainingTypeId.
    *
    * @return List<Training>
    */
   public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId);
}
