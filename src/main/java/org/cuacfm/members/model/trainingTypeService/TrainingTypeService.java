package org.cuacfm.members.model.trainingTypeService;

import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingType.TrainingType;

/** The Class TrainingTypeService. */
public interface TrainingTypeService {

	/**
	 * Save an training into database.
	 *
	 * @param training
	 *            the training
	 * @return the training
	 */
	public TrainingType save(TrainingType trainingType);

	/**
	 * Update TraininfType
	 *
	 * @param trainingType
	 *            the trainingType
	 * @return TrainingType
	 */
	public TrainingType update(TrainingType trainingType);

	/**
	 * Delete.
	 *
	 * @param trainingType
	 *            the trainingType
	 * @return TrainingType
	 * @throws ExistTrainingsException 
	 */
	public void delete(Long id) throws ExistTrainingsException;

	/**
	 * Find by Name the trainingType
	 *
	 * @param id
	 *            the id
	 * @return TrainingType
	 */
	public TrainingType findByName(String name);

	/**
	 * Find by id returns trainingType which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return trainingType
	 */
	public TrainingType findById(Long id);

	/**
	 * Get all trainingTypes.
	 *
	 * @return List<TrainingType>
	 */
	public List<TrainingType> getTrainingTypeList();
	
    /**
     * Get all trainings by trainingTypeId.
     *
     * @return List<Training>
     */
    public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId);
}
