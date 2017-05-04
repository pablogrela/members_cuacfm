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
package org.cuacfm.members.web.api;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UserAlreadyReserveException;
import org.cuacfm.members.model.reserve.Reserve;
import org.cuacfm.members.model.reserve.ReserveDTO;
import org.cuacfm.members.model.reserveservice.ReserveService;
import org.cuacfm.members.model.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** The Class ReserveListController. */
@Controller
public class ReserveAPIController {

	private static final Logger logger = LoggerFactory.getLogger(ReserveAPIController.class);

	@Autowired
	private ReserveService reserveService;

	@Autowired
	private AccountService accountService;

	/**
	 * Gets the reserves API.
	 *
	 * @param token the token
	 * @return the reserves API
	 */
	@RequestMapping(value = "api/reserveList/")
	public ResponseEntity<String> getReservesAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			if (account.getPermissions().contains(permissions.ROLE_RESERVE.toString())) {
				List<ReserveDTO> reservesDTO = reserveService.getReservesDTO(reserveService.getReserveListActive());
				String reservesJson = new Gson().toJson(reservesDTO);
				return new ResponseEntity<>(reservesJson, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Gets the reserves API.
	 *
	 * @param token the token
	 * @return the reserves API
	 */
	@RequestMapping(value = "api/reserveUserList/")
	public ResponseEntity<String> getReservesUserAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			List<ReserveDTO> reservesDTO = reserveService.getReservesDTO(reserveService.getReserveListByUser(account));
			String reservesJson = new Gson().toJson(reservesDTO);
			return new ResponseEntity<>(reservesJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Reserve answer API.
	 *
	 * @param reserveId the reserve id
	 * @param token the token
	 * @param answer the answer
	 * @param manage the manage
	 * @return the response entity
	 */
	@RequestMapping(value = { "api/reserveList/reserveAnswer/{reserveId}",
			"api/reserveUserList/reserveAnswer/{reserveId}" }, method = RequestMethod.POST)
	public ResponseEntity<String> reserveAnswerAPI(@PathVariable("reserveId") Long reserveId, @RequestParam(value = "token") String token,
			@RequestParam(value = "answer") String answer, @RequestParam(value = "manage") Boolean manage) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			Reserve reserve = reserveService.findById(reserveId);

			reserve = reserveService.answer(reserve, account, answer, manage);

			ReserveDTO newReserveDTO = reserveService.getReserveDTO(reserve);
			String newReserveJson = new Gson().toJson(newReserveDTO);
			return new ResponseEntity<>(newReserveJson, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Creates the reserve API.
	 *
	 * @param token the token
	 * @param reserveJson the reserve json
	 * @return the response entity
	 */
	@RequestMapping(value = "api/reserveList/reserveCreate", method = RequestMethod.POST)
	public ResponseEntity<String> createReserveAPI(@RequestParam(value = "token") String token,
			@RequestParam(value = "reserveJson") String reserveJson) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			try {
				Account account = accountService.findByEmail(email);
				Gson gson = new GsonBuilder().setDateFormat(DateUtils.FORMAT_LOCAL).create();
				ReserveDTO reserveDTO = gson.fromJson(reserveJson, ReserveDTO.class);
				Reserve reserve = reserveService.getReserve(reserveDTO, account);
				reserve = reserveService.save(reserve);
				ReserveDTO newReserveDTO = reserveService.getReserveDTO(reserve);
				String newReserveJson = new Gson().toJson(newReserveDTO);
				return new ResponseEntity<>(newReserveJson, HttpStatus.CREATED);
			} catch (UserAlreadyReserveException e) {
				logger.error("createReserveAPI UserAlreadyReserveException", e);
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} catch (Exception e) {
				logger.error("createReserveAPI", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
