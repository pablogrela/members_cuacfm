/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.trainingtype;

import java.util.List;

/**
 * The Interface TrainingTypeRepository.
 */
public interface TrainingTypeRepository {

	/**
	 * Save.
	 *
	 * @param trainingType the training type
	 * @return trainingType
	 */
	public TrainingType save(TrainingType trainingType);

	/**
	 * Update.
	 *
	 * @param trainingType the trainingType
	 * @return trainingType
	 */
	public TrainingType update(TrainingType trainingType);

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);

	/**
	 * Find by id.
	 *
	 * @param id the id of trainingType
	 * @return the trainingType
	 */
	public TrainingType findById(Long id);

	/**
	 * Find by login.
	 *
	 * @param name the name of trainingType
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
