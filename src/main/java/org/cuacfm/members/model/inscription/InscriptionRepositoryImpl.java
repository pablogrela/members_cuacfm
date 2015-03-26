package org.cuacfm.members.model.inscription;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class InscriptionRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class InscriptionRepositoryImpl implements InscriptionRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save Inscription.
	 *
	 * @param inscription
	 *            the inscription
	 * @return the inscription
	 */
	@Override
	@Transactional
	public void save(Inscription inscription) {
		Inscription inscriptionSearched = findByInscriptionIds(inscription
				.getAccount().getId(), inscription.getTraining().getId());

		// Check if exist Inscription
		if (inscriptionSearched == null) {
			entityManager.persist(inscription);
		}
	}

	/**
	 * Update Inscription.
	 * 
	 * @param accountId
	 *            the id of user
	 * @param traningId
	 *            the id of training
	 * @return the inscription
	 */
	@Override
	@Transactional
	public void update(Inscription inscription) {
		Inscription inscriptionSearched = findByInscriptionIds(inscription
				.getAccount().getId(), inscription.getTraining().getId());
		if (inscriptionSearched != null) {
			entityManager.merge(inscription);
		}
	}

	/**
	 * Delete Inscription.
	 *
	 * @param inscription
	 *            the inscription
	 * @return the inscription
	 */
	@Override
	@Transactional
	public void delete(Long accountId, Long trainingId) {
		Inscription inscription = findByInscriptionIds(accountId, trainingId);
		if (inscription != null) {
			entityManager.remove(inscription);
		}
	}

	/**
	 * Find by accountId.
	 *
	 * @param accountId
	 *            the id of user
	 * @return the List<Inscription> pertain to user
	 */
	@Override
	public List<Inscription> getByAccountId(Long accountId) {
		try {
			return entityManager
					.createQuery(
							"select i from Inscription i where i.account.id = :accountId",
							Inscription.class)
					.setParameter("accountId", accountId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find by traningId.
	 *
	 * @param traningId
	 *            the id of training
	 * @return the List<Inscription> pertain to training
	 */
	@Override
	public List<Inscription> getByTrainingId(Long trainingId) {
		try {
			return entityManager
					.createQuery(
							"select i from Inscription i where i.training.id = :trainingId",
							Inscription.class)
					.setParameter("trainingId", trainingId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find Inscription by accountId and trainingId.
	 *
	 * @param accountId
	 *            the id of user
	 * @param traningId
	 *            the id of training
	 * @return inscription
	 */
	@Override
	public Inscription findByInscriptionIds(Long accountId, Long trainingId) {
		try {
			return entityManager
					.createQuery(
							"select i from Inscription i where i.account.id = :accountId and i.training.id = :trainingId",
							Inscription.class)
					.setParameter("accountId", accountId)
					.setParameter("trainingId", trainingId).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Get all Inscriptions by trainingId.
	 *
	 * @return List<Inscription>
	 */
	@Override
	public List<Inscription> getInscriptionListByTrainingId(Long trainingId) {
		try {
			return entityManager
					.createQuery(
							"select i from Inscription i where i.training.id = :trainingId",
							Inscription.class)
					.setParameter("trainingId", trainingId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Get inscriptions by accountId with unsubscribe = true.
	 *
	 * @param accountId
	 *            the id of account
	 * @return List<Inscription> pertain to account
	 */
	@Override
	public List<Inscription> getUnsubscribeByAccountId(Long accountId) {
		try {
			return entityManager
					.createQuery(
							"select i from Inscription i where i.account.id = :accountId and i.unsubscribe = true",
							Inscription.class)
					.setParameter("accountId", accountId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
