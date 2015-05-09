package org.cuacfm.members.web.feeprogram;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
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

/** The Class FeeProgramEditController. */
@Controller
public class FeeProgramEditController {

   /** The Constant FEEPROGRAM_VIEW_NAME. */
   private static final String FEEPROGRAM_VIEW_NAME = "feeprogram/feeprogramedit";

   /** The Fee Program service. */
   @Autowired
   private FeeProgramService feeProgramService;

   /** The Global variable feeProgram. */
   private FeeProgram feeProgram;

   /**
    * Instantiates a new training Controller.
    */
   public FeeProgramEditController() {
      // Default empty constructor.
   }

   /**
    * FeeProgram.
    *
    * @return FeeProgram
    */
   @ModelAttribute("feeProgram")
   public FeeProgram feeProgram() {
      return feeProgram;
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/feeProgramEdit")
   public String feeProgram(Model model) {

      if (feeProgram != null) {
         FeeProgramForm feeProgramForm = new FeeProgramForm();
         feeProgramForm.setName(feeProgram.getName());
         feeProgramForm.setPrice(feeProgram.getPrice());
         feeProgramForm.setDescription(feeProgram.getDescription());
         feeProgramForm.setDate(DisplayDate.monthOfYearToString(feeProgram.getDate()));
         feeProgramForm.setDateLimit(DisplayDate.monthOfYearToString(feeProgram.getDateLimit()));
         model.addAttribute(feeProgram);
         model.addAttribute(feeProgramForm);
         return FEEPROGRAM_VIEW_NAME;
      }
      // If not have feeProgram, redirect to feeProgramList
      else {
         return "redirect:/feeProgramList";
      }
   }

   /**
    * Training.
    *
    * @param feeProgramForm
    *           the pay inscription form
    * @param errors
    *           the errors
    * @param ra
    *           the ra
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "feeProgramList/feeProgramEdit", method = RequestMethod.POST)
   public String feeProgram(@Valid @ModelAttribute FeeProgramForm feeProgramForm, Errors errors,
         RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return FEEPROGRAM_VIEW_NAME;
      }

      try {
         feeProgramService.update(feeProgramForm.updateFeeProgram(feeProgram));
         // It is verified that there is not exist year of feeProgram in
         // other feeProgram
      } catch (UniqueException e) {
         errors.rejectValue("date", "feeProgram.dateException", new Object[] { e.getValue() },
               "date");
         return FEEPROGRAM_VIEW_NAME;
      }

      MessageHelper.addWarningAttribute(ra, "feeProgram.successModify", feeProgramForm.getName());
      return "redirect:/feeProgramList";
   }

   /**
    * Modify FeeProgram by Id.
    *
    * @param id
    *           the id
    * @param ra
    *           the redirect atributes
    * @return the string destinity page
    */
   @RequestMapping(value = "feeProgramList/feeProgramEdit/{id}", method = RequestMethod.POST)
   public String modifyFeeProgram(@PathVariable Long id) {

      feeProgram = feeProgramService.findById(id);
      return "redirect:/feeProgramList/feeProgramEdit";
   }
}
