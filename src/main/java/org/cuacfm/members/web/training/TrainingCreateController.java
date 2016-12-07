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
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
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

/** The Class TrainingCreateController. */
@Controller
public class TrainingCreateController {

	private static final String TRAINING_VIEW_NAME = "training/trainingcreate";

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private TrainingTypeService trainingTypeService;

	private TrainingType trainingType;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingCreateController() {
		// Default empty constructor.
	}

	/**
	 * Training.
	 *
	 * @return Training
	 */
	@ModelAttribute("trainingType")
	public TrainingType trainingType() {
		return trainingType;
	}

	/**
	 * Get page trainingCreate.
	 *
	 * @param model the model
	 * @return String to view to trainingCreate
	 */
	@RequestMapping(value = "trainingList/trainingCreate")
	public String training(Model model) {

		if (trainingType != null) {
			TrainingForm trainingForm = new TrainingForm();
			trainingForm.setName(trainingType.getName());
			trainingForm.setDescription(trainingType.getDescription());
			trainingForm.setPlace(trainingType.getPlace());
			trainingForm.setDuration(trainingType.getDuration());
			model.addAttribute(trainingForm);
			return TRAINING_VIEW_NAME;
		}
		// If not have trainingType, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * Add trainingType at form
	 *
	 * @param trainingSelectForm the training select form
	 * @return String to redirect to trainingCreate
	 */
	@RequestMapping(value = "trainingList/trainingLoad", method = RequestMethod.POST)
	public String addTrainingType(@ModelAttribute TrainingSelectForm trainingSelectForm) {

		trainingType = trainingTypeService.findById(trainingSelectForm.getTrainingTypeId());
		return "redirect:/trainingList/trainingCreate";
	}

	/**
	 * Post to create a new training.
	 *
	 * @param trainingForm the training form
	 * @param errors the errors
	 * @param ra the ra
	 * @return String to redirect to trainingList or if fault trainingCreate
	 * @throws UniqueException
	 * 
	 * @throws DateLimitException
	 */
	@RequestMapping(value = "trainingList/trainingCreate", method = RequestMethod.POST)
	public String trainingCreate(@Valid @ModelAttribute TrainingForm trainingForm, Errors errors, RedirectAttributes ra) throws UniqueException {

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}

		try {
			trainingService.save(trainingForm.createTraining(trainingType));
		} catch (DateLimitException e) {
			errors.rejectValue("dateLimit", "dateLimit.message", new Object[] { e.getDateTraining() }, "dateTraining");
			return TRAINING_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "training.successCreate", trainingForm.getName());
		return "redirect:/trainingList";
	}
}
