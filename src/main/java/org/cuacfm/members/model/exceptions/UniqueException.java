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

/** The Class ExistTransactionIdException. */
@SuppressWarnings("serial")
public class UniqueException extends Exception {

	/** The attribute. */
	private final String attribute;

	/** The attribute. */
	private final String value;

	/**
	 * Instantiates a new exist transaction id exception.
	 *
	 * @param attribute the id txn
	 * @param value the value
	 */
	public UniqueException(String attribute, String value) {
		super("It already exist arribute " + attribute + " with value: " + value);
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
