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
package org.cuacfm.members.model.event;

import java.util.List;

/**
 * The Interface EventRepository.
 */
public interface EventRepository {

	/**
	 * Save.
	 *
	 * @param event the event
	 * @return the event
	 */
	public Event save(Event event);

	/**
	 * Update.
	 *
	 * @param event the event
	 * @return the event
	 */
	public Event update(Event event);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the event
	 */
	public Event findById(Long id);

	/**
	 * Gets the all events.
	 *
	 * @return the all events
	 */
	public List<Event> findAll();

	/**
	 * Gets the active events.
	 *
	 * @return the active events
	 */
	public List<Event> findAllOpen();

	/**
	 * Gets the close events.
	 *
	 * @return the close events
	 */
	public List<Event> findAllClose();

	/**
	 * Gets the events.
	 *
	 * @param accountId the account id
	 * @return the events
	 */
	public List<Event> findAllByAccountId(Long accountId);
}
