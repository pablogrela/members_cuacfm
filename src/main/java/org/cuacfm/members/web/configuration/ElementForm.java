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
package org.cuacfm.members.web.configuration;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.util.Constants;
import org.hibernate.validator.constraints.NotBlank;

/** The Class ElementForm. */
public class ElementForm {

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = Constants.MAX_CHARACTERS)
	private String name;

	@NotBlank(message = Constants.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = Constants.MAX_CHARACTERS)
	private String description;

	private boolean location;

	private boolean reservable;

	/** Instantiates a new element */
	public ElementForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLocation() {
		return location;
	}

	public void setLocation(boolean location) {
		this.location = location;
	}

	public boolean isReservable() {
		return reservable;
	}

	public void setReservable(boolean reservable) {
		this.reservable = reservable;
	}

	/**
	 * Creates the element.
	 *
	 * @return the element
	 */
	public Element createElement() {
		return new Element(getName(), getDescription(), isReservable(), isLocation());
	}

	/**
	 * Update element.
	 *
	 * @param element the element
	 * @return the element
	 */
	public Element updateElement(Element element) {
		element.setName(getName());
		element.setDescription(getDescription());
		element.setReservable(isReservable());
		element.setLocation(isLocation());
		return element;
	}
}
