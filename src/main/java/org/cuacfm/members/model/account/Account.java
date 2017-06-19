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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.cuacfm.members.model.program.Program;

/** The Class Account. */
@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum roles {
		ROLE_PREREGISTERED, ROLE_USER, ROLE_EXUSER, ROLE_ADMIN,
	}

	public enum permissions {
		ROLE_REPORT, ROLE_BOOK, ROLE_TRAINER,
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String surname;
	private String nickName;

	@Column(unique = true)
	private String dni;

	private String address;
	private String cp;
	private String province;
	private String codeCountry;

	@Column(unique = true)
	private String login;

	@Column(unique = true)
	private String email;

	private String phone;
	private String mobile;
	private String password;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "methodPaymentId")
	private MethodPayment methodPayment;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "accountTypeId")
	private AccountType accountType;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "accounts")
	private List<Program> programs;

	@OrderBy("dateCreate DESC")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<BankAccount> bankAccounts;

	/** The installments, number of installments for pay inscription fee. */
	private int installments;

	/** The active is a check if account if active. */
	private boolean active;

	private boolean student;

	private boolean emitProgram;

	private Date dateBirth;

	private String observations;

	private String personality;

	private String knowledge;

	private String programName;

	@Enumerated(EnumType.STRING)
	private roles role;

	private String permissions;

	private String devicesToken;

	private Date dateCreate;

	private Date dateDown;

	// Temporal value, to register user in Firebase
	private String token;

	/** Instantiates a new account. */
	public Account() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new account.
	 *
	 * @param name the name
	 * @param dni the dni
	 * @param address the address
	 * @param login the login
	 * @param email the email
	 * @param phone the phone
	 * @param mobile the mobile
	 * @param password the password
	 * @param role the role
	 */
	public Account(String name, String surname, String dni, String address, String login, String email, String phone, String mobile, String password,
			roles role) {
		super();
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.login = login;
		this.email = email;
		this.phone = phone;
		this.mobile = mobile;
		this.password = password;
		this.installments = 1;
		this.role = role;
		this.dateCreate = new Date();
		this.active = true;
	}

	/**
	 * Instantiates a new account.
	 *
	 * @param name the name
	 * @param dni the dni
	 * @param address the address
	 * @param login the login
	 * @param email the email
	 * @param phone the phone
	 * @param mobile the mobile
	 * @param password the password
	 * @param role the role
	 * @param programName the program name
	 * @param student the student
	 * @param emitProgram the emit program
	 * @param personality the personality
	 * @param knowledge the knowledge
	 */
	public Account(String name, String surname, String dni, String address, String login, String email, String phone, String mobile, String password,
			roles role, String programName, boolean student, boolean emitProgram, String personality, String knowledge) {
		super();
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.login = login;
		this.email = email;
		this.phone = phone;
		this.mobile = mobile;
		this.password = password;
		this.installments = 1;
		this.active = true;
		this.role = role;
		this.programName = programName;
		this.student = student;
		this.emitProgram = emitProgram;
		this.personality = personality;
		this.knowledge = knowledge;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullNameNick() {
		if (nickName != null && !nickName.isEmpty()) {
			return getFullName() + " (" + nickName + ")";
		}
		return getFullName();
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

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCodeCountry() {
		return codeCountry;
	}

	public void setCodeCountry(String codeCountry) {
		this.codeCountry = codeCountry;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MethodPayment getMethodPayment() {
		return methodPayment;
	}

	public void setMethodPayment(MethodPayment methodPayment) {
		this.methodPayment = methodPayment;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public BankAccount activeBankAccount() {
		if (bankAccounts.isEmpty()) {
			return null;
		}
		return bankAccounts.get(0);
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public roles getRole() {
		return role;
	}

	public void setRole(roles role) {
		this.role = role;
	}

	public List<String> getPermissions() {
		List<String> newRoles = new ArrayList<>();
		if (permissions != null && !permissions.isEmpty() && !"[]".equals(permissions)) {
			String roleAux = permissions.replace("[", "").replace("]", "");
			newRoles = Arrays.asList(roleAux.split(", "));
		}
		return newRoles;
	}

	public void setPermissions(List<permissions> permissions) {
		this.permissions = permissions.toString();
	}

	public void addPermissions(permissions permissions) {
		Set<String> permissionsString = new LinkedHashSet<>(getPermissions());
		permissionsString.add(permissions.toString());
		this.permissions = permissionsString.toString();
	}

	public void removePermissions(permissions permissions) {
		List<String> permissionsString = new ArrayList<>(getPermissions());
		permissionsString.remove(permissions.toString());
		this.permissions = permissionsString.toString();
	}

	public List<String> getDevicesToken() {
		List<String> newDevicesToken = new ArrayList<>();
		if (devicesToken != null && !devicesToken.isEmpty() && !"[]".equals(devicesToken)) {
			String roleAux = devicesToken.replace("[", "").replace("]", "");
			newDevicesToken = Arrays.asList(roleAux.split(", "));
		}
		return newDevicesToken;
	}

	public void setDevicesToken(List<String> devicesToken) {
		this.devicesToken = devicesToken.toString();
	}

	public void addDeviceToken(String deviceToken) {
		Set<String> devicesTokenString = new LinkedHashSet<>(getDevicesToken());
		devicesTokenString.add(deviceToken);
		this.devicesToken = devicesTokenString.toString();
	}

	public void removeDevicesToken(String deviceToken) {
		List<String> permissionsString = new ArrayList<>(getDevicesToken());
		permissionsString.remove(deviceToken);
		this.devicesToken = permissionsString.toString();
	}

	public boolean isEmitProgram() {
		return emitProgram;
	}

	public void setEmitProgram(boolean emitProgram) {
		this.emitProgram = emitProgram;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", surname=" + surname + ", nickName=" + nickName + ", dni=" + dni + ", address=" + address
				+ ", cp=" + cp + ", province=" + province + ", codeCountry=" + codeCountry + ", login=" + login + ", email=" + email + ", phone="
				+ phone + ", mobile=" + mobile + ", password=" + password + ", methodPayment=" + methodPayment + ", accountType=" + accountType
				+ ", programs=" + programs + ", bankAccounts=" + bankAccounts + ", installments=" + installments + ", active=" + active + ", student="
				+ student + ", emitProgram=" + emitProgram + ", dateBirth=" + dateBirth + ", observations=" + observations + ", personality="
				+ personality + ", knowledge=" + knowledge + ", programName=" + programName + ", role=" + role + ", permissions=" + permissions
				+ ", devicesToken=" + devicesToken + ", dateCreate=" + dateCreate + ", dateDown=" + dateDown + ", token=" + token + "]";
	}

}
