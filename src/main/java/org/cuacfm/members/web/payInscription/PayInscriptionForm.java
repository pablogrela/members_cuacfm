package org.cuacfm.members.web.payInscription;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscriptionService.PayInscriptionService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

/** The Class PayInscriptionForm. */
public class PayInscriptionForm {

	/** The training type service. */
	@Autowired
	private PayInscriptionService payInscriptionService;

	/** The Constant NOT_BLANK_MESSAGE. */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The name. */
	@NotBlank(message = PayInscriptionForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = PayInscriptionForm.MAX_CHARACTERS)
	private String name;

	/** The year. */
	@Min(2015)
	private int year;

	/** The price. */
	@NotNull
	@Digits(fraction = 2, integer = 2)
	@DecimalMin("0.00")
	private Double price;

	/** The description. */
	@NotBlank(message = PayInscriptionForm.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = PayInscriptionForm.MAX_CHARACTERS)
	private String description;

	/** Instantiates a new training form. */
	public PayInscriptionForm() {
		// Default empty constructor.
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

	/**
	 * Creates the training Type.
	 *
	 * @return PayInscription
	 */
	public PayInscription createPayInscription() {

		return new PayInscription(getName(),
				getYear(), getPrice(),
				getDescription());
	}

}
