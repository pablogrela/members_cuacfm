package org.cuacfm.members.model.methodPaymentService;

import java.util.List;

import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.methodPayment.MethodPayment;
import org.cuacfm.members.model.methodPayment.MethodPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The Class MethodPaymentRepositoryImpl. */
@Service("methodPaymentService")
public class MethodPaymentServiceImpl implements MethodPaymentService {

	/** The methodPaymentRepository. */
	@Autowired
	private MethodPaymentRepository methodPaymentRepository;

	/**
	 * Save.
	 *
	 * @param account
	 *            the account
	 * @return the account type
	 * @throws UniqueException
	 */
	@Override
	public MethodPayment save(MethodPayment methodPayment)
			throws UniqueException {
		// It is verified that there is not exist name of accountType in other methodPayment
		if (methodPaymentRepository.findByName(methodPayment.getName()) != null) {
			throw new UniqueException("Name", methodPayment.getName());
		}
		return methodPaymentRepository.save(methodPayment);
	}

	/**
	 * Update.
	 *
	 * @param account
	 *            the account
	 * @return the account type
	 * @throws UniqueException
	 */
	@Override
	@Transactional
	public MethodPayment update(MethodPayment methodPayment)
			throws UniqueException {
		// It is verified that there is not exist name of methodPayment in other methodPayment
		MethodPayment methodPaymentSearch = methodPaymentRepository
				.findByName(methodPayment.getName());
		if (methodPaymentSearch != null) {
			if (methodPaymentSearch.getId() != methodPayment.getId()) {
				throw new UniqueException("Name", methodPayment.getName());
			}
		}
		return methodPaymentRepository.update(methodPayment);
	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 */
	public void delete(Long id) {
		methodPaymentRepository.delete(id);
	}

	/**
	 * Find by id.
	 *
	 * @param id
	 *            the id
	 * @return the account type
	 */
	@Override
	public MethodPayment findById(Long id) {
		return methodPaymentRepository.findById(id);
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
		return methodPaymentRepository.findByName(name);
	}

	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	@Override
	public List<MethodPayment> getMethodPayments() {
		return methodPaymentRepository.getMethodPayments();
	}
}
