package org.cuacfm.members.web.training;

import java.util.List;

import org.cuacfm.members.model.trainingType.TrainingType;

/** The TrainingSelectForm. */
public class TrainingSelectForm {

	/** The trainingType List. */
	private List<TrainingType> trainingTypes;

	/** The Global variable trainingTypeId. */
	private Long trainingTypeId;

	/**
	 * Gets the training types.
	 *
	 * @return the training types
	 */
	public List<TrainingType> getTrainingTypes() {
		return trainingTypes;
	}

	/**
	 * Sets the training types.
	 *
	 * @param trainingTypes
	 *            the new training types
	 */
	public void setTrainingTypes(List<TrainingType> trainingTypes) {
		this.trainingTypes = trainingTypes;
	}

	/**
	 * Gets the training type id.
	 *
	 * @return the training type id
	 */
	public Long getTrainingTypeId() {
		return trainingTypeId;
	}

	/**
	 * Sets the training type id.
	 *
	 * @param trainingTypeId
	 *            the new training type id
	 */
	public void setTrainingTypeId(Long trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
}
