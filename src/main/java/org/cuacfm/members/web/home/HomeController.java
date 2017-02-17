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
package org.cuacfm.members.web.home;

import java.security.Principal;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.event.Event;
import org.cuacfm.members.model.event.EventDTO;
import org.cuacfm.members.model.eventservice.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** The Class HomeController. */
@Controller
public class HomeController {

	private static final String HOMESIGNEDIN = "home/homeSignedIn";
	private static final String HOMENOTSIGNEDIN = "home/homeNotSignedIn";
	private static final String EVENTLISTCLOSE = "home/eventlistclose";

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private EventService eventService;

	@Autowired
	private AccountService accountService;
	
	// The email of configuration
	private String email;

	/**
	 * Email.
	 *
	 * @return the string
	 */
	@ModelAttribute("email")
	public String email() {
		return email;
	}

	/**
	 * Index.
	 *
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal principal) {
		if (principal != null) {
			email = configurationService.getConfiguration().getEmail();
			model.addAttribute("email", email);
			// TODO prueba de cambio de idioma
//			Locale.setDefault(new Locale("gl","ES"));
//			System.setProperty("user.language", "gl");
//			System.setProperty("user.country", "ES");		
//			Locale.getDefault();
			
			return HOMESIGNEDIN;
		} else {
			return HOMENOTSIGNEDIN;
		}
	}

	/**
	 * Event list close.
	 *
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "/eventListClose", method = RequestMethod.GET)
	public String eventListClose(Principal principal) {
		return EVENTLISTCLOSE;
	}

	/**
	 * List all events.
	 *
	 * @return the response entity
	 */
	@RequestMapping(value = "/events/", method = RequestMethod.GET)
	public ResponseEntity<List<EventDTO>> getEventList() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<EventDTO> eventsDTO;

		if (auth.getAuthorities().toString().contains(roles.ROLE_ADMIN.toString())) {
			eventsDTO = eventService.getDTO(eventService.findAllOpen());
		} else {
			Account account = accountService.findByLogin(auth.getName());
			eventsDTO = eventService.getDTO(eventService.findAllByAccountId(account.getId()));
		}
		if (eventsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
	}

	/**
	 * List all events.
	 *
	 * @return the response entity
	 */
	@RequestMapping(value = "/eventListClose/", method = RequestMethod.GET)
	public ResponseEntity<List<EventDTO>> getEventListClose() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<EventDTO> eventsDTO = null;

		if (auth.getAuthorities().toString().contains(roles.ROLE_ADMIN.toString())) {
			eventsDTO = eventService.getDTO(eventService.findAllClose());
		}
		if (eventsDTO == null || eventsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
	}

	/**
	 * Highlight.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(value = "events/highlight/{id}", method = RequestMethod.POST)
	public ResponseEntity<EventDTO> highlight(@PathVariable("id") long id) {

		Event event = eventService.findById(id);
		if (event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		eventService.highlight(event, 1);
		EventDTO eventDTO = new EventDTO(event.getId(), event.getAccount().getName(), event.getDateEvent(), event.getPriority(),
				event.getDescription());
		return new ResponseEntity<>(eventDTO, HttpStatus.OK);
	}

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(value = "events/remove/{id}", method = RequestMethod.POST)
	public ResponseEntity<EventDTO> remove(@PathVariable("id") long id) {

		Event event = eventService.findById(id);
		if (event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		eventService.highlight(event, 0);
		EventDTO eventDTO = new EventDTO(event.getId(), event.getAccount().getName(), event.getDateEvent(), event.getPriority(),
				event.getDescription());
		return new ResponseEntity<>(eventDTO, HttpStatus.OK);
	}
}
