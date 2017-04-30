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
package org.cuacfm.members.model.trainingservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;

/** The Class TrainingService. */

public interface TrainingService {

	/**
	 * Save, saves an training into database.
	 *
	 * @param training the training
	 * @return the training
	 * @throws DatesException the date limit exception
	 * @throws UniqueException the unique exception
	 */
	public Training save(Training training) throws DatesException, UniqueException;

	/**
	 * Update, updates an user registered into bd depending if he wants to update his password or not.
	 *
	 * @param training the training
	 * @return the training
	 * @throws DatesException the date limit exception
	 */
	public Training update(Training training) throws DatesException;

	/**
	 * Delete.
	 *
	 * @param training the training
	 * @return the training
	 * @throws ExistInscriptionsException the exist inscriptions exception
	 * @throws UniqueException the unique exception
	 */
	public void delete(Training training) throws ExistInscriptionsException, UniqueException;

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param name the name
	 * @return the training
	 */
	public Training findByName(String name);

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id the id
	 * @return the training
	 */
	public Training findById(Long id);

	/**
	 * Get all trainings.
	 *
	 * @return List<Training>
	 */
	public List<Training> getTrainingList();

	/**
	 * Get all trainings with close = false.
	 *
	 * @return List<Training>
	 */
	public List<Training> getTrainingListOpen();

	/**
	 * Gets the training list close with close = true.
	 *
	 * @param year the year
	 * @return the training list close
	 */
	public List<Training> getTrainingListClose(int year);

	/**
	 * Create the new inscription.
	 *
	 * @param account the account
	 * @param training the training
	 * @throws MaximumCapacityException the maximum capacity exception
	 * @throws DateLimitExpirationException the date limit expiration exception
	 * @throws UserAlreadyJoinedException the user already joined exception
	 */
	public void createInscription(Account account, Training training)
			throws MaximumCapacityException, DateLimitExpirationException, UserAlreadyJoinedException;

	/**
	 * Update.
	 *
	 * @param inscription the inscription
	 * @return the inscription
	 */
	public void updateInscription(Inscription inscription);

	/**
	 * Unsubscribe Inscription.
	 *
	 * @param account the account
	 * @param training the training
	 * @return the inscription
	 * @throws UnsubscribeException the unsubscribe exception
	 */
	public void unsubscribeInscription(Account account, Training training) throws UnsubscribeException;

	/**
	 * Delete.
	 *
	 * @param accountId the account id
	 * @param trainingId the training id
	 * @return the inscription
	 */
	public void deleteInscription(Long accountId, Long trainingId);

	/**
	 * Find Inscription by accountId and trainingId.
	 *
	 * @param accountId the id of user
	 * @param trainingId the id of training
	 * @return inscription
	 */
	public Inscription findByInscriptionIds(Long accountId, Long trainingId);

	/**
	 * Get all Inscriptions by accountId.
	 *
	 * @param accountId the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Inscription> getInscriptionsByAccountId(Long accountId);

	/**
	 * Get all Inscriptions by trainingId.
	 *
	 * @param trainingId the id of training
	 * @return the List<Inscription> pertain to training
	 */
	public List<Inscription> getInscriptionsByTrainingId(Long trainingId);

	/**
	 * Get inscriptions id´s by accountId.
	 *
	 * @param accountId the id of user
	 * @return the List<Long> pertain to training
	 */
	public List<Long> getInscriptionsIdsByAccountId(Long accountId);

	/**
	 * Get inscriptions unsubscribe id´s by accountId.
	 *
	 * @param accountId the id of user
	 * @return the List<Long> pertain to traing
	 */
	public List<Long> getUnsubscribeIdsByAccountId(Long accountId);

	/**
	 * Gets the name users by inscription with role=ROLE_USER an active=true.
	 *
	 * @param trainingId the training id
	 * @return the name users by inscription
	 */
	public List<String> getUsernamesByInscription(Long trainingId);
}
