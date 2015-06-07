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
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class MethodPaymentEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MethodPaymentEditControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The account Type service. */
   @Autowired
   private MethodPaymentService methodPaymentService;

   /** The method payment. */
   private MethodPayment methodPayment;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "55555555C", "London", "admin", "admin@udc.es",
            "666666666", "666666666", "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      methodPayment = new MethodPayment("Paypal", false, "Pay by Paypal");
      methodPaymentService.save(methodPayment);
   }

   /**
    * Display MethodPaymentList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayMethodPaymentEditPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/configuration/methodPaymentEdit")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectAccountListBecauseMethodPaymentIsNullTest() throws Exception {
      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + Long.valueOf(0)).locale(Locale.ENGLISH)
                  .session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc.perform(
            get("/configuration/methodPaymentEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/configuration"));
   }

   /**
    * Send displaysMethodPaymentList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysMethodPaymentEditTest() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + methodPayment.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc
            .perform(
                  get("/configuration/methodPaymentEdit").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("configuration/methodpaymentedit"))
            .andExpect(content().string(containsString("<title>Modify Method Payment</title>")));
   }

   /**
    * Send displaysMethodPaymentList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postMethodPaymentEditTest() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + methodPayment.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc.perform(
            post("/configuration/methodPaymentEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "Paypal").param("description", "Pay by Paypal")).andExpect(
            view().name("redirect:/configuration"));
   }

   /**
    * Send displaysMethodPaymentList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void nameAlreadyExistTest() throws Exception {

      mockMvc
            .perform(
                  post("/configuration/methodPaymentEdit").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", "Paypal")
                        .param("description", "Pay by Paypal"))
            .andExpect(
                  content().string(
                        containsString("Already exist method payment with name "
                              + methodPayment.getName() + ", please chose other")))
            .andExpect(view().name("configuration/methodpaymentedit"));

   }

   /**
    * Send displaysMethodPaymentList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void updateMethodPaymentTheSameParamsTest() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + methodPayment.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc.perform(
            post("/configuration/methodPaymentEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "Paypal").param("description", "Pay  by Paypal")).andExpect(
            view().name("redirect:/configuration"));

   }

   /**
    * notBlankMessage.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void notBlankMessageInMethodPaymentEditTest() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + methodPayment.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc
            .perform(
                  post("/configuration/methodPaymentEdit").locale(Locale.ENGLISH).session(
                        defaultSession))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("configuration/methodpaymentedit"));

   }

   /**
    * "Already exist type of account with name "+ methodPayment.getName() +
    * ", please chose other" Send displaysMethodPaymentList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersInMethodPaymentEditTest() throws Exception {

      mockMvc.perform(
            post("/configuration/methodPaymentEdit/" + methodPayment.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/configuration/methodPaymentEdit"));

      mockMvc
            .perform(
                  post("/configuration/methodPaymentEdit").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("name", "Paypaltttttttttttttttttttttttttttttttttttttttttttttttt")
                        .param("description", "Pay  by Paypalttttttttttttttttttttttttttttttttt"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("configuration/methodpaymentedit"));

   }
}