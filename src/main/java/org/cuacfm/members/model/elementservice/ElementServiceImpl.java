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
package org.cuacfm.members.model.elementservice;

import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.element.ElementDTO;
import org.cuacfm.members.model.element.ElementRepository;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.util.Constants.levels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class ElementServiceImpl. */
@Service("elementService")
public class ElementServiceImpl implements ElementService {

	private static final Logger logger = LoggerFactory.getLogger(ElementServiceImpl.class);

	@Autowired
	private ElementRepository elementRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new element service. */
	public ElementServiceImpl() {
		super();
	}

	@Override
	public Element save(Element element) throws UniqueException {
		logger.info("save element element");

		if (elementRepository.findByName(element.getName()) != null) {
			throw new UniqueException("Name", element.getName());
		}
		Object[] arguments = { element.getName() };
		eventService.save("element.create.success", null, levels.MEDIUM, arguments);
		return elementRepository.save(element);
	}

	@Override
	public Element update(Element element) throws UniqueException {
		logger.info("save update element");
		Element elementSearch = elementRepository.findByName(element.getName());
		if ((elementSearch != null) && (elementSearch.getId() != element.getId())) {
			throw new UniqueException("Name", element.getName());
		}
		Object[] arguments = { element.getName() };
		eventService.save("element.edit.success", null, levels.MEDIUM, arguments);
		return elementRepository.update(element);
	}

	@Override
	public void delete(Element element) {
		Object[] arguments = { element.getName() };
		elementRepository.delete(element);
		eventService.save("element.delete.success", null, levels.MEDIUM, arguments);
	}

	@Override
	public Element findByName(String login) {
		return elementRepository.findByName(login);
	}

	@Override
	public Element findById(Long id) {
		return elementRepository.findById(id);
	}

	@Override
	public List<Element> getElementList() {
		return elementRepository.getElementList();
	}

	@Override
	public List<Element> getElementListReservable() {
		return elementRepository.getElementListReservable();
	}

	@Override
	public List<Element> getElementListLocation() {
		return elementRepository.getElementListLocation();
	}

	@Override
	public List<Element> getElementListReservableLocation() {
		return elementRepository.getElementListReservableLocation();
	}

	@Override
	public List<ElementDTO> getElementsDTO(List<Element> elements) {
		List<ElementDTO> elementsDTO = new ArrayList<>();
		for (Element element : elements) {
			elementsDTO.add(getElementDTO(element));
		}
		return elementsDTO;
	}

	@Override
	public ElementDTO getElementDTO(Element element) {
		ElementDTO elementDTO = null;

		if (element != null) {
			elementDTO = new ElementDTO(element.getId(), element.getName(), element.getDescription(), element.isReservable(), element.isLocation(),
					element.getDateCreate());
		}
		return elementDTO;
	}

}
