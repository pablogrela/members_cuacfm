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
package org.cuacfm.members.web.paymember;

import javax.validation.Valid;

import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
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

/** The Class PayMemberEditController. */
@Controller
public class PayMemberEditController {

	//private static final Logger logger = LoggerFactory.getLogger(PayMemberEditController.class);
	private static final String PAYMEMBER_VIEW_NAME = "paymember/paymemberedit";

	@Autowired
	private PayMemberService payMemberService;

	private PayMember payMember;

	/**
	 * Instantiates a new payMember Controller.
	 */
	public PayMemberEditController() {
		super();
	}

	/**
	 * PayMember.
	 *
	 * @return PayMember
	 */
	@ModelAttribute("payMember")
	public PayMember payMember() {
		return payMember;
	}

	/**
	 * PayMember.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit")
	public String payMember(Model model) {

		if (payMember != null) {
			PayMemberForm payMemberForm = new PayMemberForm();
			payMemberForm.setPrice(payMember.getPrice());
			payMemberForm.setInstallment(payMember.getInstallment());
			payMemberForm.setInstallments(payMember.getInstallments());
			payMemberForm.setState(String.valueOf(payMember.getState()));
			payMemberForm.setStateList(java.util.Arrays.asList(states.values()));
			payMemberForm.setMethod(String.valueOf(payMember.getMethod()));
			payMemberForm.setMethodList(java.util.Arrays.asList(methods.values()));
			payMemberForm.setIdTxn(payMember.getIdTxn());
			payMemberForm.setIdPayer(payMember.getIdPayer());
			payMemberForm.setEmailPayer(payMember.getEmailPayer());
			payMemberForm.setIdPayer(payMember.getIdPayer());
			payMemberForm.setDatePay(DateUtils.format(payMember.getDatePay(), DateUtils.FORMAT_LOCAL));
			model.addAttribute(payMember);
			model.addAttribute(payMemberForm);
			return PAYMEMBER_VIEW_NAME;
		}
		// If not have payMember, redirect to payMemberList
		else {
			return "redirect:/feeMemberList/payMemberList";
		}
	}

	/**
	 * PayMember.
	 *
	 * @param payMemberForm the payMember form
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 * @throws DatesException the date limit exception
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit", method = RequestMethod.POST)
	public String payMember(@Valid @ModelAttribute PayMemberForm payMemberForm, Errors errors, RedirectAttributes ra) throws DatesException {

		if (errors.hasErrors()) {
			return PAYMEMBER_VIEW_NAME;
		}

		//		try {
		payMemberService.update(payMemberForm.updatePayMember(payMember));
		//		} catch (ExistTransactionIdException e) {
		//			logger.error("payMember", e);
		//			errors.rejectValue("idTxn", "existIdTxn.message", new Object[] { e.getIdTxn() }, "idTxn");	
		//			return PAYMEMBER_VIEW_NAME;
		//		}

		MessageHelper.addSuccessAttribute(ra, "payMember.successModify", payMemberForm.getInstallment(), payMember.getAccount().getName());
		return "redirect:/feeMemberList/payMemberList";
	}

	/**
	 * Modify PayMember by Id.
	 *
	 * @param id the id
	 * @return the string destinity page
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/payMemberEdit/{id}", method = RequestMethod.POST)
	public String modifyPayMember(@PathVariable Long id) {

		payMember = payMemberService.findById(id);
		return "redirect:/feeMemberList/payMemberList/payMemberEdit";
	}
}
