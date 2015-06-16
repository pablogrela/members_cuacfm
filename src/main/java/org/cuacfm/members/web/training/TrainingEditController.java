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

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.DateLimitException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class TrainingEditController. */
@Controller
public class TrainingEditController {

   /** The Constant TRAINING_VIEW_NAME. */
   private static final String TRAINING_VIEW_NAME = "training/trainingedit";

   /** The training service. */
   @Autowired
   private TrainingService trainingService;

   /** The Global variable training. */
   private Training training;

   /**
    * Instantiates a new training Controller.
    */
   public TrainingEditController() {
      // Default empty constructor.
   }

   /**
    * Training.
    *
    * @return Training
    */
   @ModelAttribute("training")
   public Training training() {
      return training;
   }

   /**
    * Training.
    *
    * @param model
    *           the model
    * @return the string
    */
   @RequestMapping(value = "trainingList/trainingEdit")
   public String training(Model model) {

      if (training != null) {
         TrainingForm trainingForm = new TrainingForm();
         trainingForm.setName(training.getName());
         trainingForm.setTimeLimit(DisplayDate.timeToString(training.getDateLimit()));
         trainingForm.setDateLimit(DisplayDate.dateToString(training.getDateLimit()));
         trainingForm.setTimeTraining(DisplayDate.timeToString(training.getDateTraining()));
         trainingForm.setDateTraining(DisplayDate.dateToString(training.getDateTraining()));
         trainingForm.setDescription(training.getDescription());
         trainingForm.setPlace(training.getPlace());
         trainingForm.setDuration(training.getDuration());
         trainingForm.setCountPlaces(training.getCountPlaces());
         trainingForm.setMaxPlaces(training.getMaxPlaces());
         trainingForm.setClose(training.isClose());
         model.addAttribute(training);
         model.addAttribute(trainingForm);
         return TRAINING_VIEW_NAME;
      }
      // If not have training, redirect to trainingList
      else {
         return "redirect:/trainingList";
      }
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
    * @throws DateLimitException
    */

   @RequestMapping(value = "trainingList/trainingEdit", method = RequestMethod.POST)
   public String training(@Valid @ModelAttribute TrainingForm trainingForm, Errors errors,
         RedirectAttributes ra) {

      if (trainingForm.getCountPlaces() > trainingForm.getMaxPlaces()) {
         errors.rejectValue("countPlaces", "training.countPlacesException",
               new Object[] { trainingForm.getMaxPlaces() }, "maxPlaces");
      }

      if (errors.hasErrors()) {
         return TRAINING_VIEW_NAME;
      }

      // Modify Training
      String timeLimit = trainingForm.getTimeLimit();
      String dateLimit = trainingForm.getDateLimit();
      String timeTraining = trainingForm.getTimeTraining();
      String dateTraining = trainingForm.getDateTraining();
      training.setName(trainingForm.getName());
      training.setDateLimit(DisplayDate.stringToDate(timeLimit + "," + dateLimit));
      training.setDateTraining(DisplayDate.stringToDate(timeTraining + "," + dateTraining));
      training.setDescription(trainingForm.getDescription());
      training.setPlace(trainingForm.getPlace());
      training.setDuration(trainingForm.getDuration());
      training.setCountPlaces(trainingForm.getCountPlaces());
      training.setMaxPlaces(trainingForm.getMaxPlaces());
      training.setClose(trainingForm.getClose());

      try {
         trainingService.update(training);
      } catch (DateLimitException e) {
         errors.rejectValue("dateLimit", "dateLimit.message", new Object[] { e.getDateTraining() },
               "dateTraining");
         return TRAINING_VIEW_NAME;
      }

      MessageHelper.addSuccessAttribute(ra, "training.successModify", trainingForm.getName());
      return "redirect:/trainingList";
   }

   /**
    * Modify Training by Id.
    *
    * @param @PathVariable Long id the training form
    * @return the string destinity page
    */
   @RequestMapping(value = "trainingList/trainingEdit/{id}", method = RequestMethod.POST)
   public String modifyTraining(@PathVariable Long id) {

      training = trainingService.findById(id);
      return "redirect:/trainingList/trainingEdit";
   }
}
