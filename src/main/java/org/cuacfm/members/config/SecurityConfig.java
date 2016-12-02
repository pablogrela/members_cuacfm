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


import org.cuacfm.members.model.userservice.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/** The Class SecurityConfig. */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /**
    * Instantiates a new security config.
    */
   public SecurityConfig() {
      // Default empty constructor.
   }

   // No sirve para nada, ni el @EnableGlobalMethodSecurity(securedEnabled =
   // true)
   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   /**
    * User service.
    *
    * @return the userService
    */
   @Bean
   public UserService userService() {
      return new UserService();
   }

   /**
    * Remember me services.
    *
    * @return the token based remember me services
    */
   @Bean
   public TokenBasedRememberMeServices rememberMeServices() {
      return new TokenBasedRememberMeServices("remember-me-key", userService());
   }

   /**
    * Password encoder.
    *
    * @return the password encoder
    */
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new StandardPasswordEncoder();
   }

   /**
    * Password encoder.
    *
    * @param AuthenticationManagerBuilder
    *           auth of user
    */
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.eraseCredentials(true).userDetailsService(userService())
            .passwordEncoder(passwordEncoder());
   }

   /**
    * Password encoder.
    *
    * @param HttpSecurity
    *           http at application
    */
   // Por defecto el csrf esta enable para bloquear posibles ataques.
   @Override
   protected void configure(HttpSecurity http) throws Exception {
	   // Se añadio csrf().disable() al http para permitir ajax
       http.csrf().disable().authorizeRequests().antMatchers("/", "/favicon.ico", "/resources/**", "/signup")
            .permitAll()

            .antMatchers("/userPayments/**")
            .hasAnyRole("TRAINER", "USER", "PREREGISTERED")

            .antMatchers("/programList/**")
            .hasAnyRole("ADMIN", "USER", "TRAINER")

            .antMatchers("/trainingUserList")
            .hasAnyRole("USER", "PREREGISTERED")

            .antMatchers("/trainingList", "/trainingList/trainingView/**")
            .hasAnyRole("ADMIN", "TRAINER", "USER", "PREREGISTERED")

            .antMatchers("/trainingTypeList/**","/trainingList/**")
            .hasAnyRole("ADMIN", "TRAINER")

            .antMatchers("/programList/programDown/**","/programList/programUp/**",
                  "/payInscriptionList/**", "/feeProgramList/**", "/accountList/**", "/configuration/**", "/bankRemittance/**", "/user/**")
            .hasRole("ADMIN")
            
            // .antMatchers("/**").hasRole("ADMIN")
            .anyRequest().authenticated().and().formLogin().loginPage("/signin").permitAll()
            .failureUrl("/signin?error=1").loginProcessingUrl("/authenticate").and().logout()
            .logoutUrl("/logout").permitAll().logoutSuccessUrl("/signin?logout").and().rememberMe()
            .rememberMeServices(rememberMeServices()).key("remember-me-key");
}
}