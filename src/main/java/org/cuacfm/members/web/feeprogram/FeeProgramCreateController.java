package org.cuacfm.members.web.feeprogram;

import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
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

/** The Class FeeProgramCreateController. */
@Controller
public class FeeProgramCreateController {

   /** The Constant FEEPROGRAM_VIEW_NAME. */
   private static final String FEEPROGRAM_VIEW_NAME = "feeprogram/feeprogramcreate";

   /** The message source. */
   @Autowired
   private MessageSource messageSource;
   
   /** The ConfigurationService. */
   @Autowired
   private ConfigurationService configurationService;
   
   /** The training service. */
   @Autowired
   private FeeProgramService feeProgramService;

   /**
    * Instantiates a new feeProgramController.
    */
   public FeeProgramCreateController() {
      // Default empty constructor.
   }

   /**
    * Fee Program.
    *
    * @param model
    *           the model
    * @return the string
    */
   @SuppressWarnings("deprecation")
   @RequestMapping(value = "feeProgramList/feeProgramCreate")
   public String training(Model model) {
      FeeProgramForm feeProgramForm = new FeeProgramForm();
      
      Date date = new Date();
      Date dateLimit = new Date();
      dateLimit.setMonth(date.getMonth()+2);
      
      String month = DisplayDate.monthOfYearToString(date);
      String monthDisplay = DisplayDate.monthOfYearToDisplay(date);
      String monthLimit = DisplayDate.monthOfYearToString(dateLimit);
      String feeProgramFile = messageSource.getMessage("feeProgramName", null, Locale.getDefault());
      
      feeProgramForm.setName(feeProgramFile + " " + monthDisplay);
      feeProgramForm.setPrice(configurationService.getConfiguration().getFeeProgram());
      feeProgramForm.setDescription(feeProgramFile + " " + monthDisplay);
      feeProgramForm.setDate(month);
      feeProgramForm.setDateLimit(monthLimit);
      model.addAttribute(feeProgramForm);
      return FEEPROGRAM_VIEW_NAME;
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
   @RequestMapping(value = "feeProgramList/feeProgramCreate", method = RequestMethod.POST)
   public String feeProgram(@Valid @ModelAttribute FeeProgramForm feeProgramForm, Errors errors,
         RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return FEEPROGRAM_VIEW_NAME;
      }

      String name = feeProgramForm.getName();
      try {
         feeProgramService.save(feeProgramForm.createFeeProgram());
         // It is verified that there is not exist year of feeProgram in other
         // feeProgram
      } catch (UniqueException e) {
         errors.rejectValue("date", "feeProgram.dateException", new Object[] { e.getValue() },
               "date");
         return FEEPROGRAM_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "feeProgram.successCreate", name);
      return "redirect:/feeProgramList";
   }

}
