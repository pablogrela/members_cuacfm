/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.test.model.trainingtypeservice;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.model.util.DateUtils;
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

	/**
	 * Save and find by TrainingType test.
	 * 
	 * @throws UniqueException
	 */
	@Test
	public void saveTrainingTypeTest() throws UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// findById
		TrainingType trainingTypeSearch;

		trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);
	}

	/**
	 * Save and find by TrainingType test with UniqueException.
	 * 
	 * @throws UniqueException
	 */
	@Test(expected = UniqueException.class)
	public void saveTrainingTypeUniqueExceptionTest() throws UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		trainingTypeService.save(trainingType);
	}

	/**
	 * findByName test.
	 * 
	 * @throws UniqueException
	 */
	@Test
	public void findByNameTest() throws UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// findById
		TrainingType trainingTypeSearch;

		trainingTypeSearch = trainingTypeService.findByName(trainingType.getName());
		assertEquals(trainingType, trainingTypeSearch);
	}

	/**
	 * deleteTrainingType test.
	 * 
	 * @throws ExistTrainingsException
	 * @throws UniqueException
	 */
	@Test
	public void deleteTrainingTypeTest() throws ExistTrainingsException, UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingTypeSearch;

		// findById
		trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);

		// Delete
		trainingTypeService.delete(trainingType);

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
		trainingTypeService.delete(new TrainingType());
	}

	/**
	 * ExistTrainingsException test.
	 * 
	 * @throws ExistTrainingsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test(expected = ExistTrainingsException.class)
	public void existTrainingsExceptionTest() throws ExistTrainingsException, DatesException, UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Create training
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		// Delete, ExistTrainingsException
		trainingTypeService.delete(trainingType);
	}

	/**
	 * Update Training test with UniqueException.
	 * 
	 * @throws UniqueException
	 */
	@Test(expected = UniqueException.class)
	public void UpdateTrainingUniqueExceptionTest() throws UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Locution2", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		// Update
		TrainingType trainingType3 = new TrainingType("Locution2", true, "Very interesting", "livingRoom", 90);
		trainingType3.setId(trainingType.getId());
		trainingTypeService.update(trainingType3);
	}

	/**
	 * Update Training test.
	 * 
	 * @throws UniqueException
	 */
	@Test
	public void UpdateTrainingTest() throws UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Update
		trainingType.setName("Locution new");
		trainingType.setRequired(false);
		trainingType.setDescription("description");
		trainingType.setPlace("Square Garden");
		trainingType.setDuration(90);
		trainingTypeService.update(trainingType);

		// Assert
		TrainingType trainingTypeSearch = trainingTypeService.findById(trainingType.getId());
		assertEquals(trainingType, trainingTypeSearch);
		assertEquals(trainingType.getName(), trainingTypeSearch.getName());
		assertEquals(trainingType.isRequired(), trainingTypeSearch.isRequired());
		assertEquals(trainingType.getDescription(), trainingTypeSearch.getDescription());
		assertEquals(trainingType.getPlace(), trainingTypeSearch.getPlace());
		assertEquals(trainingType.getDuration(), trainingTypeSearch.getDuration());

		TrainingType trainingType2 = new TrainingType("Locution2", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.update(trainingType2);
	}

	/**
	 * getTrainingTypeList test.
	 * 
	 * @throws UniqueException
	 */
	@Test
	public void getTrainingTypeListTest() throws UniqueException {

		// getTrainingTypeList, no TrainingTypes
		List<TrainingType> trainingTypeList = trainingTypeService.getTrainingTypeList();
		// Assert
		assertEquals(trainingTypeList.size(), 0);

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true, "Very interesting", "livingRoom", 90);
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
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void getTrainingListByTrainingTypeIdTest()
			throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException, UniqueException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// getTrainingTypeList
		List<Training> trainingList = trainingTypeService.getTrainingListByTrainingTypeId(trainingType.getId());
		// Assert
		assertEquals(trainingList.size(), 0);

		// Create training
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		// getTrainingTypeList
		trainingList = trainingTypeService.getTrainingListByTrainingTypeId(trainingType.getId());
		// Assert
		assertEquals(trainingList.size(), 1);
	}
}
