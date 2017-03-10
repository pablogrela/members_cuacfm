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

/** The Class UserAlreadyJoinedException. */
@SuppressWarnings("serial")
public class UserAlreadyJoinedException extends Exception {

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new UserAlreadyJoined exception.
	 *
	 * @param name the name
	 */
	public UserAlreadyJoinedException(String name) {
		super("User " + name + ", already inscription in this formation ");
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
