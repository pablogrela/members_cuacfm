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
package org.cuacfm.members.web.signup;

import javax.validation.constraints.Size;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/** The Class SignupForm. */
public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String INSUFFICIENT_CHARACTERS = "{insuficient.characters}";
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The name. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = SignupForm.MAX_CHARACTERS)
	private String name;

	/** The login. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = SignupForm.MAX_CHARACTERS)
	private String login;

	/** The dni. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 10, message = SignupForm.MAX_CHARACTERS)
	private String dni;

	/** The address. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 50, message = SignupForm.MAX_CHARACTERS)
	private String address;

	/** The email. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	@Size(max = 50, message = SignupForm.MAX_CHARACTERS)
	private String email;

	private boolean student;
	private boolean studentTrue;
	private boolean studentFalse;

	private boolean emitProgram;
	private boolean emitProgramTrue;
	private boolean emitProgramFalse;

	/** The phone. */
	@Size(max = 20, message = SignupForm.MAX_CHARACTERS)
	private String phone;

	/** The mobile. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 20, message = SignupForm.MAX_CHARACTERS)
	private String mobile;

	/** The rule. */
	private boolean rule;

	/** The program name. */
	@Size(max = 50, message = SignupForm.MAX_CHARACTERS)
	private String programName;

	/** The password. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(min = 6, max = 80, message = SignupForm.INSUFFICIENT_CHARACTERS)
	private String password;

	/** The retry password. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(min = 6, max = 80, message = SignupForm.INSUFFICIENT_CHARACTERS)
	private String rePassword;

	@Size(max = 500, message = SignupForm.MAX_CHARACTERS)
	private String personality;

	@Size(max = 500, message = SignupForm.MAX_CHARACTERS)
	private String knowledge;

	private String captcha;

	/** Instantiates a new sign up form. */
	public SignupForm() {
		// Default empty constructor.
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
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

	public void setDni(String dni) {
		this.dni = dni;
	}

	public boolean getRule() {
		return rule;
	}

	public void setRule(boolean rule) {
		this.rule = rule;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	/**
	 * Creates the account.
	 *
	 * @return the account
	 */
	public Account createAccount() {
		return new Account(getName(), getDni(), getAddress(), getLogin(), getEmail(), getPhone(), getMobile(), getPassword(),
				roles.ROLE_PREREGISTERED, programName, studentTrue, emitProgramTrue, personality, knowledge);
	}
}
