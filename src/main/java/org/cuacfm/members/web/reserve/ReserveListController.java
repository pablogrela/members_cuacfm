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
package org.cuacfm.members.web.reserve;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.reserve.Reserve;
import org.cuacfm.members.model.reserve.ReserveDTO;
import org.cuacfm.members.model.reserveservice.ReserveService;
import org.cuacfm.members.web.support.MessageHelper;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ReserveListController. */
@Controller
public class ReserveListController {

	//private static final Logger logger = LoggerFactory.getLogger(ReserveListController.class)
	private static final String RESERVE_VIEW_NAME = "reserve/reservelist";
	private static final String RESERVE_CLOSE_VIEW_NAME = "reserve/reservelistclose";
	private static final String RESERVE_USER_VIEW_NAME = "reserve/reserveuserlist";

	@Autowired
	private ReserveService reserveService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Instantiates a new training Controller.
	 */
	public ReserveListController() {
		super();
	}

	/**
	 * Show Reserve List.
	 *
	 * @return the string the view
	 * @throws UniqueException the unique exception
	 */

	@RequestMapping(value = "reserveList")
	public String getReserveListView() {
		return RESERVE_VIEW_NAME;
	}

	/**
	 * Gets the reserve list close view.
	 *
	 * @return the reserve list close view
	 */
	@RequestMapping(value = "reserveList/close")
	public String getReserveListCloseView() {
		return RESERVE_CLOSE_VIEW_NAME;
	}

	/**
	 * Gets the reserve user list view.
	 *
	 * @return the reserve user list view
	 */
	@RequestMapping(value = "reserveUserList")
	public String getReserveUserListView() {
		return RESERVE_USER_VIEW_NAME;
	}

	/**
	 * Gets the reserves.
	 *
	 * @param principal the principal
	 * @return the reserves
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "reserveList/")
	public ResponseEntity<List<ReserveDTO>> getReserves(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of reserves
		List<ReserveDTO> reservesDTO;
		if (account.getPermissions().contains(permissions.ROLE_RESERVE.toString())) {
			reservesDTO = reserveService.getReservesDTO(reserveService.getReserveListActive());
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if (reservesDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reservesDTO, HttpStatus.OK);
	}

	/**
	 * Gets the reserves user.
	 *
	 * @param principal the principal
	 * @return the reserves user
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "reserveUserList/")
	public ResponseEntity<List<ReserveDTO>> getReservesUser(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of reserves
		List<ReserveDTO> reservesDTO = reserveService.getReservesDTO(reserveService.getReserveListByUser(account));

		if (reservesDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reservesDTO, HttpStatus.OK);
	}

	/**
	 * Gets the reserves close.
	 *
	 * @param principal the principal
	 * @return the reserves close
	 */
	@RequestMapping(value = "reserveList/close/")
	public ResponseEntity<List<ReserveDTO>> getReservesClose(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());

		// List of reserves
		List<ReserveDTO> reservesDTO;
		if (account.getPermissions().contains(permissions.ROLE_RESERVE.toString())) {
			reservesDTO = reserveService.getReservesDTO(reserveService.getReserveListClose());
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if (reservesDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reservesDTO, HttpStatus.OK);
	}

	/**
	 * Reserve accept.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "reserveList/reserveAccept/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reserveAccept(@PathVariable("id") Long id, RedirectAttributes ra) {

		Reserve reserve = reserveService.findById(id);

		reserveService.accept(reserve);
		Object[] arguments = { reserve.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("reserve.accept.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Reserve deny.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "reserveList/reserveDeny/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reserveDeny(@PathVariable("id") Long id, RedirectAttributes ra) {

		Reserve reserve = reserveService.findById(id);

		reserveService.deny(reserve);
		Object[] arguments = { reserve.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("reserve.deny.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Reserve answer.
	 *
	 * @param principal the principal
	 * @param reserveId the reserve id
	 * @param answer the answer
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = { "reserveList/reserveAnswer/{reserveId}", "reserveUserList/reserveAnswer/{reserveId}" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reserveAnswer(Principal principal, @PathVariable("reserveId") Long reserveId,
			@RequestParam(value = "answer") String answer, RedirectAttributes ra) {

		Reserve reserve = reserveService.findById(reserveId);
		reserveService.answer(reserve, accountService.findByLogin(principal.getName()), answer, null);

		Object[] arguments = { reserve.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("reserve.answer.success", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Reserve down.
	 *
	 * @param id the id
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "reserveList/reserveUp/{id}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> reserveUp(@PathVariable("id") Long id, RedirectAttributes ra) {

		Reserve reserve = reserveService.findById(id);

		reserveService.up(reserve);
		Object[] arguments = { reserve.getElement().getName() };
		MessageHelper.addInfoAttribute(ra, messageSource.getMessage("reserve.up.sucess", arguments, Locale.getDefault()));

		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}
}
