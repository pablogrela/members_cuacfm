package org.cuacfm.members.model.methodPayment;

import java.util.List;

/** The Interface MethodPaymentRepository. */
public interface MethodPaymentRepository {

	/**
	 * Save.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 */
	public MethodPayment save(MethodPayment methodPayment);

	/**
	 * Update.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 */
	public MethodPayment update(MethodPayment methodPayment);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 */
	public void delete(Long id);

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the method payment
	 */
	public MethodPayment findById(Long id);

	/**
	 * Find by name.
	 *
	 * @param name
	 *            the name of MethodPayment
	 * @return MethodPayment
	 */
	public MethodPayment findByName(String name);

	/**
	 * Gets the method payments.
	 *
	 * @return the method payments
	 */
	public List<MethodPayment> getMethodPayments();

}
