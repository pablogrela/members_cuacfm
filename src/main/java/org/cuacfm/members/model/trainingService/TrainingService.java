package org.cuacfm.members.model.trainingService;

import java.util.List;

import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.training.Training;

/** The Class TrainingService. */

public interface TrainingService {

	/**
	 * Save, saves an training into database.
	 *
	 * @param training
	 *            the training
	 * @return the training
	 */
	public Training save(Training training) throws DateLimitException;

	/**
	 * Update, updates an user registered into bd depending if he wants to
	 * update his password or not.
	 *
	 * @param training
	 *            the training
	 * @param passwordUpdate
	 *            the passwordUpdate
	 * @return the training
	 */
	public Training update(Training training) throws DateLimitException;

	/**
	 * Delete.
	 *
	 * @param training
	 *            the training
	 * @return the training
	 */
	public void delete(Long id) throws ExistInscriptionsException;

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the training
	 */
	public Training findByName(String name);

	/**
	 * Find by id returns user which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return the training
	 */
	public Training findById(Long id);

	/**
	 * Get all trainings.
	 *
	 * @return List<Training>
	 */
	public List<Training> getTrainingList();

	/**
	 * Get all trainings with close = false.
	 *
	 * @return List<Training>
	 */
	public List<Training> getTrainingListOpen();
	
	/**
	 * Get all trainings with close = true.
	 *
	 * @return List<Training>
	 */
	public List<Training> getTrainingListClose();
	
	/**
	 * Create the new inscription
	 *
	 * @param accountId
	 *            the id of user
	 * @param trainingId
	 *            the id of training
	 */
	public void createInscription(Long accountId, Long trainingId)
			throws MaximumCapacityException, DateLimitExpirationException;

	/**
	 * Update.
	 * 
	 * @param accountId
	 *            the id of user
	 * @param trainingId
	 *            the id of training
	 * @return the inscription
	 */
	public void updateInscription(Inscription inscription);

	/**
	 * Unsubscribe Inscription.
	 *
	 * @param inscription
	 *            the inscription
	 * @return the inscription
	 * @throws UnsubscribeException
	 */
	public void unsubscribeInscription(Long accountId, Long trainingId)
			throws UnsubscribeException;

	/**
	 * Delete.
	 *
	 * @param inscription
	 *            the inscription
	 * @return the inscription
	 */
	public void deleteInscription(Long accountId, Long trainingId);

	
	/**
	 * Find Inscription by accountId and trainingId.
	 *
	 * @param accountId
	 *            the id of user
	 * @param trainingId
	 *            the id of training
	 * @return inscription
	 */
	public Inscription findByInscriptionIds(Long accountId,
			Long trainingId);
	
	
	/**
	 * Get all Inscriptions by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Inscription> getInscriptionsByAccountId(Long accountId);

	/**
	 * Get all Inscriptions by trainingId.
	 *
	 * @param trainingId
	 *            the id of training
	 * @return the List<Inscription> pertain to training
	 */
	public List<Inscription> getInscriptionsByTrainingId(Long trainingId);


	/**
	 *  Get inscriptions id´s by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Long> pertain to training
	 */
	public List<Long> getInscriptionsIdsByAccountId(Long accountId);
	
	/**
	 * Get inscriptions unsubscribe id´s by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Long> pertain to traing
	 */
	public List<Long> getUnsubscribeIdsByAccountId(Long accountId);
}