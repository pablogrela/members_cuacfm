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
package org.cuacfm.members.model.reserve;

import java.util.List;

import org.cuacfm.members.model.account.Account;

/**
 * The Interface ReserveRepository.
 */
public interface ReserveRepository {

	/**
	 * Save.
	 *
	 * @param reserve the reserve
	 * @return reserve
	 */
	public Reserve save(Reserve reserve);

	/**
	 * Update.
	 *
	 * @param reserve the reserve
	 * @return reserve
	 */
	public Reserve update(Reserve reserve);

	/**
	 * Delete.
	 *
	 * @param reserve the reserve
	 */
	public void delete(Reserve reserve);

	/**
	 * Find by id.
	 *
	 * @param id the id of reserve
	 * @return the reserve
	 */
	public Reserve findById(Long id);

	/**
	 * Get all reserves.
	 *
	 * @return List<Reserve>
	 */
	public List<Reserve> getReserveList();

	/**
	 * Gets the reserve list conflicts.
	 *
	 * @param reserve the reserve
	 * @return the reserve list conflicts
	 */
	public List<Reserve> getReserveListConflicts(Reserve reserve);

	/**
	 * Get all active reserves.
	 *
	 * @return List<Reserve>
	 */
	public List<Reserve> getReserveListActive();

	/**
	 * Gets the reserve list close.
	 *
	 * @return the reserve list close
	 */
	public List<Reserve> getReserveListClose();

	/**
	 * Gets the reserve list by user.
	 *
	 * @param account the account
	 * @return the reserve list by user
	 */
	public List<Reserve> getReserveListByUser(Account account);

	/**
	 * Gets the reserve list by user.
	 *
	 * @param account the account
	 * @return the reserve list by user
	 */
	public List<Reserve> getReserveListActiveByUser(Account account);

}
