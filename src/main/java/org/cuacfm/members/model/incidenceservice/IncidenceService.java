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
package org.cuacfm.members.model.incidenceservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.incidence.Incidence;
import org.cuacfm.members.model.incidence.IncidenceDTO;
import org.springframework.web.multipart.MultipartFile;

/** The Class IncidenceService. */
public interface IncidenceService {

	/**
	 * Save an training into database.
	 *
	 * @param incidence the training
	 * @return Incidence
	 * @throws UniqueException the unique exception
	 */
	public Incidence save(Incidence incidence, MultipartFile[] files);

	/**
	 * Update Incidence.
	 *
	 * @param incidence the incidence
	 * @return Incidence
	 * @throws UniqueException the unique exception
	 */
	public Incidence update(Incidence incidence);

	/**
	 * Delete.
	 *
	 * @param incidence the incidence
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Incidence incidence);

	/**
	 * Delete.
	 *
	 * @param incidence the incidence
	 * @param account the account
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Incidence incidence, Account account) throws ExistPaymentsException;

	/**
	 * Find by Name the incidence.
	 *
	 * @param name the name
	 * @return Incidence
	 */
	public Incidence findByName(String name);

	/**
	 * Find by id returns incidence which has this identifier.
	 *
	 * @param id the id
	 * @return incidence
	 */
	public Incidence findById(Long id);

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

	/**
	 * Up.
	 *
	 * @param incidence the incidence
	 */
	public void up(Incidence incidence);

	/**
	 * Down.
	 *
	 * @param incidence the incidence
	 */
	public void down(Incidence incidence);

	/**
	 * Answer.
	 *
	 * @param incidence the incidence
	 * @param answer the answer
	 */
	public void answer(Incidence incidence, String answer);

	/**
	 * Gets the incidences DTO.
	 *
	 * @param incidences the incidences
	 * @return the incidences DTO
	 */
	public List<IncidenceDTO> getIncidencesDTO(List<Incidence> incidences);

	/**
	 * Gets the incidence DTO.
	 *
	 * @param incidence the incidence
	 * @return the incidence DTO
	 */
	public IncidenceDTO getIncidenceDTO(Incidence incidence);
}
