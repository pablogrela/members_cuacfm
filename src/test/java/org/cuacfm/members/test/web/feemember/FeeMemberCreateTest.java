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
package org.cuacfm.members.test.web.feemember;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class FeeMemberListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeMemberCreateTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The pay Inscription service. */
   @Inject
   private FeeMemberService feeMemberService;

   /** The pay inscription. */
   private FeeMember feeMember;

   /**
    * Initialize default session.
    * 
    * @throws UniqueException
    */
   @Before
   public void initializeDefaultSession() throws UniqueException, UniqueListException {
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "demo", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin@udc.es");

      Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
   }

   /**
    * Display FeeMemberCreateView page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayFeeMemberCreateViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeMemberList/feeMemberCreate")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysFeeMemberCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysFeeMemberCreateTest() throws Exception {

      mockMvc
            .perform(
                  get("/feeMemberList/feeMemberCreate").locale(Locale.ENGLISH).session(
                        defaultSession))
            .andExpect(view().name("feemember/feemembercreate"))
            .andExpect(content().string(containsString("<title>Create fee member</title>")));
   }

   /**
    * Send displaysFeeMemberCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postFeeMemberCreateTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/feeMemberCreate").locale(Locale.ENGLISH)
                  .session(defaultSession).param("name", "Pay 2015").param("year", "2015")
                  .param("price", "24").param("dateLimit1", "2015-04-05")
                  .param("dateLimit2", "2015-07-05")
                  .param("description", "Pay of inscription 2015")).andExpect(
            view().name("redirect:/feeMemberList"));
   }

   /**
    * Send maxCharactersFeeMemberCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersFeeMemberCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/feeMemberList/feeMemberCreate").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("name", "111111111111111111111111111111111111111111111111111")
                        .param("year", "2015").param("price", "24")
                        .param("description", "Pay of inscription 2015"))
            .andExpect(content().string(containsString("Maximum 50 characters")))
            .andExpect(view().name("feemember/feemembercreate"));
   }

   /**
    * Send dataBlankFeeMemberCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void dataBlankFeeMemberCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/feeMemberList/feeMemberCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", " ").param("year", "2015")
                        .param("price", "24").param("description", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("feemember/feemembercreate"));
   }

   /**
    * Send dataBlankFeeMemberCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void yearAlreadyExistFeeMemberCreateTest() throws Exception {
      // Create Payment
      feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      mockMvc
            .perform(
                  post("/feeMemberList/feeMemberCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", "pay of 2015").param("year", "2015")
                        .param("dateLimit1", "2015-04-05").param("dateLimit2", "2015-07-05")
                        .param("price", "24").param("description", "pay of 2015"))
            .andExpect(content().string(containsString("Repeated year")))
            .andExpect(view().name("feemember/feemembercreate"));
   }
}