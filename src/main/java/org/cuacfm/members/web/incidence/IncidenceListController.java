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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

/** The Class IncidenceListController. */
@Controller
public class IncidenceListController {

	private static final Logger logger = LoggerFactory.getLogger(IncidenceListController.class);
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
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "incidenceList")
	public String getincidenceListView() throws UniqueException {
		return PROGRAM_VIEW_NAME;
	}

	/**
	 * Gets the incidence list close view.
	 *
	 * @return the incidence list close view
	 */
	@RequestMapping(value = "incidenceList/close")
	public String getincidenceListCloseView() {
		return PROGRAM_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the incidences.
	 *
	 * @param principal the principal
	 * @return the incidences
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "incidenceList/")
	public ResponseEntity<List<IncidenceDTO>> getIncidences(Principal principal) {

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
	 * Gets the incidences API.
	 *
	 * @param token the token
	 * @return the incidences API
	 */
	@RequestMapping(value = "api/incidenceList/")
	public ResponseEntity<String> getIncidencesAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			List<IncidenceDTO> incidencesDTO = incidenceService.getIncidencesDTO(incidenceService.getIncidenceListByUser(account));
			String incidencesJson = new Gson().toJson(incidencesDTO);
			// Return with data "{ \"data\": " + incidencesJson + " }" instead of incidencesJson
			return new ResponseEntity<>(incidencesJson, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Gets the incidences close.
	 *
	 * @param principal the principal
	 * @return the incidences close
	 */
	@RequestMapping(value = "incidenceList/close/")
	public ResponseEntity<List<IncidenceDTO>> getIncidencesClose(Principal principal) {

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
	 * @param answer the answer
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "incidenceList/incidenceAnswer/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> incidenceAnswer(@PathVariable("id") Long id, @RequestParam(value = "answer") String answer,
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
	 */
	@RequestMapping(value = "incidenceList/image/{incidenceId}")
	@ResponseBody
	public byte[] getImageIncidence(@PathVariable("incidenceId") Long incidenceId, @RequestParam(value = "imageName") String imageName) {

		Incidence incidence = incidenceService.findById(incidenceId);
		File serverFile = new File(incidence.getFile() + imageName);
		byte[] image = null;

		try {
			image = Files.readAllBytes(serverFile.toPath());
		} catch (IOException e) {
			logger.error("getImageIncidence", e);
		}

		return image;
	}

	/**
	 * Gets the image incidence API.
	 *
	 * @param incidenceId the incidence id
	 * @param imageName the image name
	 * @param token the token
	 * @return the image incidence API
	 */
	@RequestMapping(value = "api/incidenceList/image/{incidenceId}")
	@ResponseBody
	public byte[] getImageIncidenceAPI(@PathVariable("incidenceId") Long incidenceId, @RequestParam(value = "imageName") String imageName,
			@RequestParam(value = "token") String token) {

		byte[] image = null;

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			image = getImageIncidence(incidenceId, imageName);
		}
		return image;
	}

	/**
	 * Gets the incidences gson.
	 *
	 * @param incidenceId the incidence id
	 * @param token the token
	 * @return the incidences gson
	 */
	// TODO revisar o eliminar
	@RequestMapping(value = "api/imagesIncidence/{incidenceId}")
	public List<byte[]> getImageIncidenceAPI(@PathVariable("incidenceId") Long incidenceId,
			@RequestParam(value = "token", required = false) String token) {

		List<byte[]> files = new ArrayList<>();
		byte[] image = null;

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Incidence incidence = incidenceService.findById(incidenceId);

			try {
				for (String imageName : incidence.getFiles()) {
					File serverFile = new File(incidence.getFile() + imageName);
					image = Files.readAllBytes(serverFile.toPath());
					files.add(image);
				}
			} catch (IOException e) {
				logger.error("getImageIncidence", e);
			}
		}
		return files;
	}
}
