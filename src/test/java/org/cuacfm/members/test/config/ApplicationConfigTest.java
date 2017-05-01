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
package org.cuacfm.members.test.config;

import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.Application;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

/** The Class ApplicationConfigTest. */
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({ Controller.class, Configuration.class }))
class ApplicationConfigTest {

	//private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigTest.class);
	private static final String MESSAGE_SOURCE = "/WEB-INF/i18n";
	private static final String RESOURCES_SOURCE = "/WEB-INF/classes";

	/**
	 * Property placeholder configurer.
	 *
	 * @return the property placeholder configurer
	 */
	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		final List<Resource> resourceLst = new ArrayList<>();
		resourceLst.add(new ClassPathResource("/hibernateTest.properties"));
		resourceLst.add(new ClassPathResource("/config.properties"));
		resourceLst.add(new ClassPathResource("/bankRemittance.properties"));
		resourceLst.add(new ClassPathResource("/firebaseMessaging.properties"));
		resourceLst.add(new ClassPathResource("/firebaseWeb.properties"));
		resourceLst.add(new ClassPathResource("/paypal.properties"));
		resourceLst.add(new ClassPathResource("/recaptcha.properties"));
		//initializeFirebase("configFirebase", "/members-firebase-adminsdk.json");
		ppc.setLocations(resourceLst.toArray(new Resource[] {}));
		return ppc;
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		// Sources
		String configFirebaseWeb = RESOURCES_SOURCE + "/firebaseWeb";
		String configPaypal = RESOURCES_SOURCE + "/paypal";
		String configMessages = MESSAGE_SOURCE + "/messages";

		messageSource.setBasenames(configFirebaseWeb, configPaypal, configMessages);
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

	/**
	 * Initialize firebase.
	 *
	 * @param pathConfig the path config
	 * @param atribute the atribute
	 * @param pathdefault the pathdefault
	 * @return the firebase auth
	 */
	//	private static void initializeFirebase(String atribute, String pathdefault) {
	//		try {
	//			InputStream serviceAccount = FileUtils.getFile(pathdefault);
	//
	//			// Initialize Firebase
	//			FirebaseOptions options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).build();
	//			FirebaseApp.initializeApp(options, "members");
	//			serviceAccount.close();
	//		} catch (Exception e) {
	//			logger.error("initializeFirebase ", e);
	//		}
	//	}
}