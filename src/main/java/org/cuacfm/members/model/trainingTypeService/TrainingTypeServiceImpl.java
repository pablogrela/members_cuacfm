package org.cuacfm.members.model.trainingTypeService;

import java.util.List;

import javax.persistence.PersistenceException;

import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.training.TrainingRepository;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingType.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class TrainingTypeServiceImpl. */
@Service("trainingTypeService")
public class TrainingTypeServiceImpl implements TrainingTypeService {
	
	/** The trainingType repository. */
	@Autowired
	private TrainingTypeRepository trainingTypeRepository;

	/** The training repository. */
	@Autowired
	private TrainingRepository trainingRepository;
	
	/** Instantiates a new trainingType service. */
	public TrainingTypeServiceImpl() {
		// Default empty constructor.
	}

	/**
	 * Save an training into database.
	 *
	 * @param trainingType
	 *            the training
	 * @return TrainingType
	 */
	@Override
	public TrainingType save(TrainingType trainingType) {
		// It is verified that there is not exist name of trainingType in other trainingType
		if (trainingTypeRepository.findByName(trainingType.getName()) != null) {
			throw new PersistenceException("Already exist name: "
					+ trainingType.getName());
		}
		return trainingTypeRepository.save(trainingType);
	}

	/**
	 * Update TraininfType
	 *
	 * @param trainingType
	 *            the trainingType
	 * @return TrainingType
	 */
	@Override
	public TrainingType update(TrainingType trainingType) {
		// It is verified that there is not exist name of trainingType in other trainingType
		TrainingType trainingTypeSearch = trainingTypeRepository.findByName(trainingType.getName());
		if (trainingTypeSearch != null) {
			if (trainingTypeSearch.getId() != trainingType.getId()) {
				throw new PersistenceException("Already exist name: "
						+ trainingType.getName());
			}
		}
		return trainingTypeRepository.update(trainingType);
	}

	/**
	 * Delete.
	 *
	 * @param trainingType
	 *            the trainingType
	 * @return TrainingType
	 * @throws ExistTrainingsException 
	 */
	@Override
	public void delete(Long id) throws ExistTrainingsException{	
		// If Exist Dependencies Trainings
		if (!trainingRepository.getTrainingListByTrainingTypeId(id).isEmpty()){			
			throw new ExistTrainingsException();
		}		
		trainingTypeRepository.delete(id);
	}
	
	/**
	 * Find by Name the trainingType
	 *
	 * @param id
	 *            the id
	 * @return TrainingType
	 */
	@Override
	public TrainingType findByName(String login) {
		return trainingTypeRepository.findByName(login);
	}

	/**
	 * Find by id returns trainingType which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return trainingType
	 */
	@Override
	public TrainingType findById(Long id) {
		return trainingTypeRepository.findById(id);
	}

    /**
     * Get all trainingTypes.
     *
     * @return List<TrainingType>
     */
	@Override
    public List<TrainingType> getTrainingTypeList(){
    	return trainingTypeRepository.getTrainingTypeList();
    }
	
    /**
     * Get all trainings by trainingTypeId.
     *
     * @return List<Training>
     */
    public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId){
    	return trainingRepository.getTrainingListByTrainingTypeId(trainingTypeId);
    }
}