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

/** The Class ExistTransactionIdException. */
@SuppressWarnings("serial")
public class ExistTransactionIdException extends Exception {

	/** The id txn. */
	private final String idTxn;

	/**
	 * Instantiates a new exist transaction id exception.
	 *
	 * @param idTxn the id txn
	 */
	public ExistTransactionIdException(String idTxn) {
		super("It already Exist Transaction Id: " + idTxn);
		this.idTxn = idTxn;
	}

	/**
	 * Gets the id txn.
	 *
	 * @return the id txn
	 */
	public String getIdTxn() {
		return idTxn;
	}

}
