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
package org.cuacfm.members.model.inscription;

import java.util.List;

/**
 * The Interface InscriptionRepository.
 */
public interface InscriptionRepository {
	/**
	 * Save.
	 *
	 * @param inscription inscription
	 */
	public void save(Inscription inscription);

	/**
	 * Update.
	 * 
	 * @param inscription the inscription
	 */
	public void update(Inscription inscription);

	/**
	 * Delete.
	 *
	 * @param accountId the account id
	 * @param trainingId the training id
	 */
	public void delete(Long accountId, Long trainingId);

	/**
	 * Find by accountId.
	 *
	 * @param accountId the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Inscription> getByAccountId(Long accountId);

	/**
	 * Find by traningId.
	 *
	 * @param trainingId the training id
	 * @return the List<Inscription> pertain to traing
	 */
	public List<Inscription> getByTrainingId(Long trainingId);

	/**
	 * Find by accountId and trainingId.
	 *
	 * @param accountId the id of user
	 * @param trainingId the training id
	 * @return the inscription
	 */
	public Inscription findByInscriptionIds(Long accountId, Long trainingId);

	/**
	 * Get all Inscriptions by trainingId.
	 *
	 * @param trainingId the training id
	 * @return List<Inscription>
	 */
	public List<Inscription> getInscriptionListByTrainingId(Long trainingId);

	/**
	 * Find by accountId.
	 *
	 * @param accountId the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Long> getIdsByAccountId(Long accountId);

	/**
	 * Get inscriptionsId by accountId with unsubscribe = true.
	 *
	 * @param accountId the id of account
	 * @return List<Long> pertain to account
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
