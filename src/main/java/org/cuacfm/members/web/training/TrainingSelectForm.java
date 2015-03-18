package org.cuacfm.members.web.training;

import java.util.List;

import org.cuacfm.members.model.trainingType.TrainingType;

/** The TrainingSelectForm. */
public class TrainingSelectForm {

	/** The trainingType List. */
	private List<TrainingType> trainingTypes;

	/** The Global variable trainingTypeId. */
	private Long trainingTypeId;

	/** The getTrainingTypes. */
	public List<TrainingType> getTrainingTypes() {
		return trainingTypes;
	}

	/** The setTrainingTypes. */
	public void setTrainingTypes(List<TrainingType> trainingTypes) {
		this.trainingTypes = trainingTypes;
	}

	/** The getTrainingTypeId. */
	public Long getTrainingTypeId() {
		return trainingTypeId;
	}

	/** The setTrainingTypeId. */
	public void setTrainingTypeId(Long trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
}
