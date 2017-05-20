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
package org.cuacfm.members.test.model.elementservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.element.ElementDTO;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class ElementServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ElementServiceTest extends WebSecurityConfigurationAware {

	@Inject
	private ElementService elementService;

	/**
	 * Save and find element test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveAndFindElementTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		// Assert
		Element elementSearched = elementService.findById(element.getId());
		assertEquals(element, elementSearched);
	}

	/**
	 * Save element exception test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void saveElementExceptionTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		Element element2 = new Element("element 1", "element 1", true, true);
		elementService.save(element2);
	}

	/**
	 * Update element test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void updateElementTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		// Update
		element.setName("Young");
		element.setBook(true);
		element.setLocation(true);
		element.setDescription("new");
		Element elementUpdate = elementService.update(element);

		// Assert
		assertEquals(element, elementUpdate);
		assertEquals(element.getName(), elementUpdate.getName());
		assertEquals(element.isBook(), elementUpdate.isBook());
		assertEquals(element.isLocation(), elementUpdate.isLocation());
		assertEquals(element.getDescription(), elementUpdate.getDescription());

		element.setLocation(false);
		element.setDescription("etc");
		elementUpdate = elementService.update(element);

		Element element2 = new Element("element 2", "element 1", true, true);
		elementService.update(element2);
	}

	/**
	 * Update element exception test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test(expected = UniqueException.class)
	public void updateElementExceptionTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);
		Element element2 = new Element("element 2", "element 1", true, true);
		elementService.save(element2);

		// Update
		Element element3 = new Element("element 1", "element 1", true, true);
		elementService.update(element3);
	}

	/**
	 * Delete element test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void deleteElementTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		// Assert
		Element elementSearched = elementService.findById(element.getId());
		assertNotNull(elementSearched);

		// Delete
		elementService.delete(element);

		// Assert, no exist Element
		elementSearched = elementService.findById(element.getId());
		assertNull(elementSearched);
	}

	/**
	 * Delete null element test.
	 */
	@Test
	public void deleteNullElementTest() {
		// Delete
		elementService.delete(null);
	}

	/**
	 * Save and find account type test.
	 *
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void saveAndFindByNameElementTest() throws UniqueException {
		// Save
		Element element = new Element("element 1", "element 1", true, true);
		elementService.save(element);

		// Assert
		Element elementSearched = elementService.findByName(element.getName());
		assertEquals(element, elementSearched);
	}

	/**
	 * Gets the elements test.
	 *
	 * @return the elements test
	 * @throws UniqueException the unique exception
	 */
	@Test
	public void getElementsTest() throws UniqueException {
		// Assert
		List<Element> elements = elementService.getElementList();
		assertEquals(elements.size(), 0);

		// Save
		Element element = new Element("element 1", "element 1", false, true);
		elementService.save(element);
		Element element2 = new Element("element 2", "element 1", true, false);
		elementService.save(element2);
		Element element3 = new Element("element 3", "element 1", true, true);
		elementService.save(element3);

		// Assert
		elements = elementService.getElementList();
		assertEquals(elements.size(), 3);
		assertTrue(elements.contains(element));
		assertTrue(elements.contains(element2));
		assertTrue(elements.contains(element3));

		elements = elementService.getElementListBook();
		assertEquals(elements.size(), 2);
		assertTrue(elements.contains(element2));
		assertTrue(elements.contains(element3));

		elements = elementService.getElementListLocation();
		assertEquals(elements.size(), 2);
		assertTrue(elements.contains(element));
		assertTrue(elements.contains(element3));

		elements = elementService.getElementListBookLocation();
		assertEquals(elements.size(), 1);
		assertTrue(elements.contains(element3));

		List<ElementDTO> elementsDTO = elementService.getElementsDTO(elementService.getElementListBookLocation());
		ElementDTO elementDTO = elementService.getElementDTO(element3);
		assertEquals(elementDTO.getId(), elementsDTO.get(0).getId());
		ElementDTO element2DTO = elementService.getElementDTO(null);
		assertNull(element2DTO);
		elementDTO.setName(elementDTO.getName());
		elementDTO.setDescription(elementDTO.getDescription());
		elementDTO.setBook(elementDTO.isBook());
		elementDTO.setLocation(elementDTO.isLocation());
		elementDTO.setDateCreate(elementDTO.getDateCreate());	
	}
}
