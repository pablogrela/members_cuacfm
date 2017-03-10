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
package org.cuacfm.members.model.exceptions;

import java.util.Date;

import org.cuacfm.members.web.support.DisplayDate;

/** The Class DateLimitException. */
@SuppressWarnings("serial")
public class DateLimitException extends Exception {

	/** The date limit. */
	private final Date dateLimit;

	/** The date training. */
	private final Date dateTraining;

	/**
	 * Instantiates a new date limit exception.
	 *
	 * @param dateLimit the date limit
	 * @param dateTraining the date training
	 */
	public DateLimitException(Date dateLimit, Date dateTraining) {
		super("The date limit " + DisplayDate.dateTimeToString(dateLimit) + " should be after or equals to date "
				+ DisplayDate.dateTimeToString(dateTraining));

		this.dateLimit = dateLimit;
		this.dateTraining = dateTraining;
	}

	/**
	 * Gets the date limit.
	 *
	 * @return the date limit
	 */
	public Date getDateLimit() {
		return dateLimit;
	}

	/**
	 * Gets the date training.
	 *
	 * @return the date training
	 */
	public Date getDateTraining() {
		return dateTraining;
	}

}
