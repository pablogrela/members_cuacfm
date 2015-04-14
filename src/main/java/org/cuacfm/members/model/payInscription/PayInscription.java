package org.cuacfm.members.model.payInscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class PayInscription. */
@SuppressWarnings("serial")
@Entity
public class PayInscription implements java.io.Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	private String name;

	/** The year. */
	private int year;

	/** The duration. */
	private Double price;

	/** The description. */
	private String description;

	/**
	 * Instantiates a new pay inscription.
	 */
	public PayInscription() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new pay inscription.
	 *
	 * @param name
	 *            the name
	 * @param year
	 *            the year
	 * @param price
	 *            the price
	 * @param description
	 *            the description
	 */
	public PayInscription(String name, int year, Double price,
			String description) {
		super();
		this.name = name;
		this.year = year;
		this.price = price;
		this.description = description;
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
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year
	 *            the new year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price
	 *            the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
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

}
