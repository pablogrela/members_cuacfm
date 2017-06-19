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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class ReturnReason.
 */
@Entity
public class ReturnReason implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String description;

	/**
	 * Instantiates a new return reason.
	 *
	 * @param id the id
	 * @param description the description
	 */
	public ReturnReason(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ReturnReason [id=" + id + ", description=" + description + "]";
	}

}
