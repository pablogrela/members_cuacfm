package org.cuacfm.members.test.model.trainingTypeService;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingService.TrainingService;
import org.cuacfm.members.model.trainingType.TrainingType;
import org.cuacfm.members.model.trainingTypeService.TrainingTypeService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class rainingTypeServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingTypeServiceTest extends WebSecurityConfigurationAware {

	/** The training service. */
	@Inject
	private TrainingService trainingService;

	/** The training Type service. */
	@Inject
	private TrainingTypeService trainingTypeService;

	public static Date stringToDate(String dateTraining) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm,yyyy-MM-dd");

		if (!dateTraining.equals(",")) {
			try {
				newDate = dateFormat.parse(dateTraining);
			} catch (ParseException ex) {
				ex.printStackTrace();

			}
		}
		return newDate;
	}

	/**
	 * Save and find by TrainingType test.
	 */
	@Test
	public void saveTrainingTypeTest() {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);

		// findById
		TrainingType trainingTypeSearch;

		trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);
	}

	/**
	 * Save and find by TrainingType test with PersistenceException.
	 */
	@Test(expected = PersistenceException.class)
	public void saveTrainingTypePersistenceExceptionTest() {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);

		trainingTypeService.save(trainingType);
	}

	/**
	 * findByName test.
	 */
	@Test
	public void findByNameTest() {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);

		// findById
		TrainingType trainingTypeSearch;

		trainingTypeSearch = trainingTypeService.findByName(trainingType
				.getName());
		assertEquals(trainingType, trainingTypeSearch);
	}

	/**
	 * deleteTrainingType test.
	 * 
	 * @throws ExistTrainingsException
	 */
	@Test
	public void deleteTrainingTypeTest() throws ExistTrainingsException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		TrainingType trainingTypeSearch;

		// findById
		trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);

		// Delete
		trainingTypeService.delete(trainingType.getId());

		// findById
		trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(null, trainingTypeSearch);
	}

	/**
	 * deleteTrainingType Null test.
	 * 
	 * @throws ExistTrainingsException
	 */
	@Test
	public void deleteTrainingTypeNullTest() throws ExistTrainingsException {
		// Delete
		trainingTypeService.delete(Long.valueOf(1));
	}

	/**
	 * ExistTrainingsException test.
	 * 
	 * @throws ExistTrainingsException
	 * @throws DateLimitException
	 */
	@Test(expected = ExistTrainingsException.class)
	public void existTrainingsExceptionTest() throws ExistTrainingsException,
			DateLimitException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";

		// Create training
		Training training = new Training(trainingType, "training1",
				stringToDate(dateTraining), stringToDate(dateTraining),
				"description", "place", Float.valueOf((float) 2.3), 10);
		trainingService.save(training);

		// Delete, ExistTrainingsException
		trainingTypeService.delete(trainingType.getId());
	}

	/**
	 * Update Training test with PersistenceException.
	 */
	@Test(expected = PersistenceException.class)
	public void UpdateTrainingPersistenceExceptionTest() {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Locution2", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType2);

		// Update
		trainingType.setName("Locution2");
		trainingTypeService.update(trainingType);
	}

	/**
	 * Update Training test.
	 */
	@Test
	public void UpdateTrainingTest() {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);

		// Update
		trainingType.setName("Locution new");
		trainingType.setRequired(false);
		trainingType.setDescription("description");
		trainingType.setPlace("Square Garden");
		trainingType.setDuration(Float.valueOf(2));
		trainingTypeService.update(trainingType);

		// Assert
		TrainingType trainingTypeSearch = trainingTypeService
				.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);
		assertEquals(trainingType.getName(), trainingTypeSearch.getName());
		assertEquals(trainingType.isRequired(), trainingTypeSearch.isRequired());
		assertEquals(trainingType.getDescription(),
				trainingTypeSearch.getDescription());
		assertEquals(trainingType.getPlace(), trainingTypeSearch.getPlace());
		assertEquals(trainingType.getDuration(),
				trainingTypeSearch.getDuration());
	}

	/**
	 * getTrainingTypeList test.
	 */
	@Test
	public void getTrainingTypeListTest() {

		// getTrainingTypeList, no TrainingTypes
		List<TrainingType> trainingTypeList = trainingTypeService
				.getTrainingTypeList();
		// Assert
		assertEquals(trainingTypeList, null);

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 3.3));
		trainingTypeService.save(trainingType2);

		// getTrainingTypeList
		trainingTypeList = trainingTypeService.getTrainingTypeList();
		// Assert
		assertEquals(trainingTypeList.size(), 2);
	}

	/**
	 * getTrainingListByTrainingTypeId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 */
	@Test
	public void getTrainingListByTrainingTypeIdTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			UnsubscribeException, DateLimitException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", Float.valueOf((float) 2.3));
		trainingTypeService.save(trainingType);
		String dateTraining = "10:30,2015-12-05";

		// getTrainingTypeList
		List<Training> trainingList = trainingTypeService
				.getTrainingListByTrainingTypeId(trainingType.getId());
		// Assert
		assertEquals(trainingList.size(), 0);

		// Create training
		Training training = new Training(trainingType, "training1",
				stringToDate(dateTraining), stringToDate(dateTraining),
				"description", "place", Float.valueOf((float) 2.3), 10);
		trainingService.save(training);

		// getTrainingTypeList
		trainingList = trainingTypeService
				.getTrainingListByTrainingTypeId(trainingType.getId());
		// Assert
		assertEquals(trainingList.size(), 1);
	}
}
