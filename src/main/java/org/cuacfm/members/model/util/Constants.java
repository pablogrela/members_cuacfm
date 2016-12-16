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
package org.cuacfm.members.model.util;

/** The Class Constants. */
public class Constants {

	public static final String NOPAY = "NOPAY";
	public static final String PAY = "PAY";
	public static final String ERRORIDEXCEPTION = "userPayments.errorIdException";
	public static final String SUCCESSPAYPAL = "userPayments.successPayPal";
	public static final String ERRORPAYPAL = "userPayments.errorPayPal";
	
	/** Instantiates a new Constants. */
	protected Constants() {
		// Default empty constructor.
	}

	/**
	 * The Enum states.
	 */
	public enum states {

		/** The no pay. */
		NO_PAY,

		/** The pay. */
		PAY,

		/** The management. */
		MANAGEMENT,

		/** The return bill. */
		RETURN_BILL,
		
		/** Cancel. */
		CANCEL
	}

	/**
	 * The Enum method.
	 */
	public enum methods {

		/** The no pay. */
		NO_PAY,

		/** The paypal. */
		PAYPAL,

		/** The directdebit. */
		DIRECTDEBIT,

		/** The cash. */
		CASH,
		
		/** The bank deposit. */
		BANK_DEPOSIT

	}
}
