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
package org.cuacfm.members.web.program;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
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

/** The Class ProgramCreateController. */
@Controller
public class ProgramCreateController {

	private static final String PROGRAM_VIEW_NAME = "program/programcreate";

	@Autowired
	private ProgramService programService;

	@Autowired
	private AccountService accountService;

	private List<String> usernames;
	private ProgramForm programForm;

	/**
	 * Instantiates a new programController.
	 */
	public ProgramCreateController() {
		super();
	}

	/**
	 * Program form.
	 *
	 * @return the program form
	 */
	@ModelAttribute("programForm")
	public ProgramForm programForm() {
		return programForm;
	}

	/**
	 * Program.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate")
	public String program(Model model, Principal principal) {

		usernames = accountService.getUsernames();
		model.addAttribute("usernames", usernames);

		programForm = new ProgramForm();
		programForm.setAccounts(new ArrayList<Account>());
		Account account = accountService.findByLogin(principal.getName());
		if (account.getRole() != roles.ROLE_ADMIN) {
			programForm.addAccount(account);
			programForm.setAccountPayer(account);
			programForm.setAccountPayerName(account.getName());
		}
		programForm.setProgramThematics(programService.findProgramThematicList());
		programForm.setProgramTypes(programService.findProgramTypeList());
		programForm.setProgramCategories(programService.findProgramCategoryList());
		programForm.setProgramLanguages(programService.findProgramLanguageList());
		model.addAttribute(programForm);
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Program.
	 *
	 * @param programForm the program form
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate", method = RequestMethod.POST, params = { "create" })
	public String program(@Valid @ModelAttribute ProgramForm programForm, Errors errors, RedirectAttributes ra, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("usernames", usernames);
			return PROGRAM_VIEW_NAME;
		}
		if (programForm.getAccountPayer() == null) {
			model.addAttribute("usernames", usernames);
			errors.rejectValue("accountPayerName", "program.accountPayer.required");
			return PROGRAM_VIEW_NAME;
		}
		try {
			Program program = programForm.createProgram();
			programService.save(program);

		} catch (UniqueException e) {
			errors.rejectValue("name", "program.existentName", new Object[] { e.getValue() }, "name");
			model.addAttribute("usernames", usernames);
			return PROGRAM_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "program.successCreate", programForm.getName());
		return "redirect:/programList";
	}

	/**
	 * Adds the user.
	 *
	 * @param programForm the program form
	 * @param errors the errors
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate", method = RequestMethod.POST, params = { "addUser" })
	public String addUser(@Valid @ModelAttribute ProgramForm programForm, Errors errors, Model model) {
		model.addAttribute("usernames", usernames);
		Account account;
		String name = programForm.getLogin();
		if (name.contains(": ")) {
			String[] parts = programForm.getLogin().split(": ");
			Long id = Long.valueOf(parts[0]);
			name = parts[1].split(" - ")[0].trim();
			account = accountService.findById(id);
		} else {
			account = accountService.findByDni(name);
		}

		if (account == null) {
			errors.rejectValue("login", "program.noExistUser", new Object[] { name }, "login");
			return PROGRAM_VIEW_NAME;
		}

		// Check if account already insert
		boolean repeated = false;
		for (Account acc : programForm.getAccounts()) {
			if (acc.getId() == account.getId()) {
				repeated = true;
			}
		}

		if (repeated) {
			errors.rejectValue("login", "program.alreadyExistUser", new Object[] { name }, "login");
			return PROGRAM_VIEW_NAME;
		}

		usernames.remove(name);
		programForm.setLogin("");
		programForm.addAccount(account);

		MessageHelper.addSuccessAttribute(model, "program.successAddUser", name);
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Removes the user.
	 *
	 * @param id the id
	 * @param programForm the program form
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate", method = RequestMethod.POST, params = { "removeUser" })
	public String removeUser(@RequestParam("removeUser") Long id, @Valid @ModelAttribute ProgramForm programForm, Model model) {
		model.addAttribute("usernames", usernames);
		String name = programForm.removeAccount(id);
		MessageHelper.addSuccessAttribute(model, "program.successRemoveUser", name);
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Adds the account payer.
	 *
	 * @param programForm the program form
	 * @param errors the errors
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate", method = RequestMethod.POST, params = { "addAccountPayer" })
	public String addAccountPayer(@Valid @ModelAttribute ProgramForm programForm, Errors errors, Model model) {
		model.addAttribute("usernames", usernames);
		Account account;
		String name = programForm.getAccountPayerName();
		if (name.contains(": ")) {
			String[] parts = programForm.getAccountPayerName().split(": ");
			Long id = Long.valueOf(parts[0]);
			name = parts[1].split(" - ")[0].trim();
			account = accountService.findById(id);
		} else {
			account = accountService.findByDni(name);
		}

		if (account == null) {
			errors.rejectValue("accountPayerName", "program.noExistUser", new Object[] { name }, "accountPayerName");
			return PROGRAM_VIEW_NAME;
		}
		programForm.setAccountPayerName(account.getName());
		programForm.setAccountPayer(account);
		MessageHelper.addSuccessAttribute(model, "program.successAddAcountPayer", name);
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Removes the account payer.
	 *
	 * @param programForm the program form
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "programList/programCreate", method = RequestMethod.POST, params = { "removeAccountPayer" })
	public String removeAccountPayer(@Valid @ModelAttribute ProgramForm programForm, Model model) {
		model.addAttribute("usernames", usernames);
		String name = programForm.getAccountPayerName();
		programForm.setAccountPayer(null);
		programForm.setAccountPayerName("");
		MessageHelper.addSuccessAttribute(model, "program.successRemoveAcountPayer", name);
		return PROGRAM_VIEW_NAME;
	}
}
