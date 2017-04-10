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
package org.cuacfm.members.web.incidence;

import javax.validation.Valid;

import org.cuacfm.members.model.incidence.Incidence;
import org.cuacfm.members.model.incidenceservice.IncidenceService;
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

/** The Class IncidenceEditController. */
@Controller
public class IncidenceEditController {

	private static final String PROGRAM_VIEW_NAME = "incidence/incidenceedit";

	@Autowired
	private IncidenceService incidenceService;

	private Incidence incidence;
	private IncidenceForm incidenceForm;

	/**
	 * Instantiates a new incidence edit controller.
	 */
	public IncidenceEditController() {
		super();
	}

	/**
	 * Incidence form.
	 *
	 * @return the incidence form
	 */
	@ModelAttribute("incidenceForm")
	public IncidenceForm incidenceForm() {
		return incidenceForm;
	}

	/**
	 * Incidence.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceEdit")
	public String incidence(Model model) {

		if (incidence != null) {
			incidenceForm = new IncidenceForm();
			incidenceForm.setProgramId(incidence.getProgram().getId());			
			incidenceForm.setDirt(incidence.getDirt());
			incidenceForm.setTidy(incidence.getTidy());
			incidenceForm.setConfiguration(incidence.getConfiguration());
			incidenceForm.setOpenDoor(incidence.isOpenDoor());
			incidenceForm.setViewMembers(incidence.isViewMembers());
			incidenceForm.setDescription(incidence.getDescription());
			model.addAttribute(incidenceForm);
			return PROGRAM_VIEW_NAME;
		}
		// If not have incidence, redirect to incidenceList
		else {
			return "redirect:/incidenceList";
		}
	}

	/**
	 * Incidence.
	 *
	 * @param incidenceForm the incidence form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceEdit", method = RequestMethod.POST, params = { "edit" })
	public String incidence(@Valid @ModelAttribute IncidenceForm incidenceForm, Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return PROGRAM_VIEW_NAME;
		}
		try {
			incidenceForm.updateIncidence(incidence);
			incidenceService.update(incidence);
		} catch (Exception e) {
			errors.rejectValue("name", "incidence.existentName", new Object[] { e }, "name");
			return PROGRAM_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "incidence.successModify", incidenceForm.getProgram().getName());
		return "redirect:/incidenceList";
	}

	/**
	 * Modify incidence.
	 *
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceEdit/{id}", method = RequestMethod.POST)
	public String modifyIncidence(@PathVariable Long id) {

		incidence = incidenceService.findById(id);
		return "redirect:/incidenceList/incidenceEdit";
	}
}
