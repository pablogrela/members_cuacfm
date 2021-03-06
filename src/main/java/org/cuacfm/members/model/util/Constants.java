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
package org.cuacfm.members.model.util;

/** The Class Constants. */
public class Constants {

	public static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	public static final String EMAIL_MESSAGE = "{email.message}";
	public static final String INSUFFICIENT_CHARACTERS = "{insuficient.characters}";
	public static final String MAX_CHARACTERS = "{max.characters}";

	public static final String ALL = "ALL";
	public static final String NOPAY = "NOPAY";
	public static final String PAY = "PAY";

	public static final String ERRORIDEXCEPTION = "userPayments.errorIdException";
	public static final String SUCCESSPAYPAL = "userPayments.successPayPal";
	public static final String ERRORPAYPAL = "userPayments.errorPayPal";

	public static final String NO_RESULT = "NoResult";

	public enum states {
		NO_PAY, PAY, MANAGEMENT, RETURN_BILL, CANCEL, ACCEPT, DENY
	}

	public enum methods {
		NO_PAY, PAYPAL, DIRECTDEBIT, CASH, BANK_DEPOSIT
	}

	public enum typePush {
		BOOK, DEFAULT, MEMBERS, REPORT,
	}
	
	public enum typeDestinataries {
		ALL, EMAIL, MOBILE,
	}

	public enum levels {

		DISABLE(0), CRITICAL(1), HIGH(2), MEDIUM(3), LOW(4), GOOD(5);

		private int value;

		levels(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
