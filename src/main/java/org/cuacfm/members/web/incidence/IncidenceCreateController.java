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
package org.cuacfm.members.web.incidence;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.incidence.Incidence;
import org.cuacfm.members.model.incidence.IncidenceDTO;
import org.cuacfm.members.model.incidenceservice.IncidenceService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

/** The Class IncidenceCreateController. */
@Controller
public class IncidenceCreateController {

	private static final String INCIDENCE_VIEW_NAME = "incidence/incidencecreate";

	@Value("${maxFiles}")
	private int maxFiles;

	@Autowired
	private IncidenceService incidenceService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProgramService programService;

	private IncidenceForm incidenceForm;

	/**
	 * Instantiates a new incidenceController.
	 */
	public IncidenceCreateController() {
		super();
	}

	/**
	 * Incidence form.
	 *
	 * @return the incidence form
	 */
	@ModelAttribute("incidenceForm")
	public IncidenceForm incidenceForm() {
		return incidenceForm;
	}

	/**
	 * Incidence.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceCreate")
	public String incidence(Model model, Principal principal) {

		incidenceForm = new IncidenceForm();
		Account account = accountService.findByLogin(principal.getName());

		List<Program> programs;
		if (account.getRole() == roles.ROLE_ADMIN) {
			programs = programService.getProgramList();
		} else {
			programs = programService.getProgramListActiveByUser(account);
			if (programs.size() == 1) {
				incidenceForm.setProgramId(programs.get(0).getId());
			}
		}
		incidenceForm.setPrograms(programs);
		model.addAttribute(incidenceForm);
		return INCIDENCE_VIEW_NAME;
	}

	/**
	 * Incidence.
	 *
	 * @param incidenceForm the incidence form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceCreate", method = RequestMethod.POST, params = { "create" })
	public String createIncidence(@Valid @ModelAttribute IncidenceForm incidenceForm, Principal principal, Errors errors, RedirectAttributes ra,
			Model model) {

		//Validate max files
		if (incidenceForm.getPhotos() != null && incidenceForm.getPhotos().length > maxFiles) {
			errors.rejectValue("photos", "incidence.photos.error.max", new Object[] { maxFiles }, "photos");
		}

		if (errors.hasErrors()) {
			return INCIDENCE_VIEW_NAME;
		}

		try {
			Account account = accountService.findByLogin(principal.getName());
			Incidence incidence = incidenceForm.createIncidence(account);
			incidenceService.save(incidence, incidenceForm.getPhotos());

		} catch (Exception e) {
			errors.rejectValue("program", "incidence.create.error", new Object[] { e }, "program");
			return INCIDENCE_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "incidence.create.success", incidenceForm.getProgram().getName());
		return "redirect:/incidenceList";
	}

	/**
	 * Creates the incidence API.
	 *
	 * @param token the token
	 * @param incidenceJson the incidence json
	 * @return the response entity
	 */
	@RequestMapping(value = "api/incidenceList/incidenceCreate", method = RequestMethod.POST)
	public ResponseEntity<String> createIncidenceAPI(@RequestParam(value = "token") String token,
			@RequestParam(value = "incidenceJson") String incidenceJson) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			IncidenceDTO incidenceDTO = new Gson().fromJson(incidenceJson, IncidenceDTO.class);

			Incidence incidence = incidenceService.getIncidence(incidenceDTO, account);
			incidence = incidenceService.save(incidence, null);

			IncidenceDTO newIncidenceDTO = incidenceService.getIncidenceDTO(incidence);
			String newIncidenceJson = new Gson().toJson(newIncidenceDTO);
			return new ResponseEntity<>(newIncidenceJson, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
}
