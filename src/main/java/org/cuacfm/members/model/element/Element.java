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
package org.cuacfm.members.model.element;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class Report. */
@Entity
public class Element implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private boolean reservable;
	private boolean location;
	private Date dateCreate;

	/** Instantiates a new Element. */
	protected Element() {
		super();
	}

	/**
	 * Instantiates a new element.
	 *
	 * @param name the name
	 * @param reservable the reservable
	 * @param location the location
	 */
	public Element(String name, String description, boolean reservable, boolean location) {
		super();
		this.name = name;
		this.description = description;
		this.reservable = reservable;
		this.location = location;
		this.dateCreate = new Date();
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

	public boolean isReservable() {
		return reservable;
	}

	public void setReservable(boolean reservable) {
		this.reservable = reservable;
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
		return "Element [id=" + id + ", name=" + name + ", description=" + description + ", reservable=" + reservable + ", location=" + location
				+ ", dateCreate=" + dateCreate + "]";
	}

}
