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


import org.cuacfm.members.model.userservice.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/** The Class SecurityConfig. */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /**
    * Instantiates a new security config.
    */
   public SecurityConfig() {
      // Default empty constructor.
   }

   // No sirve para nada, ni el @EnableGlobalMethodSecurity(securedEnabled = true)
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
	   // csrf().ignoringAntMatchers() ignora la validacion de csrf para las peticiones de la api
	   // csrf().disable() deshabilita la validacion crsf para todo
       http.csrf().ignoringAntMatchers("/api/**").and().
       authorizeRequests().antMatchers("/", "/#", "/favicon.ico", "/resources/**", "/signup", "/signup",  "/logout", "/logout/**", "/signin/**", "/api/**")
            .permitAll()

            .antMatchers("/trainingUserList")
            .hasAnyRole("USER", "EXUSER", "PREREGISTERED")
            
            .antMatchers("/trainingUserList/**")
            .hasAnyRole("USER", "PREREGISTERED")
            
            .antMatchers("/userPayments/**")
            .hasAnyRole("USER", "EXUSER", "PREREGISTERED")

            .antMatchers("/programList", "/programList/", "/programList/programEdit/**", "/reportList", "/reportList/image/**", "/bookList")
            .hasAnyRole("ADMIN", "USER", "EXUSER")
            
            .antMatchers("/reportList/**")
            .hasAnyRole("REPORT")
            
            .antMatchers("/bookList/**")
            .hasAnyRole("BOOK")
            
            .antMatchers("/reportUserList/**", "/bookUserList/**")
            .hasAnyRole("USER")
            
            .antMatchers("/programList/**")
            .hasAnyRole("ADMIN", "USER")
            
            .antMatchers("/trainingList", "/trainingList/trainingView/**")
            .hasAnyRole("ADMIN", "USER", "EXUSER", "PREREGISTERED")

            .antMatchers("/trainingTypeList/**", "/trainingList/**")
            .hasAnyRole("TRAINER")

            .antMatchers("/programList/programDown/**" ,"/programList/programUp/**",
                  "/payInscriptionList/**", "/feeProgramList/**", "/accountList/**", "/configuration/**", "/bankRemittance/**", "/directDebit/**", "/user/**")
            .hasRole("ADMIN")

            .anyRequest().authenticated().and().formLogin().loginPage("/signin").permitAll()
            .failureUrl("/signin?error=1").loginProcessingUrl("/authenticate").and().logout()
            .logoutUrl("/logout").permitAll().logoutSuccessUrl("/signin?logout").and().rememberMe()
            .rememberMeServices(rememberMeServices()).key("remember-me-key");
}
}