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
package org.cuacfm.members.web.trainingtype;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.util.Constants;
import org.hibernate.validator.constraints.NotBlank;

/** The Class TrainingTypeForm. */
public class TrainingTypeForm {

	/** The required. */
	private boolean required;

	/** The name. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	/** The description. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	/** The place. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String place;

	/** The duration. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Min(0)
	private int duration;

	/** Instantiates a new training form. */
	public TrainingTypeForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public TrainingType createTrainingType() {
		return new TrainingType(getName(), getRequired(), getDescription(), getPlace(), getDuration());
	}

	/**
	 * Update training type.
	 *
	 * @param trainingType the training type
	 * @return the training type
	 */
	public TrainingType updateTrainingType(TrainingType trainingType) {
		trainingType.setName(getName());
		trainingType.setRequired(getRequired());
		trainingType.setDescription(getDescription());
		trainingType.setPlace(getPlace());
		trainingType.setDuration(getDuration());
		return trainingType;
	}
}
