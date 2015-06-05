package org.cuacfm.members.test.web.paymember;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class PayMemberEditControllerTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PayMemberEditControllerTest extends WebSecurityConfigurationAware {

   /** The default session. */
   private MockHttpSession defaultSession;

   /** The account service. */
   @Inject
   private AccountService accountService;

   /** The account type service. */
   @Inject
   private AccountTypeService accountTypeService;

   /** The method payment service. */
   @Inject
   private MethodPaymentService methodPaymentService;

   /** The pay inscription service. */
   @Inject
   private FeeMemberService feeMemberService;

   /** The user pay inscription service. */
   @Inject
   private PayMemberService payMemberService;

   /** The user. */
   private Account user;

   /** The account type. */
   private AccountType accountType;

   /** The method payment. */
   private MethodPayment methodPayment;

   /** The fee member. */
   private FeeMember feeMember;

   /** The pay. */
   private PayMember pay;

   /**
    * Initialize default session.
    *
    * @throws UniqueException
    *            the unique exception
    */
   @Before
   public void initializeDefaultSession() throws UniqueException {
      accountType = new AccountType("Adult", false, "Fee for adults", 0);
      accountTypeService.save(accountType);
      methodPayment = new MethodPayment("cash", false, "cash");
      methodPaymentService.save(methodPayment);

      Account admin = new Account("admin", "55555555B", "London", "admin", "admin@udc.es",
            666666666, 666666666, "admin", roles.ROLE_ADMIN);
      accountService.save(admin);
      admin.setAccountType(accountType);
      admin.setMethodPayment(methodPayment);
      admin.setInstallments(1);
      accountService.update(admin, false);
      defaultSession = getDefaultSession("admin");

      // Create User
      user = new Account("user", "55555555C", "London", "user", "email1@udc.es", 666666666,
            666666666, "demo", roles.ROLE_USER);
      accountService.save(user);
      user.setAccountType(accountType);
      user.setMethodPayment(methodPayment);
      user.setInstallments(1);
      accountService.update(user, false);

      Account account = new Account("user2", "255555555C", "London", "user2", "email2@udc.es",
            666666666, 666666666, "demo", roles.ROLE_USER);
      accountService.save(account);

      // Create Payment
      feeMember = new FeeMember("pay of 2015", 2015, Double.valueOf(20),
            DisplayDate.stringToDate2("2015-03-01"), DisplayDate.stringToDate2("2015-09-01"),
            "pay of 2015");
      feeMemberService.save(feeMember);

      pay = payMemberService.getPayMemberListByAccountId(user.getId()).get(0);
   }

   /**
    * Display PayMemberList page without signin in test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displayPayMemberEditPageWithoutSiginInTest() throws Exception {
      mockMvc.perform(get("/feeMemberList/payMemberList/payMemberEdit")).andExpect(
            redirectedUrl("http://localhost/signin"));
   }

   /**
    * Send null.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void redirectTrainingListBecausePayMemberIsNullTest() throws Exception {
      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit/" + Long.valueOf(0)).locale(
                  Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList/payMemberEdit"));

      mockMvc.perform(
            get("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH).session(
                  defaultSession)).andExpect(view().name("redirect:/feeMemberList/payMemberList"));
   }

   /**
    * Displays user pay inscription edit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void displaysPayMemberEditTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit/" + pay.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList/payMemberEdit"));

      mockMvc
            .perform(
                  get("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("paymember/paymemberedit"))
            .andExpect(model().attributeExists("payMemberForm"))
            .andExpect(content().string(containsString("<title>Edit User Payment</title>")));
   }

   /**
    * Send displaysPayMemberList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postPayMemberEditTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit/" + pay.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList/payMemberEdit"));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("installment", "1").param("installments", "1").param("idPayer", "1G3210")
                  .param("idTxn", "1G3210").param("emailPayer", "user@hotmail.com")
                  .param("datePay", "10:10 10/10/2015")).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));
   }

   /**
    * Send displaysPayMemberList.
    * 
    * @throws Exception
    *            the exception
    */
   @Test
   public void postBlankPayMemberEditTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit/" + pay.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList/payMemberEdit"));

      mockMvc
            .perform(
                  get("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH).session(
                        defaultSession)).andExpect(view().name("paymember/paymemberedit"))
            .andExpect(model().attributeExists("payMemberForm"))
            .andExpect(content().string(containsString("<title>Edit User Payment</title>")));

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("installment", "1").param("installments", "1").param("idPayer", "")
                  .param("idTxn", "").param("emailPayer", "")
                  .param("datePay", "")).andExpect(
            view().name("redirect:/feeMemberList/payMemberList"));
   }

   /**
    * Max characters in user pay inscription edit test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void maxCharactersInPayMemberEditTest() throws Exception {

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit/" + pay.getId())
                  .locale(Locale.ENGLISH).session(defaultSession)).andExpect(
            view().name("redirect:/feeMemberList/payMemberList/payMemberEdit"));

      mockMvc
            .perform(
                  post("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH)
                        .session(defaultSession).param("price", "24").param("hasPay", "true")
                        .param("installment", "1").param("installments", "1")
                        .param("idPayer", "1G3210111111111111111111111111111111111111111")
                        .param("idTxn", "1G321011111111111111111111111111111111111111111")
                        .param("emailPayer", "user@hotmail.com11111111111111111111111111")
                        .param("statusPay", "Completed1111111111111111111111111111111111")
                        .param("datePay", "10:10 10/10/2015"))
            .andExpect(content().string(containsString("Maximum 30 characters")))
            .andExpect(view().name("paymember/paymemberedit"));

   }

   /**
    * Exist transaction id test.
    *
    * @throws Exception
    *            the exception
    */
   @Test
   public void existTransactionIdTest() throws Exception {

      pay.setIdTxn("1G3210");
      payMemberService.update(pay);

      mockMvc.perform(
            post("/feeMemberList/payMemberList/payMemberEdit").locale(Locale.ENGLISH)
                  .session(defaultSession).param("price", "24").param("state", "PAY").param("method", "CASH")
                  .param("installment", "1").param("installments", "1").param("idPayer", "1")
                  .param("idTxn", "1G3210").param("emailPayer", "user@hotmail.com")
                  .param("datePay", "10:10 10/10/2015")).andExpect(
            view().name("paymember/paymemberedit"));

   }
}