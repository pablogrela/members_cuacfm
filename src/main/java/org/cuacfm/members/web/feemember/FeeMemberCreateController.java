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
package org.cuacfm.members.web.feemember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.Valid;

import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
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

/** The Class FeeMemberCreateController. */
@Controller
public class FeeMemberCreateController {

	private static final String FEEMEMBER_VIEW_NAME = "feemember/feemembercreate";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private FeeMemberService feeMemberService;

	/**
	 * Instantiates a new fee member Controller.
	 */
	public FeeMemberCreateController() {
		super();
	}

	/**
	 * Training.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/feeMemberCreate")
	public String feeMember(Model model) {

		FeeMemberForm feeMemberForm = new FeeMemberForm();

		String feeProgramFile = messageSource.getMessage("feeMemberName", null, Locale.getDefault());
		feeMemberForm.setName(feeProgramFile + " " + LocalDate.now().getYear());
		feeMemberForm.setPrice(configurationService.getConfiguration().getFeeMember());
		feeMemberForm.setDescription(feeProgramFile + " " + LocalDate.now().getYear());
		feeMemberForm.setYear(LocalDate.now().getYear());
		String monthLimit = DateUtils.format(LocalDateTime.now().plusMonths(2), "yyyy-MM");
		feeMemberForm.setDateLimit1(monthLimit);
		monthLimit = DateUtils.format(LocalDateTime.now().plusMonths(10), "yyyy-MM");
		feeMemberForm.setDateLimit2(monthLimit);
		model.addAttribute(feeMemberForm);

		return FEEMEMBER_VIEW_NAME;
	}

	/**
	 * Fee member.
	 *
	 * @param feeMemberForm the fee member form
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/feeMemberCreate", method = RequestMethod.POST)
	public String feeMember(@Valid @ModelAttribute FeeMemberForm feeMemberForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return FEEMEMBER_VIEW_NAME;
		}

		int year = feeMemberForm.getYear();
		String name = feeMemberForm.getName();
		try {
			feeMemberService.save(feeMemberForm.createFeeMember());
			// It is verified that there is not exist year of feeMember in
			// other feeMember
		} catch (UniqueException e) {
			errors.rejectValue("year", "feeMember.yearException", new Object[] { year }, "year");
		}

		if (errors.hasErrors()) {
			return FEEMEMBER_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "feeMember.successCreate", name);
		return "redirect:/feeMemberList";
	}

}
