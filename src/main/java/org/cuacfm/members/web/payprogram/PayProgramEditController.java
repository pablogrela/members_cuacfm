package org.cuacfm.members.web.payprogram;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayProgramEditController. */
@Controller
public class PayProgramEditController {

   /** The Constant PAYPROGRAM_VIEW_NAME. */
   private static final String PAYPROGRAM_VIEW_NAME = "payprogram/payprogramedit";

   /** The payProgram service. */
   @Autowired
   private PayProgramService payProgramService;

   /** The Global variable payProgram. */
   private PayProgram payProgram;

   /** The nameUsers. */
   private List<String> usernames;

   /** Instantiates a new payProgram Controller. */
   public PayProgramEditController() {
      // Default empty constructor.
   }

   /**
    * PayProgram.
    *
    * @return PayProgram
    */
   @ModelAttribute("payProgram")
   public PayProgram payProgram() {
      return payProgram;
   }

   /**
    * Usernames.
    *
    * @return the list
    */
   @ModelAttribute("usernames")
   public List<String> usernames() {
      return usernames;
   }

   /**
    * PayProgram.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/payProgramList/payProgramEdit")
   public String getPayProgram(Model model) {

      if (payProgram != null) {
         usernames = new ArrayList<String>();
         for (Account account : payProgram.getProgram().getAccounts()) {
            usernames.add(account.getName());
         }
         model.addAttribute("usernames", usernames);

         PayProgramForm payProgramForm = new PayProgramForm();
         payProgramForm.setAccountPayer(payProgram.getAccountPayer());
         payProgramForm.setPrice(payProgram.getPrice());
         payProgramForm.setHasPay(payProgram.isHasPay());
         payProgramForm.setIdTxn(payProgram.getIdTxn());
         payProgramForm.setIdPayer(payProgram.getIdPayer());
         payProgramForm.setEmailPayer(payProgram.getEmailPayer());
         payProgramForm.setStatusPay(payProgram.getStatusPay());
         payProgramForm.setIdPayer(payProgram.getIdPayer());
         payProgramForm.setDatePay(DisplayDate.dateTimeToString(payProgram.getDatePay()));
         model.addAttribute(payProgram);
         model.addAttribute(payProgramForm);
         return PAYPROGRAM_VIEW_NAME;
      }
      // If not have payProgram, redirect to payProgramList
      else {
         return "redirect:/feeProgramList/payProgramList";
      }
   }

   /**
    * PayProgram.
    *
    * @param payProgramForm
    *           the payProgram form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    * @throws DateLimitException
    */

   @RequestMapping(value = "feeProgramList/payProgramList/payProgramEdit", method = RequestMethod.POST)
   public String editPayProgram(@Valid @ModelAttribute PayProgramForm payProgramForm,
         Errors errors, RedirectAttributes ra) throws DateLimitException {

      if (errors.hasErrors()) {
         return PAYPROGRAM_VIEW_NAME;
      }

      payProgramService.update(payProgramForm.updatePayProgram(payProgram));
      MessageHelper.addSuccessAttribute(ra, "payProgram.successModify", payProgram.getProgram().getName());
      return "redirect:/feeProgramList/payProgramList";
   }

   /**
    * Modify PayProgram by Id.
    *
    * @param @PathVariable Long id the payProgram form
    * @return the string destinity page
    */
   @RequestMapping(value = "feeProgramList/payProgramList/payProgramEdit/{id}", method = RequestMethod.POST)
   public String chargePayProgram(@PathVariable Long id) {

      payProgram = payProgramService.findById(id);
      return "redirect:/feeProgramList/payProgramList/payProgramEdit";
   }
}
