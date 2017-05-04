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
package org.cuacfm.members.web.feeprogram;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.DateUtils;
import org.hibernate.validator.constraints.NotBlank;

/** The Class FeeProgramForm. */
public class FeeProgramForm {

	/** The name. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	/** The price. */
	@NotNull(message = Constants.NOT_BLANK_MESSAGE)
	@Digits(fraction = 2, integer = 5)
	@DecimalMin("0.00")
	private Double price;

	/** The date. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String date;

	/** The dateLimit. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	private String dateLimit;

	/** The description. */
	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	/** Instantiates a new training form. */
	public FeeProgramForm() {
		super();
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	/**
	 * Creates the training Type.
	 *
	 * @return FeeProgram
	 */
	public FeeProgram createFeeProgram() {
		return new FeeProgram(getName(), getPrice(), DateUtils.format(getDate(), DateUtils.FORMAT_MONTH_YEAR),
				DateUtils.format(getDateLimit(), DateUtils.FORMAT_MONTH_YEAR), getDescription());
	}

	/**
	 * Update fee program.
	 *
	 * @param feeProgram the fee program
	 * @return the program
	 */
	public FeeProgram updateFeeProgram(FeeProgram feeProgram) {
		feeProgram.setName(getName());
		feeProgram.setPrice(getPrice());
		feeProgram.setDate(DateUtils.format(getDate(), DateUtils.FORMAT_MONTH_YEAR));
		feeProgram.setDateLimit(DateUtils.format(getDateLimit(), DateUtils.FORMAT_MONTH_YEAR));
		feeProgram.setDescription(getDescription());
		return feeProgram;
	}
}
