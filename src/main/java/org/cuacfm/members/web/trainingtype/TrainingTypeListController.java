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

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.trainingtype.TrainingType;
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

/** The Class TrainingTypeListController. */
@Controller
public class TrainingTypeListController {

	private static final String TRAINING_VIEW_NAME = "trainingtype/trainingtypelist";

	@Autowired
	private TrainingTypeService trainingTypeService;

	private List<TrainingType> trainingTypes;

	/**
	 * Instantiates a new training Controller.
	 */
	public TrainingTypeListController() {
		// Default empty constructor.
	}

	/**
	 * Show TrainingType List.
	 *
	 * @param model the model
	 * @return the string the view
	 */

	@RequestMapping(value = "trainingTypeList")
	public String trainings(Model model) {
		// List of Training Types
		trainingTypes = trainingTypeService.getTrainingTypeList();
		model.addAttribute("trainingTypes", trainingTypes);
		return TRAINING_VIEW_NAME;
	}

	/**
	 * List of TrainingType.
	 *
	 * @return List<TrainingType>
	 */
	@ModelAttribute("trainingTypes")
	public List<TrainingType> trainingTypes() {
		return trainingTypes;
	}

	/**
	 * Delete TrainingType by Id.
	 *
	 * @param @PathVariable Long id
	 * 
	 * @param ra the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "trainingTypeList/trainingTypeDelete/{id}", method = RequestMethod.POST)
	public String remove(@PathVariable Long id, RedirectAttributes ra) {

		TrainingType trainingType = trainingTypeService.findById(id);

		// If Exist Dependencies Trainings
		try {
			trainingTypeService.delete(trainingType);
			MessageHelper.addInfoAttribute(ra, "trainingType.successDelete", trainingType.getName());
		} catch (ExistTrainingsException e) {
			MessageHelper.addErrorAttribute(ra, "trainingType.existDependenciesTrainings", trainingType.getName());
		}

		return "redirect:/trainingTypeList";
	}
}
