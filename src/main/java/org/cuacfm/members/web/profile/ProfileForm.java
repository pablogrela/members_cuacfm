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
package org.cuacfm.members.web.profile;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accounttype.AccountType;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/** The Class ProfileForm. */
public class ProfileForm {

	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String INSUFFICIENT_CHARACTERS = "{insuficient.characters}";
	private static final String MAX_CHARACTERS = "{max.characters}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	/** The name. */
	@NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String name;

	/** The nickName. */
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String nickName;

	/** The dni. */
	@NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
	@Size(max = 10, message = ProfileForm.MAX_CHARACTERS)
	private String dni;

	/** The address. */
	@NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String address;

	/** The cp. */
	@Size(max = 10, message = ProfileForm.MAX_CHARACTERS)
	private String cp;

	/** The province. */
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String province;

	/** The code country. */
	@Size(max = 2, message = ProfileForm.MAX_CHARACTERS)
	private String codeCountry;

	/** The login. */
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String login;

	/** The email. */
	@Email(message = ProfileForm.EMAIL_MESSAGE)
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String email;

	/** The phone. */
	@Size(max = 20, message = ProfileForm.MAX_CHARACTERS)
	private String phone;

	/** The mobile. */
	@NotBlank(message = ProfileForm.NOT_BLANK_MESSAGE)
	@Size(max = 20, message = ProfileForm.MAX_CHARACTERS)
	private String mobile;

	/** The program name. */
	@Size(max = 50, message = ProfileForm.MAX_CHARACTERS)
	private String programName;

	private boolean student;
	private boolean studentTrue;
	private boolean studentFalse;

	private boolean emitProgram;
	private boolean emitProgramTrue;
	private boolean emitProgramFalse;

	/** The date birth. */
	private String dateBirth;

	/** The password. */
	@Size(min = 6, max = 20, message = ProfileForm.INSUFFICIENT_CHARACTERS)
	private String password;

	/** The retry password. */
	@Size(min = 6, max = 20, message = ProfileForm.INSUFFICIENT_CHARACTERS)
	private String newPassword;

	/** The retry password. */
	@Size(min = 6, max = 20, message = ProfileForm.INSUFFICIENT_CHARACTERS)
	private String rePassword;

	/** The accountType List. */
	private List<AccountType> accountTypes;

	/** The Global variable accountTypeId. */
	private Long accountTypeId;

	/** The methodPayment List. */
	private List<MethodPayment> methodPayments;

	/** The Global variable methodPaymentId. */
	private Long methodPaymentId;

	/** The installments. */
	@Min(0)
	@Max(12)
	private int installments;

	/** The observations. */
	@Size(max = 500, message = ProfileForm.MAX_CHARACTERS)
	private String observations;

	@Size(max = 500, message = ProfileForm.MAX_CHARACTERS)
	private String personality;

	@Size(max = 500, message = ProfileForm.MAX_CHARACTERS)
	private String knowledge;

	/** The roles. */
	private List<roles> roles;

	/** The role. */
	private String role;

	/** The on login. */
	private boolean onLogin;

	/** The on email. */
	private boolean onEmail;

	/** The on password. */
	private boolean onPassword;

	/** The on role. */
	private boolean onRole;

	/**
	 * Instantiates a new profile form.
	 */
	public ProfileForm() {
		// Default empty constructor.
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public boolean isStudentTrue() {
		return studentTrue;
	}

	public void setStudentTrue(boolean studentTrue) {
		this.studentTrue = studentTrue;
	}

	public boolean isStudentFalse() {
		return studentFalse;
	}

	public void setStudentFalse(boolean studentFalse) {
		this.studentFalse = studentFalse;
	}

	public boolean isEmitProgram() {
		return emitProgram;
	}

	public void setEmitProgram(boolean emitProgram) {
		this.emitProgram = emitProgram;
	}

	public boolean isEmitProgramTrue() {
		return emitProgramTrue;
	}

	public void setEmitProgramTrue(boolean emitProgramTrue) {
		this.emitProgramTrue = emitProgramTrue;
	}

	public boolean isEmitProgramFalse() {
		return emitProgramFalse;
	}

	public void setEmitProgramFalse(boolean emitProgramFalse) {
		this.emitProgramFalse = emitProgramFalse;
	}

	public String getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public List<AccountType> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<AccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public List<MethodPayment> getMethodPayments() {
		return methodPayments;
	}

	public void setMethodPayments(List<MethodPayment> methodPayments) {
		this.methodPayments = methodPayments;
	}

	public Long getMethodPaymentId() {
		return methodPaymentId;
	}

	public void setMethodPaymentId(Long methodPaymentId) {
		this.methodPaymentId = methodPaymentId;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<Account.roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Account.roles> roles) {
		this.roles = roles;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public boolean isOnLogin() {
		return onLogin;
	}

	public void setOnLogin(boolean onLogin) {
		this.onLogin = onLogin;
	}

	public boolean isOnEmail() {
		return onEmail;
	}

	public void setOnEmail(boolean onEmail) {
		this.onEmail = onEmail;
	}

	public boolean isOnPassword() {
		return onPassword;
	}

	public void setOnPassword(boolean onPassword) {
		this.onPassword = onPassword;
	}

	public boolean isOnRole() {
		return onRole;
	}

	public void setOnRole(boolean onRole) {
		this.onRole = onRole;
	}

}
