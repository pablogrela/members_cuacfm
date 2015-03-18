package org.cuacfm.members.web.training;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.web.support.DisplayDate;
import org.hibernate.validator.constraints.NotBlank;

public class TrainingForm {

/** The Constant NOT_BLANK_MESSAGE. */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	/** The Constant MAX_CHARACTERS. */
	private static final String MAX_CHARACTERS = "{max.characters}";

	/** The name. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = TrainingForm.MAX_CHARACTERS)
	private String name;

	/** The timeTraining. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	private String timeTraining;

	/** The dateTraining. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	private String dateTraining;

	/** The timeLimit. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	private String timeLimit;

	/** The dateLimit. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	private String dateLimit;

	/** The description. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	@Size(max = 500, message = TrainingForm.MAX_CHARACTERS)
	private String description;

	/** The place. */
	@NotBlank(message = TrainingForm.NOT_BLANK_MESSAGE)
	@Size(max = 30, message = TrainingForm.MAX_CHARACTERS)
	private String place;

	/** The duration. */
	@NotNull
	@Digits(fraction = 2, integer = 2)
	//@DecimalMin("0.01")
	@Min(0)
	private float duration;

	/** The countPlaces. */
	@NotNull
	@Digits(fraction = 0, integer = 2)
	@Min(0)
	private int countPlaces;
	
	/** The maxPlaces. */
	@NotNull
	@Digits(fraction = 0, integer = 2)
	@Min(1)
	private int maxPlaces;


	/** The close. */
	private Boolean close;
	
	
	/** Instantiates a new training form. */
	public TrainingForm() {
		// Default empty constructor.
	}

	/**
	 * Get the name.
	 *
	 * @return the name
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
	 * Get the close.
	 *
	 * @return the String getClose
	 */
	public Boolean getClose() {
		if (close == null) {
			close = false;
		} else {
			close = true;
		}
		return close;
	}

	/**
	 * Set the close.
	 *
	 * @param close
	 *            Boolean
	 */
	public void setClose(Boolean close) {
		this.close = close;
	}

	/**
	 * Get the dateTrining.
	 *
	 * @return Calendar with timeLimit
	 */
	public String getTimeTraining() {
		return timeTraining;
	}

	/**
	 * Set the dateTraining
	 *
	 * @param dateTraining
	 *            String, Calendar
	 */
	public void setTimeTraining(String timeTraining) {
		this.timeTraining = timeTraining;
	}

	/**
	 * Get the dateTrinig.
	 *
	 * @return Calendar with dateTraining
	 */
	public String getDateTraining() {
		return dateTraining;
	}

	/**
	 * Set the dateTraining
	 *
	 * @param dateTraining
	 *            String, Calendar
	 */
	public void setDateTraining(String dateTraining) {
		this.dateTraining = dateTraining;
	}

	/**
	 * Get the dateTrinig.
	 *
	 * @return Calendar with timeLimit
	 */
	public String getTimeLimit() {
		return timeLimit;
	}

	/**
	 * Set the dateLimit
	 *
	 * @param dateLimit
	 *            String, Calendar
	 */
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * Get the dateTrinig.
	 *
	 * @return Calendar with dateLimit
	 */
	public String getDateLimit() {
		return dateLimit;
	}

	/**
	 * Set the dateLimit
	 *
	 * @param dateLimit
	 *            String, Calendar
	 */
	public void setDateLimit(String dateLimit) {
		this.dateLimit = dateLimit;
	}

	/**
	 * Get the description..
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
	 * Get The duration.
	 *
	 * @return float duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * Set the duration.
	 *
	 * @param duration
	 *            float, the new duration
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}

	/**
	 * Get The Max Places.
	 *
	 * @return int the max places
	 */
	public int getMaxPlaces() {
		return maxPlaces;
	}

	/**
	 * Set the Max Places.
	 *
	 * @param Place
	 *            int, the max Places
	 */
	public void setMaxPlaces(int maxPlaces) {
		this.maxPlaces = maxPlaces;
	}

	/**
	 * Get The Count Places.
	 *
	 * @return int the count places
	 */
	public int getCountPlaces() {
		return countPlaces;
	}

	/**
	 * Set the Count Places.
	 *
	 * @param Count
	 *            int, the count Places
	 */
	public void setCountPlaces(int countPlaces) {
		this.countPlaces = countPlaces;
	}
	
	/**
	 * Creates the training.
	 *
	 * @return the training
	 */
	public Training createTraining(TrainingType trainingType) {
		
		return new Training(trainingType, getName(),
				DisplayDate.stringToDate(timeTraining + "," + dateTraining),
				DisplayDate.stringToDate(timeLimit + "," + dateLimit),
				getDescription(), getPlace(), getDuration(), getMaxPlaces());
	}

}
