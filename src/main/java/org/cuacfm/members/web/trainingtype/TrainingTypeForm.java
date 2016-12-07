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
import org.hibernate.validator.constraints.NotBlank;

/** The Class TrainingTypeForm. */
public class TrainingTypeForm {

	/** The Constant NOT_BLANK_MESSAGE. */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The required. */
	private boolean required;

	/** The name. */
	@NotBlank(message = TrainingTypeForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = TrainingTypeForm.MAX_CHARACTERS)
	private String name;

	/** The description. */
	@NotBlank(message = TrainingTypeForm.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = TrainingTypeForm.MAX_CHARACTERS)
	private String description;

	/** The place. */
	@NotBlank(message = TrainingTypeForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = TrainingTypeForm.MAX_CHARACTERS)
	private String place;

	/** The duration. */
	@NotNull
	@Min(0)
	private int duration;

	/** Instantiates a new training form. */
	public TrainingTypeForm() {
		// Default empty constructor.
	}

	/**
	 * Get the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name.
	 *
	 * @param name String, the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the close.
	 *
	 * @return the String getClose
	 */
	public boolean getRequired() {
		return required;
	}

	/**
	 * Set the close.
	 *
	 * @param required the new required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Get the description..
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description.
	 *
	 * @param description String, the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the place.
	 *
	 * @return String
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Set the place.
	 *
	 * @param place String, the new place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Creates the training Type.
	 *
	 * @return TrainingType
	 */
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
