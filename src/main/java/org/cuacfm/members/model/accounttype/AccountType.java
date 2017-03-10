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
package org.cuacfm.members.model.accounttype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class AccountType. */
@Entity
public class AccountType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;

	private boolean organization;
	private String description;
	private int discount;

	/** Instantiates a new account type. */
	protected AccountType() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new account type.
	 *
	 * @param name the name
	 * @param organization the organization
	 * @param description the description
	 * @param discount the discount
	 */
	public AccountType(String name, boolean organization, String description, int discount) {
		super();
		this.name = name;
		this.organization = organization;
		this.description = description;
		this.discount = discount;
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

	public boolean isOrganization() {
		return organization;
	}

	public void setOrganization(boolean organization) {
		this.organization = organization;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "AccountType [id=" + id + ", name=" + name + ", organization=" + organization + ", description=" + description + ", discount="
				+ discount + "]";
	}

}
