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
package org.cuacfm.members.test.web.home;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configuration.Configuration;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.event.Event;
import org.cuacfm.members.model.event.EventDTO;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.util.Constants.levels;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The class HomeControlTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HomeControllerTest extends WebSecurityConfigurationAware {

	private MockHttpSession defaultSession;

	@Inject
	private AccountService accountService;

	@Inject
	private ConfigurationService configurationService;

	@Inject
	private EventService eventService;

	private Account account;

	/**
	 * Initialize default session.
	 *
	 * @throws UniqueException the unique exception
	 * @throws UniqueListException the unique list exception
	 */
	@Before
	public void initializeDefaultSession() throws UniqueException, UniqueListException {
		account = new Account("user", "1", "55555555C", "London", "user", "user@test.es", "666666666", "666666666", "demo", roles.ROLE_USER, "", true,
				true, "", "");
		accountService.save(account);
		Account admin = new Account("admin", "", "55555555D", "London", "admin", "admin@test.es", "666666666", "666666666", "demo", roles.ROLE_ADMIN);
		accountService.save(admin);
		defaultSession = getDefaultSession("user@test.es");
		Configuration configuration = new Configuration("CuacFM", "cuacfm@org", 6666666, Double.valueOf(24), Double.valueOf(25), "Rul");
		configurationService.save(configuration);
	}

	/**
	 * Show page homeNotSignedIn.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayHomeNotSignedIn() throws Exception {
		mockMvc.perform(get("/").locale(Locale.ENGLISH)).andExpect(view().name("home/homeNotSignedIn"));
	}

	/**
	 * Show page homeSignedIn.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayHomeSignedIn() throws Exception {
		mockMvc.perform(get("/").locale(Locale.ENGLISH).session(defaultSession)).andExpect(view().name("home/homeSignedIn"));
	}

	/**
	 * Display event list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayEventList() throws Exception {
		mockMvc.perform(get("/eventList/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/eventList/").locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
		for (Event event : eventService.findAllOpen()) {
			event.setPriority(levels.DISABLE.getValue());
			eventService.save(event);
		}
		;
		mockMvc.perform(get("/eventList/").locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
	}

	/**
	 * Display event list close.
	 *
	 * @return the event list close
	 * @throws Exception the exception
	 */
	@Test
	public void getEventListClose() throws Exception {
		mockMvc.perform(get("/eventList/close").locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
	}

	/**
	 * Display event list close.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void displayEventListClose() throws Exception {
		mockMvc.perform(get("/eventList/close/").locale(Locale.ENGLISH).session(defaultSession));
		mockMvc.perform(get("/eventList/close/").locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
		Event event = eventService.save(new Event(account, new Date(), levels.MEDIUM.getValue(), ""));
		eventService.findAll();
		mockMvc.perform(post("/eventList/remove/" + event.getId()).locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
		mockMvc.perform(get("/eventList/close/").locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
		
		EventDTO eventDTO = new EventDTO();
		event.setAccount(account);
		eventDTO.setAccount(accountService.getAccountDTO(event.getAccount()));
		event.setDateEvent(new Date());
		eventDTO.setDateEvent(event.getDateEvent());
		event.setDescription("new");
		eventDTO.setDescription(event.getDescription());
		eventDTO.setId(event.getId());
		event.setPriority(levels.DISABLE.getValue());
		eventDTO.setPriority(event.getPriority());
		event.toString();
		eventDTO.toString();
		assertEquals(eventService.findAllClose().size(), 1);
		assertNull(eventService.findById(Long.valueOf(0)));
	}

	/**
	 * Highlight.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void highlight() throws Exception {
		Event event = eventService.save(new Event(account, new Date(), levels.MEDIUM.getValue(), ""));
		mockMvc.perform(post("/eventList/highlight/" + event.getId()).locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
		mockMvc.perform(post("/eventList/remove/" + event.getId()).locale(Locale.ENGLISH).session(getDefaultSession("admin@test.es")));
	}

}