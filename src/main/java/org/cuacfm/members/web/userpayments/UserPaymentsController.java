package org.cuacfm.members.web.userpayments;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.userpayinscription.UserPayInscription;
import org.cuacfm.members.model.userpayinscriptionservice.UserPayInscriptionService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// TODO: Auto-generated Javadoc
/** The Class UserPaymentsController. */
@Controller
public class UserPaymentsController {

   /** The Constant USERPAYINSCRIPTION_VIEW_NAME. */
   private static final String USERPAYINSCRIPTION_VIEW_NAME = "userpayments/userpayments";

   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The UserPayInscriptionService. */
   @Autowired
   private UserPayInscriptionService userPayInscriptionService;

   /** The PayProgramService. */
   @Autowired
   private PayProgramService payProgramService;

   /** The payInscriptions. */
   private List<UserPayInscription> userPayInscriptions;

   /** The payInscriptions. */
   private List<PayProgram> payPrograms;

   /** The email. */
   private String email;

   /**
    * Instantiates a new user payments controller.
    */
   public UserPaymentsController() {
      // Default empty constructor.
   }

   /**
    * Email.
    *
    * @return the string
    */
   @ModelAttribute("email")
   public String email() {
      return email;
   }

   /**
    * Pay programs.
    *
    * @return the list
    */
   @ModelAttribute("payPrograms")
   public List<PayProgram> payPrograms() {
      return payPrograms;
   }

   /**
    * List of UserPayInscription.
    *
    * @return List<UserPayInscription>
    */
   @ModelAttribute("userPayInscriptions")
   public List<UserPayInscription> userPayInscriptions() {
      return userPayInscriptions;
   }

   /**
    * User payments.
    *
    * @param model
    *           the model
    * @param principal
    *           the principal
    * @return the string
    */
   @RequestMapping(value = "userPayments")
   public String userPayments(Model model, Principal principal) {
      email = configurationService.getConfiguration().getEmail();
      model.addAttribute(email);
      Account account = accountService.findByLogin(principal.getName());
      userPayInscriptions = userPayInscriptionService.getUserPayInscriptionListByAccountId(account
            .getId());
      model.addAttribute("userPayInscriptions", userPayInscriptions);
      payPrograms = payProgramService.getPayProgramListByAccountId(account.getId());
      model.addAttribute("payPrograms", payPrograms);
      return USERPAYINSCRIPTION_VIEW_NAME;
   }

   /**
    * View user pay inscriptions by pay inscription id.
    *
    * @param userPayInscriptionId
    *           the user pay inscription id
    * @param emailPayer
    *           the email payer
    * @param idPayer
    *           the id payer
    * @param datePay
    *           the date pay
    * @param statusPay
    *           the status pay
    * @param idTxn
    *           the id txn
    * @param principal
    *           the principal
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "userPayments/payMember/{userPayInscriptionId}", method = RequestMethod.POST)
   public String payMemberByPayPal(@PathVariable Long userPayInscriptionId,
         @RequestParam("payer_email") String emailPayer, @RequestParam("payer_id") String idPayer,
         @RequestParam("payment_date") String datePay,
         @RequestParam("payment_status") String statusPay, @RequestParam("txn_id") String idTxn,
         Principal principal, RedirectAttributes ra) {

      // Validar que el pago, este realmente echo en paypal, con la informacion
      // que viene en el post....

      Account account = accountService.findByLogin(principal.getName());
      UserPayInscription userPayInscription = userPayInscriptionService
            .findById(userPayInscriptionId);

      // Verified if account is equals to account of userPayAccount
      if (userPayInscription.getAccount().getId() == account.getId()) {
         try {
            userPayInscriptionService.payPayPal(userPayInscription, idTxn, idPayer, emailPayer,
                  statusPay, datePay);
            MessageHelper.addSuccessAttribute(ra, "userPayments.successPayPayPal",
                  userPayInscription.getPayInscription().getName());
         } catch (ExistTransactionIdException e) {
            MessageHelper.addErrorAttribute(ra, "userPayments.errorPayPayPal", userPayInscription
                  .getPayInscription().getName());
         }
      }

      return "redirect:/userPayments";
   }

   /**
    * View user pay inscriptions by pay inscription id.
    *
    * @param payProgramId
    *           the pay program id
    * @param emailPayer
    *           the email payer
    * @param idPayer
    *           the id payer
    * @param datePay
    *           the date pay
    * @param statusPay
    *           the status pay
    * @param idTxn
    *           the id txn
    * @param principal
    *           the principal
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "userPayments/payProgram/{payProgramId}", method = RequestMethod.POST)
   public String payProgramByPayPal(@PathVariable Long payProgramId,
         @RequestParam("payer_email") String emailPayer, @RequestParam("payer_id") String idPayer,
         @RequestParam("payment_date") String datePay,
         @RequestParam("payment_status") String statusPay, @RequestParam("txn_id") String idTxn,
         Principal principal, RedirectAttributes ra) {

      Account account = accountService.findByLogin(principal.getName());
      PayProgram payProgram = payProgramService.findById(payProgramId);

      // Verified if account is equals to account of userPayAccount
      if (payProgram.getProgram().getAccounts().contains(account)) {
         try {
            payProgramService.payPayPal(payProgram, account.getName(), idTxn, idPayer, emailPayer,
                  statusPay, datePay);
            MessageHelper.addSuccessAttribute(ra, "userPayments.successPayPayPal", payProgram
                  .getProgram().getName());
         } catch (ExistTransactionIdException e) {
            MessageHelper.addErrorAttribute(ra, "userPayments.errorPayPayPal", payProgram
                  .getProgram().getName());
         }
      }

      return "redirect:/userPayments";
   }
}
