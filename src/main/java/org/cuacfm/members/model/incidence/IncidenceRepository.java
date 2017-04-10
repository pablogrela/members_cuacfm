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
package org.cuacfm.members.model.incidence;

import java.util.List;

import org.cuacfm.members.model.account.Account;

/**
 * The Interface IncidenceRepository.
 */
public interface IncidenceRepository {

	/**
	 * Save.
	 *
	 * @param incidence the incidence
	 * @return incidence
	 */
	public Incidence save(Incidence incidence);

	/**
	 * Update.
	 *
	 * @param incidence the incidence
	 * @return incidence
	 */
	public Incidence update(Incidence incidence);

	/**
	 * Delete.
	 *
	 * @param incidence the incidence
	 */
	public void delete(Incidence incidence);

	/**
	 * Find by id.
	 *
	 * @param id the id of incidence
	 * @return the incidence
	 */
	public Incidence findById(Long id);

	/**
	 * Find by login.
	 *
	 * @param name the name of incidence
	 * @return Incidence
	 */
	public Incidence findByName(String name);

	/**
	 * Get all incidences.
	 *
	 * @return List<Incidence>
	 */
	public List<Incidence> getIncidenceList();

	/**
	 * Get all active incidences.
	 *
	 * @return List<Incidence>
	 */
	public List<Incidence> getIncidenceListActive();

	/**
	 * Gets the incidence list by user.
	 *
	 * @param account the account
	 * @return the incidence list by user
	 */
	public List<Incidence> getIncidenceListByUser(Account account);
	
	/**
	 * Gets the incidence list close.
	 *
	 * @return the incidence list close
	 */
	public List<Incidence> getIncidenceListClose();
}
