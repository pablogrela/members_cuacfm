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
package org.cuacfm.members.config;

import org.cuacfm.members.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/** The Class WebMvcConfig. */
@Configuration
@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebMvcConfig extends WebMvcConfigurationSupport {

	private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Autowired
	private MessageSource messageSource;

	/** The Constant VIEWS. */
	private static final String VIEWS = "/WEB-INF/views/";

	/** The Constant RESOURCES_HANDLER. */
	private static final String RESOURCES_LOCATION = "/resources/";

	/** The Constant RESOURCES_LOCATION. */
	private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

	/** Instantiates a new web mvc config. */
	public WebMvcConfig() {
		// Default empty constructor.
	}

	// Bean name must be "multipartResolver", by default Spring uses method name as bean name.
	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * Open entity manager in view filter.
	 *
	 * @return the open entity manager in view filter
	 */
	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

	// Formateo de las fechas
	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateFormatter dateFormatter = new DateFormatter("HH:mm dd/MM/yyyy");
		registry.addFormatter(dateFormatter);
	}

	/**
	 * RequestMappingHandlerMapping.
	 *
	 * @return RequestMappingHandlerMapping
	 */
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	@Bean(name = "properties")
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		try {
			ppc.setProperties(ApplicationConfig.getProperties());
		} catch (Exception e) {
			logger.error("PropertyPlaceholderConfigurer ", e);
		}
		return ppc;
	}

	/**
	 * Template resolver.
	 *
	 * @return the template resolver
	 */
	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix(VIEWS);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * Template engine.
	 *
	 * @return the spring template engine
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}

	/**
	 * View resolver.
	 *
	 * @return the thymeleaf view resolver
	 */
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		return thymeleafViewResolver;
	}

	/**
	 * Get Validator .
	 *
	 * @return validator
	 */
	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource);
		return validator;
	}

	/**
	 * Get Validator .
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
	}

	/**
	 * Configure Default Server Handling.
	 *
	 * @param configurer the configurer
	 * @return DefaultServletHandlerConfigurer configurer
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
	 */
	@Controller
	static class FaviconController {
		/** Instantiates a new favicon controller. */

		FaviconController() {
			// Default empty constructor.
		}

		/**
		 * Favicon.
		 *
		 * @return the string
		 */
		@RequestMapping("favicon.ico")
		String favicon() {
			return "forward:/resources/images/favicon.ico";
		}
	}
}
