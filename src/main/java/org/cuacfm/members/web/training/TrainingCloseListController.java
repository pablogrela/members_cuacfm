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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

   /** The year show. */
   private Integer yearShow = LocalDate.now().getYear();

   /**
    * Instantiates a new trainingCloseController.
    */
   public TrainingCloseListController() {
      // Default empty constructor.
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

   /**
    * Year show.
    *
    * @return the integer
    */
   @ModelAttribute("yearShow")
   public Integer yearShow() {
      return yearShow;
   }

   /**
    * Years.
    *
    * @return the list
    */
   @ModelAttribute("years")
   public List<Integer> years() {
      List<Integer> years = new ArrayList<Integer>();
      for (int i = 2015; i <= LocalDate.now().getYear(); i++) {
         years.add(i);
      }
      return years;
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
      model.addAttribute("yearShow ", yearShow);
      closeTrainings = trainingService.getTrainingListClose(yearShow);
      model.addAttribute("closeTrainings", closeTrainings);

      return TRAINING_VIEW_NAME;
   }

   /**
    * Gets the close trainings close by year.
    *
    * @param year
    *           the year
    * @return the close trainings close by year
    */
   @RequestMapping(value = "trainingList/trainingCloseList", method = RequestMethod.POST)
   public String getCloseTrainingsCloseByYear(@RequestParam("year") int year) {
      yearShow = year;
      return "redirect:/trainingList/trainingCloseList";
   }

}
