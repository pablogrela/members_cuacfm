package org.cuacfm.members.web.paymember;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.util.States.methods;
import org.cuacfm.members.model.util.States.states;
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

/** The Class PayMemberEditController. */
@Controller
public class PayMemberEditController {

   /** The Constant PAYMEMBER_VIEW_NAME. */
   private static final String PAYMEMBER_VIEW_NAME = "paymember/paymemberedit";

   /** The payMember service. */
   @Autowired
   private PayMemberService payMemberService;

   /** The Global variable payMember. */
   private PayMember payMember;

   /**
    * Instantiates a new payMember Controller.
    */
   public PayMemberEditController() {
      // Default empty constructor.
   }

   /**
    * PayMember.
    *
    * @return PayMember
    */
   @ModelAttribute("payMember")
   public PayMember payMember() {
      return payMember;
   }

   /**
    * PayMember.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit")
   public String payMember(Model model) {

      if (payMember != null) {
         PayMemberForm payMemberForm = new PayMemberForm();
         payMemberForm.setPrice(payMember.getPrice());
         payMemberForm.setInstallment(payMember.getInstallment());
         payMemberForm.setInstallments(payMember.getInstallments());
         payMemberForm.setState(String.valueOf(payMember.getState()));
         payMemberForm.setStateList(java.util.Arrays.asList(states.values()));
         payMemberForm.setMethod(String.valueOf(payMember.getMethod()));
         payMemberForm.setMethodList(java.util.Arrays.asList(methods.values()));
         payMemberForm.setIdTxn(payMember.getIdTxn());
         payMemberForm.setIdPayer(payMember.getIdPayer());
         payMemberForm.setEmailPayer(payMember.getEmailPayer());
         payMemberForm.setIdPayer(payMember.getIdPayer());
         payMemberForm.setDatePay(DisplayDate.dateTimeToString(payMember
               .getDatePay()));
         model.addAttribute(payMember);
         model.addAttribute(payMemberForm);
         return PAYMEMBER_VIEW_NAME;
      }
      // If not have payMember, redirect to payMemberList
      else {
         return "redirect:/feeMemberList/payMemberList";
      }
   }

   /**
    * PayMember.
    *
    * @param payMemberForm
    *           the payMember form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    * @throws DateLimitException
    */
   @RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit", method = RequestMethod.POST)
   public String payMember(
         @Valid @ModelAttribute PayMemberForm payMemberForm, Errors errors,
         RedirectAttributes ra) throws DateLimitException {

      if (errors.hasErrors()) {
         return PAYMEMBER_VIEW_NAME;
      }

      try {
         payMemberService.update(payMemberForm
               .updatePayMember(payMember));
      } catch (ExistTransactionIdException e) {
         errors.rejectValue("idTxn", "existIdTxn.message", new Object[] {e.getIdTxn() }, "idTxn");
         return PAYMEMBER_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "payMember.successModify",
            payMemberForm.getInstallment());
      return "redirect:/feeMemberList/payMemberList";
   }

   /**
    * Modify PayMember by Id.
    *
    * @param @PathVariable Long id the payMember form
    * @param errors
    *           the errors
    * @return the string destinity page
    */
   @RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit/{id}", method = RequestMethod.POST)
   public String modifyPayMember(@PathVariable Long id) {

      payMember = payMemberService.findById(id);
      return "redirect:/feeMemberList/payMemberList/payMemberEdit";
   }
}
