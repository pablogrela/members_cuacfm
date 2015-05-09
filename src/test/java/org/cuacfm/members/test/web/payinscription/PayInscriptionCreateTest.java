package org.cuacfm.members.test.web.payinscription;

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
import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayInscriptionListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayInscriptionCreateTest extends WebSecurityConfigurationAware {

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
   private PayInscriptionService payInscriptionService;

   /** The pay inscription. */
   private PayInscription payInscription;

   /**
    * Initialize default session.
    * 
    * @throws UniqueException
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "55555555D", "London", "admin", "admin@udc.es",
            666666666, 666666666, "demo", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666,
            Double.valueOf(24), Double.valueOf(25), "Rul");
      configurationService.save(configuration);
   }

   /**
    * Display PayInscriptionCreateView page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayPayInscriptionCreateViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/payInscriptionList/payInscriptionCreate")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysPayInscriptionCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysPayInscriptionCreateTest() throws Exception {

      mockMvc
            .perform(
                  get("/payInscriptionList/payInscriptionCreate").locale(Locale.ENGLISH).session(
                        defaultSession))
            .andExpect(view().name("payinscription/payinscriptioncreate"))
            .andExpect(content().string(containsString("<title>Create Pay Inscription</title>")));
   }

   /**
    * Send displaysPayInscriptionCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postPayInscriptionCreateTest() throws Exception {

      mockMvc.perform(
            post("/payInscriptionList/payInscriptionCreate").locale(Locale.ENGLISH)
                  .session(defaultSession).param("name", "Pay 2015").param("year", "2015")
                  .param("price", "24").param("dateLimit1", "2015-04-05")
                  .param("dateLimit2", "2015-07-05")
                  .param("description", "Pay of inscription 2015")).andExpect(
            view().name("redirect:/payInscriptionList"));
   }

   /**
    * Send maxCharactersPayInscriptionCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersPayInscriptionCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/payInscriptionList/payInscriptionCreate").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("name", "111111111111111111111111111111111111111111111111111")
                        .param("year", "2015").param("price", "24")
                        .param("description", "Pay of inscription 2015"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("payinscription/payinscriptioncreate"));
   }

   /**
    * Send dataBlankPayInscriptionCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void dataBlankPayInscriptionCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/payInscriptionList/payInscriptionCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", " ").param("year", "2015")
                        .param("price", "24").param("description", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("payinscription/payinscriptioncreate"));
   }

   /**
    * Send dataBlankPayInscriptionCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void yearAlreadyExistPayInscriptionCreateTest() throws Exception {
      // Create Payment
      payInscription = new PayInscription("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-04-05"), DisplayDate.stringToDate2("2015-07-05"),
            "pay of 2015");
      payInscriptionService.save(payInscription);

      mockMvc
            .perform(
                  post("/payInscriptionList/payInscriptionCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", "pay of 2015").param("year", "2015")
                        .param("dateLimit1", "2015-04-05").param("dateLimit2", "2015-07-05")
                        .param("price", "24").param("description", "pay of 2015"))
            .andExpect(content().string(containsString("Year repeated")))
            .andExpect(view().name("payinscription/payinscriptioncreate"));
   }
}