package org.cuacfm.members.web.userpayinscription;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.userpayinscription.UserPayInscription;
import org.cuacfm.members.model.userpayinscriptionservice.UserPayInscriptionService;
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

/** The Class UserPayInscriptionEditController. */
@Controller
public class UserPayInscriptionEditController {

   /** The Constant USERPAYINSCRIPTION_VIEW_NAME. */
   private static final String USERPAYINSCRIPTION_VIEW_NAME = "userpayinscription/userpayinscriptionedit";

   /** The userPayInscription service. */
   @Autowired
   private UserPayInscriptionService userPayInscriptionService;

   /** The Global variable userPayInscription. */
   private UserPayInscription userPayInscription;

   /**
    * Instantiates a new userPayInscription Controller.
    */
   public UserPayInscriptionEditController() {
      // Default empty constructor.
   }

   /**
    * UserPayInscription.
    *
    * @return UserPayInscription
    */
   @ModelAttribute("userPayInscription")
   public UserPayInscription userPayInscription() {
      return userPayInscription;
   }

   /**
    * UserPayInscription.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList/userPayInscriptionEdit")
   public String userPayInscription(Model model) {

      if (userPayInscription != null) {
         UserPayInscriptionForm userPayInscriptionForm = new UserPayInscriptionForm();
         userPayInscriptionForm.setPrice(userPayInscription.getPrice());
         userPayInscriptionForm.setInstallment(userPayInscription.getInstallment());
         userPayInscriptionForm.setInstallments(userPayInscription.getInstallments());
         userPayInscriptionForm.setHasPay(userPayInscription.isHasPay());
         userPayInscriptionForm.setIdTxn(userPayInscription.getIdTxn());
         userPayInscriptionForm.setIdPayer(userPayInscription.getIdPayer());
         userPayInscriptionForm.setEmailPayer(userPayInscription.getEmailPayer());
         userPayInscriptionForm.setStatusPay(userPayInscription.getStatusPay());
         userPayInscriptionForm.setIdPayer(userPayInscription.getIdPayer());
         userPayInscriptionForm.setDatePay(DisplayDate.dateTimeToString(userPayInscription
               .getDatePay()));
         model.addAttribute(userPayInscription);
         model.addAttribute(userPayInscriptionForm);
         return USERPAYINSCRIPTION_VIEW_NAME;
      }
      // If not have userPayInscription, redirect to userPayInscriptionList
      else {
         return "redirect:/payInscriptionList/userPayInscriptionList";
      }
   }

   /**
    * UserPayInscription.
    *
    * @param userPayInscriptionForm
    *           the userPayInscription form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    * @throws DateLimitException
    */

   @RequestMapping(value = "payInscriptionList/userPayInscriptionList/userPayInscriptionEdit", method = RequestMethod.POST)
   public String userPayInscription(
         @Valid @ModelAttribute UserPayInscriptionForm userPayInscriptionForm, Errors errors,
         RedirectAttributes ra) throws DateLimitException {

      if (errors.hasErrors()) {
         return USERPAYINSCRIPTION_VIEW_NAME;
      }

      // Modify UserPayInscription
      userPayInscription.setPrice(userPayInscriptionForm.getPrice());
      userPayInscription.setInstallment(userPayInscriptionForm.getInstallment());
      userPayInscription.setInstallments(userPayInscriptionForm.getInstallments());
      userPayInscription.setHasPay(userPayInscriptionForm.getHasPay());
      userPayInscription.setIdTxn(userPayInscriptionForm.getIdTxn());
      userPayInscription.setIdPayer(userPayInscriptionForm.getIdPayer());
      userPayInscription.setEmailPayer(userPayInscriptionForm.getEmailPayer());
      userPayInscription.setStatusPay(userPayInscriptionForm.getStatusPay());
      userPayInscription.setIdPayer(userPayInscriptionForm.getIdPayer());
      userPayInscription.setDatePay(DisplayDate.stringToDateTime(userPayInscriptionForm
            .getDatePay()));

      userPayInscriptionService.update(userPayInscription);
      MessageHelper.addSuccessAttribute(ra, "userPayInscription.successModify",
            userPayInscriptionForm.getInstallment());

      return "redirect:/payInscriptionList/userPayInscriptionList";
   }

   /**
    * Modify UserPayInscription by Id.
    *
    * @param @PathVariable Long id the userPayInscription form
    * @param errors
    *           the errors
    * @return the string destinity page
    */
   @RequestMapping(value = "payInscriptionList/userPayInscriptionList/userPayInscriptionEdit/{id}", method = RequestMethod.POST)
   public String modifyUserPayInscription(@PathVariable Long id) {

      userPayInscription = userPayInscriptionService.findById(id);
      return "redirect:/payInscriptionList/userPayInscriptionList/userPayInscriptionEdit";
   }
}
