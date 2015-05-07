package org.cuacfm.members.test.web.feeprogram;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class FeeProgramListControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FeeProgramCreateTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The configuration service. */
   @Inject
   private ConfigurationService configurationService;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The fee program service. */
   @Inject
   private FeeProgramService feeProgramService;

   /**
    * Initialize default session.
    *
    * @throws UniqueException the unique exception
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
    * Display FeeProgramCreateView page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayFeeProgramCreateViewPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeProgramList/feeProgramCreate")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send displaysFeeProgramCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysFeeProgramCreateTest() throws Exception {

      mockMvc
            .perform(
                  get("/feeProgramList/feeProgramCreate").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("feeprogram/feeprogramcreate"))
            .andExpect(content().string(containsString("<title>Create fee programs</title>")));
   }

   /**
    * Send displaysFeeProgramCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postFeeProgramCreateTest() throws Exception {

      mockMvc.perform(
            post("/feeProgramList/feeProgramCreate").locale(Locale.ENGLISH).session(defaultSession)
                  .param("name", "Pay 2015").param("price", "24").param("date", "2015-04")
                  .param("dateLimit", "2015-07")
                  .param("description", "Pay of inscription april 2015")).andExpect(
            view().name("redirect:/feeProgramList"));
   }

   /**
    * Send maxCharactersFeeProgramCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersFeeProgramCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/feeProgramList/feeProgramCreate").locale(Locale.ENGLISH)
                        .session(defaultSession)
                        .param("name", "111111111111111111111111111111111111111111111111111")
                        .param("price", "24").param("description", "Pay of inscription 2015"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("feeprogram/feeprogramcreate"));
   }

   /**
    * Send dataBlankFeeProgramCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void dataBlankFeeProgramCreateTest() throws Exception {

      mockMvc
            .perform(
                  post("/feeProgramList/feeProgramCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", " ").param("year", "2015")
                        .param("price", "24").param("description", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("feeprogram/feeprogramcreate"));
   }

   /**
    * Send dataBlankFeeProgramCreate.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void dateAlreadyExistFeeProgramCreateTest() throws Exception {
      // Create Payment
      Date date = DisplayDate.stringToMonthOfYear("2015-12");
      FeeProgram feeProgram = new FeeProgram("name", Double.valueOf(25), date, date, "description");
      feeProgramService.save(feeProgram);

      mockMvc
            .perform(
                  post("/feeProgramList/feeProgramCreate").locale(Locale.ENGLISH)
                        .session(defaultSession).param("name", "pay of 2015")
                        .param("date", "2015-12").param("dateLimit", "2015-12")
                        .param("price", "24").param("description", "pay of april 2015"))
            .andExpect(content().string(containsString("Date repeated")))
            .andExpect(view().name("feeprogram/feeprogramcreate"));
   }
}