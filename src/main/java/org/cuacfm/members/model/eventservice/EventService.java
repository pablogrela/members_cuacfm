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
	 */
	public void save(String message, Account account, int priority, Object[] arguments);

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
	 * Highlight.
	 *
	 * @param event the event
	 * @param priority the priority
	 * @return the event
	 */
	public Event highlight(Event event, int priority);

	/**
	 * Gets the events.
	 *
	 * @param accountId the account id
	 * @return the events
	 */
	public List<Event> getEvents(Long accountId);

	/**
	 * Gets the all events.
	 *
	 * @return the all events
	 */
	public List<Event> getAllEvents();

	/**
	 * Gets the events DTO.
	 *
	 * @return the events DTO
	 */
	public List<EventDTO> getEventsDTO();

	/**
	 * Gets the active events DTO.
	 *
	 * @return the active events DTO
	 */
	public List<EventDTO> getActiveEventsDTO();

	/**
	 * Gets the close events DTO.
	 *
	 * @return the close events DTO
	 */
	public List<EventDTO> getCloseEventsDTO();

	/**
	 * Gets the events DTO.
	 *
	 * @param accountId the account id
	 * @return the events DTO
	 */
	public List<EventDTO> getEventsDTO(Long accountId);

}
