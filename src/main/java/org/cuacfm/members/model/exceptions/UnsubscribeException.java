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

/** The Class UnsubscribeException. */
@SuppressWarnings("serial")
public class UnsubscribeException extends Exception {

	/** The name training. */
	private final String nameTraining;

	/**
	 * Instantiates a new unsubscribe exception.
	 *
	 * @param nameTraining the name training
	 */
	public UnsubscribeException(String nameTraining) {
		super("You have already unsubscribed from this formation " + nameTraining);
		this.nameTraining = nameTraining;
	}

	/**
	 * Gets the name training.
	 *
	 * @return the name training
	 */
	public String getNameTraining() {
		return nameTraining;
	}

}
