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
package org.cuacfm.members.model.feemember;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class FeeMember. */
@SuppressWarnings("serial")
@Entity
public class FeeMember implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int year;
	private Double price;
	private Date dateLimit1;
	private Date dateLimit2;
	private String description;

	/**
	 * Instantiates a new fee member.
	 */
	public FeeMember() {
		super();
	}

	/**
	 * Instantiates a new fee member.
	 *
	 * @param name the name
	 * @param year the year
	 * @param price the price
	 * @param dateLimit1 the date limit1
	 * @param dateLimit2 the date limit2
	 * @param description the description
	 */
	public FeeMember(String name, int year, Double price, Date dateLimit1, Date dateLimit2, String description) {
		super();
		this.name = name;
		this.year = year;
		this.price = price;
		this.dateLimit1 = dateLimit1;
		this.dateLimit2 = dateLimit2;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDateLimit1() {
		return dateLimit1;
	}

	public void setDateLimit1(Date dateLimit1) {
		this.dateLimit1 = dateLimit1;
	}

	public Date getDateLimit2() {
		return dateLimit2;
	}

	public void setDateLimit2(Date dateLimit2) {
		this.dateLimit2 = dateLimit2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
