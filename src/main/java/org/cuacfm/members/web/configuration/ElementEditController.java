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
package org.cuacfm.members.web.configuration;

import javax.validation.Valid;

import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
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

/** The Class ElementEditController. */
@Controller
public class ElementEditController {

	private static final String ELEMENT_VIEW_NAME = "configuration/elementedit";

	@Autowired
	private ElementService elementService;

	private Element element;

	/** Instantiates a new element Controller. */
	public ElementEditController() {
		super();
	}

	/**
	 * Get page elementEdit.
	 *
	 * @param model the model
	 * @return String to view to elementEdit
	 */
	@RequestMapping(value = "configuration/elementEdit")
	public String element(Model model) {
		if (element != null) {
			ElementForm elementForm = new ElementForm();
			elementForm.setName(element.getName());
			elementForm.setDescription(element.getDescription());
			elementForm.setReservable(element.isReservable());
			elementForm.setLocation(element.isLocation());
			model.addAttribute(elementForm);
			return ELEMENT_VIEW_NAME;
		}
		// If not have element, redirect to configuration
		else {
			return "redirect:/configuration";
		}
	}

	/**
	 * Post to create a new element.
	 *
	 * @param elementForm the element form
	 * @param errors the errors
	 * @param ra the ra
	 * @return String to redirect to elementList or if fault elementEdit
	 * 
	 */
	@RequestMapping(value = "configuration/elementEdit", method = RequestMethod.POST)
	public String elementEdit(@Valid @ModelAttribute ElementForm elementForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return ELEMENT_VIEW_NAME;
		}

		try {
			elementService.update(elementForm.updateElement(element));
		} catch (UniqueException e) {
			errors.rejectValue("name", "element.existentName", new Object[] { e.getValue() }, "name");
			return ELEMENT_VIEW_NAME;
		}
		MessageHelper.addWarningAttribute(ra, "element.edit.success", elementForm.getName());
		return "redirect:/configuration";
	}

	/**
	 * Charge account type.
	 *
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = "configuration/elementEdit/{id}", method = RequestMethod.POST)
	public String chargeElement(@PathVariable Long id) {
		element = elementService.findById(id);
		return "redirect:/configuration/elementEdit";
	}
}
