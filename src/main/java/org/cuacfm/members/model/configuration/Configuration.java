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
package org.cuacfm.members.model.configuration;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class Configuration. */
@Entity
public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private int phone;
	private Double feeMember;
	private Double feeProgram;
	private String descriptionRule;

	/** Instantiates a new configuration. */
	protected Configuration() {
		super();
	}

	/**
	 * Instantiates a new configuration.
	 *
	 * @param name the name
	 * @param email the email
	 * @param phone the phone
	 * @param feeMember the fee member
	 * @param feeProgram the fee program
	 * @param descriptionRule the description rul
	 */
	public Configuration(String name, String email, int phone, Double feeMember, Double feeProgram, String descriptionRule) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.feeMember = feeMember;
		this.feeProgram = feeProgram;
		this.descriptionRule = descriptionRule;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public Double getFeeMember() {
		return feeMember;
	}

	public void setFeeMember(Double feeMember) {
		this.feeMember = feeMember;
	}

	public Double getFeeProgram() {
		return feeProgram;
	}

	public void setFeeProgram(Double feeProgram) {
		this.feeProgram = feeProgram;
	}

	public String getDescriptionRule() {
		return descriptionRule;
	}

	public void setDescriptionRule(String descriptionRule) {
		this.descriptionRule = descriptionRule;
	}

	@Override
	public String toString() {
		return "Configuration [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", feeMember=" + feeMember + ", feeProgram="
				+ feeProgram + ", descriptionRule=" + descriptionRule + "]";
	}

}
