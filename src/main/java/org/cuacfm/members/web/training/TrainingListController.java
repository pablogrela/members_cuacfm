/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.training;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingListController. */
@Controller
public class TrainingListController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "training/traininglist";

   /** The Constant REDIRECT_TRAINING. */
   private static final String REDIRECT_TRAINING = "redirect:/trainingList";
   
   @Autowired
   private AccountService accountService;

   @Autowired
   private TrainingTypeService trainingTypeService;

   @Autowired
   private TrainingService trainingService;

   private List<Training> trainings;

   private List<Long> trainingAccountIds;

   private List<Long> trainingUnsubscribeIds;

   /**
    * Instantiates a new training Controller.
    */
   public TrainingListController() {
      // Default empty constructor.
   }

   /**
    * List of Training Ids trainings to which the user joined.
    *
    * @return List<Long> the id list of account joined
    */
   @ModelAttribute("trainingAccountIds")
   public List<Long> trainingAccountIds() {
      return trainingAccountIds;
   }

   /**
    * List of Training Ids unsubscribe to which the user can not join neither
    * remove join.
    *
    * @return List<Long> the id list of account joined
    */
   @ModelAttribute("trainingUnsubscribeIds")
   public List<Long> trainingUnsubscribeIds() {
      return trainingUnsubscribeIds;
   }

   /**
    * List of Training for show in table.
    *
    * @return the list
    */
   @ModelAttribute("trainings")
   public List<Training> trainings() {
      return trainings;
   }

   /**
    * Get page trainingList.
    *
    * @param model
    *           the model
    * @param principal
    *           Principal
    * @return the string to view page trainingList
    */
   @RequestMapping(value = "trainingList")
   public String trainings(Model model, Principal principal) {

      // List of Trainings for table
      trainings = trainingService.getTrainingListOpen();
      model.addAttribute("trainings", trainings);

      // Ids trainings to which the user joined
      Account account = accountService.findByLogin(principal.getName());
      trainingAccountIds = trainingService.getInscriptionsIdsByAccountId(account.getId());
      model.addAttribute("trainingAccountIds", trainingAccountIds);

      // Ids trainings unsubscribe to which the user not join and remove join
      trainingUnsubscribeIds = trainingService.getUnsubscribeIdsByAccountId(account.getId());
      model.addAttribute("trainingUnsubscribeIds", trainingUnsubscribeIds);

      // Insert Training Type List in Select ()
      TrainingSelectForm trainingSelectForm = new TrainingSelectForm();
      trainingSelectForm.setTrainingTypes(trainingTypeService.getTrainingTypeList());
      model.addAttribute(trainingSelectForm);

      return TRAINING_VIEW_NAME;
   }

   /**
    * Delete Training by Id.
    *
    * @param @PathVariable Long id
    * 
    * @param ra
    *           the redirect atributes
    * @return the string destinity page to page trainingList
    * @throws UniqueException
    */
   @RequestMapping(value = "trainingList/trainingDelete/{id}", method = RequestMethod.POST)
   public String remove(RedirectAttributes ra, @PathVariable Long id) throws UniqueException {

     Training training = trainingService.findById(id);
      try {
         trainingService.delete(training);
         MessageHelper.addInfoAttribute(ra, "training.successDelete", training.getName());
      } catch (ExistInscriptionsException e) {
         MessageHelper.addErrorAttribute(ra, "training.existDependenciesTrainingsException", training.getName());
      }
      return REDIRECT_TRAINING;
   }

   /**
    * Join account to training by trainingId.
    *
    * @param @PathVariable Long trainingId
    * 
    * @param ra
    *           the redirect atributes
    * @return the string destinity page to page trainingList
    */
   @RequestMapping(value = "trainingUserList/trainingJoin/{trainingId}", method = RequestMethod.POST)
   public String joinUser(@PathVariable Long trainingId, RedirectAttributes ra, Principal principal) {

      Long accountId = accountService.findByLogin(principal.getName()).getId();
      Training training = trainingService.findById(trainingId);

      try {
         trainingService.createInscription(accountId, trainingId);
         MessageHelper.addSuccessAttribute(ra, "training.successJoin", training.getName());
      } catch (UserAlreadyJoinedException e) {
         MessageHelper.addErrorAttribute(ra, "inscription.alreadyExistLogin", e.getName());
      } catch (MaximumCapacityException e) {
         MessageHelper.addErrorAttribute(ra, "training.maxInscriptionsException",
               training.getName());
      } catch (DateLimitExpirationException e) {
         MessageHelper.addErrorAttribute(ra, "training.dateLimitExpirationException",
               e.getTrainingName(), e.getDateLimit());
      }

      return REDIRECT_TRAINING;
   }

   /**
    * Remove join account to training by trainingId.
    *
    * @param @PathVariable Long trainingId
    * 
    * @param ra
    *           the redirect atributes
    * @param principal
    *           Principal
    * @return the string destinity page to page trainingList
    */
   @RequestMapping(value = "trainingUserList/trainingRemoveJoin/{trainingId}", method = RequestMethod.POST)
   public String removeJoinUser(@PathVariable Long trainingId, RedirectAttributes ra,
         Principal principal) {

      Long accountId = accountService.findByLogin(principal.getName()).getId();
      Training training = trainingService.findById(trainingId);

      try {
         trainingService.unsubscribeInscription(accountId, trainingId);
         MessageHelper.addErrorAttribute(ra, "training.removeJoin", training.getName());
      } catch (UnsubscribeException e) {
         MessageHelper.addErrorAttribute(ra, "training.unsubscribeInscriptionException",
               e.getNameTraining());
      }

      return REDIRECT_TRAINING;
   }
}
