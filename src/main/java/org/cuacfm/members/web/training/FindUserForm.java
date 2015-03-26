package org.cuacfm.members.web.training;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/** The Class FindUserForm. */
public class FindUserForm {

	/** The Constant NOT_BLANK_MESSAGE. */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The login. */
	@NotBlank(message = FindUserForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = FindUserForm.MAX_CHARACTERS)
	private String login;

	/**
	 * Instantiates a new FindUserForm.
	 */
	public FindUserForm() {
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
}
