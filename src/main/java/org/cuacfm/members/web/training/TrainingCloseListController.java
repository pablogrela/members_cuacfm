package org.cuacfm.members.web.training;

import java.util.List;

import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class TrainingCloseListController. */
@Controller
public class TrainingCloseListController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "training/trainingcloselist";

   /** The training service. */
   @Autowired
   private TrainingService trainingService;

   /** The trainings. */
   private List<Training> closeTrainings;

   /**
    * Instantiates a new trainingCloseController.
    */
   public TrainingCloseListController() {
      // Default empty constructor.
   }

   /**
    * Get view of close trainings.
    *
    * @param model
    *           the model
    * @return the string to view trainingCloseList
    */
   @RequestMapping(value = "trainingList/trainingCloseList")
   public String getCloseTrainings(Model model) {

      // List of close trainings to table
      closeTrainings = trainingService.getTrainingListClose();
      model.addAttribute("closeTrainings", closeTrainings);

      return TRAINING_VIEW_NAME;
   }

   /**
    * Add to view at list of Training with close=true.
    *
    * @return List<Training>
    */
   @ModelAttribute("closeTrainings")
   public List<Training> closeTrainings() {
      return closeTrainings;
   }
}
