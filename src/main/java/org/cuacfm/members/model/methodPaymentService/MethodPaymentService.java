package org.cuacfm.members.model.methodPaymentService;

import java.util.List;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.springframework.transaction.annotation.Transactional;

/** The Interface MethodPaymentService.*/
public interface MethodPaymentService {

	/**
	 * Save.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 * @throws UniqueException
	 */
	public MethodPayment save(MethodPayment methodPayment)
			throws UniqueException;

	/**
	 * Update.
	 *
	 * @param methodPayment
	 *            the method payment
	 * @return the method payment
	 * @throws UniqueException
	 */
	@Transactional
	public MethodPayment update(MethodPayment methodPayment)
			throws UniqueException;

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
