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
package org.cuacfm.members.web.training;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.web.support.DisplayDate;
import org.hibernate.validator.constraints.NotBlank;

/** The Class TrainingForm. */
public class TrainingForm {

	/** The name. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	/** The timeTraining. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String timeTraining;

	/** The dateTraining. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateTraining;

	/** The timeLimit. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String timeLimit;

	/** The dateLimit. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateLimit;

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

	/** The countPlaces. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Digits(fraction = 0, integer = 2)
	@Min(0)
	private int countPlaces;

	/** The maxPlaces. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Digits(fraction = 0, integer = 2)
	@Min(1)
	private int maxPlaces;

	/** The close. */
	private boolean close;

	/** Instantiates a new training form. */
	public TrainingForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getClose() {
		return close;
	}

	public void setClose(Boolean close) {
		this.close = close;
	}

	public String getTimeTraining() {
		return timeTraining;
	}

	public void setTimeTraining(String timeTraining) {
		this.timeTraining = timeTraining;
	}

	public String getDateTraining() {
		return dateTraining;
	}

	public void setDateTraining(String dateTraining) {
		this.dateTraining = dateTraining;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(String dateLimit) {
		this.dateLimit = dateLimit;
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

	public int getMaxPlaces() {
		return maxPlaces;
	}

	public void setMaxPlaces(int maxPlaces) {
		this.maxPlaces = maxPlaces;
	}

	public int getCountPlaces() {
		return countPlaces;
	}

	public void setCountPlaces(int countPlaces) {
		this.countPlaces = countPlaces;
	}

	/**
	 * Creates the training.
	 *
	 * @param trainingType the training type
	 * @return the training
	 */
	public Training createTraining(TrainingType trainingType) {
		return new Training(trainingType, getName(), DisplayDate.stringToDate(timeTraining + "," + dateTraining),
				DisplayDate.stringToDate(timeLimit + "," + dateLimit), getDescription(), getPlace(), getDuration(), getMaxPlaces());
	}

}
