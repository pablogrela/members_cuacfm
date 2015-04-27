package org.cuacfm.members.model.inscription;

import java.util.List;

public interface InscriptionRepository {
	/**
	 * Save.
	 *
	 * @param training
	 *            the training
	 * @return the training
	 */
	public void save(Inscription inscription);

	/**
	 * Update.
	 * 
	 * @param accountId
	 *            the id of user
	 * @param traningId
	 *            the id of training
	 * @return the inscription
	 */
	public void update(Inscription inscription);

	/**
	 * Delete.
	 *
	 * @param inscription
	 *            the inscription
	 * @return the inscription
	 */

	public void delete(Long accountId, Long trainingId);

	/**
	 * Find by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Inscription> getByAccountId(Long accountId);

	/**
	 * Find by traningId.
	 *
	 * @param traningId
	 *            the id of training
	 * @return the List<Inscription> pertain to traing
	 */
	public List<Inscription> getByTrainingId(Long trainingId);

	/**
	 * Find by accountId, inscriptions unsubscribe.
	 *
	 * @param accountId
	 *            the id of account
	 * @return the List<Inscription> pertain to traing
	 */
	public List<Inscription> getUnsubscribeByAccountId(Long accountId);
	
	/**
	 * Find by accountId and trainingId.
	 *
	 * @param accountId
	 *            the id of user
	 * @param traningId
	 *            the id of training
	 * @return the inscription
	 */
	public Inscription findByInscriptionIds(Long accountId,
			Long trainingId);
	
    /**
     * Get all Inscriptions by trainingId.
     *
     * @return List<Inscription>
     */
	public List<Inscription> getInscriptionListByTrainingId(Long trainingId);
	
	/**
	 * Find by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Inscription> pertain to user
	 */
	public List<Long> getIdsByAccountId(Long accountId);
	
	/**
	 * Get inscriptionsId by accountId with unsubscribe = true.
	 *
	 * @param accountId
	 *            the id of account
	 * @return List<Long> pertain to account
	 */
	public List<Long> getUnsubscribeIdsByAccountId(Long accountId);
	
	/**
	 * Gets the name users by inscription with role=ROLE_USER an active=true.
	 *
	 * @param trainingId the training id
	 * @return the name users by inscription
	 */
	public List<String> getUsernamesByInscription(Long trainingId);
}
