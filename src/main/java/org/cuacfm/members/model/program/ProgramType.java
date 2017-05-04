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
package org.cuacfm.members.model.program;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class Program Type. */
@Entity
public class ProgramType implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The name. */
	@Column(unique = true)
	private String name;

	/** The description. */
	private String description;

	/**
	 * Instantiates a new program type.
	 */
	protected ProgramType() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new program type.
	 *
	 * @param name the name
	 * @param description the description
	 */
	public ProgramType(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProgramType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
