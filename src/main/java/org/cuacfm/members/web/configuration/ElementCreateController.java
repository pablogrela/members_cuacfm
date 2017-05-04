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

import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ElementCreateController. */
@Controller
public class ElementCreateController {

	private static final String ELEMENT_VIEW_NAME = "configuration/elementcreate";

	@Autowired
	private ElementService elementService;

	/** Instantiates a new element Controller. */
	public ElementCreateController() {
		super();
	}

	/**
	 * Get page elementCreate.
	 *
	 * @param model the model
	 * @return String to view to elementCreate
	 */
	@RequestMapping(value = "configuration/elementCreate")
	public String element(Model model) {
		model.addAttribute(new ElementForm());
		return ELEMENT_VIEW_NAME;
	}

	/**
	 * Post to create a new element.
	 *
	 * @param elementForm the element form
	 * @param errors the errors
	 * @param ra the ra
	 * @return String to redirect to elementList or if fault elementCreate
	 * 
	 */
	@RequestMapping(value = "configuration/elementCreate", method = RequestMethod.POST)
	public String elementCreate(@Valid @ModelAttribute ElementForm elementForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return ELEMENT_VIEW_NAME;
		}

		try {
			elementService.save(elementForm.createElement());
		} catch (UniqueException e) {
			errors.rejectValue("name", "element.existentName", new Object[] { e.getValue() }, "name");
			return ELEMENT_VIEW_NAME;
		}
		MessageHelper.addSuccessAttribute(ra, "element.create.success", elementForm.getName());
		return "redirect:/configuration";
	}
}
