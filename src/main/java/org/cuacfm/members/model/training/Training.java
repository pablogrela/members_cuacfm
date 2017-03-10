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
package org.cuacfm.members.model.training;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cuacfm.members.model.trainingtype.TrainingType;

/** The Class Training. */
@Entity
public class Training implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "trainingTypeId")
	private TrainingType trainingType;

	@Column(unique = true)
	private String name;
	private Date dateLimit;
	private Date dateTraining;
	private String description;
	private String place;
	private int duration;
	private int maxPlaces;
	private int countPlaces;
	private boolean close;

	/** Instantiates a new training. */
	protected Training() {
		super();
	}

	/**
	 * Instantiates a new training.
	 *
	 * @param trainingType TrainingType
	 * @param name String
	 * @param dateTraining the date training
	 * @param dateLimit the date limit
	 * @param description String
	 * @param place String
	 * @param duration int
	 * @param maxPlaces int
	 */
	public Training(TrainingType trainingType, String name, Date dateTraining, Date dateLimit, String description, String place, int duration,
			int maxPlaces) {
		super();
		this.trainingType = trainingType;
		this.name = name;
		this.dateTraining = dateTraining;
		this.dateLimit = dateLimit;
		this.description = description;
		this.place = place;
		this.duration = duration;
		this.maxPlaces = maxPlaces;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public Date getDateTraining() {
		return dateTraining;
	}

	public void setDateTraining(Date dateTraining) {
		this.dateTraining = dateTraining;
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

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(Date dateLimit) {
		this.dateLimit = dateLimit;
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

	@Override
	public String toString() {
		return "Training [id=" + id + ", trainingType=" + trainingType + ", name=" + name + ", dateLimit=" + dateLimit + ", dateTraining="
				+ dateTraining + ", description=" + description + ", place=" + place + ", duration=" + duration + ", maxPlaces=" + maxPlaces
				+ ", countPlaces=" + countPlaces + ", close=" + close + "]";
	}

}
