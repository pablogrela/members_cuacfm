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
package org.cuacfm.members.model.account;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account.roles;

/** The Class AccountDTO. */
public class AccountDTO {

	private Long id;
	private String name;
	private String surname;
	private String nickName;
	private String dni;
	private String address;
	private String login;
	private String email;
	private String phone;
	private String mobile;
	private boolean active;
	private boolean deviceToken;
	private roles role;
	private List<String> permissions;
	private String methodPayment;
	private String accountType;
	private int installments;
	private Date dateCreate;
	private Date dateDown;

	/**
	 * Instantiates a new account DTO.
	 */
	public AccountDTO() {
		super();
	}

	/**
	 * Instantiates a new account DTO.
	 *
	 * @param id the id
	 * @param login the login
	 * @param dni the dni
	 * @param email the email
	 */
	public AccountDTO(Long id, String login, String dni, String email) {
		super();
		this.id = id;
		this.dni = dni;
		this.login = login;
		this.email = email;
	}

	/**
	 * Instantiates a new account DTO.
	 *
	 * @param id the id
	 * @param login the login
	 * @param dni the dni
	 * @param email the email
	 * @param name the name
	 * @param nickName the nick name
	 * @param address the address
	 * @param active the active
	 * @param role the role
	 * @param installments the installments
	 */
	public AccountDTO(Long id, String login, String dni, String email, String phone, String mobile, String name, String surname, String nickName,
			String address, boolean active, boolean deviceToken, roles role, List<String> permissions, int installments, Date dateCreate,
			Date dateDown) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.nickName = nickName;
		this.dni = dni;
		this.address = address;
		this.login = login;
		this.email = email;
		this.phone = phone;
		this.mobile = mobile;
		this.active = active;
		this.deviceToken = deviceToken;
		this.role = role;
		this.permissions = permissions;
		this.installments = installments;
		this.dateCreate = dateCreate;
		this.dateDown = dateDown;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}
	
	public String getFullNameNick() {
		if (nickName != null && !nickName.isEmpty()){
			return getFullName() + " (" + nickName + ")";
		}
		return getFullName();
	}

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(boolean deviceToken) {
		this.deviceToken = deviceToken;
	}

	public roles getRole() {
		return role;
	}

	public void setRole(roles role) {
		this.role = role;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}

	public String getMethodPayment() {
		return methodPayment;
	}

	public void setMethodPayment(String methodPayment) {
		this.methodPayment = methodPayment;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateDown() {
		return dateDown;
	}

	public void setDateDown(Date dateDown) {
		this.dateDown = dateDown;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", name=" + name + ", surname=" + surname + ", nickName=" + nickName + ", dni=" + dni + ", address=" + address
				+ ", login=" + login + ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", active=" + active + ", deviceToken="
				+ deviceToken + ", role=" + role + ", permissions=" + permissions + ", methodPayment=" + methodPayment + ", accountType="
				+ accountType + ", installments=" + installments + ", dateCreate=" + dateCreate + ", dateDown=" + dateDown + "]";
	}

}
