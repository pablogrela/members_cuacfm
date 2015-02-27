package org.cuacfm.members.model.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/** The Class Account. */
@SuppressWarnings("serial")
@Entity
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_ID, query = "select a from Account a where a.id = :id"),
		@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
		@NamedQuery(name = Account.FIND_BY_LOGIN, query = "select a from Account a where a.login = :login"),
		@NamedQuery(name = Account.DIFERENT_EMAIL_OR_LOGIN, query = "select a from Account a where "
				+ "(a.login = :login or a.email = :email)")})
public class Account implements java.io.Serializable {

	/** The Constant FIND_BY_ID. */
	public static final String FIND_BY_ID = "Account.findById";

	/** The Constant FIND_BY_EMAIL. */
	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	/** The Constant FIND_BY_LOGIN. */
	public static final String FIND_BY_LOGIN = "Account.findByLogin";

	/** The Constant FIND_BY_LOGIN. */
	public static final String DIFERENT_EMAIL_OR_LOGIN = "Account.diferent";
	
	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	private String name;

	/** The login. */
	@Column(unique = true)
	private String login;

	/** The email. */
	@Column(unique = true)
	private String email;

	/** The password. */
	@JsonIgnore
	private String password;

	/** The role. */
	private String role = "ROLE_USER";

	/** Instantiates a new account. */
	protected Account() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new account.
	 *
	 * @param name
	 *            name
	 * @param login
	 *            login
	 * @param email
	 *            email
	 * @param password
	 *            the password
	 * @param role
	 *            the role
	 */
	public Account(String name, String login, String email, String password,
			String role) {
		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 *            the new login
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role
	 *            the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
