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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.program.ProgramDTO;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.web.support.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ProgramListController. */
@Controller
public class ProgramListController {

	private static final Logger logger = LoggerFactory.getLogger(ProgramListController.class);
	private static final String PROGRAM_VIEW_NAME = "program/programlist";
	private static final String PROGRAM_CLOSE_VIEW_NAME = "program/programlistclose";

	@Autowired
	private ProgramService programService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Instantiates a new training Controller.
	 */
	public ProgramListController() {
		super();
	}

	/**
	 * Show Program List.
	 *
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "programList")
	public String getprogramListView() throws UniqueException {
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Direct debit view.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "programList/close")
	public String getprogramListCloseView() {
		return PROGRAM_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the programs.
	 *
	 * @param principal the principal
	 * @return the programs
	 */
	@RequestMapping(value = "programList/")
	public ResponseEntity<List<ProgramDTO>> getPrograms(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of programs
		List<ProgramDTO> programsDTO;
		if (account.getRole() == roles.ROLE_ADMIN) {
			programsDTO = programService.getProgramsDTO(programService.getProgramListActive());
		} else {
			programsDTO = programService.getProgramsDTO(account.getPrograms());
		}

		if (programsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(programsDTO, HttpStatus.OK);
	}

	/**
	 * Gets the programs close.
	 *
	 * @param principal the principal
	 * @return the programs close
	 */
	@RequestMapping(value = "programList/close/")
	public ResponseEntity<List<ProgramDTO>> getProgramsClose(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of programs
		List<ProgramDTO> programsDTO;
		if (account.getRole() == roles.ROLE_ADMIN) {
			programsDTO = programService.getProgramsDTO(programService.getProgramListClose());
		} else {
			programsDTO = programService.getProgramsDTO(account.getPrograms());
		}

		if (programsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(programsDTO, HttpStatus.OK);
	}

	/**
	 * Delete Program by Id.
	 *
	 * @param id the id
	 * @param ra the redirect atributes
	 * @param principal the principal
	 * @return the string destinity page
	 */
	@RequestMapping(value = "programList/programDelete/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> remove(@PathVariable Long id, RedirectAttributes ra, Principal principal) {

		Account account = accountService.findByLogin(principal.getName());
		Program program = programService.findById(id);

		// Security, Only user can delete him program or admin
		if (program.getAccounts().contains(account) || (account.getRole() == roles.ROLE_ADMIN)) {
			try {
				programService.delete(program, account);
				Object[] arguments = { program.getName() };
				MessageHelper.addSuccessAttribute(ra, messageSource.getMessage("program.successDelete", arguments, Locale.getDefault()));

			} catch (ExistPaymentsException e) {
				logger.error("payMember", e);
				Object[] arguments = { program.getName() };
				MessageHelper.addErrorAttribute(ra, messageSource.getMessage("program.existPayments", arguments, Locale.getDefault()));
			}
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Unsubscribe.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "programList/programDown/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> programDown(@PathVariable("id") Long id, RedirectAttributes ra) {

		Program program = programService.findById(id);

		programService.down(program);
		Object[] arguments = { program.getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("program.successDown", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Program down.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "programList/programUp/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> programUp(@PathVariable("id") Long id, RedirectAttributes ra) {

		Program program = programService.findById(id);

		programService.up(program);
		Object[] arguments = { program.getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("program.successUp", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}
}
