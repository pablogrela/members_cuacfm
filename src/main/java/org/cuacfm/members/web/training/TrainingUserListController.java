package org.cuacfm.members.web.training;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class TrainingListController. */
@Controller
public class TrainingUserListController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "training/traininguserlist";

   /** The training service. */
   @Autowired
   private AccountService accountService;

   /** The training service. */
   @Autowired
   private TrainingService trainingService;

   /** The inscriptions. */
   private List<Inscription> inscriptions;

   /**
    * Instantiates a new training Controller.
    */
   public TrainingUserListController() {
      // Default empty constructor.
   }

   /**
    * List of Training.
    *
    * @return the list
    */
   @ModelAttribute("inscriptions")
   public List<Inscription> inscriptions() {
      return inscriptions;
   }

   /**
    * Trainings.
    *
    * @param model
    *           the model
    * @param principal
    *           Principal
    * @return the string
    */

   @RequestMapping(value = "trainingUserList")
   public String trainings(Model model, Principal principal) {

      // List of Trainings
      Long accountId = accountService.findByLogin(principal.getName()).getId();
      inscriptions = trainingService.getInscriptionsByAccountId(accountId);
      model.addAttribute("inscriptions", inscriptions);

      return TRAINING_VIEW_NAME;
   }
}
