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
package org.cuacfm.members.model.eventservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.event.Event;
import org.cuacfm.members.model.event.EventDTO;
import org.cuacfm.members.model.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The Class EventServiceImpl.
 */
@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private AccountService accountService;

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public String save(String message, Account account, int priority, Object[] arguments) {
		String messageI18n = messageSource.getMessage(message, arguments, Locale.getDefault());

		// Si es nula se asume que es el administrador
		if (account == null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				account = accountService.findByLogin(auth.getName());
			}
		}
		// If account is null, it is not save message
		if (account != null) {
			eventRepository.save(new Event(account, new Date(), priority, messageI18n));
		}
		
		return messageI18n;
	}

	@Override
	public void save(String message, Account account, int priority) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String adminName = null;
		if (auth != null) {
			adminName = auth.getName();
		}
		Object[] arguments = { adminName, account.getName() };
		save("account.admin.successModify", account, 3, arguments);
	}

	@Override
	public Event findById(Long id) {
		return eventRepository.findById(id);
	}

	@Override
	public List<Event> findAllByAccountId(Long accountId) {
		return eventRepository.findAllByAccountId(accountId);
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public List<Event> findAllOpen() {
		return eventRepository.findAllOpen();
	}

	@Override
	public List<Event> findAllClose() {
		return eventRepository.findAllClose();
	}

	@Override
	public Event highlight(Event event, int priority) {
		event.setPriority(priority);
		return eventRepository.update(event);
	}

	@Override
	public List<EventDTO> getDTO(List<Event> events) {
		List<EventDTO> eventsDTO = new ArrayList<>();
		for (Event event : events) {
			EventDTO eventDTO = new EventDTO(event.getId(), event.getAccount().getName(), event.getDateEvent(), event.getPriority(),
					event.getDescription());
			eventsDTO.add(eventDTO);
		}
		return eventsDTO;
	}
}
