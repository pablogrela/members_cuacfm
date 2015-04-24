package org.cuacfm.members.model.methodPayment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** The Class MethodPaymentRepositoryImpl. */
@Repository
@Transactional(readOnly = true)
public class MethodPaymentRepositoryImpl implements MethodPaymentRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Save.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 */
	@Override
	@Transactional
	public MethodPayment save(MethodPayment methodPayment)
			throws PersistenceException {
		entityManager.persist(methodPayment);
		return methodPayment;
	}

	/**
	 * Update.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 */
	@Override
	@Transactional
	public MethodPayment update(MethodPayment methodPayment)
			throws PersistenceException {
		return entityManager.merge(methodPayment);
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		MethodPayment methodPayment = findById(id);
		if (methodPayment != null) {
			entityManager.remove(methodPayment);
		}
	};
	
	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the method payment
	 */
	@Override
	public MethodPayment findById(Long id) {
		try {
			return entityManager
					.createQuery(
							"select a from MethodPayment a where a.id = :id",
							MethodPayment.class).setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name of MethodPayment
	 * @return MethodPayment
	 */
	@Override
	public MethodPayment findByName(String name) {
		try {
			return entityManager
					.createQuery(
							"select a from MethodPayment a where a.name = :name",
							MethodPayment.class).setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException e) {
			throw new PersistenceException();
		}
	}
	
	/**
	 * Gets the method payments.
	 *
	 * @return the method payments
	 */
	@Override
	public List<MethodPayment> getMethodPayments() {
		try {
			return entityManager.createQuery("select a from MethodPayment a",
					MethodPayment.class).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
