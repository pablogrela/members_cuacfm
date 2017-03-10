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
package org.cuacfm.members.web.configuration;

import javax.validation.Valid;

import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
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

/** The Class MethodPaymentCreateController. */
@Controller
public class MethodPaymentCreateController {

	private static final String METHODPAYMENT_VIEW_NAME = "configuration/methodpaymentcreate";

	@Autowired
	private MethodPaymentService methodPaymentService;

	/**
	 * Instantiates a new methodPayment Controller.
	 */
	public MethodPaymentCreateController() {
		super();
	}

	/**
	 * Get page methodPaymentCreate.
	 *
	 * @param model the model
	 * @return String to view to methodPaymentCreate
	 */
	@RequestMapping(value = "configuration/methodPaymentCreate")
	public String methodPayment(Model model) {
		model.addAttribute(new MethodPaymentForm());
		return METHODPAYMENT_VIEW_NAME;
	}

	/**
	 * Post to create a new methodPayment.
	 *
	 * @param methodPaymentForm the methodPayment form
	 * @param errors the errors
	 * @param ra the ra
	 * @return String to redirect to configuration or if fault methodPaymentCreate
	 * 
	 */
	@RequestMapping(value = "configuration/methodPaymentCreate", method = RequestMethod.POST)
	public String methodPaymentCreate(@Valid @ModelAttribute MethodPaymentForm methodPaymentForm, Errors errors, RedirectAttributes ra) {

		if (errors.hasErrors()) {
			return METHODPAYMENT_VIEW_NAME;
		}

		try {
			methodPaymentService.save(methodPaymentForm.createMethodPayment());
		} catch (UniqueException e) {
			errors.rejectValue("name", "methodPayment.existentName", new Object[] { e.getValue() }, "name");
			return METHODPAYMENT_VIEW_NAME;
		}
		MessageHelper.addSuccessAttribute(ra, "methodPayment.successCreate", methodPaymentForm.getName());
		return "redirect:/configuration";
	}
}
