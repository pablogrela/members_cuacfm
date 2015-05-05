package org.cuacfm.members.config;

import org.cuacfm.members.Application;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

/**
 * The Class ApplicationConfig.
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({ Controller.class,
      Configuration.class }))
class ApplicationConfig {
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
   @Bean
   public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
      ppc.setLocation(new ClassPathResource("/persistence.properties"));
      return ppc;
   }

}