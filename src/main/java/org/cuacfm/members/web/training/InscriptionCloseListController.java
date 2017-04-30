/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.training;

import java.util.List;

import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** The Class InscriptionCloseListController. */
@Controller
public class InscriptionCloseListController {

	private static final String TRAINING_VIEW_NAME = "training/inscriptioncloselist";

	@Autowired
	private TrainingService trainingService;

	private List<Inscription> inscriptions;
	private Training training;

	/**
	 * Instantiates a new inscriptionListController Controller.
	 */
	public InscriptionCloseListController() {
		super();
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
	 * Add list of TrainingType for show in form select.
	 *
	 * @return the list
	 */
	@ModelAttribute("inscriptions")
	public List<Inscription> inscriptions() {
		return inscriptions;
	}

	/**
	 * Show inscriptionList.
	 *
	 * @param model the model
	 * @return string the view
	 */

	@RequestMapping(value = "trainingList/inscriptionCloseList")
	public String getInscriptionCloseList(Model model) {
		if (training != null) {
			inscriptions = trainingService.getInscriptionsByTrainingId(training.getId());
			model.addAttribute("inscriptions", inscriptions);
			model.addAttribute("training", training);
			return TRAINING_VIEW_NAME;
		}
		// If not have training, redirect to trainingList
		else {
			return "redirect:/trainingList/trainingCloseList";
		}
	}

	/**
	 * Charge inscription.
	 *
	 * @param trainingId the training id
	 * @return string the view to redirect inscriptionCloseList
	 */
	@RequestMapping(value = "trainingList/inscriptionCloseList/{trainingId}", method = RequestMethod.POST)
	public String postTraining(@PathVariable Long trainingId) {
		training = trainingService.findById(trainingId);
		return "redirect:/trainingList/inscriptionCloseList";
	}
}
