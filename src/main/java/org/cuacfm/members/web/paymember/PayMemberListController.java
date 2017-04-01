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
package org.cuacfm.members.web.paymember;

import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.training.FindUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class PayMemberListController. */
@Controller
public class PayMemberListController {

	private static final String PAYMEMBER_VIEW_NAME = "paymember/paymemberlist";
	private static final String REDIRECT_PAYMEMBER = "redirect:/feeMemberList/payMemberList";

	@Autowired
	private AccountService accountService;

	@Autowired
	private FeeMemberService feeMemberService;

	@Autowired
	private PayMemberService payMemberService;

	private FindUserForm findUserform;
	private FeeMember feeMember;
	private static List<PayMember> payMembers;
	private List<String> usernames;

	/**
	 * Instantiates a new training Controller.
	 */
	public PayMemberListController() {
		// Default empty constructor.
	}

	/**
	 * fee member.
	 *
	 * @return the fee member
	 */
	@ModelAttribute("feeMember")
	public FeeMember feeMember() {
		return feeMember;
	}

	/**
	 * List of PayMember.
	 *
	 * @return List<PayMember>
	 */
	@ModelAttribute("payMembers")
	public List<PayMember> payMembers() {
		return payMembers;
	}

	/**
	 * User fee members.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/payMemberList")
	public String getPayMembers(Model model) {
		if (feeMember != null) {
			findUserform = new FindUserForm();
			model.addAttribute(findUserform);
			payMembers = payMemberService.getPayMemberListByFeeMemberId(feeMember.getId());
			model.addAttribute("payMembers", payMembers);
			usernames = payMemberService.getUsernamesByFeeMember(feeMember.getId());
			model.addAttribute("usernames", usernames);

			return PAYMEMBER_VIEW_NAME;
		} else {
			return "redirect:/feeMemberList";
		}
	}

	/**
	 * View user fee members by fee member id.
	 *
	 * @param feeMemberId the fee member id
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/{feeMemberId}", method = RequestMethod.POST)
	public String viewPayMembersByFeeMemberId(@PathVariable Long feeMemberId) {
		feeMember = feeMemberService.findById(feeMemberId);
		return REDIRECT_PAYMEMBER;
	}

	/**
	 * Join account to PayMember.
	 *
	 * @param findUserForm the find user form
	 * @param errors the errors
	 * @param ra the redirect atributes
	 * @param model the model
	 * @return the string destinity page to page trainingList
	 */
	@RequestMapping(value = "feeMemberList/payMemberList", method = RequestMethod.POST)
	public String addUserToPayMemberList(@Valid @ModelAttribute FindUserForm findUserForm, Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("usernames", usernames);
			return PAYMEMBER_VIEW_NAME;
		}

		String name = findUserForm.getLogin();
		Long id = Long.valueOf(0);
		if (name.contains(": ")) {
			String[] parts = findUserForm.getLogin().split(": ");
			id = Long.valueOf(parts[0]);
			name = parts[1].split(" - ")[0].trim();
		}

		// Check if account exist
		Account account = accountService.findById(id);
		if (account == null) {
			errors.rejectValue("login", "inscription.noExistLogin", new Object[] { name }, "login");
			model.addAttribute("usernames", usernames);
			return PAYMEMBER_VIEW_NAME;
		}

		// Check if account already payMember
		List<PayMember> payMembersSearched = payMemberService.findByPayMemberIds(account.getId(), feeMember.getId());
		if (!payMembersSearched.isEmpty()) {
			errors.rejectValue("login", "payMember.alreadyExistLogin", new Object[] { name }, "login");
			model.addAttribute("usernames", usernames);
			return PAYMEMBER_VIEW_NAME;
		}

		feeMemberService.savePayMember(account, feeMember);
		MessageHelper.addSuccessAttribute(ra, "payMember.successJoin", name);
		return REDIRECT_PAYMEMBER;
	}

	/**
	 * Pay bill.
	 *
	 * @param payMemberId the user fee member id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/pay/{payMemberId}", method = RequestMethod.POST)
	public String pay(@PathVariable Long payMemberId, RedirectAttributes ra) {
		PayMember payMember = payMemberService.findById(payMemberId);
		payMemberService.pay(payMember);

		MessageHelper.addSuccessAttribute(ra, "payMember.successPay", payMember.getInstallment(), payMember.getAccount().getName());
		return REDIRECT_PAYMEMBER;
	}

	/**
	 * Creates the pdf.
	 *
	 * @param feeMemberId the fee member id
	 * @param createPdf the create pdf
	 * @return the response entity
	 */
	@RequestMapping(value = "feeMemberList/payMemberList/createPdf/{feeMemberId}", method = RequestMethod.POST, params = { "createPdf" })
	public ResponseEntity<byte[]> createPdf(@PathVariable Long feeMemberId, @RequestParam("createPdf") String createPdf) {
		return payMemberService.createPdfFeeMember(feeMemberId, createPdf);
	}

}
