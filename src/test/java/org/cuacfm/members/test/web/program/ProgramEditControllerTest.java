package org.cuacfm.members.test.web.program;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/** The class ProfileControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProgramEditControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The training Type service. */
   @Inject
   private ProgramService programService;

   /** The program. */
   private Program program;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      Account admin = new Account("admin", "55555555A", "London", "admin", "admin@udc.es",
            666666666, 666666666, "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      defaultSession = getDefaultSession("admin");

      List<Account> accounts = new ArrayList<Account>();
      Account account = new Account("userz", "11111111C", "London", "userz", "userz@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      accounts.add(account);
      program = new Program("ProgramZ", Float.valueOf(1), "Very interesting", 9, accounts);
      programService.save(program);

   }

   /**
    * Display ProgramList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayTrainingEditEditPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/programList/programEdit")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectTrainingListBecauseTrainingTypeIsNullTest() throws Exception {
      mockMvc.perform(
            post("/programList/programEdit/" + Long.valueOf(0)).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc.perform(
            get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("redirect:/programList"));
   }

   /**
    * Send displaysProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysProgramEditTest() throws Exception {
      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));
   }

   
   /**
    * Displays program edit acount payer test.
    *
    * @throws Exception the exception
    */
   @Test
   public void displaysProgramEditAcountPayerTest() throws Exception {
      Account account2 = new Account("user2", "11111111J", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);
      List<Account> accounts = new ArrayList<Account>();
      accounts.add(account2);
      program = new Program("Program2", Float.valueOf(1), "Very interesting", 9, accounts);
      program.setAccountPayer(account2);
      programService.save(program);
      
      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));
   
      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));
   }
   
   /**
    * Send displaysProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysUserProgramEditTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      defaultSession = getDefaultSession("user");

      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));
   }

   /**
    * Send displaysProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postProgramEditTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser")
                  .param("Login", account.getId() + ": " + account.getName())).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("edit", "edit").param("name", "Program").param("periodicity", "1")
                  .param("description", "Very interesting2").param("duration", "1")).andExpect(
            view().name("redirect:/programList"));
   }

   /**
    * Send displaysProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postNameAlreadExistTest() throws Exception {

      mockMvc
            .perform(
                  post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                        .param("edit", "edit").param("name", "ProgramZ").param("periodicity", "1")
                        .param("description", "Very interesting2").param("duration", "1"))
            .andExpect(
                  content().string(
                        containsString("Already exist program with name " + program.getName()
                              + ", please chose other")))
            .andExpect(view().name("program/programedit"));

   }

   /**
    * notBlankMessage.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void notBlankMessageTest() throws Exception {
      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(
                  post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                        .param("edit", " ").param("name", " ").param("periodicity", " ")
                        .param("description", " ").param("duration", " "))
            .andExpect(content().string(containsString("The value may not be empty!")))
            .andExpect(view().name("program/programedit"));

   }

   /**
    * "Already exist type of formation with name "+ program.getName() +
    * ", please chose other" Send displaysProgramList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersTest() throws Exception {
      mockMvc
            .perform(
                  post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                        .param("edit", "edit")
                        .param("name", "1111111111111111111111111111111111111111111111111111111")
                        .param("description", "111111111111111111111111111111111111111111111111"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("program/programedit"));

   }

   /**
    * Adds the user byn ni test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void addUserByDniTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser").param("Login", account.getDni())).andExpect(
            view().name("program/programedit"));

   }

   /**
    * Adds the user byn ni test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void accountPayerDniTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addAccountPayer", "accountPayer")
                  .param("accountPayerName", account.getDni()))
                  .andExpect(view().name("program/programedit"));
   }
   
   @Test
   public void accountRemovePayerDniTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addAccountPayer", "accountPayer")
                  .param("accountPayerName", account.getDni()))
                  .andExpect(view().name("program/programedit"));
      
      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("removeAccountPayer", "accountPayer"))
                  .andExpect(view().name("program/programedit"));
   }
   
   /**
    * Account payer dni no found test.
    *
    * @throws Exception the exception
    */
   @Test
   public void accountPayerDniNoFoundTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addAccountPayer", "accountPayer")
                  .param("accountPayerName", "not found"))
                  .andExpect(view().name("program/programedit"));
   }
   
   /**
    * Account payer id dni test.
    *
    * @throws Exception the exception
    */
   @Test
   public void accountPayerIdDniTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addAccountPayer", "accountPayer")
                  .param("accountPayerName", account.getId() + ": " + account.getName()))
                  .andExpect(view().name("program/programedit"));
   }
   
   /**
    * Account payer id null dni test.
    *
    * @throws Exception the exception
    */
   @Test
   public void accountPayerIdNullDniTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addAccountPayer", "accountPayer")
                  .param("accountPayerName", Long.valueOf(0) + ": " + account.getName()))
                  .andExpect(view().name("program/programedit"));
   }
   
   /**
    * Removes the user test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void removeUserTest() throws Exception {

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser")
                  .param("Login", account.getId() + ": " + account.getName())).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("removeUser", String.valueOf(account.getId()))).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("removeUser", String.valueOf(account.getId()))).andExpect(
            view().name("program/programedit"));
   }

   /**
    * Removes the user test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void addUserNullTest() throws Exception {
      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser").param("Login", 0 + ": " + "Login")).andExpect(
            view().name("program/programedit"));
   }

   /**
    * Removes the user test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void addUserRepeatedTest() throws Exception {

      mockMvc.perform(
            post("/programList/programEdit/" + program.getId()).locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/programList/programEdit"));

      mockMvc
            .perform(get("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession))
            .andExpect(view().name("program/programedit"))
            .andExpect(content().string(containsString("<title>Edit program</title>")));

      Account account = new Account("user", "55555555C", "London", "user", "user@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);
      Account account2 = new Account("user2", "255555555C", "London", "user2", "user2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account2);

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser")
                  .param("Login", account.getId() + ": " + account.getName())).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser")
                  .param("Login", account.getId() + ": " + account.getName())).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("addUser", "addUser")
                  .param("Login", account2.getId() + ": " + account2.getName())).andExpect(
            view().name("program/programedit"));

      mockMvc.perform(
            post("/programList/programEdit").locale(Locale.ENGLISH).session(defaultSession)
                  .param("removeUser", String.valueOf(account.getId()))).andExpect(
            view().name("program/programedit"));
   }
}