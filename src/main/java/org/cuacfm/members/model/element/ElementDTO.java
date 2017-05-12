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

import java.util.Date;

/**
 * The Class ReportDTO.
 */
public class ElementDTO {

	private Long id;
	private String name;
	private String description;
	private boolean book;
	private boolean location;
	private Date dateCreate;

	/**
	 * Instantiates a new element DTO.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param book the book
	 * @param location the location
	 */
	public ElementDTO(Long id, String name, String description, boolean book, boolean location, Date dateCreate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.book = book;
		this.location = location;
		this.dateCreate = dateCreate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isBook() {
		return book;
	}

	public void setBook(boolean book) {
		this.book = book;
	}

	public boolean isLocation() {
		return location;
	}

	public void setLocation(boolean location) {
		this.location = location;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public String toString() {
		return "ElementDTO [id=" + id + ", name=" + name + ", description=" + description + ", book=" + book + ", location=" + location
				+ ", dateCreate=" + dateCreate + "]";
	}

}
