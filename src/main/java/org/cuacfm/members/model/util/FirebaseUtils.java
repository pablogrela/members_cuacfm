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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The Class FileUtils.
 */
public class FirebaseUtils {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

	/**
	 * Instantiates a new firebase utils.
	 */
	FirebaseUtils() {
		super();
	}

	/**
	 * Gets the emailVerified of token.
	 *
	 * @param token the token
	 * @return the emailVerified of token
	 */
	public static String getEmailOfToken(String token) {

		if (token == null || token.isEmpty()) {
			return null;
		}

		final StringBuilder emailVerified = new StringBuilder();
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		FirebaseApp app = FirebaseApp.getInstance("members");
		FirebaseAuth.getInstance(app).verifyIdToken(token).addOnSuccessListener(decodedToken -> {
			emailVerified.append(decodedToken.getEmail());
			countDownLatch.countDown();
		}).addOnFailureListener(e -> {
			logger.error("verifyIdToken: ", e);
			countDownLatch.countDown();
		});

		try {
			countDownLatch.await(30L, TimeUnit.SECONDS);
			return emailVerified.toString();
		} catch (InterruptedException e) {
			logger.error("verifyIdToken: ", e);
			return null;
		}
	}

	/**
	 * Gets the user.
	 *
	 * @param email the email
	 * @return the user
	 */
	public static String getUser(String email) {

		if (email == null || email.isEmpty()) {
			return null;
		}

		final StringBuilder emailVerified = new StringBuilder();
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		FirebaseApp app = FirebaseApp.getInstance("members");
		FirebaseAuth.getInstance(app).getUserByEmail(email).addOnSuccessListener(userRecord -> {
			emailVerified.append(userRecord.getEmail());
			countDownLatch.countDown();
		}).addOnFailureListener(e -> {
			logger.error("getUser: ", e);
			countDownLatch.countDown();
		});

		try {
			countDownLatch.await(30L, TimeUnit.SECONDS);
			return emailVerified.toString();
		} catch (InterruptedException e) {
			logger.error("getUser: ", e);
			return null;
		}
	}
}
