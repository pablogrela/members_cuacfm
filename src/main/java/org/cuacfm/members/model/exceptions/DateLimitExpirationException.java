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
package org.cuacfm.members.model.exceptions;

import java.util.Date;

import org.cuacfm.members.model.util.DateUtils;

/** The Class DateLimitExpirationException. */
@SuppressWarnings("serial")
public class DateLimitExpirationException extends Exception {

	/** The date limit. */
	private final Date dateLimit;

	/** The training name. */
	private final String trainingName;

	/**
	 * Instantiates a new date limit expiration exception.
	 *
	 * @param trainingName the training name
	 * @param dateLimit the date limit
	 */
	public DateLimitExpirationException(String trainingName, Date dateLimit) {
		super("The training " + trainingName + " expired at " + DateUtils.format(dateLimit, DateUtils.FORMAT_LOCAL));
		this.trainingName = trainingName;
		this.dateLimit = dateLimit;
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
	 * Gets the training name.
	 *
	 * @return the training name
	 */
	public String getTrainingName() {
		return trainingName;
	}
}
