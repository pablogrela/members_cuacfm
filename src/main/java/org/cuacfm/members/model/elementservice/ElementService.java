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
package org.cuacfm.members.model.elementservice;

import java.util.List;

import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.element.ElementDTO;
import org.cuacfm.members.model.exceptions.UniqueException;

/** The Class ElementService. */
public interface ElementService {

	/**
	 * Save an element into database.
	 *
	 * @param element the training
	 * @return Element
	 * @throws UniqueException the unique exception
	 */
	public Element save(Element element) throws UniqueException;

	/**
	 * Update Element.
	 *
	 * @param element the element
	 * @return Element
	 * @throws UniqueException the unique exception
	 */
	public Element update(Element element) throws UniqueException;

	/**
	 * Delete.
	 *
	 * @param element the element
	 */
	public void delete(Element element);

	/**
	 * Find by Name the element.
	 *
	 * @param name the name
	 * @return Element
	 */
	public Element findByName(String name);

	/**
	 * Find by id returns element which has this identifier.
	 *
	 * @param id the id
	 * @return element
	 */
	public Element findById(Long id);

	/**
	 * Get all elements.
	 *
	 * @return List<Element>
	 */
	public List<Element> getElementList();

	/**
	 * Get all active elements.
	 *
	 * @return List<Element>
	 */
	public List<Element> getElementListLocation();

	/**
	 * Gets the element list by user.
	 *
	 * @return the element list by user
	 */
	public List<Element> getElementListReservable();

	/**
	 * Gets the element list by user.
	 *
	 * @return the element list by user
	 */
	public List<Element> getElementListReservableLocation();

	/**
	 * Gets the elements DTO.
	 *
	 * @param elements the elements
	 * @return the elements DTO
	 */
	public List<ElementDTO> getElementsDTO(List<Element> elements);

	/**
	 * Gets the element DTO.
	 *
	 * @param element the element
	 * @return the element DTO
	 */
	public ElementDTO getElementDTO(Element element);
}
