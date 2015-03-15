package org.cuacfm.members.config;

import org.cuacfm.members.model.userService.UserService;
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

/** The Class SecurityConfig.*/
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
     * @param AuthenticationManagerBuilder auth of user
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(true)
            .userDetailsService(userService())
            .passwordEncoder(passwordEncoder());
    }

    /**
     * Password encoder.
     *
     * @param HttpSecurity http at aplication
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/favicon.ico", "/resources/**", "/signup").permitAll()
                //.antMatchers("/training/**").hasRole("ADMIN") 
                
                .antMatchers("/trainingList", "/trainingList/trainingView/**", "/trainingList/trainingUserList/**", 
                		"trainingList/trainingJoin/**", "trainingList/trainingJoin/",  "trainingList/trainingJoin","trainingList/trainingRemoveJoin/**").hasAnyRole("USER", "TRAINER", "ADMIN") 
                //.antMatchers("/trainingList/**").hasAnyRole("TRAINER", "ADMIN")
                .antMatchers("/trainingTypeList/**").hasAnyRole("TRAINER", "ADMIN")
                //.antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/signin")
                .permitAll()
                .failureUrl("/signin?error=1")
                .loginProcessingUrl("/authenticate")
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/signin?logout")
                .and()
            .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key("remember-me-key");
    }
}