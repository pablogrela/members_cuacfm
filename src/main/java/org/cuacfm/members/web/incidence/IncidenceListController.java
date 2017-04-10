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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.incidence.Incidence;
import org.cuacfm.members.model.incidence.IncidenceDTO;
import org.cuacfm.members.model.incidenceservice.IncidenceService;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class IncidenceListController. */
@Controller
public class IncidenceListController {

	//private static final Logger logger = LoggerFactory.getLogger(IncidenceListController.class)
	private static final String PROGRAM_VIEW_NAME = "incidence/incidencelist";
	private static final String PROGRAM_CLOSE_VIEW_NAME = "incidence/incidencelistclose";

	@Autowired
	private IncidenceService incidenceService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Instantiates a new training Controller.
	 */
	public IncidenceListController() {
		super();
	}

	/**
	 * Show Incidence List.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "incidenceList")
	public String getincidenceListView(Model model, Principal principal) throws UniqueException {
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Direct debit view.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/close")
	public String getincidenceListCloseView(Model model, Principal principal) {
		return PROGRAM_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the incidences.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the incidences
	 */
	@RequestMapping(value = "incidenceList/")
	public ResponseEntity<List<IncidenceDTO>> getIncidences(Model model, Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of incidences
		List<IncidenceDTO> incidencesDTO;
		if (account.getRole() == roles.ROLE_ADMIN) {
			incidencesDTO = incidenceService.getIncidencesDTO(incidenceService.getIncidenceListActive());
		} else {
			incidencesDTO = incidenceService.getIncidencesDTO(incidenceService.getIncidenceListByUser(account));
		}

		if (incidencesDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(incidencesDTO, HttpStatus.OK);
	}

	/**
	 * Gets the incidences close.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the incidences close
	 */
	@RequestMapping(value = "incidenceList/close/")
	public ResponseEntity<List<IncidenceDTO>> getIncidencesClose(Model model, Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of incidences
		List<IncidenceDTO> incidencesDTO;
		if (account.getRole() == roles.ROLE_ADMIN) {
			incidencesDTO = incidenceService.getIncidencesDTO(incidenceService.getIncidenceListClose());
		} else {
			incidencesDTO = incidenceService.getIncidencesDTO(incidenceService.getIncidenceListByUser(account));
		}

		if (incidencesDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(incidencesDTO, HttpStatus.OK);
	}

	/**
	 * Unsubscribe.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "incidenceList/incidenceDown/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> incidenceDown(@PathVariable("id") Long id, RedirectAttributes ra) {

		Incidence incidence = incidenceService.findById(id);

		incidenceService.down(incidence);
		Object[] arguments = { incidence.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("incidence.down.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Incidence asnwer.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "incidenceList/incidenceAnswer/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> incidenceAsnwer(@PathVariable("id") Long id, @RequestParam(value = "answer") String answer,
			RedirectAttributes ra) {

		Incidence incidence = incidenceService.findById(id);
		incidenceService.answer(incidence, answer);

		Object[] arguments = { incidence.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("incidence.answer.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Incidence down.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "incidenceList/incidenceUp/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> incidenceUp(@PathVariable("id") Long id, RedirectAttributes ra) {

		Incidence incidence = incidenceService.findById(id);

		incidenceService.up(incidence);
		Object[] arguments = { incidence.getProgram().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("incidence.up.sucess", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Gets the image incidence.
	 *
	 * @param incidenceId the incidence id
	 * @param imageName the image name
	 * @return the image incidence
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "imageIncidence/{incidenceId}")
	@ResponseBody
	public byte[] getImageIncidence(@PathVariable("incidenceId") Long incidenceId, @RequestParam(value = "imageName") String imageName)
			throws IOException {
		Incidence incidence = incidenceService.findById(incidenceId);

		File serverFile = new File(incidence.getFile() + imageName);
		return Files.readAllBytes(serverFile.toPath());
	}
}
