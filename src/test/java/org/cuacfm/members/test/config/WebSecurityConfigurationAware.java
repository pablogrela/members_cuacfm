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

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public abstract class WebSecurityConfigurationAware extends WebAppConfigurationAware {
    /** The spring security filter chain. */
    @Inject
    private FilterChainProxy springSecurityFilterChain;

    /** The user details service. */
    @Autowired
    @Qualifier("userService")
    protected UserDetailsService userDetailsService;

    /** Inizialize mockMVC */
    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.wac).addFilters(
                this.springSecurityFilterChain).build();
    }

    /**
     * Gets the principal.
     *
     * @param username
     *            the username
     * @return the principal
     */
    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {

        UserDetails user = this.userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());

        return authentication;
    }

    /**
     * Gets the default session.
     *
     * @param nickName
     *            the nick name
     * @return the default session
     */
    protected MockHttpSession getDefaultSession(String nickName) {
        UsernamePasswordAuthenticationToken principal = this
                .getPrincipal(nickName);
        SecurityContextHolder.getContext().setAuthentication(principal);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        return session;
    }
}
