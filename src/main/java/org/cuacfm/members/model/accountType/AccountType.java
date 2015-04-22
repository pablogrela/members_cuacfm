package org.cuacfm.members.model.accountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class AccountType. */
@SuppressWarnings("serial")
@Entity
public class AccountType implements java.io.Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	@Column(unique = true)
	private String name;

	/** The description. */
	private String description;

	/** The discount in %. */
	private int discount;

	/** Instantiates a new account type. */
	protected AccountType() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new account type.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param discount
	 *            the discount
	 */
	public AccountType(String name, String description, int discount) {
		super();
		this.name = name;
		this.description = description;
		this.discount = discount;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	// If necessary for probe test in Junit, because is necessary one object in detach
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * Sets the discount.
	 *
	 * @param discount
	 *            the new discount
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
