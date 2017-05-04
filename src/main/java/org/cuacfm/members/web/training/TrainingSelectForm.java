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
package org.cuacfm.members.web.training;

import java.util.List;

import org.cuacfm.members.model.trainingtype.TrainingType;

/** The TrainingSelectForm. */
public class TrainingSelectForm {

	private List<TrainingType> trainingTypes;
	private Long trainingTypeId;

	public List<TrainingType> getTrainingTypes() {
		return trainingTypes;
	}

	public void setTrainingTypes(List<TrainingType> trainingTypes) {
		this.trainingTypes = trainingTypes;
	}

	public Long getTrainingTypeId() {
		return trainingTypeId;
	}

	public void setTrainingTypeId(Long trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
}
