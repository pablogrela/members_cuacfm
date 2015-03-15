package org.cuacfm.members.web.signup;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.*;
import org.cuacfm.members.model.account.Account;

/**
 * The Class SignupForm.
 */
public class SignupForm {

	/** The Constant NOT_BLANK_MESSAGE. */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	/** The Constant EMAIL_MESSAGE. */
	private static final String EMAIL_MESSAGE = "{email.message}";
	/** The Constant INSUFFICIENT_CHARACTERS. */
	private static final String INSUFFICIENT_CHARACTERS = "{insuficient.characters}";
	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The name. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = SignupForm.MAX_CHARACTERS)
	private String name;

	/** The login. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = SignupForm.MAX_CHARACTERS)
	private String login;

	/** The email. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	@Size(max = 30, message = SignupForm.MAX_CHARACTERS)
	private String email;

	/** The password. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(min = 4, max = 20, message = SignupForm.INSUFFICIENT_CHARACTERS)
	private String password;

	/** The retry password. */
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Size(min = 4, max = 20, message = SignupForm.INSUFFICIENT_CHARACTERS)
	private String rePassword;

	/** Instantiates a new sign up form. */
	public SignupForm() {
		// Default empty constructor.
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login
	 *            String, the new login
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the retry password.
	 *
	 * @return the retry password
	 */
	public String getRePassword() {
		return rePassword;
	}

	/**
	 * Sets the retry password.
	 *
	 * @param retry
	 *            password the new retry password
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	/**
	 * Creates the account.
	 *
	 * @return the account
	 */
	public Account createAccount() {
		return new Account(getName(), getLogin(), getEmail(), getPassword(),
				"ROLE_USER");
	}
}
