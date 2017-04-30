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

/** The Class MaximumCapacityException. */
@SuppressWarnings("serial")
public class MaximumCapacityException extends Exception {

	/** The max places. */
	private final int maxPlaces;

	/**
	 * Instantiates a new maximum capacity exception.
	 *
	 * @param maxPlaces the max places
	 */
	public MaximumCapacityException(int maxPlaces) {
		super("The capacity of the training is " + maxPlaces + " and already full.");

		this.maxPlaces = maxPlaces;
	}

	/**
	 * Gets the max places.
	 *
	 * @return the max places
	 */
	public int getMaxPlaces() {
		return maxPlaces;
	}

}
