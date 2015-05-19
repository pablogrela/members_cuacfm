package org.cuacfm.members.model.trainingservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.AccountRepository;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.inscription.InscriptionRepository;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.training.TrainingRepository;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class TrainingService. */
@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

   /** The account repository. */
   @Autowired
   private AccountRepository accountRepository;

   /** The training repository. */
   @Autowired
   private TrainingRepository trainingRepository;

   /** The training Type Service. */
   @Autowired
   private TrainingTypeService trainingTypeService;

   /** The inscription repository. */
   @Autowired
   private InscriptionRepository inscriptionRepository;

   /** Instantiates a new training service. */
   public TrainingServiceImpl() {
      // Default empty constructor.
   }

   /**
    * Save, saves an training into database.
    *
    * @param training
    *           the training
    * @return the training
    * @throws DateLimitException
    * @throws UniqueException
    */
   @Override
   public Training save(Training training) throws DateLimitException, UniqueException {
      if (training.getDateTraining().before(training.getDateLimit())) {
         throw new DateLimitException(training.getDateLimit(), training.getDateTraining());
      }
      // Update dependecy
      TrainingType trainingType = training.getTrainingType();
      trainingType.setHasTrainings(true);
      trainingTypeService.update(trainingType);

      return trainingRepository.save(training);
   }

   /**
    * Update.
    *
    * @param training
    *           the training
    * @param passwordUpdate
    *           the passwordUpdate
    * @return the training
    */
   @Override
   public Training update(Training training) throws DateLimitException {
      if (training.getDateTraining().before(training.getDateLimit())) {
         throw new DateLimitException(training.getDateLimit(), training.getDateTraining());
      }
      return trainingRepository.update(training);
   }

   /**
    * Delete.
    *
    * @param training
    *           the training
    * @return the training
    * @throws ExistInscriptionsException
    * @throws UniqueException
    */
   @Override
   public void delete(Long id) throws ExistInscriptionsException, UniqueException {
      // If Exist Dependencies Inscriptions
      if (!inscriptionRepository.getInscriptionListByTrainingId(id).isEmpty()) {
         throw new ExistInscriptionsException();
      }

      Training training = trainingRepository.findById(id);
      TrainingType trainingType = training.getTrainingType();

      // Delete training
      trainingRepository.delete(id);

      // Update dependencies
      List<Training> trainings = trainingRepository.getTrainingListByTrainingTypeId(trainingType
            .getId());
      if (trainings.isEmpty()) {
         trainingType.setHasTrainings(false);
         trainingTypeService.update(trainingType);
      }
   }

   /**
    * Find by id returns user which has this identifier.
    *
    * @param id
    *           the id
    * @return the training
    */
   @Override
   public Training findByName(String login) {
      return trainingRepository.findByName(login);
   }

   /**
    * Find by id returns user which has this identifier.
    *
    * @param id
    *           the id
    * @return the training
    */
   @Override
   public Training findById(Long id) {
      return trainingRepository.findById(id);
   }

   /**
    * Get all trainings.
    *
    * @return List<Training>
    */
   @Override
   public List<Training> getTrainingList() {
      return trainingRepository.getTrainingList();
   }

   /**
    * Get all trainings with close = false.
    *
    * @return List<Training>
    */
   @Override
   public List<Training> getTrainingListOpen() {
      return trainingRepository.getTrainingListOpen();
   }

   /**
    * Gets the training list close with close = true.
    *
    * @param year
    *           the year
    * @return the training list close
    */
   @Override
   public List<Training> getTrainingListClose(int year) {
      return trainingRepository.getTrainingListClose(year);
   }

   /**
    * Create the new inscription
    *
    * @param accountId
    *           the id of user
    * @param trainingId
    *           the id of training
    * @throws MaximumCapacityException
    * @throws DateLimitExpirationException
    * @throws UserAlreadyJoinedException
    */
   @Override
   public void createInscription(Long accountId, Long trainingId) throws MaximumCapacityException,
         DateLimitExpirationException, UserAlreadyJoinedException {

      Training training = trainingRepository.findById(trainingId);

      // Checks if a limit past the deadline
      Date nowDate = new Date();
      if (training.getDateLimit().before(nowDate)) {
         throw new DateLimitExpirationException(training.getName(), nowDate);
      }

      // Check if available space in this training
      int countPlaces = training.getCountPlaces();
      int maxPlaces = training.getMaxPlaces();
      if (countPlaces >= maxPlaces) {
         throw new MaximumCapacityException(maxPlaces);
      }

      Account account = accountRepository.findById(accountId);
      // Check if account exist

      if (account == null) {
         return;
         // throw new UsernameNotFoundException(accountId);
      }

      // Check if account already inscription
      Inscription inscriptionSearched = inscriptionRepository.findByInscriptionIds(accountId,
            trainingId);
      if (inscriptionSearched != null) {
         throw new UserAlreadyJoinedException(account.getLogin());
      }

      training.setCountPlaces(training.getCountPlaces() + 1);
      trainingRepository.update(training);

      Inscription inscription = new Inscription(account, training);
      inscriptionRepository.save(inscription);
   }

   /**
    * Delete Inscription.
    *
    * @param inscription
    *           the inscription
    * @return the inscription
    */
   @Override
   public void deleteInscription(Long accountId, Long trainingId) {
      inscriptionRepository.delete(accountId, trainingId);
   }

   /**
    * Unsubscribe Inscription.
    *
    * @param inscription
    *           the inscription
    * @return the inscription
    * @throws UnsubscribeException
    */
   @Override
   public void unsubscribeInscription(Long accountId, Long trainingId) throws UnsubscribeException {

      Inscription inscription = inscriptionRepository.findByInscriptionIds(accountId, trainingId);

      if (inscription.isUnsubscribe()) {
         throw new UnsubscribeException(inscription.getTraining().getName());
      }
      Training trainingUpdate = trainingRepository.findById(trainingId);
      trainingUpdate.setCountPlaces(trainingUpdate.getCountPlaces() - 1);
      trainingRepository.update(trainingUpdate);
      inscription.setUnsubscribe(true);
      inscriptionRepository.update(inscription);
   }

   /**
    * Update Inscription.
    * 
    * @param accountId
    *           the id of user
    * @param trainingId
    *           the id of training
    * @return the inscription
    */
   @Override
   public void updateInscription(Inscription inscription) {
      inscriptionRepository.update(inscription);
   }

   /**
    * Find Inscription by accountId and trainingId.
    *
    * @param accountId
    *           the id of user
    * @param trainingId
    *           the id of training
    * @return inscription
    */
   @Override
   public Inscription findByInscriptionIds(Long accountId, Long trainingId) {
      return inscriptionRepository.findByInscriptionIds(accountId, trainingId);
   }

   /**
    * Get all Inscriptions by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Inscription> pertain to user
    */
   @Override
   public List<Inscription> getInscriptionsByAccountId(Long accountId) {
      return inscriptionRepository.getByAccountId(accountId);
   }

   /**
    * Get all Inscriptions by trainingId.
    *
    * @param trainingId
    *           the id of training
    * @return the List<Inscription> pertain to traing
    */
   @Override
   public List<Inscription> getInscriptionsByTrainingId(Long trainingId) {
      return inscriptionRepository.getByTrainingId(trainingId);
   }

   /**
    * Get inscriptions id´s by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Long> pertain to traing
    */
   @Override
   public List<Long> getInscriptionsIdsByAccountId(Long accountId) {
      return inscriptionRepository.getIdsByAccountId(accountId);
   }

   /**
    * Get inscriptions unsubscribe id´s by accountId.
    *
    * @param accountId
    *           the id of user
    * @return the List<Long> pertain to traing
    */
   @Override
   public List<Long> getUnsubscribeIdsByAccountId(Long accountId) {
      return inscriptionRepository.getUnsubscribeIdsByAccountId(accountId);
   }

   /**
    * Gets the name users by inscription with role=ROLE_USER an active=true.
    *
    * @param trainingId
    *           the training id
    * @return the name users by inscription
    */
   @Override
   public List<String> getUsernamesByInscription(Long trainingId) {
      return inscriptionRepository.getUsernamesByInscription(trainingId);
   }
}
