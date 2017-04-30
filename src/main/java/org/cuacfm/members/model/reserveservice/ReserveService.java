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
package org.cuacfm.members.model.reserveservice;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UserAlreadyReserveException;
import org.cuacfm.members.model.reserve.Reserve;
import org.cuacfm.members.model.reserve.ReserveDTO;

/** The Class ReserveService. */
public interface ReserveService {

	/**
	 * Save.
	 *
	 * @param reserve the reserve
	 * @return the reserve
	 */
	public Reserve save(Reserve reserve) throws DatesException, UserAlreadyReserveException;

	/**
	 * Update Reserve.
	 *
	 * @param reserve the reserve
	 * @return Reserve
	 */
	public Reserve update(Reserve reserve) throws DatesException;

	/**
	 * Delete.
	 *
	 * @param reserve the reserve
	 */
	public void delete(Reserve reserve);

	/**
	 * Delete.
	 *
	 * @param reserve the reserve
	 * @param account the account
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Reserve reserve, Account account) throws ExistPaymentsException;

	/**
	 * Find by id returns reserve which has this identifier.
	 *
	 * @param id the id
	 * @return reserve
	 */
	public Reserve findById(Long id);

	/**
	 * Get all reserves.
	 *
	 * @return List<Reserve>
	 */
	public List<Reserve> getReserveList();

	/**
	 * Get all active reserves.
	 *
	 * @return List<Reserve>
	 */
	public List<Reserve> getReserveListActive();

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

	/**
	 * Gets the reserve list close.
	 *
	 * @return the reserve list close
	 */
	public List<Reserve> getReserveListClose();

	/**
	 * Up.
	 *
	 * @param reserve the reserve
	 */
	public void up(Reserve reserve);

	/**
	 * Accept.
	 *
	 * @param reserve the reserve
	 */
	public void accept(Reserve reserve);

	/**
	 * Deny.
	 *
	 * @param reserve the reserve
	 */
	public void deny(Reserve reserve);

	/**
	 * Answer.
	 *
	 * @param reserve the reserve
	 * @param account the account
	 * @param manage the manage
	 * @param answer the answer
	 * @return the reserve
	 */
	public Reserve answer(Reserve reserve, Account account, String answer, Boolean manage);

	/**
	 * Gets the reserves DTO.
	 *
	 * @param reserves the reserves
	 * @return the reserves DTO
	 */
	public List<ReserveDTO> getReservesDTO(List<Reserve> reserves);

	/**
	 * Gets the reserve DTO.
	 *
	 * @param reserve the reserve
	 * @return the reserve DTO
	 */
	public ReserveDTO getReserveDTO(Reserve reserve);

	/**
	 * Gets the reserve.
	 *
	 * @param reserveDTO the reserve DTO
	 * @param account the account
	 * @return the reserve
	 */
	public Reserve getReserve(ReserveDTO reserveDTO, Account account);
}
