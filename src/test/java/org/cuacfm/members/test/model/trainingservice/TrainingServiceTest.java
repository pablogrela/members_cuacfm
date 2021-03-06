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
package org.cuacfm.members.test.model.trainingservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** The Class TrainingServiceTest. */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TrainingServiceTest extends WebSecurityConfigurationAware {

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The training service. */
	@Autowired
	private TrainingService trainingService;

	/** The training Type service. */
	@Autowired
	private TrainingTypeService trainingTypeService;

	/**
	 * Save and find by training test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void saveAndFindsByIdTest() throws ExistInscriptionsException, UniqueListException, DatesException, UniqueException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		// Save
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		Training trainingSearch;
		// findById
		trainingSearch = trainingService.findById(training.getId());
		assertEquals(training, trainingSearch);

		// findById null
		assertNull(trainingService.findById(Long.valueOf(0)));

		// findByName
		trainingService.findByName(training.getName());
		assertEquals(training, trainingSearch);

		// Null
		assertNull(trainingService.findByName("No exist"));
	}

	/**
	 * Save and DateLimitException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test(expected = DatesException.class)
	public void saveDateLimitExceptionTest() throws ExistInscriptionsException, DatesException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Save
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-13-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);
	}

	/**
	 * Save and DateLimitException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws UniqueException
	 */
	@Test
	public void asaveDateLimitExceptionTest() throws ExistInscriptionsException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Save
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-13-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		Date value = new Date();
		try {
			trainingService.save(training);
		} catch (DatesException e) {
			value = e.getDateLimit();
		}
		assertEquals(training.getDateLimit(), value);
	}

	/**
	 * updateTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void updateTrainingTest() throws ExistInscriptionsException, DatesException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingList().get(0).getName(), training.getName());

		// Update Training
		training.setName("new training");
		training.setClose(false);
		training.setCountPlaces(2);
		training.setDescription("new Description");
		training.setMaxPlaces(20);
		training.setPlace("new place");
		training.setDateLimit(DateUtils.format("2015-20-05 10:30", DateUtils.FORMAT_LOCAL_DATE));
		training.setDateTraining(DateUtils.format("2015-20-05 10:30", DateUtils.FORMAT_LOCAL_DATE));
		training.setDuration(120);
		Training trainingUpdate = trainingService.update(training);

		// Assert
		assertEquals(training, trainingUpdate);
		assertEquals(trainingUpdate.getName(), training.getName());
		assertEquals(trainingUpdate.getTrainingType(), training.getTrainingType());
		assertEquals(trainingUpdate.isClose(), training.isClose());
		assertEquals(trainingUpdate.getPlace(), training.getPlace());
		assertEquals(trainingUpdate.getCountPlaces(), training.getCountPlaces());
		assertEquals(trainingUpdate.getDescription(), training.getDescription());
		assertEquals(trainingUpdate.getMaxPlaces(), training.getMaxPlaces());
		assertEquals(trainingUpdate.getDateLimit(), training.getDateLimit());
		assertEquals(trainingUpdate.getDateTraining(), training.getDateTraining());
		assertEquals(trainingUpdate.getDuration(), training.getDuration());

		Training training2 = new Training(trainingType, "training2", DateUtils.format("2015-12-06 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-06 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.update(training2);
	}

	/**
	 * updateTraining DateLimitException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test(expected = DatesException.class)
	public void updateDateLimitExceptionTest() throws ExistInscriptionsException, DatesException, UniqueException, UniqueListException {

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		// Update Training
		training.setDateLimit(DateUtils.format("2015-13-06 10:40", DateUtils.FORMAT_LOCAL_DATE));
		training.setDateTraining(DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE));
		trainingService.update(training);
	}

	/**
	 * inscriptionUpdate test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void inscriptionUpdateTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);

		// Join and Update
		trainingService.createInscription(account, training);
		Inscription inscriptionSearch = trainingService.findByInscriptionIds(account.getId(), training.getId());
		inscriptionSearch.setAttend(true);
		inscriptionSearch.setNote("not attend");
		inscriptionSearch.setPass(true);
		trainingService.updateInscription(inscriptionSearch);
		Inscription inscriptionSearch2 = trainingService.findByInscriptionIds(account.getId(), training.getId());

		// Equals
		assertEquals(inscriptionSearch, inscriptionSearch2);
		assertTrue(inscriptionSearch.equals(inscriptionSearch2));
		assertEquals(inscriptionSearch.getAccount(), inscriptionSearch2.getAccount());
		assertEquals(inscriptionSearch.getTraining(), inscriptionSearch2.getTraining());
		assertEquals(inscriptionSearch.getNote(), inscriptionSearch2.getNote());
		assertEquals(inscriptionSearch.isAttend(), inscriptionSearch2.isAttend());
		assertEquals(inscriptionSearch.isPass(), inscriptionSearch2.isPass());
		assertEquals(inscriptionSearch.isUnsubscribe(), inscriptionSearch2.isUnsubscribe());
	}

	/**
	 * saveUpdateAndDeleteTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void saveUpdateAndDeleteTrainingTest() throws ExistInscriptionsException, DatesException, UniqueException, UniqueListException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);

		trainingService.save(training);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingList().get(0).getName(), training.getName());

		// Update Training
		training.setName("new training");
		Training trainingUpdate = trainingService.update(training);
		assertEquals(trainingUpdate.getName(), training.getName());

		// Delete, no trainings
		trainingService.delete(training);
		assertEquals(trainingService.getTrainingList().size(), 0);

		trainingService.deleteInscription(Long.valueOf(0), Long.valueOf(0));
	}

	/**
	 * saveUpdateAndDeleteTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void updateHasTrainingTest() throws ExistInscriptionsException, DatesException, UniqueException, UniqueListException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		Training training2 = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training2);

		// Assert
		TrainingType trainingTypeSarched = trainingTypeService.findById(training.getTrainingType().getId());
		assertEquals(trainingTypeSarched.isHasTrainings(), true);

		// Delete, 1 training
		trainingService.delete(training);
		assertEquals(trainingService.getTrainingList().size(), 1);

		// Delete, no trainings
		trainingService.delete(training2);
		trainingTypeSarched = trainingTypeService.findById(training.getTrainingType().getId());
		assertEquals(trainingTypeSarched.isHasTrainings(), false);
	}

	/**
	 * existInscriptionsException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test(expected = ExistInscriptionsException.class)
	public void existInscriptionsExceptionTest() throws ExistInscriptionsException, MaximumCapacityException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);

		// Delete
		trainingService.delete(training);
	}

	/**
	 * existInscriptionsException test.
	 * 
	 * @throws MaximumCapacityException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test(expected = DateLimitExpirationException.class)
	public void dateLimitExpirationExceptionTest() throws ExistInscriptionsException, MaximumCapacityException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Save
		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-01-01 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);
	}

	/**
	 * maximumCapacityException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test(expected = MaximumCapacityException.class)
	public void maximumCapacityExceptionTest() throws ExistInscriptionsException, MaximumCapacityException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		Account account2 = new Account("user2", "2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		account2 = accountService.save(account2);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);
		trainingService.createInscription(account2, training);
	}

	/**
	 * unsubscribeInscriptionSuccesfull test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void unsubscribeInscriptionSuccesfullTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException,
			DatesException, DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);
		Inscription inscriptionSearch = trainingService.findByInscriptionIds(account.getId(), training.getId());
		inscriptionSearch.getId();
		assertTrue(!inscriptionSearch.isUnsubscribe());

		// Unsubscribe
		trainingService.unsubscribeInscription(account, training);
		inscriptionSearch = trainingService.findByInscriptionIds(account.getId(), training.getId());
		assertTrue(inscriptionSearch.isUnsubscribe());
	}

	/**
	 * UnsubscribeException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test(expected = UnsubscribeException.class)
	public void UnsubscribeExceptionTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);

		// Unsubscribe
		trainingService.unsubscribeInscription(account, training);

		// Unsubscribe Exception
		trainingService.unsubscribeInscription(account, training);
	}

	/**
	 * getByAccountId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void getByAccountIdTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		Training trainingSaved = trainingService.save(training);

		Training training2 = new Training(trainingType, "training2", dateTraining, dateTraining, "description", "place", 90, 10);
		Training trainingSaved2 = trainingService.save(training2);

		// Joins
		trainingService.createInscription(account, training);
		trainingService.createInscription(account, training2);

		// getByAccountId
		List<Inscription> inscriptionList = trainingService.getInscriptionsByAccountId(account.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 2);
		assertEquals(inscriptionList.get(0).getTraining(), trainingSaved);
		assertEquals(inscriptionList.get(1).getTraining(), trainingSaved2);
	}

	/**
	 * getByTrainingId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void getByTrainingIdTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		Account account2 = new Account("user2", "2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		account2 = accountService.save(account2);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);

		// Joins
		trainingService.createInscription(account, training);
		trainingService.createInscription(account2, training);

		// getByAccountId
		List<Inscription> inscriptionList = trainingService.getInscriptionsByTrainingId(training.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 2);
		assertEquals(inscriptionList.get(0).getAccount(), account);
		assertEquals(inscriptionList.get(1).getAccount(), account2);
	}

	/**
	 * deleteInscription test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void deleteInscriptionTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException, DatesException,
			DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		trainingService.save(training);

		// Joins
		trainingService.createInscription(account, training);

		// getByAccountId
		List<Inscription> inscriptionList = trainingService.getInscriptionsByTrainingId(training.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 1);
		assertEquals(inscriptionList.get(0).getAccount(), account);

		// Delete
		trainingService.deleteInscription(account.getId(), training.getId());

		// getByAccountId
		inscriptionList = trainingService.getInscriptionsByTrainingId(training.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 0);
	}

	/**
	 * getIdsUnsubscribeByAccountId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DatesException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void getIdsUnsubscribeByAccountIdTest() throws ExistInscriptionsException, MaximumCapacityException, UnsubscribeException,
			DatesException, DateLimitExpirationException, UniqueException, UniqueListException, UserAlreadyJoinedException {

		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		// Save
		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account, training);

		// Assert, no ids in unsubscribe
		List<Long> inscriptionList = trainingService.getUnsubscribeIdsByAccountId(account.getId());
		assertEquals(inscriptionList.size(), 0);

		// Unsubscribe
		trainingService.unsubscribeInscription(account, training);

		// Assert, no ids in unsubscribe
		inscriptionList = trainingService.getUnsubscribeIdsByAccountId(account.getId());
		assertEquals(inscriptionList.size(), 1);
		assertEquals(inscriptionList.get(0), training.getId());
	}

	/**
	 * getIdsByAccountId test.
	 * 
	 * @throws DatesException
	 * @throws MaximumCapacityException
	 * @throws DateLimitExpirationException
	 * @throws UniqueException
	 * @throws UserAlreadyJoinedException
	 */
	@Test
	public void getIdsByAccountIdTest() throws DatesException, MaximumCapacityException, DateLimitExpirationException, UniqueException,
			UniqueListException, UserAlreadyJoinedException {
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		LocalDateTime date = LocalDateTime.now().plusMonths(10);
		Date dateTraining = Date.from(date.toInstant(ZoneOffset.UTC));

		Training training = new Training(trainingType, "training1", dateTraining, dateTraining, "description", "place", 90, 10);
		Training trainingSaved = trainingService.save(training);

		Training training2 = new Training(trainingType, "training2", dateTraining, dateTraining, "description", "place", 90, 10);
		Training trainingSaved2 = trainingService.save(training2);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 2);
		assertEquals(trainingService.getTrainingList().get(0).getName(), training.getName());

		trainingTypeService.findById(trainingType.getId());

		trainingService.findById(training.getId());

		// No trainingAccounts
		List<Inscription> trainingAccounts = trainingService.getInscriptionsByAccountId(account.getId());
		// assertEquals(trainingAccounts, null);

		// 1 TrainingAccount
		trainingService.createInscription(account, trainingSaved);
		trainingAccounts = trainingService.getInscriptionsByAccountId(account.getId());
		assertEquals(trainingAccounts.size(), 1);

		// 2 TrainingAccount
		trainingService.createInscription(account, trainingSaved2);
		trainingAccounts = trainingService.getInscriptionsByAccountId(account.getId());
		assertEquals(trainingAccounts.size(), 2);

		// List of id Traings of user
		List<Long> trainingIds = trainingService.getInscriptionsIdsByAccountId(account.getId());
		assertEquals(trainingIds.size(), 2);
		assertTrue(trainingIds.contains(trainingSaved.getId()));
		assertTrue(trainingIds.contains(trainingSaved2.getId()));
	}

	/**
	 * getTrainingsOpenAndCloseTest.
	 * 
	 * @throws DatesException
	 * @throws UniqueException
	 */
	@Test
	public void getTrainingsOpenAndCloseTest() throws DatesException, UniqueException, UniqueListException {
		Account account = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		account = accountService.save(account);

		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		Training trainingClose = new Training(trainingType, "trainingClose", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingClose.setClose(true);
		trainingService.save(trainingClose);

		Training trainingOpen = new Training(trainingType, "trainingOpen", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 10);
		trainingService.save(trainingOpen);

		// it have 2 trainings into database
		int size = trainingService.getTrainingList().size();
		assertEquals(size, 2);

		// it have 1 training into database with close = true
		size = trainingService.getTrainingListOpen().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingListClose(2015).get(0).getName(), trainingClose.getName());

		// it have 1 training into database with close = false
		size = trainingService.getTrainingListOpen().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingListOpen().get(0).getName(), trainingOpen.getName());
	}

	/**
	 * Gets the user pay inscription list by pay inscription test.
	 *
	 * @return the user pay inscription list by pay inscription test
	 * @throws UniqueException
	 * @throws DatesException
	 */
	@Test
	public void getUsernamesByInscription() throws UniqueException, UniqueListException, DatesException {

		// Save
		TrainingType trainingType = new TrainingType("Locution", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true, "Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		Training training = new Training(trainingType, "training1", DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE),
				DateUtils.format("2015-12-05 10:30", DateUtils.FORMAT_LOCAL_DATE), "description", "place", 90, 1);
		trainingService.save(training);

		Account user = new Account("user", "1", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666", "demo", roles.ROLE_USER);
		accountService.save(user);
		Account account = new Account("user2", "2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account);
		Account account3 = new Account("user3", "3", "55555555F", "London", "user3", "user3@udc.es", "666666666", "666666666", "demo",
				roles.ROLE_USER);
		accountService.save(account3);
		account3.setNickName("terminataror");
		accountService.update(account3, false, true);

		// Assert
		List<String> payMembers = trainingService.getUsernamesByInscription(training.getId());
		assertEquals(payMembers.size(), 3);
	}
}
