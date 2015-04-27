package org.cuacfm.members.model.trainingType;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TrainingRepositoryImpl.
 */
@Repository
@Transactional(readOnly = true)
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save.
	 *
	 * @param trainingType
	 *            the trainingType
	 * @return trainingType
	 */
	@Override
	@Transactional
	public TrainingType save(TrainingType trainingType)
			throws PersistenceException {
		entityManager.persist(trainingType);
		return trainingType;
	}

	/**
	 * Update.
	 *
	 * @param training
	 *            the trainingType
	 * @return trainingType
	 */
	@Override
	@Transactional
	public TrainingType update(TrainingType trainingType)
			throws PersistenceException {
		return entityManager.merge(trainingType);
	}

	/**
	 * Delete.
	 *
	 * @param trainingType
	 *            the trainingType
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		TrainingType trainingType = findById(id);
		if (trainingType != null) {
			entityManager.remove(trainingType);
		}
	};

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id of trainingType
	 * @return trainingType
	 */
	@Override
	public TrainingType findById(Long id) {
		try {
			return entityManager
					.createQuery(
							"select a from TrainingType a where a.id = :id",
							TrainingType.class).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name of trainingType
	 * @return TrainingType
	 */
	@Override
	public TrainingType findByName(String name) {
		try {
			return entityManager
					.createQuery(
							"select a from TrainingType a where a.name = :name",
							TrainingType.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceException();
		}
	}

	/**
	 * Get all trainingTypes.
	 *
	 * @return List<TrainingType>
	 */
	@Override
	public List<TrainingType> getTrainingTypeList() {
		try {
			return entityManager.createQuery(
					"select a from TrainingType a order by a.name",
					TrainingType.class).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
