package org.cuacfm.members.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.cuacfm.members.Application;

/** The Class WebMvcConfig. */
@Configuration
@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/** The Constant MESSAGE_SOURCE. */
	private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";

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

	/**
	 * RequestMappingHandlerMapping.
	 * 
	 * @param RequestMappingHandlerMapping
	 *            requestMappingHandlerMapping
	 * @return RequestMappingHandlerMapping
	 */
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super
				.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	/**
	 * Message source.
	 *
	 * @return the message source
	 */
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE);
		messageSource.setCacheSeconds(5);
		return messageSource;
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
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	/**
	 * Get Validator .
	 *
	 * @param ResourceHandlerRegistry
	 *            registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(
				RESOURCES_LOCATION);
	}

	/**
	 * Configure Default Server Handling
	 *
	 * @return DefaultServletHandlerConfigurer configurer
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Handles favicon.ico requests assuring no <code>404 Not Found</code> error
	 * is returned.
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
