package org.cuacfm.members.web.payinscription;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payinscriptionservice.PayInscriptionService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayInscriptionCreateController. */
@Controller
public class PayInscriptionCreateController {

   /** The Constant PAYINSCRIPTION_VIEW_NAME. */
   private static final String PAYINSCRIPTION_VIEW_NAME = "payinscription/payinscriptioncreate";

   /** The message source. */
   @Autowired
   private MessageSource messageSource;
   
   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;
   
   /** The pay Inscription service. */
   @Autowired
   private PayInscriptionService payInscriptionService;

   /**
    * Instantiates a new pay Inscription Controller.
    */
   public PayInscriptionCreateController() {
      // Default empty constructor.
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @SuppressWarnings("deprecation")
   @RequestMapping(value = "payInscriptionList/payInscriptionCreate")
   public String payInscription(Model model) {
      
      PayInscriptionForm payInscriptionForm = new PayInscriptionForm();
      
      String feeProgramFile = messageSource.getMessage("feeMemberFile", null, Locale.getDefault());
      payInscriptionForm.setName(feeProgramFile + " " + LocalDate.now().getYear());
      payInscriptionForm.setPrice(configurationService.getConfiguration().getFeeMember());
      payInscriptionForm.setDescription(feeProgramFile + " " + LocalDate.now().getYear());
      payInscriptionForm.setYear(LocalDate.now().getYear());
      Date dateLimit = new Date();
      dateLimit.setMonth(2);
      String monthLimit = DisplayDate.monthOfYearToString(dateLimit);
      payInscriptionForm.setDateLimit1(monthLimit);
      dateLimit.setMonth(8);
      monthLimit = DisplayDate.monthOfYearToString(dateLimit);
      payInscriptionForm.setDateLimit2(monthLimit);
      model.addAttribute(payInscriptionForm);
      
      return PAYINSCRIPTION_VIEW_NAME;
   }

   /**
    * Training.
    *
    * @param trainingForm
    *           the training form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @return the string
    */
   @RequestMapping(value = "payInscriptionList/payInscriptionCreate", method = RequestMethod.POST)
   public String payInscription(@Valid @ModelAttribute PayInscriptionForm payInscriptionForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return PAYINSCRIPTION_VIEW_NAME;
      }

      int year = payInscriptionForm.getYear();
      String name = payInscriptionForm.getName();
      try {
         payInscriptionService.save(payInscriptionForm.createPayInscription());
         // It is verified that there is not exist year of payInscription in
         // other payInscription
      } catch (UniqueException e) {
         errors.rejectValue("year", "payInscription.yearException", new Object[] { year }, "year");
      }

      if (errors.hasErrors()) {
         return PAYINSCRIPTION_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "payInscription.successCreate", name);
      return "redirect:/payInscriptionList";
   }

}
