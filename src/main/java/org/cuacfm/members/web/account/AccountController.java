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
package org.cuacfm.members.web.account;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.accounttypeservice.AccountTypeService;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.methodpaymentservice.MethodPaymentService;
import org.cuacfm.members.web.profile.ProfileForm;
import org.cuacfm.members.web.support.DisplayDate;
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

import com.aeat.valida.Validador;

/** The Class AccountController. */
@Controller
public class AccountController {

	private static final String ACCOUNT_VIEW_NAME = "account/account";

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private MethodPaymentService methodPaymentService;

	private List<BankAccount> bankAccounts;
	private Account account;

	/**
	 * Instantiates a new Profile controller.
	 */
	public AccountController() {
		// Default empty constructor.
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
		profileForm.setObservations(account.getObservations());
		profileForm.setRole(String.valueOf(account.getRole()));
		profileForm.setRoles(java.util.Arrays.asList(roles.values()));
		model.addAttribute(profileForm);
		return ACCOUNT_VIEW_NAME;
	}

	/**
	 * Profile.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "account")
	public String profile(Model model) {
		if (account != null) {
			account = accountService.findById(account.getId());
			bankAccounts = account.getBankAccounts();
			model.addAttribute("bankAccounts", bankAccounts);
			model.addAttribute(new BankAccountForm());
			return createProfileForm(model, new ProfileForm());
		}
		// If not have account, redirect to accounts
		else {
			return "redirect:/accountList";
		}
	}

	/**
	 * Modify account.
	 *
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = "account/{id}", method = RequestMethod.POST)
	public String modifyAccount(@PathVariable Long id) {
		account = accountService.findById(id);
		return "redirect:/account";
	}

	/**
	 * Account.
	 *
	 * @param profileForm the profile form
	 * @param errors the errors
	 * @param ra the RedirectAttributes
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "account", method = RequestMethod.POST)
	public String profile(@Valid @ModelAttribute ProfileForm profileForm, Errors errors, RedirectAttributes ra, Model model) {

		// Validar DNI
		Validador validador = new Validador();
		if (validador.checkNif(profileForm.getDni()) < 0){
			errors.rejectValue("dni", "signup.dni.noValid", new Object[] { profileForm.getDni() }, "dni");
		}
		
		if (errors.hasErrors()) {
			model.addAttribute(new BankAccountForm());
			return createProfileForm(model, profileForm);
		}

		// Password
		boolean modifyPassword = false;
		if (profileForm.isOnPassword()) {
			// check that the password and rePassword are the same
			String password = profileForm.getPassword();
			String rePassword = profileForm.getRePassword();
			if (!password.equals(rePassword)) {
				errors.rejectValue("password", "profile.passwordsDontMatch");
				errors.rejectValue("rePassword", "profile.passwordsDontMatch");
				model.addAttribute(new BankAccountForm());
				return createProfileForm(model, profileForm);
			} else {
				account.setPassword(password);
				modifyPassword = true;
			}
		}

		account.setName(profileForm.getName());
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
		account.setAccountType(accountTypeService.findById(profileForm.getAccountTypeId()));
		account.setMethodPayment(methodPaymentService.findById(profileForm.getMethodPaymentId()));
		account.setInstallments(profileForm.getInstallments());
		account.setObservations(profileForm.getObservations());

		// Login, Queries to database
		String login = profileForm.getLogin();
		if (profileForm.isOnLogin() && login != "") {
			account.setLogin(login);
		}

		// Email
		String email = profileForm.getEmail();
		if (profileForm.isOnEmail() && email != "") {
			account.setEmail(email);
		}

		// Role
		if (profileForm.isOnRole()) {
			account.setRole(roles.valueOf(profileForm.getRole()));
		}

		// If correct
		try {
			accountService.update(account, modifyPassword, false);
		} catch (UniqueListException e) {
			for (UniqueException unique : e.getMessages()) {
				errors.rejectValue(unique.getAttribute(), "signup.existent." + unique.getAttribute(), new Object[] { unique.getValue() },
						unique.getAttribute());
			}

			if (errors.hasErrors()) {
				model.addAttribute(new BankAccountForm());
				return createProfileForm(model, profileForm);
			}
		}

		MessageHelper.addWarningAttribute(ra, "account.successModify", account.getName());
		return "redirect:/accountList";
	}

	/**
	 * Bank account.
	 *
	 * @param bankAccountForm the bank account form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "bankAccount", method = RequestMethod.POST)
	public String bankAccount(@Valid @ModelAttribute BankAccountForm bankAccountForm, Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			return createProfileForm(model, new ProfileForm());
		}

		IBANCheckDigit IbanCheckDigit = new IBANCheckDigit();
		if (!IbanCheckDigit.isValid(bankAccountForm.getIban())) {
			errors.rejectValue("iban", "account.errorIban", new Object[] { bankAccountForm.getIban() }, "iban");
			return createProfileForm(model, new ProfileForm());
		}

		accountService.saveBankAccount(bankAccountForm.createBankAccount(account));
		MessageHelper.addWarningAttribute(ra, "account.successCreateBankAccount", bankAccountForm.getBank());
		return "redirect:/account";
	}
}
