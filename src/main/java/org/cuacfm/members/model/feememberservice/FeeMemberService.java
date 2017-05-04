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
package org.cuacfm.members.model.feememberservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feemember.FeeMember;

/** The Class FeeMemberService. */
public interface FeeMemberService {

	/**
	 * Save.
	 *
	 * @param payInscription the pay inscription
	 * @return the pay inscription
	 * @throws UniqueException the unique exception
	 */
	public FeeMember save(FeeMember payInscription) throws UniqueException;

	/**
	 * Save user pay inscription.
	 *
	 * @param account the account
	 * @param payInscription the pay inscription
	 */
	public void savePayMember(Account account, FeeMember payInscription);

	/**
	 * Update.
	 *
	 * @param payInscription the pay inscription
	 * @return the pay inscription
	 * @throws UniqueException the unique exception
	 */
	public FeeMember update(FeeMember payInscription) throws UniqueException;

	/**
	 * Find by Name the payInscription.
	 *
	 * @param name the name
	 * @return FeeMember
	 */
	public FeeMember findByName(String name);

	/**
	 * Find by id returns payInscription which has this identifier.
	 *
	 * @param id the id
	 * @return payInscription
	 */
	public FeeMember findById(Long id);

	/**
	 * Find by year.
	 *
	 * @param year the year
	 * @return the pay inscription
	 */
	public FeeMember findByYear(int year);

	/**
	 * Get all payInscriptions.
	 *
	 * @return List<FeeMember>
	 */
	public List<FeeMember> getFeeMemberList();

	/**
	 * Gets the last fee member.
	 *
	 * @return the last fee member
	 */
	public FeeMember getLastFeeMember();
}
