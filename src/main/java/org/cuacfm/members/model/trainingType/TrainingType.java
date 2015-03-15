package org.cuacfm.members.model.trainingType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** The Class TrainingType. */
@SuppressWarnings("serial")
@Entity
public class TrainingType implements java.io.Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	@Column(unique = true)
	private String name;

	/** The required. */
	private boolean required;

	/** The description. */
	private String description;

	/** The place. */
	private String place;

	/** The duration. */
	private Float duration;

	/** Instantiates a new training. */
	protected TrainingType() {
		// Default empty constructor.
	}

	/**
	 * Instantiates a new training.
	 *
	 * @param name
	 *            String
	 * @param required
	 *            boolean
	 * @param description
	 *            String
	 * @param place
	 *            String
	 * @param duration
	 *            Float
	 */
	public TrainingType(String name, boolean required, String description,
			String place, Float duration) {
		super();
		this.name = name;
		this.required = required;
		this.description = description;
		this.place = place;
		this.duration = duration;
	}

	/**
	 * Get the id.
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Get the name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name.
	 *
	 * @param name
	 *            String, the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the required.
	 *
	 * @return the boolean isRequired
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Set the required.
	 *
	 * @param required
	 *            boolean
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Get the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description.
	 *
	 * @param description
	 *            String, the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the place.
	 *
	 * @return String
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Set the place.
	 *
	 * @param place
	 *            String, the new place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Get the duration.
	 *
	 * @return Float
	 */
	public Float getDuration() {
		return duration;
	}

	/**
	 * Set the duration.
	 *
	 * @param duration
	 *            Float, the new duration
	 */
	public void setDuration(Float duration) {
		this.duration = duration;
	}
}
