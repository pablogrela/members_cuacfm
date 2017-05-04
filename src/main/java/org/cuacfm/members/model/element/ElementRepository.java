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
package org.cuacfm.members.model.element;

import java.util.List;

/**
 * The Interface ElementRepository.
 */
public interface ElementRepository {

	/**
	 * Save.
	 *
	 * @param report the report
	 * @return report
	 */
	public Element save(Element report);

	/**
	 * Update.
	 *
	 * @param report the report
	 * @return report
	 */
	public Element update(Element report);

	/**
	 * Delete.
	 *
	 * @param report the report
	 */
	public void delete(Element report);

	/**
	 * Find by id.
	 *
	 * @param id the id of report
	 * @return the report
	 */
	public Element findById(Long id);

	/**
	 * Find by login.
	 *
	 * @param name the name of report
	 * @return Element
	 */
	public Element findByName(String name);

	/**
	 * Get all reports.
	 *
	 * @return List<Element>
	 */
	public List<Element> getElementList();

	/**
	 * Get all active reports.
	 *
	 * @return List<Element>
	 */
	public List<Element> getElementListReservable();

	/**
	 * Gets the report list close.
	 *
	 * @return the report list close
	 */
	public List<Element> getElementListLocation();

	/**
	 * Gets the report list by user.
	 *
	 * @return the report list by user
	 */
	public List<Element> getElementListReservableLocation();

}
