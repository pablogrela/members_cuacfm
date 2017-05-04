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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** The Class PushService. */
@Service
public class PushService {

	private static final Logger logger = LoggerFactory.getLogger(PushService.class);
	private static String urlApiFCM;
	private static String authenticationKeyFCM;

	/**
	 * Instantiates a new push service.
	 */
	public PushService() {
		super();
	}

	@Value("${firebase.fcm.api.url}")
	public void setUrlApiFCM(String value) {
		urlApiFCM = value;
	}

	@Value("${firebase.fcm.auth.key}")
	public void setAuthenticationKeyFCM(String value) {
		authenticationKeyFCM = value;
	}

	/**
	 * Send push notification to device.
	 *
	 * @param devicesToken the devices token
	 * @param title the title
	 * @param message the message
	 * @return true, if successful
	 */
	public static boolean sendPushNotificationToDevice(List<String> devicesToken, String title, String message) {

		if (devicesToken == null || devicesToken.isEmpty()) {
			return false;
		}

		try {
			URL url = new URL(urlApiFCM);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authenticationKeyFCM);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject jsonSend = new JSONObject();

			// Documentation of FCM, 1 element must go with "to"
			if (devicesToken.size() == 1) {
				jsonSend.put("to", devicesToken.get(0));
			} else {
				jsonSend.put("registration_ids", devicesToken);
			}

			JSONObject info = new JSONObject();
			// Notification title

			info.put("title", title);

			// Notification body
			info.put("body", message);

			jsonSend.put("notification", info);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(jsonSend.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String output;
			while ((output = br.readLine()) != null) {
				logger.info("Send push" + output);
			}
			return true;

		} catch (Exception e) {
			logger.error("sendPushNotificationToDevice", e);
			return false;
		}

	}
}
