package org.cuacfm.members.web.trainingtype;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingTypeCreateController. */
@Controller
public class TrainingTypeCreateController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "trainingtype/trainingtypecreate";

   /** The training service. */
   @Autowired
   private TrainingTypeService trainingTypeService;

   /**
    * Instantiates a new trainingTypeController.
    */
   public TrainingTypeCreateController() {
      // Default empty constructor.
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "trainingTypeList/trainingTypeCreate")
   public String training(Model model) {
      model.addAttribute(new TrainingTypeForm());
      return TRAINING_VIEW_NAME;
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
   @RequestMapping(value = "trainingTypeList/trainingTypeCreate", method = RequestMethod.POST)
   public String trainingType(@Valid @ModelAttribute TrainingTypeForm trainingTypeForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return TRAINING_VIEW_NAME;
      }

      try {
         trainingTypeService.save(trainingTypeForm.createTrainingType());
      } catch (UniqueException e) {
         errors.rejectValue("name", "trainingType.existentName", new Object[] { e.getValue() },
               "name");
         return TRAINING_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "trainingType.successCreate",
            trainingTypeForm.getName());
      return "redirect:/trainingTypeList";
   }
}
