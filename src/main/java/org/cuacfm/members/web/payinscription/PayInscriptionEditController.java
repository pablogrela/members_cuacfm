package org.cuacfm.members.web.payinscription;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payinscription.PayInscription;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
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

/** The Class PayInscriptionEditController. */
@Controller
public class PayInscriptionEditController {

   /** The Constant PAYINSCRIPTION_VIEW_NAME. */
   private static final String PAYINSCRIPTION_VIEW_NAME = "payinscription/payinscriptionedit";

   /** The pay Inscription service. */
   @Autowired
   private PayInscriptionService payInscriptionService;

   /** The Global variable pay Inscription. */
   private PayInscription payInscription;

   /**
    * Instantiates a pay Inscription Controller.
    */
   public PayInscriptionEditController() {
      // Default empty constructor.
   }

   /**
    * PayInscription.
    *
    * @return PayInscription
    */
   @ModelAttribute("payInscription")
   public PayInscription payInscription() {
      return payInscription;
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/payInscriptionEdit")
   public String payInscription(Model model) {

      if (payInscription != null) {
         PayInscriptionForm payInscriptionForm = new PayInscriptionForm();
         payInscriptionForm.setName(payInscription.getName());
         payInscriptionForm.setYear(payInscription.getYear());
         payInscriptionForm.setPrice(payInscription.getPrice());
         payInscriptionForm.setDescription(payInscription.getDescription());
         payInscriptionForm.setDateLimit1(DisplayDate.monthOfYearToString(payInscription
               .getDateLimit1()));
         payInscriptionForm.setDateLimit2(DisplayDate.monthOfYearToString(payInscription
               .getDateLimit2()));
         model.addAttribute(payInscription);
         model.addAttribute(payInscriptionForm);
         return PAYINSCRIPTION_VIEW_NAME;
      }
      // If not have payInscription, redirect to payInscriptionList
      else {
         return "redirect:/payInscriptionList";
      }
   }

   /**
    * Training.
    *
    * @param payInscriptionForm
    *           the pay inscription form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/payInscriptionEdit", method = RequestMethod.POST)
   public String payInscription(@Valid @ModelAttribute PayInscriptionForm payInscriptionForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return PAYINSCRIPTION_VIEW_NAME;
      }

      try {
         payInscriptionService.update(payInscriptionForm.updatePayInscription(payInscription));
         // It is verified that there is not exist year of payInscription in
         // other payInscription
      } catch (UniqueException e) {
         errors.rejectValue("year", "payInscription.yearException",
               new Object[] { payInscriptionForm.getYear() }, "year");
         return PAYINSCRIPTION_VIEW_NAME;
      }

      MessageHelper.addWarningAttribute(ra, "payInscription.successModify",
            payInscriptionForm.getName());
      return "redirect:/payInscriptionList";
   }

   /**
    * Modify PayInscription by Id.
    *
    * @param id
    *           the id
    * @param ra
    *           the redirect atributes
    * @return the string destinity page
    */
   @RequestMapping(value = "payInscriptionList/payInscriptionEdit/{id}", method = RequestMethod.POST)
   public String modifyPayInscription(@PathVariable Long id) {

      payInscription = payInscriptionService.findById(id);
      return "redirect:/payInscriptionList/payInscriptionEdit";
   }
}
