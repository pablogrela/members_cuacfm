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
package org.cuacfm.members.model.feemember;

import java.util.List;

/** The Interface FeeMemberRepository. */
public interface FeeMemberRepository {

	/**
	 * Save.
	 *
	 * @param payInscription the fee member
	 * @return the fee member
	 */
	public FeeMember save(FeeMember payInscription);

	/**
	 * Update.
	 *
	 * @param payInscription the fee member
	 * @return the fee member
	 */
	public FeeMember update(FeeMember payInscription);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the fee member
	 */
	public FeeMember findById(Long id);

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the fee member
	 */
	public FeeMember findByName(String name);

	/**
	 * Find by year.
	 *
	 * @param year the year
	 * @return the fee member
	 */
	public FeeMember findByYear(int year);

	/**
	 * Gets the fee member list.
	 *
	 * @return the fee member list
	 */
	public List<FeeMember> getFeeMemberList();

	/**
	 * Gets the last fee member.
	 *
	 * @return the last fee member
	 */
	public FeeMember getLastFeeMember();
}
