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

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.util.DateUtils;
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

/** The Class FeeMemberEditController. */
@Controller
public class FeeMemberEditController {

	private static final String FEEMEMBER_VIEW_NAME = "feemember/feememberedit";

	@Autowired
	private FeeMemberService feeMemberService;

	private FeeMember feeMember;

	/**
	 * Instantiates a fee member Controller.
	 */
	public FeeMemberEditController() {
		super();
	}

	/**
	 * FeeMember.
	 *
	 * @return FeeMember
	 */
	@ModelAttribute("feeMember")
	public FeeMember feeMember() {
		return feeMember;
	}

	/**
	 * Training.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/feeMemberEdit")
	public String feeMember(Model model) {

		if (feeMember != null) {
			FeeMemberForm feeMemberForm = new FeeMemberForm();
			feeMemberForm.setName(feeMember.getName());
			feeMemberForm.setYear(feeMember.getYear());
			feeMemberForm.setPrice(feeMember.getPrice());
			feeMemberForm.setDescription(feeMember.getDescription());
			feeMemberForm.setDateLimit1(DateUtils.format(feeMember.getDateLimit1(), DateUtils.FORMAT_MONTH_YEAR));
			feeMemberForm.setDateLimit2(DateUtils.format(feeMember.getDateLimit2(), DateUtils.FORMAT_MONTH_YEAR));
			model.addAttribute(feeMember);
			model.addAttribute(feeMemberForm);
			return FEEMEMBER_VIEW_NAME;
		}
		// If not have feeMember, redirect to feeMemberList
		else {
			return "redirect:/feeMemberList";
		}
	}

	/**
	 * Training.
	 *
	 * @param feeMemberForm the fee member form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/feeMemberEdit", method = RequestMethod.POST)
	public String feeMember(@Valid @ModelAttribute FeeMemberForm feeMemberForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return FEEMEMBER_VIEW_NAME;
		}

		try {
			feeMemberService.update(feeMemberForm.updateFeeMember(feeMember));
			// It is verified that there is not exist year of feeMember in
			// other feeMember
		} catch (UniqueException e) {
			errors.rejectValue("year", "feeMember.yearException", new Object[] { feeMemberForm.getYear() }, "year");
			return FEEMEMBER_VIEW_NAME;
		}

		MessageHelper.addWarningAttribute(ra, "feeMember.successModify", feeMemberForm.getName());
		return "redirect:/feeMemberList";
	}

	/**
	 * Modify FeeMember by Id.
	 *
	 * @param id the id
	 * @param ra the redirect atributes
	 * @return the string destinity page
	 */
	@RequestMapping(value = "feeMemberList/feeMemberEdit/{id}", method = RequestMethod.POST)
	public String modifyFeeMember(@PathVariable Long id) {

		feeMember = feeMemberService.findById(id);
		return "redirect:/feeMemberList/feeMemberEdit";
	}
}
