package org.cuacfm.members.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

/** The Class WebAppInitializer. */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   /** Instantiates a new webb app initializer. */
   public WebAppInitializer() {
      // Default empty constructor.
   }

   /**
    * getServletMappings.
    * 
    * @return String[]
    */
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }

   /**
    * getRootConfigClasses.
    * 
    * @return Class<?>[]
    */
   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class<?>[] { ApplicationConfig.class, JpaConfig.class, SecurityConfig.class };
   }

   /**
    * getServletConfigClasses.
    * 
    * @return Class<?>[]
    */
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class<?>[] { WebMvcConfig.class };
   }

   /**
    * getServletFilters.
    * 
    * @return Filter[]
    */
   @Override
   protected Filter[] getServletFilters() {
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      characterEncodingFilter.setForceEncoding(true);

      DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy(
            "springSecurityFilterChain");

      return new Filter[] { characterEncodingFilter, securityFilterChain };
   }

   /**
    * ustomizeRegistration.
    * 
    * @param ServletRegistration
    *           .Dynamic registration
    */
   @Override
   protected void customizeRegistration(ServletRegistration.Dynamic registration) {
      registration.setInitParameter("defaultHtmlEscape", "true");
      registration.setInitParameter("spring.profiles.active", "default");
   }
}