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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.internal.NonNull;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.OnSuccessListener;

/**
 * The Class FileUtils.
 */
public class FirebaseUtils {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

	public static String getEmailOfToken(String token) {
		
		if (token == null || token.isEmpty()) {
			return null;
		}

		final StringBuilder email = new StringBuilder();
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		FirebaseApp app = FirebaseApp.getInstance("members");
		FirebaseAuth.getInstance(app).verifyIdToken(token).addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
			@Override
			public void onSuccess(FirebaseToken decodedToken) {
				email.append(decodedToken.getEmail());
				countDownLatch.countDown();
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				logger.error("verifyIdToken: ", e);
				countDownLatch.countDown();
			}
		});

		try {
			countDownLatch.await(30L, TimeUnit.SECONDS);
			return email.toString();
		} catch (InterruptedException e) {
			logger.error("verifyIdToken: ", e);
			return null;
		}
	}

}
