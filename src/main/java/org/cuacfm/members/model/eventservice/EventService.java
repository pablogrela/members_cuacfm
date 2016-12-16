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

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.event.Event;
import org.cuacfm.members.model.event.EventDTO;

/**
 * The Interface EventService.
 */
public interface EventService {

	/**
	 * Save.
	 *
	 * @param event the event
	 * @return the event
	 */
	public Event save(Event event);

	/**
	 * Save.
	 *
	 * @param message the message
	 * @param account the account
	 * @param priority the priority
	 * @param arguments the arguments
	 * @return the string
	 */
	public String save(String message, Account account, int priority, Object[] arguments);

	/**
	 * Save.
	 *
	 * @param message the message
	 * @param account the account
	 * @param priority the priority
	 */
	public void save(String message, Account account, int priority);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the event
	 */
	public Event findById(Long id);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Event> findAll();

	/**
	 * Find all active.
	 *
	 * @return the list
	 */
	public List<Event> findAllOpen();

	/**
	 * Find all close.
	 *
	 * @return the list
	 */
	public List<Event> findAllClose();

	/**
	 * Gets the events.
	 *
	 * @param accountId the account id
	 * @return the events
	 */
	public List<Event> findAllByAccountId(Long accountId);

	/**
	 * Highlight.
	 *
	 * @param event the event
	 * @param priority the priority
	 * @return the event
	 */
	public Event highlight(Event event, int priority);

	/**
	 * Convert events to events DTO.
	 *
	 * @param events the events
	 * @return the list
	 */
	public List<EventDTO> getDTO(List<Event> events);
}
