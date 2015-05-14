package org.cuacfm.members.test.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import org.cuacfm.members.Application;


/** The Class ApplicationConfigTest.*/
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfigTest {
	
	/**
	 * Property placeholder configurer.
	 *
	 * @return the property placeholder configurer
	 */
	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("/persistenceTest.properties"));
		return ppc;
	}
	
}