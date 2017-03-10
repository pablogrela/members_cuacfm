/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.methodpaymentservice;

import java.util.List;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodpayment.MethodPayment;
import org.springframework.transaction.annotation.Transactional;

/** The Interface MethodPaymentService. */
public interface MethodPaymentService {

	/**
	 * Save.
	 *
	 * @param methodPayment the method payment
	 * @return the method payment
	 * @throws UniqueException the unique exception
	 */
	public MethodPayment save(MethodPayment methodPayment) throws UniqueException;

	/**
	 * Update.
	 *
	 * @param methodPayment the method payment
	 * @return the method payment
	 * @throws UniqueException the unique exception
	 */
	@Transactional
	public MethodPayment update(MethodPayment methodPayment) throws UniqueException;

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the method payment
	 */
	public MethodPayment findById(Long id);

	/**
	 * Find by name.
	 *
	 * @param name the name of MethodPayment
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
