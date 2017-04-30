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
package org.cuacfm.members.model.methodpayment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class MethodPayment. */
@Entity
public class MethodPayment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;
	private boolean directDebit;
	private String description;

	/** Instantiates a new method payment. */
	protected MethodPayment() {
		super();
	}

	/**
	 * Instantiates a new method payment.
	 *
	 * @param name the name
	 * @param directDebit the direct debit
	 * @param description the description
	 */
	public MethodPayment(String name, boolean directDebit, String description) {
		super();
		this.name = name;
		this.directDebit = directDebit;
		this.description = description;
	}

	public Long getId() {
		return id;
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

	public boolean isDirectDebit() {
		return directDebit;
	}

	public void setDirectDebit(boolean directDebit) {
		this.directDebit = directDebit;
	}

	@Override
	public String toString() {
		return "MethodPayment [id=" + id + ", name=" + name + ", directDebit=" + directDebit + ", description=" + description + "]";
	}

}
