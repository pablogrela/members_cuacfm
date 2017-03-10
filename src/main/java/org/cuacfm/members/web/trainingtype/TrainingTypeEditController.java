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
package org.cuacfm.members.web.trainingtype;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
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

/** The Class TrainingTypeEditController. */
@Controller
// @Secured("ROLE_ADMIN")
public class TrainingTypeEditController {

	private static final String TRAINING_VIEW_NAME = "trainingtype/trainingtypedit";

	@Autowired
	private TrainingTypeService trainingTypeService;

	private TrainingType trainingType;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingTypeEditController() {
		super();
	}

	/**
	 * Training.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeEdit")
	public String training(Model model) {

		if (trainingType != null) {
			TrainingTypeForm trainingTypeForm = new TrainingTypeForm();
			trainingTypeForm.setName(trainingType.getName());
			trainingTypeForm.setRequired(trainingType.isRequired());
			trainingTypeForm.setDescription(trainingType.getDescription());
			trainingTypeForm.setPlace(trainingType.getPlace());
			trainingTypeForm.setDuration(trainingType.getDuration());
			model.addAttribute(trainingType);
			model.addAttribute(trainingTypeForm);
			return TRAINING_VIEW_NAME;
		}
		// If not have trainingType, redirect to trainingList
		else {
			return "redirect:/trainingList";
		}
	}

	/**
	 * TrainingType.
	 *
	 * @return TrainingType
	 */
	@ModelAttribute("trainingType")
	public TrainingType trainingType() {
		return trainingType;
	}

	/**
	 * Training.
	 *
	 * @param trainingTypeForm the training type form
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */

	@RequestMapping(value = "trainingTypeList/trainingTypeEdit", method = RequestMethod.POST)
	public String training(@Valid @ModelAttribute TrainingTypeForm trainingTypeForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return TRAINING_VIEW_NAME;
		}

		try {
			trainingTypeService.update(trainingTypeForm.updateTrainingType(trainingType));
		} catch (UniqueException e) {
			errors.rejectValue("name", "trainingType.existentName", new Object[] { e.getValue() }, "name");
			return TRAINING_VIEW_NAME;
		}

		MessageHelper.addWarningAttribute(ra, "trainingType.successModify", trainingTypeForm.getName());
		return "redirect:/trainingTypeList";
	}

	/**
	 * Modify TrainingType by Id.
	 *
	 * @param id the id
	 * @return the string destinity page
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeEdit/{id}", method = RequestMethod.POST)
	public String modifyTraining(@PathVariable Long id) {

		trainingType = trainingTypeService.findById(id);
		return "redirect:/trainingTypeList/trainingTypeEdit";
	}
}
