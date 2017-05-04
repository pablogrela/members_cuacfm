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
package org.cuacfm.members.test.web.configuration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class AccountTypeCreateControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccountTypeCreateControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The account Type service. */
   @Autowired
   private AccountTypeService accountTypeService;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException, UniqueListException {
      Account admin = new Account("admin", "", "55555555C", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin@udc.es");
   }

   /**
    * Display accountTypeList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayaccountCreateCreatePageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/configuration/accountTypeCreate")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysaccountTypeList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysAccountTypeCreateTest() throws Exception {
      mockMvc
            .perform(
                  get("/configuration/accountTypeCreate").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("configuration/accountypecreate"))
            .andExpect(content().string(containsString("<title>Create account type</title>")));
   }

   /**
    * Send displaysaccountTypeList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postAccountTypeCreateTest() throws Exception {
      mockMvc.perform(
            post("/configuration/accountTypeCreate").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "Adult").param("description", "Tax for adult")
                  .param("discount", "0")).andExpect(view().name("redirect:/configuration"));
   }

   /**
    * Send displaysaccountTypeList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void nameAlreadyExistTest() throws Exception {
      AccountType accountType = new AccountType("Adult", false, "Tax for Adult", 0);
      accountTypeService.save(accountType);

      mockMvc
            .perform(
                  post("/configuration/accountTypeCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", "Adult")
                        .param("description", "Tax for adult").param("discount", "0"))
            .andExpect(
                  content().string(
                        containsString("Already exist account type with name "
                              + accountType.getName() + ", please choose another")))
            .andExpect(view().name("configuration/accountypecreate"));

   }

   /**
    * notBlankMessage.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void notBlankMessageTest() throws Exception {
      mockMvc
            .perform(
                  post("/configuration/accountTypeCreate").locale(Locale.ENGLISH).session(
                        defaultSession))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("configuration/accountypecreate"));
   }

   /**
    * "Already exist type of formation with name "+ accountType.getName() +
    * ", please chose other" Send displaysaccountTypeList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersTest() throws Exception {
      mockMvc
            .perform(
                  post("/configuration/accountTypeCreate").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("name", "1111111111111111111111111111111111111111111111111111111111111111111111111111111")
                        .param("description", "111111111111111111111111111111111111111111111111111111111111111111111111")
                        .param("discount", "0"))
            .andExpect(content().string(containsString("Maximum 50 characters")))
            .andExpect(view().name("configuration/accountypecreate"));

   }
}