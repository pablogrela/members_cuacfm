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
package org.cuacfm.members.web.api;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.program.ProgramDTO;
import org.cuacfm.members.model.programservice.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

/** The Class ProgramAPIController. */
@Controller
public class ProgramAPIController {

	//private static final Logger logger = LoggerFactory.getLogger(ProgramAPIController.class)

	@Autowired
	private ProgramService programService;

	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new training Controller.
	 */
	public ProgramAPIController() {
		super();
	}

	/**
	 * Gets the programs API.
	 *
	 * @param token the token
	 * @return the programs API
	 */
	@RequestMapping(value = "api/programList/")
	public ResponseEntity<String> getProgramsAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			List<ProgramDTO> programsDTO = programService.getProgramsDTO(programService.getProgramListActive());
			String programsJson = new Gson().toJson(programsDTO);
			// Return with data "{ \"data\": " + programsJson + " }" instead of incidencesJson
			return new ResponseEntity<>(programsJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Gets the programs API.
	 *
	 * @param token the token
	 * @return the programs API
	 */
	@RequestMapping(value = "api/programUserList/")
	public ResponseEntity<String> getProgramsUserAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			List<ProgramDTO> programsDTO = programService.getProgramsDTO(programService.getProgramListActiveByUser(account));
			String programsJson = new Gson().toJson(programsDTO);
			return new ResponseEntity<>(programsJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
}
