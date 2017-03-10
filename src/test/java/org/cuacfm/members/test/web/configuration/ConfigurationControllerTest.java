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
package org.cuacfm.members.test.web.configuration;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
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
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class ConfigurationControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The account Type service. */
   @Inject
   private AccountTypeService accountTypeService;

   /** The account Type service. */
   @Inject
   private MethodPaymentService methodPaymentService;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /**
    * Initialize default session.
    *
    * @throws UniqueException, UniqueListException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException, UniqueListException {
      Account admin = new Account("admin", "", "55555555C", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin@udc.es");

      accountType = new AccountType("Adult", false, "Tax for Adult", 0);
      accountTypeService.save(accountType);

      methodPayment = new MethodPayment("Paypal", false, "Pay by Paypal");
      methodPaymentService.save(methodPayment);

      Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
      configuration.getId();
   }

   /**
    * Display Configuration page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayConfigurationPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/configuration")).andExpect(redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysConfiguration.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayConfiguration() throws Exception {
      mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("configuration/configuration"))
            .andExpect(content().string(containsString("<title>Configuration</title>")));
   }

   /**
    * Send displaysConfiguration.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysConfigurationWithDatabase() throws Exception {
      mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("configuration/configuration"))
            .andExpect(content().string(containsString("<title>Configuration</title>")));
   }

   /**
    * Send displaysConfiguration.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void deleteAccountType() throws Exception {
      mockMvc.perform(
            post("/configuration/accountTypeDelete/" + accountType.getId()).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(view().name("redirect:/configuration"));

      // Assert, it remove accountType
      assertEquals(accountTypeService.findById(accountType.getId()), null);
   }

   /**
    * Send displaysConfiguration.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void deleteMethodPayment() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentDelete/" + methodPayment.getId()).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration"));

      // Assert, it remove methodPayment
      assertEquals(methodPaymentService.findById(methodPayment.getId()), null);
   }

   /**
    * Post configuration succesfull test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void postConfigurationSuccesfullTest() throws Exception {

      mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("configuration/configuration"))
            .andExpect(content().string(containsString("<title>Configuration</title>")));

      mockMvc.perform(
            post("/configuration").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "New Name").param("email", "email@udc.es")
                  .param("phone", "111111").param("feeMember", "30").param("feeProgram", "32")
                  .param("descriptionRule", "New Description")).andExpect(
            view().name("redirect:/configuration"));
   }

   /**
    * Not blank message configuration test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void notBlankMessageConfigurationTest() throws Exception {

      mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("configuration/configuration"))
            .andExpect(content().string(containsString("<title>Configuration</title>")));

      mockMvc.perform(
            post("/configuration").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", " ").param("email", " ").param("phone", " ")
                  .param("descriptionRule", " ")).andExpect(
            view().name("configuration/configuration"));
   }

   /**
    * Max characters in configuration test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersInConfigurationTest() throws Exception {

      mockMvc.perform(get("/configuration").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("configuration/configuration"))
            .andExpect(content().string(containsString("<title>Configuration</title>")));

      mockMvc.perform(
            post("/configuration").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "111111111111111111111111111111111111")
                  .param("email", "11111111111111111111111111111111111")
                  .param("phone", "11111111111111111111111111111111111")
                  .param("descriptionRule", "1111111111111111111111111111")).andExpect(
            view().name("configuration/configuration"));
   }
}