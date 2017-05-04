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
package org.cuacfm.members.web.feeprogram;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogramservice.FeeProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class FeeProgramCreateController. */
@Controller
public class FeeProgramCreateController {

	private static final String FEEPROGRAM_VIEW_NAME = "feeprogram/feeprogramcreate";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private FeeProgramService feeProgramService;

	/**
	 * Instantiates a new feeProgramController.
	 */
	public FeeProgramCreateController() {
		super();
	}

	/**
	 * Fee Program.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/feeProgramCreate")
	public String training(Model model) {
		FeeProgramForm feeProgramForm = new FeeProgramForm();

		String month = DateUtils.format(LocalDateTime.now(), "yyyy-MM");
		String monthDisplay = DateUtils.format(LocalDateTime.now(), "MMMM yyyy");
		String monthLimit = DateUtils.format(LocalDateTime.now().plusMonths(2), "yyyy-MM");
		String feeProgramFile = messageSource.getMessage("feeProgramName", null, Locale.getDefault());

		feeProgramForm.setName(feeProgramFile + " " + monthDisplay);
		feeProgramForm.setPrice(configurationService.getConfiguration().getFeeProgram());
		feeProgramForm.setDescription(feeProgramFile + " " + monthDisplay);
		feeProgramForm.setDate(month);
		feeProgramForm.setDateLimit(monthLimit);
		model.addAttribute(feeProgramForm);
		return FEEPROGRAM_VIEW_NAME;
	}

	/**
	 * Training.
	 *
	 * @param feeProgramForm the fee program form
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeProgramList/feeProgramCreate", method = RequestMethod.POST)
	public String feeProgram(@Valid @ModelAttribute FeeProgramForm feeProgramForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return FEEPROGRAM_VIEW_NAME;
		}

		String name = feeProgramForm.getName();
		try {
			feeProgramService.save(feeProgramForm.createFeeProgram());
			// It is verified that there is not exist year of feeProgram in other
			// feeProgram
		} catch (UniqueException e) {
			errors.rejectValue("date", "feeProgram.dateException", new Object[] { e.getValue() }, "date");
			return FEEPROGRAM_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "feeProgram.successCreate", name);
		return "redirect:/feeProgramList";
	}

}
