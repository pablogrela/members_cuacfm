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
package org.cuacfm.members.model.feeprogram;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class FeeProgram. */
@SuppressWarnings("serial")
@Entity
public class FeeProgram implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Double price;

	@Column(unique = true)
	private Date date;
	private Date dateLimit;
	private String description;

	/**
	 * Instantiates a new pay inscription.
	 */
	public FeeProgram() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new pay inscription.
	 *
	 * @param name the name
	 * @param price the price
	 * @param date the date
	 * @param dateLimit the date limit
	 * @param description the description
	 */
	public FeeProgram(String name, Double price, Date date, Date dateLimit, String description) {
		super();
		this.name = name;
		this.price = price;
		this.date = date;
		this.dateLimit = dateLimit;
		this.description = description;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(Date dateLimit) {
		this.dateLimit = dateLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
