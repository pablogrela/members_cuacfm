package org.cuacfm.members.model.userPayInscriptionService;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscription.UserPayInscriptionRepository;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class UserPayInscriptionServiceImpl. */
@Service("userPayInscriptionService")
public class UserPayInscriptionServiceImpl implements UserPayInscriptionService {

	/** The payInscription repository. */
	@Autowired
	private UserPayInscriptionRepository userPayInscriptionRepository;

	/** Instantiates a new payInscription service. */
	public UserPayInscriptionServiceImpl() {
		// Default empty constructor.
	}

	/**
	 * Save an userPayInscription into database.
	 *
	 * @param userPayInscription
	 *            the user pay inscription
	 * @return UserPayInscription
	 */
	@Override
	public UserPayInscription save(UserPayInscription userPayInscription) {
		return userPayInscriptionRepository.save(userPayInscription);
	}

	/**
	 * Update UserPayInscription.
	 *
	 * @param userPayInscription
	 *            the user pay inscription
	 * @return UserPayInscription
	 */
	@Override
	public UserPayInscription update(UserPayInscription userPayInscription) {
		return userPayInscriptionRepository.update(userPayInscription);
	}

	/**
	 * Pay the UserPayInscription.
	 *
	 * @param userPayInscription
	 *            the user pay inscription
	 */
	@Override
	public void pay(UserPayInscription userPayInscription) {
		userPayInscription.setHasPay(true);
		userPayInscription.setDatePay(new Date());
		userPayInscriptionRepository.update(userPayInscription);
	}

	/**
	 * Pay paypal.
	 *
	 * @param userPayInscription
	 *            the user pay inscription
	 * @param idTxn
	 *            the id txn
	 * @param idPayer
	 *            the id payer
	 * @param emailPayer
	 *            the email payer
	 * @param statusPay
	 *            the status pay
	 * @param datePay
	 *            the date pay
	 * @throws ExistTransactionIdException
	 *             the exist transaction id exception
	 */
	@Override
	public void payPayPal(UserPayInscription userPayInscription, String idTxn, String idPayer, String emailPayer, String statusPay, String datePay) throws ExistTransactionIdException {
		
		UserPayInscription paymentExist = userPayInscriptionRepository.findByIdTxn(idTxn);
		if (paymentExist != null){
			throw new ExistTransactionIdException(idTxn);
		}
		
		userPayInscription.setIdTxn(idTxn);
		userPayInscription.setEmailPayer(emailPayer);
		userPayInscription.setIdPayer(idPayer);
		userPayInscription.setStatusPay(statusPay);
		userPayInscription.setDatePay(DisplayDate.stringPaypalToDate(datePay));
		
		if (statusPay.contains("Completed")) {
			userPayInscription.setHasPay(true);
		}
		userPayInscriptionRepository.update(userPayInscription);
	}
	
	/**
	 * Find by id returns payInscription which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return payInscription
	 */
	@Override
	public UserPayInscription findById(Long id) {
		return userPayInscriptionRepository.findById(id);
	}

	/**
	 * Find by id txn.
	 *
	 * @param idTxn
	 *            the id txn
	 * @return the user pay inscription
	 */
	@Override
	public UserPayInscription findByIdTxn(String idTxn){
		return userPayInscriptionRepository.findByIdTxn(idTxn);
	}
	
	/**
	 * Find by user pay inscription ids.
	 *
	 * @param accountId
	 *            the account id
	 * @param payInscriptionId
	 *            the pay inscription id
	 * @return List<UserPayInscription>
	 */
	@Override
	public List<UserPayInscription> findByUserPayInscriptionIds(Long accountId,
			Long payInscriptionId) {
		return userPayInscriptionRepository.findByUserPayInscriptionIds(
				accountId, payInscriptionId);
	}

	/**
	 * Get all payInscriptions.
	 *
	 * @return List<PayInscription>
	 */
	@Override
	public List<UserPayInscription> getUserPayInscriptionList() {
		return userPayInscriptionRepository.getUserPayInscriptionList();
	}

	/**
	 * Gets the user pay inscription list by pay inscription id.
	 *
	 * @param payInscriptionId
	 *            the pay inscription id
	 * @return the user pay inscription list by pay inscription id
	 */
	@Override
	public List<UserPayInscription> getUserPayInscriptionListByPayInscriptionId(
			Long payInscriptionId) {
		return userPayInscriptionRepository
				.getUserPayInscriptionListByPayInscriptionId(payInscriptionId);
	}

	/**
	 * Gets the user pay inscription list by pay account id.
	 *
	 * @param accountId
	 *            the pay inscription id
	 * @return the user pay inscription list by account id
	 */
	@Override
	public List<UserPayInscription> getUserPayInscriptionListByAccountId(
			Long accountId) {
		return userPayInscriptionRepository
				.getUserPayInscriptionListByAccountId(accountId);
	}

}
