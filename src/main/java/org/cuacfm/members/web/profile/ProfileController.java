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
package org.cuacfm.members.web.profile;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.model.userservice.UserService;
import org.cuacfm.members.web.support.DisplayDate;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aeat.valida.Validador;

/** The Class ProfileController. */
@Controller
public class ProfileController {

	private static final String PROFILE_VIEW_NAME = "profile/profile";

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private MethodPaymentService methodPaymentService;

	/** The user service. */
	@Autowired
	private UserService userService;

	private Account account = null;
	private List<BankAccount> bankAccounts;

	/**
	 * Instantiates a new Profile controller.
	 */
	public ProfileController() {
		super();
	}

	/**
	 * Bank accounts.
	 *
	 * @return the list
	 */
	@ModelAttribute("bankAccounts")
	public List<BankAccount> bankAccounts() {
		return bankAccounts;
	}

	/**
	 * Creates the profile form.
	 *
	 * @param model the model
	 * @param profileForm the profile form
	 * @return the string
	 */
	public String createProfileForm(Model model, ProfileForm profileForm) {
		profileForm.setName(account.getName());
		profileForm.setSurname(account.getSurname());
		profileForm.setNickName(account.getNickName());
		profileForm.setDni(account.getDni());
		profileForm.setAddress(account.getAddress());
		profileForm.setCp(account.getCp());
		profileForm.setProvince(account.getProvince());
		profileForm.setCodeCountry(account.getCodeCountry());
		profileForm.setLogin(account.getLogin());
		profileForm.setEmail(account.getEmail());
		profileForm.setPhone(account.getPhone());
		profileForm.setMobile(account.getMobile());
		profileForm.setProgramName(account.getProgramName());
		if (account.isStudent()) {
			profileForm.setStudentTrue(true);
		} else {
			profileForm.setStudentFalse(true);
		}
		if (account.isEmitProgram()) {
			profileForm.setEmitProgramTrue(true);
		} else {
			profileForm.setEmitProgramFalse(true);
		}
		profileForm.setPersonality(account.getPersonality());
		profileForm.setKnowledge(account.getKnowledge());
		profileForm.setDateBirth(DisplayDate.dateToString(account.getDateBirth()));
		if (account.getAccountType() != null) {
			profileForm.setAccountTypeId(account.getAccountType().getId());
		}
		profileForm.setAccountTypes(accountTypeService.getAccountTypes());
		if (account.getMethodPayment() != null) {
			profileForm.setMethodPaymentId(account.getMethodPayment().getId());
		}
		profileForm.setMethodPayments(methodPaymentService.getMethodPayments());
		profileForm.setInstallments(account.getInstallments());
		profileForm.setActive(account.isActive());
		model.addAttribute(profileForm);
		return PROFILE_VIEW_NAME;
	}

	/**
	 * Profile.
	 * 
	 * @param model the model
	 * @param principal the actual user
	 * @return the string
	 */
	@RequestMapping(value = "profile")
	public String profile(Model model, Principal principal) {
		account = accountService.findByLogin(principal.getName());
		bankAccounts = account.getBankAccounts();
		model.addAttribute("bankAccounts", bankAccounts);
		return createProfileForm(model, new ProfileForm());
	}

	/**
	 * Order Up.
	 *
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "profile/up", method = RequestMethod.POST)
	public String up(RedirectAttributes ra) {
		accountService.orderUp(account);
		MessageHelper.addSuccessAttribute(ra, "profile.info.orderUp");
		return "redirect:/profile";
	}

	/**
	 * Order Down.
	 *
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "profile/down", method = RequestMethod.POST)
	public String down(RedirectAttributes ra) {
		accountService.orderDown(account);
		MessageHelper.addErrorAttribute(ra, "profile.info.orderDown");
		return "redirect:/profile";
	}

	/**
	 * Profile.
	 *
	 * @param attribute the attribute
	 * @param error the error
	 * @param profileForm the profile form
	 * @param errors the errors
	 * @param ra the RedirectAttributes
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "profile", method = RequestMethod.POST)
	public String profile(@RequestParam(value = "attribute", required = false) String attribute,
			@RequestParam(value = "error", required = false) String error, @Valid @ModelAttribute ProfileForm profileForm, Errors errors,
			RedirectAttributes ra, Model model) {

		// If there is an error in firebase
		if (error != null) {
			errors.rejectValue(attribute, "firebase." + error, new Object[] { attribute }, attribute);
		}

		// Validate DNI
		Validador validador = new Validador();
		if (validador.checkNif(profileForm.getDni()) < 0) {
			errors.rejectValue("dni", "signup.dni.noValid", new Object[] { profileForm.getDni() }, "dni");
		}

		if (errors.hasErrors()) {
			// Return default form
			//return createProfileForm(model, profileForm);
			// Return form with wrong data
			return PROFILE_VIEW_NAME;
		}

		// Password, no use with firebase
		boolean modifyPassword = false;
		if (profileForm.isOnPassword()) {
			// check that the password and rePassword are the same
			String newPassword = profileForm.getNewPassword();
			String rePassword = profileForm.getRePassword();
			if (!newPassword.equals(rePassword)) {
				errors.rejectValue("newPassword", "signup.passwordsDontMatch");
				errors.rejectValue("rePassword", "signup.passwordsDontMatch");
				return createProfileForm(model, profileForm);
			} else {
				account.setPassword(newPassword);
				modifyPassword = true;
			}
		}

		// Login
		boolean modifyLogin = false;
		String login = profileForm.getLogin();
		if (profileForm.isOnLogin() && login != "") {
			account.setLogin(login);
			modifyLogin = true;
		}

		// Email
		String email = profileForm.getEmail();
		if (profileForm.isOnEmail() && email != "") {
			account.setEmail(profileForm.getEmail());
		}

		account.setName(profileForm.getName());
		account.setSurname(profileForm.getSurname());
		account.setNickName(profileForm.getNickName());
		account.setDni(profileForm.getDni());
		account.setAddress(profileForm.getAddress());
		account.setCp(profileForm.getCp());
		account.setProvince(profileForm.getProvince());
		account.setCodeCountry(profileForm.getCodeCountry());
		account.setPhone(profileForm.getPhone());
		account.setMobile(profileForm.getMobile());
		account.setProgramName(profileForm.getProgramName());
		account.setStudent(profileForm.isStudentTrue());
		account.setEmitProgram(profileForm.isEmitProgramTrue());
		account.setDateBirth(DisplayDate.stringToDate2(profileForm.getDateBirth()));
		account.setPersonality(profileForm.getPersonality());
		account.setKnowledge(profileForm.getKnowledge());

		// If correct
		try {
			accountService.update(account, modifyPassword, true);
		} catch (UniqueListException e) {
			for (UniqueException unique : e.getMessages()) {
				errors.rejectValue(unique.getAttribute(), "signup.existent." + unique.getAttribute(), new Object[] { unique.getValue() },
						unique.getAttribute());
			}
		}

		if (errors.hasErrors()) {
			return createProfileForm(model, profileForm);
		}

		if (modifyLogin) {
			userService.signin(account);
		}
		MessageHelper.addSuccessAttribute(ra, "profile.success");

		return "redirect:/profile";
	}

}
