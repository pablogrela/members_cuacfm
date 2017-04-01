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
package org.cuacfm.members.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.cuacfm.members.Application;
import org.cuacfm.members.model.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * The Class ApplicationConfig.
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({ Controller.class, Configuration.class }))
public class ApplicationConfig {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	private static final String MESSAGE_SOURCE = "/WEB-INF/i18n";
	private static final String RESOURCES_SOURCE = "/WEB-INF/classes";
	private static Properties properties;

	/**
	 * Instantiates a new application config.
	 */
	protected ApplicationConfig() {
		// Default empty constructor.
	}

	/**
	 * Property placeholder configurer.
	 *
	 * @return the property placeholder configurer
	 */
	@Bean(name = "properties")
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		try {

			properties = new Properties();
			properties.load(FileUtils.getFile("/config.properties"));
			String pathConfig = properties.getProperty("pathConfig");

			// If exist another config.properties, choose the outside
			addResource(pathConfig, "configFile", "/config.properties");

			// Add Resources properties
			addResource(pathConfig, "configBankRemittance", "/bankRemittance.properties");
			addResource(pathConfig, "configFirebaseWeb", "/firebaseWeb.properties");
			addResource(pathConfig, "configHibernate", "/hibernate.properties");
			addResource(pathConfig, "configPaypal", "/paypal.properties");
			addResource(pathConfig, "configReCaptcha", "/recaptcha.properties");

			// Change logs, log4j for hibernate and logback for the rest
			changeLog4j(pathConfig, "configLog4j");
			changeLogBack(pathConfig, "configLogBack");

			// Inicialice firebase
			initializeFirebase(pathConfig, "configFirebase", "/members-firebase-adminsdk.json");

			ppc.setProperties(properties);
		} catch (Exception e) {
			logger.error("PropertyPlaceholderConfigurer ", e);
		}
		return ppc;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		// Sources
		String configFirebaseWeb = returnPath("configFirebaseWeb", RESOURCES_SOURCE + "/firebaseWeb.properties");
		String configPaypal = returnPath("configPaypal", RESOURCES_SOURCE + "/paypal.properties");
		String configMessages = returnPath("configMessages", MESSAGE_SOURCE + "/messages.properties");

		messageSource.setBasenames(configFirebaseWeb, configPaypal, configMessages);
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

	/**
	 * Multipart resolver.
	 *
	 * @return the commons multipart resolver
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(50000000);
		return commonsMultipartResolver;
	}

	/**
	 * Adds the resource.
	 *
	 * @param pathConfig the path config
	 * @param atribute the atribute
	 * @param pathdefault the pathdefault
	 */
	private static void addResource(String pathConfig, String atribute, String pathdefault) {
		try {
			File config = new File(pathConfig + properties.getProperty(atribute));
			if (config.exists()) {
				properties.load(new FileInputStream(config));
			} else {
				properties.load(FileUtils.getFile(pathdefault));
			}
		} catch (Exception e) {
			logger.error("addResource " + pathConfig, e);
		}
	}

	/**
	 * Change log4j.
	 *
	 * @param pathConfig the path config
	 * @param atribute the atribute
	 * @param pathdefault the pathdefault
	 */
	private static void changeLog4j(String pathConfig, String atribute) {
		Properties propertiesLog = new Properties();
		try {
			File config = new File(pathConfig + properties.getProperty(atribute));
			if (config.exists()) {
				propertiesLog.load(new FileInputStream(config));
				PropertyConfigurator.configure(propertiesLog);
			}
		} catch (Exception e) {
			logger.error("changeLog4j " + pathConfig, e);
		}
	}

	/**
	 * Change log back.
	 *
	 * @param pathConfig the path config
	 * @param atribute the atribute
	 */
	private static void changeLogBack(String pathConfig, String atribute) {
		// Assume SLF4J is bound to logback in the current environment
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {
			File config = new File(pathConfig + properties.getProperty(atribute));
			if (config.exists()) {
				JoranConfigurator configurator = new JoranConfigurator();
				configurator.setContext(context);
				// Call context.reset() to clear any previous configuration, e.g. default
				context.reset();
				configurator.doConfigure(config);
			}
		} catch (Exception e) {
			logger.error("changeLogBack " + atribute, e);
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
	}

	/**
	 * Return path.
	 *
	 * @param atribute the atribute
	 * @param pathdefault the pathdefault
	 * @return the string
	 */
	private static String returnPath(String atribute, String pathdefault) {
		String pathFinal = "";
		try {
			File config = new File(properties.getProperty("pathConfig") + properties.getProperty(atribute));
			if (config.exists()) {
				pathFinal = "file:" + config;
			} else {
				pathFinal = pathdefault + pathdefault;
			}
		} catch (Exception e) {
			logger.error("returnPath " + atribute, e);
		}
		return pathFinal.split(".properties")[0];
	}

	/**
	 * Initialize firebase.
	 *
	 * @param pathConfig the path config
	 * @param atribute the atribute
	 * @param pathdefault the pathdefault
	 * @return the firebase auth
	 */
	private static void initializeFirebase(String pathConfig, String atribute, String pathdefault) {
		try {
			File config = new File(pathConfig + properties.getProperty(atribute));
			InputStream serviceAccount;
			if (config.exists()) {
				serviceAccount = new FileInputStream(config);
				logger.info("initializeFirebase outside" + config);
			} else {
				serviceAccount = FileUtils.getFile(pathdefault);
				logger.info("initializeFirebase inside" + pathdefault);
			}
			// Initialize Firebase
			FirebaseOptions options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).build();
			FirebaseApp.initializeApp(options, "members");
			serviceAccount.close();
		} catch (Exception e) {
			logger.error("initializeFirebase ", e);
		}
	}
}