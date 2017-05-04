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
package org.cuacfm.members.model.directdebit;

import java.util.List;

/** The Interface ReturnReasonRepository. */
public interface ReturnReasonRepository {

	/**
	 * Save.
	 *
	 * @param returnReason the return reason
	 * @return the return reason
	 */
	public ReturnReason save(ReturnReason returnReason);

	/**
	 * Update.
	 *
	 * @param returnReason the return reason
	 * @return the return reason
	 */
	public ReturnReason update(ReturnReason returnReason);

	/**
	 * Removes the.
	 *
	 * @param returnReason the return reason
	 */
	public void remove(ReturnReason returnReason);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the return reason
	 */
	public ReturnReason findById(String id);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ReturnReason> findAll();

}
