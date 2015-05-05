package org.cuacfm.members.web.training;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class InscriptionListController. */
@Controller
public class InscriptionListController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "training/inscriptionlist";

   /** The account service. */
   @Autowired
   private AccountService accountService;

   /** The TrainingTypeService. */
   @Autowired
   private TrainingService trainingService;

   /** The inscriptions. */
   private List<Inscription> inscriptions;

   /** The inscriptions form. */
   private InscriptionsForm inscriptionsForm;

   /** The usersNames. */
   private List<String> usernames;

   /** The users. */
   private List<Account> users;

   /** The training. */
   private Training training;

   /**
    * Instantiates a new inscriptionListController Controller.
    */
   public InscriptionListController() {
      // Default empty constructor.
   }

   /**
    * Add training to view.
    *
    * @return Training
    */
   @ModelAttribute("training")
   public Training training() {
      return training;
   }

   /**
    * Add training to view.
    *
    * @return Training
    */
   @ModelAttribute("inscriptionsForm")
   public InscriptionsForm inscriptionsForm() {
      return inscriptionsForm;
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
    * Show inscriptionList.
    *
    * @param model
    *           the model
    * @return string the view
    */
   @RequestMapping(value = "trainingList/inscriptionList")
   public String getInscriptionList(Model model) {
      if (training != null) {

         training = trainingService.findById(training.getId());
         model.addAttribute("training", training);

         inscriptions = trainingService.getInscriptionsByTrainingId(training.getId());
         inscriptionsForm = new InscriptionsForm();
         inscriptionsForm.setInscriptions(inscriptions);
         model.addAttribute(inscriptionsForm);
         usernames = trainingService.getUsernamesByInscription(training.getId());
         model.addAttribute("usernames", usernames);

         users = accountService.getUsers();
         model.addAttribute("users", users);

         model.addAttribute(new FindUserForm());
         return TRAINING_VIEW_NAME;
      }
      // If not have training, redirect to trainingList
      else {
         return "redirect:/trainingList";
      }
   }

   /**
    * Charge inscription.
    *
    * @param trainingId
    *           the training id
    * @return string the view
    */
   @RequestMapping(value = "trainingList/inscriptionList/{trainingId}", method = RequestMethod.POST)
   public String postTraining(@PathVariable Long trainingId) {
      training = trainingService.findById(trainingId);
      return "redirect:/trainingList/inscriptionList";
   }

   /**
    * Save or update depend on submit.
    *
    * @param inscriptionsForm
    *           the inscriptions form
    * @param submit
    *           the submit
    * @param ra
    *           the ra
    * @return the string
    * @throws DateLimitException
    *            the date limit exception
    */
   @RequestMapping(value = "trainingList/inscriptionList/save", method = RequestMethod.POST)
   public String save(@Valid @ModelAttribute InscriptionsForm inscriptionsForm,
         @RequestParam("submit") String submit, RedirectAttributes ra) throws DateLimitException {

      List<Inscription> insUpdate = inscriptionsForm.getInscriptions();
      // System.out.println("inscriptionsForm: " + inscriptionsForm);
      // System.out.println("inscriptionsForm inscriptions: " +
      // inscriptionsForm.getInscriptions());
      // System.out.println("inscriptions: " + inscriptions);
      // System.out.println("training: " + training);
      // System.out.println("insUpdate: " + insUpdate);
      // System.out.println("submit: " + submit);

      boolean modify = false;
      int count = training.getCountPlaces();

      for (int index = 0; index < insUpdate.size(); index++) {

         if (insUpdate.get(index).getNote() != inscriptions.get(index).getNote()) {
            inscriptions.get(index).setNote(insUpdate.get(index).getNote());
            modify = true;
         }
         if (insUpdate.get(index).isAttend() != inscriptions.get(index).isAttend()) {
            inscriptions.get(index).setAttend(insUpdate.get(index).isAttend());
            modify = true;
         }
         if (insUpdate.get(index).isPass() != inscriptions.get(index).isPass()) {
            inscriptions.get(index).setPass(insUpdate.get(index).isPass());
            modify = true;
         }
         if (insUpdate.get(index).isUnsubscribe() != inscriptions.get(index).isUnsubscribe()) {
            inscriptions.get(index).setUnsubscribe(insUpdate.get(index).isUnsubscribe());
            if (insUpdate.get(index).isUnsubscribe()) {
               count = count - 1;
            } else {
               count = count + 1;
            }
            modify = true;
         }
         if (modify) {
            trainingService.updateInscription(inscriptions.get(index));
            modify = false;
         }
      }
      training.setCountPlaces(count);

      // If update
      if (submit.contains("update")) {
         trainingService.update(training);
         MessageHelper.addSuccessAttribute(ra, "training.successModify", training.getName());
         return "redirect:/trainingList/inscriptionList";
      }
      // If save
      else {
         training.setClose(true);
         trainingService.update(training);
         MessageHelper.addSuccessAttribute(ra, "training.successClose", training.getName());
         return "redirect:/trainingList";
      }
   }

   /**
    * Join account to training by trainingId.
    *
    * @param findUserForm
    *           the find user form
    * @param errors
    *           the errors
    * @param ra
    *           the redirect atributes
    * @param model
    *           the model
    * @return the string destinity page to page trainingList
    */
   @RequestMapping(value = "trainingList/inscriptionList", method = RequestMethod.POST)
   public String addUserToInscriptionList(@Valid @ModelAttribute FindUserForm findUserForm,
         Errors errors, RedirectAttributes ra) {

      if (errors.hasErrors()) {
         return TRAINING_VIEW_NAME;
      }

      String name = findUserForm.getLogin();
      Long id = Long.valueOf(0);
      if (name.contains(": ")) {
         String[] parts = findUserForm.getLogin().split(": ");
         id = Long.valueOf(parts[0]);
         name = parts[1].split(" - ")[0].trim();
      }

      // Check if account exist
      Account account = accountService.findById(id);
      if (account == null) {
         errors.rejectValue("login", "inscription.noExistLogin", new Object[] { name }, "login");
         return TRAINING_VIEW_NAME;
      }

      try {
         trainingService.createInscription(id, training.getId());
         MessageHelper.addSuccessAttribute(ra, "inscription.successJoin", name);

      } catch (UserAlreadyJoinedException e) {
         errors.rejectValue("login", "inscription.alreadyExistLogin", new Object[] { e.getName() },
               "login");
         return TRAINING_VIEW_NAME;

      } catch (MaximumCapacityException e) {
         MessageHelper.addErrorAttribute(ra, "training.maxInscriptionsException",
               training.getName());

      } catch (DateLimitExpirationException e) {
         MessageHelper.addErrorAttribute(ra, "training.dateLimitExpirationException",
               training.getName(), DisplayDate.dateTimeToString(training.getDateLimit()));
      }

      return "redirect:/trainingList/inscriptionList";
   }

}
