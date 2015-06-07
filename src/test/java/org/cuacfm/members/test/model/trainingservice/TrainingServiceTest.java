package org.cuacfm.members.test.model.trainingservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.trainingservice.TrainingService;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.cuacfm.members.test.config.WebSecurityConfigurationAware;
import org.cuacfm.members.web.support.DisplayDate;
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
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test
	public void saveAndFindsByIdTest() throws ExistInscriptionsException,
			DateLimitException, UniqueException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
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
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test(expected = DateLimitException.class)
	public void saveDateLimitExceptionTest() throws ExistInscriptionsException,
			DateLimitException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-13-05"),
				"description", "place", 90, 10);
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

      TrainingType trainingType = new TrainingType("Locution", true,
            "Very interesting", "livingRoom", 90);
      trainingTypeService.save(trainingType);

      // Save
      Training training = new Training(trainingType, "training1",
            DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-13-05"),
            "description", "place", 90, 10);
      Date value = new Date();
      try {
         trainingService.save(training);
      } catch (DateLimitException e) {
         value = e.getDateLimit();
      }
      assertEquals(training.getDateLimit(), value);
   }

	/**
	 * updateTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test
	public void updateTrainingTest() throws ExistInscriptionsException,
			DateLimitException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingList().get(0).getName(),
				training.getName());

		// Update Training
		training.setName("new training");
		training.setClose(false);
		training.setCountPlaces(2);
		training.setDescription("new Description");
		training.setMaxPlaces(20);
		training.setPlace("new place");
		training.setDateLimit(DisplayDate.stringToDate("10:30,2015-20-05"));
		training.setDateTraining(DisplayDate.stringToDate("10:30,2015-20-05"));
		training.setDuration(120);
		Training trainingUpdate = trainingService.update(training);

		// Assert
		assertEquals(training, trainingUpdate);
		assertEquals(trainingUpdate.getName(), training.getName());
		assertEquals(trainingUpdate.getTrainingType(),
				training.getTrainingType());
		assertEquals(trainingUpdate.isClose(), training.isClose());
		assertEquals(trainingUpdate.getPlace(), training.getPlace());
		assertEquals(trainingUpdate.getCountPlaces(), training.getCountPlaces());
		assertEquals(trainingUpdate.getDescription(), training.getDescription());
		assertEquals(trainingUpdate.getMaxPlaces(), training.getMaxPlaces());
		assertEquals(trainingUpdate.getDateLimit(), training.getDateLimit());
		assertEquals(trainingUpdate.getDateTraining(),
				training.getDateTraining());
		assertEquals(trainingUpdate.getDuration(), training.getDuration());
		
	    Training training2 = new Training(trainingType, "training2",
	            DisplayDate.stringToDate("10:30,2015-12-06"), DisplayDate.stringToDate("10:30,2015-12-06"),
	            "description", "place", 90, 10);
	      trainingService.update(training2);
	}

	/**
	 * updateTraining DateLimitException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test(expected = DateLimitException.class)
	public void updateDateLimitExceptionTest()
			throws ExistInscriptionsException, DateLimitException, UniqueException {

		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);

		// Update Training
		training.setDateLimit(DisplayDate.stringToDate("10:40,2015-13-06"));
		training.setDateTraining(DisplayDate.stringToDate("10:30,2015-12-05"));
		trainingService.update(training);
	}

	/**
	 * inscriptionUpdate test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void inscriptionUpdateTest() throws ExistInscriptionsException,
			MaximumCapacityException, UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 1);
		trainingService.save(training);

		// Join and Update
		//trainingService.createInscription(Long.valueOf(0), training.getId());
		trainingService.createInscription(account.getId(), training.getId());
		Inscription inscriptionSearch = trainingService.findByInscriptionIds(
				account.getId(), training.getId());
		inscriptionSearch.setAttend(true);
		inscriptionSearch.setNote("not attend");
		inscriptionSearch.setPass(true);
		trainingService.updateInscription(inscriptionSearch);
		Inscription inscriptionSearch2 = trainingService.findByInscriptionIds(
				account.getId(), training.getId());

		// Equals
		assertEquals(inscriptionSearch, inscriptionSearch2);
		assertTrue(inscriptionSearch.equals(inscriptionSearch2));
		assertEquals(inscriptionSearch.getAccount(),
				inscriptionSearch2.getAccount());
		assertEquals(inscriptionSearch.getTraining(),
				inscriptionSearch2.getTraining());
		assertEquals(inscriptionSearch.getNote(), inscriptionSearch2.getNote());
		assertEquals(inscriptionSearch.isAttend(),
				inscriptionSearch2.isAttend());
		assertEquals(inscriptionSearch.isPass(), inscriptionSearch2.isPass());
		assertEquals(inscriptionSearch.isUnsubscribe(),
				inscriptionSearch2.isUnsubscribe());
	}

	/**
	 * saveUpdateAndDeleteTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test
	public void saveUpdateAndDeleteTrainingTest()
			throws ExistInscriptionsException, DateLimitException, UniqueException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);
		

		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);

		trainingService.save(training);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingList().get(0).getName(),
				training.getName());

		// Update Training
		training.setName("new training");
		Training trainingUpdate = trainingService.update(training);
		assertEquals(trainingUpdate.getName(), training.getName());

		// Delete, no trainings
		trainingService.delete(training.getId());
		assertEquals(trainingService.getTrainingList().size(), 0);
		
	   trainingService.deleteInscription(Long.valueOf(0), Long.valueOf(0));
	}


	/**
	 * saveUpdateAndDeleteTraining test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test
	public void updateHasTrainingTest()
			throws ExistInscriptionsException, DateLimitException, UniqueException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);
		
		Training training2 = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training2);

		// Assert
		TrainingType trainingTypeSarched = trainingTypeService.findById(training.getTrainingType().getId());
		assertEquals(trainingTypeSarched.isHasTrainings(), true);
		
		// Delete, 1 training
		trainingService.delete(training.getId());
		assertEquals(trainingService.getTrainingList().size(), 1);	
		
		// Delete, no trainings
		trainingService.delete(training2.getId());
		trainingTypeSarched = trainingTypeService.findById(training.getTrainingType().getId());
		assertEquals(trainingTypeSarched.isHasTrainings(), false);
	}
	
	
	/**
	 * existInscriptionsException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test(expected = ExistInscriptionsException.class)
	public void existInscriptionsExceptionTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());

		// Delete
		trainingService.delete(training.getId());
	}


	/**
	 * existInscriptionsException test.
	 * 
	 * @throws MaximumCapacityException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test(expected = DateLimitExpirationException .class)
	public void dateLimitExpirationExceptionTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-01-01"),
				"description", "place", 90, 10);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());
	}

	/**
	 * maximumCapacityException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test(expected = MaximumCapacityException.class)
	public void maximumCapacityExceptionTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		Account account2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account2 = accountService.save(account2);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());
		trainingService.createInscription(account2.getId(), training.getId());
	}

	/**
	 * unsubscribeInscriptionSuccesfull test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void unsubscribeInscriptionSuccesfullTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());
		Inscription inscriptionSearch = trainingService.findByInscriptionIds(
				account.getId(), training.getId());
		inscriptionSearch.getId();
		assertTrue(!inscriptionSearch.isUnsubscribe());

		// Unsubscribe
		trainingService.unsubscribeInscription(account.getId(),
				training.getId());
		inscriptionSearch = trainingService.findByInscriptionIds(
				account.getId(), training.getId());
		assertTrue(inscriptionSearch.isUnsubscribe());
	}

	/**
	 * UnsubscribeException test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test(expected = UnsubscribeException.class)
	public void UnsubscribeExceptionTest() throws ExistInscriptionsException,
			MaximumCapacityException, UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());

		// Unsubscribe
		trainingService.unsubscribeInscription(account.getId(),
				training.getId());

		// Unsubscribe Exception
		trainingService.unsubscribeInscription(account.getId(),
				training.getId());
	}

	/**
	 * getByAccountId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void getByAccountIdTest() throws ExistInscriptionsException,
			MaximumCapacityException, UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		Training trainingSaved = trainingService.save(training);

		Training training2 = new Training(trainingType, "training2",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		Training trainingSaved2 = trainingService.save(training2);

		// Joins
		trainingService.createInscription(account.getId(), training.getId());
		trainingService.createInscription(account.getId(), training2.getId());

		// getByAccountId
		List<Inscription> inscriptionList = trainingService
				.getInscriptionsByAccountId(account.getId());

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
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void getByTrainingIdTest() throws ExistInscriptionsException,
			MaximumCapacityException, UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		Account account2 = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account2 = accountService.save(account2);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);

		// Joins
		trainingService.createInscription(account.getId(), training.getId());
		trainingService.createInscription(account2.getId(), training.getId());

		// getByAccountId
		List<Inscription> inscriptionList = trainingService
				.getInscriptionsByTrainingId(training.getId());

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
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void deleteInscriptionTest() throws ExistInscriptionsException,
			MaximumCapacityException, UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(training);

		// Joins
		trainingService.createInscription(account.getId(), training.getId());

		// getByAccountId
		List<Inscription> inscriptionList = trainingService
				.getInscriptionsByTrainingId(training.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 1);
		assertEquals(inscriptionList.get(0).getAccount(), account);

		// Delete
		trainingService.deleteInscription(account.getId(), training.getId());

		// getByAccountId
		inscriptionList = trainingService.getInscriptionsByTrainingId(training
				.getId());

		// Asserts
		assertEquals(inscriptionList.size(), 0);
	}

	/**
	 * getIdsUnsubscribeByAccountId test.
	 * 
	 * @throws ExistInscriptionsException
	 * @throws MaximumCapacityException
	 * @throws UnsubscribeException
	 * @throws DateLimitException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void getIdsUnsubscribeByAccountIdTest()
			throws ExistInscriptionsException, MaximumCapacityException,
			UnsubscribeException, DateLimitException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {

		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);
		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		// Save
		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 1);
		trainingService.save(training);

		// Join
		trainingService.createInscription(account.getId(), training.getId());

		// Assert, no ids in unsubscribe
		List<Long> inscriptionList = trainingService
				.getUnsubscribeIdsByAccountId(account.getId());
		assertEquals(inscriptionList.size(), 0);

		// Unsubscribe
		trainingService.unsubscribeInscription(account.getId(),
				training.getId());

		// Assert, no ids in unsubscribe
		inscriptionList = trainingService.getUnsubscribeIdsByAccountId(account
				.getId());
		assertEquals(inscriptionList.size(), 1);
		assertEquals(inscriptionList.get(0), training.getId());
	}

	/**
	 * getIdsByAccountId test.
	 * 
	 * @throws DateLimitException
	 * @throws MaximumCapacityException
	 * @throws DateLimitExpirationException 
	 * @throws UniqueException 
	 * @throws UserAlreadyJoinedException 
	 */
	@Test
	public void getIdsByAccountIdTest() throws DateLimitException,
			MaximumCapacityException, DateLimitExpirationException, UniqueException, UserAlreadyJoinedException {
		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);

		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		

		Training training = new Training(trainingType, "training1",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		Training trainingSaved = trainingService.save(training);

		Training training2 = new Training(trainingType, "training2",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		Training trainingSaved2 = trainingService.save(training2);

		int size = trainingService.getTrainingList().size();
		assertEquals(size, 2);
		assertEquals(trainingService.getTrainingList().get(0).getName(),
				training.getName());

		trainingTypeService.findById(trainingType.getId());

		trainingService.findById(training.getId());

		// No trainingAccounts
		List<Inscription> trainingAccounts = trainingService
				.getInscriptionsByAccountId(account.getId());
		// assertEquals(trainingAccounts, null);

		// 1 TrainingAccount
		trainingService.createInscription(account.getId(),
				trainingSaved.getId());
		trainingAccounts = trainingService.getInscriptionsByAccountId(account
				.getId());
		assertEquals(trainingAccounts.size(), 1);

		// 2 TrainingAccount
		trainingService.createInscription(account.getId(),
				trainingSaved2.getId());
		trainingAccounts = trainingService.getInscriptionsByAccountId(account
				.getId());
		assertEquals(trainingAccounts.size(), 2);

		// List of id Traings of user
		List<Long> trainingIds = trainingService
				.getInscriptionsIdsByAccountId(account.getId());
		assertEquals(trainingIds.size(), 2);
		assertTrue(trainingIds.contains(trainingSaved.getId()));
		assertTrue(trainingIds.contains(trainingSaved2.getId()));
	}

	/**
	 * getTrainingsOpenAndCloseTest.
	 * 
	 * @throws DateLimitException
	 * @throws UniqueException 
	 */
	@Test
	public void getTrainingsOpenAndCloseTest() throws DateLimitException, UniqueException {
		Account account = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
		account = accountService.save(account);

		TrainingType trainingType = new TrainingType("Locution", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType);
		TrainingType trainingType2 = new TrainingType("Filming", true,
				"Very interesting", "livingRoom", 90);
		trainingTypeService.save(trainingType2);

		Training trainingClose = new Training(trainingType, "trainingClose",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingClose.setClose(true);
		trainingService.save(trainingClose);

		Training trainingOpen = new Training(trainingType, "trainingOpen",
				DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
				"description", "place", 90, 10);
		trainingService.save(trainingOpen);

		// it have 2 trainings into database
		int size = trainingService.getTrainingList().size();
		assertEquals(size, 2);

		// it have 1 training into database with close = true
		size = trainingService.getTrainingListOpen().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingListClose(2015).get(0).getName(),
				trainingClose.getName());

		// it have 1 training into database with close = false
		size = trainingService.getTrainingListOpen().size();
		assertEquals(size, 1);
		assertEquals(trainingService.getTrainingListOpen().get(0).getName(),
				trainingOpen.getName());
	}
	
	
   /**
   * Gets the user pay inscription list by pay inscription test.
   *
   * @return the user pay inscription list by pay inscription test
   * @throws UniqueException 
    * @throws DateLimitException 
   */
  @Test
  public void getUsernamesByInscription() throws UniqueException, DateLimitException {

     // Save
     TrainingType trainingType = new TrainingType("Locution", true,
           "Very interesting", "livingRoom", 90);
     trainingTypeService.save(trainingType);
     TrainingType trainingType2 = new TrainingType("Filming", true,
           "Very interesting", "livingRoom", 90);
     trainingTypeService.save(trainingType2);
     
     Training training = new Training(trainingType, "training1",
           DisplayDate.stringToDate("10:30,2015-12-05"), DisplayDate.stringToDate("10:30,2015-12-05"),
           "description", "place", 90, 1);
     trainingService.save(training);
     
     Account user = new Account("user", "55555555C", "London", "user", "user@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
     accountService.save(user);
     Account account = new Account("user2", "55555555B", "London", "user2", "user2@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
     accountService.save(account);
     Account account3 = new Account("user3", "55555555F", "London", "user3", "user3@udc.es", "666666666", "666666666","demo", roles.ROLE_USER);
     accountService.save(account3);
     account3.setNickName("terminataror");
     accountService.update(account3, false);

     // Assert
     List<String> payMembers = trainingService.getUsernamesByInscription(training.getId());
     assertEquals(payMembers.size(), 3);
  }
}
