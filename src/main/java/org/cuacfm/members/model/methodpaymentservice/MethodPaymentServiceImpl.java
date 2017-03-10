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
import org.cuacfm.members.model.methodpayment.MethodPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The Class MethodPaymentRepositoryImpl. */
@Service("methodPaymentService")
public class MethodPaymentServiceImpl implements MethodPaymentService {

	@Autowired
	private MethodPaymentRepository methodPaymentRepository;

	@Override
	public MethodPayment save(MethodPayment methodPayment) throws UniqueException {
		// It is verified that there is not exist name of accountType in other
		// methodPayment
		if (methodPaymentRepository.findByName(methodPayment.getName()) != null) {
			throw new UniqueException("Name", methodPayment.getName());
		}
		return methodPaymentRepository.save(methodPayment);
	}

	@Override
	@Transactional
	public MethodPayment update(MethodPayment methodPayment) throws UniqueException {
		// It is verified that there is not exist name of methodPayment in other
		// methodPayment
		MethodPayment methodPaymentSearch = methodPaymentRepository.findByName(methodPayment.getName());
		if ((methodPaymentSearch != null) && (methodPaymentSearch.getId() != methodPayment.getId())) {
			throw new UniqueException("Name", methodPayment.getName());
		}
		return methodPaymentRepository.update(methodPayment);
	}

	@Override
	public void delete(Long id) {
		methodPaymentRepository.delete(id);
	}

	@Override
	public MethodPayment findById(Long id) {
		return methodPaymentRepository.findById(id);
	}

	@Override
	public MethodPayment findByName(String name) {
		return methodPaymentRepository.findByName(name);
	}

	@Override
	public List<MethodPayment> getMethodPayments() {
		return methodPaymentRepository.getMethodPayments();
	}
}
