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
package org.cuacfm.members.web.feemember;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

/** The Class FeeMemberForm. */
public class FeeMemberForm {

	/** The name. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	/** The year. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Min(2015)
	private Integer year;

	/** The price. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Digits(fraction = 2, integer = 5)
	@DecimalMin("0.00")
	private Double price;

	/** The dateLimit1. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateLimit1;

	/** The dateLimit2. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateLimit2;

	/** The description. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	/** Instantiates a new training form. */
	public FeeMemberForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDateLimit1() {
		return dateLimit1;
	}

	public void setDateLimit1(String dateLimit1) {
		this.dateLimit1 = dateLimit1;
	}

	public String getDateLimit2() {
		return dateLimit2;
	}

	public void setDateLimit2(String dateLimit2) {
		this.dateLimit2 = dateLimit2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Creates the training Type.
	 *
	 * @return FeeMember
	 */
	public FeeMember createFeeMember() {
		return new FeeMember(getName(), getYear(), getPrice(), DateUtils.format(dateLimit1, DateUtils.FORMAT_MONTH_YEAR),
				DateUtils.format(dateLimit2, DateUtils.FORMAT_MONTH_YEAR), getDescription());
	}

	/**
	 * Creates the fee member.
	 *
	 * @param feeMember the fee member
	 * @return the fee member
	 */
	public FeeMember updateFeeMember(FeeMember feeMember) {
		feeMember.setName(getName());
		feeMember.setYear(getYear());
		feeMember.setPrice(getPrice());
		feeMember.setDescription(getDescription());
		feeMember.setDateLimit1(DateUtils.format(getDateLimit1(), DateUtils.FORMAT_MONTH_YEAR));
		feeMember.setDateLimit2(DateUtils.format(getDateLimit2(), DateUtils.FORMAT_MONTH_YEAR));
		return feeMember;
	}
}
