package org.cuacfm.members.model.payInscriptionService;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payInscription.PayInscription;
import org.cuacfm.members.model.payInscription.PayInscriptionRepository;
import org.cuacfm.members.model.userPayInscription.UserPayInscription;
import org.cuacfm.members.model.userPayInscriptionService.UserPayInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class PayInscriptionServiceImpl. */
@Service("payInscriptionService")
public class PayInscriptionServiceImpl implements PayInscriptionService {

	/** The PayInscription repository. */
	@Autowired
	private PayInscriptionRepository payInscriptionRepository;

	/** The UserPayInscriptionService. */
	@Inject
	private UserPayInscriptionService userPayInscriptionService;
	
	/** The account service. */
	@Inject
	private AccountService accountService;
	
	/** Instantiates a new payInscription service. */
	public PayInscriptionServiceImpl() {
		// Default empty constructor.
	}

	/**
	 * Save.
	 *
	 * @param payInscription
	 *            the pay inscription
	 * @return the pay inscription
	 * @throws UniqueException 
	 */
	@Override
	public PayInscription save(PayInscription payInscription) throws UniqueException {
		// It is verified that there is not exist year of payInscription in other payInscription
		if (payInscriptionRepository.findByYear(payInscription.getYear()) != null) {
			throw new UniqueException("Year", String.valueOf(payInscription.getYear()));
		}
		
		payInscriptionRepository.save(payInscription);
		int percent;
		
		
		// Create payments of users
		for (Account user :accountService.getUsers()){
			if (user.getAccountType() != null){
				percent = user.getAccountType().getDiscount();
			}
			else {
				percent = 0;
			}
			
			Double discount = (payInscription.getPrice() * percent) / 100;
			Double price = payInscription.getPrice() - discount;
			price = (price/user.getInstallments());
			
			// Create installments
			for (int installment=1; installment<=user.getInstallments(); installment++){
				UserPayInscription userPayInscription = new UserPayInscription(user, payInscription, price, installment, user.getInstallments());
				userPayInscriptionService.save(userPayInscription);
			}
		}
		
		return payInscription;
	}


	/**
	 * Save user pay inscription.
	 *
	 * @param account the account
	 * @param payInscription the pay inscription
	 */
	@Override
	public void saveUserPayInscription(Account account, PayInscription payInscription) {
		/*// Version anterior del algoritmo
		int month = LocalDate.now().getMonthValue();
		Double fee = payInscription.getPrice()/12 * (12-month);
		Double discount = fee * account.getAccountType().getDiscount() / 100;
		Double price = fee - discount;*/
		
		int month = 0;
		int percent = 0;
		if (account.getAccountType() != null){
			percent = account.getAccountType().getDiscount();
		}

		// If the same year applied the discount
		if (payInscription.getYear()==LocalDate.now().getYear()){
			month = LocalDate.now().getMonthValue();
		}
		
		Double fee = payInscription.getPrice()/12 * month;
		Double discount = (payInscription.getPrice() * percent) / 100;
		Double price = payInscription.getPrice() - discount;
		price = (price/account.getInstallments());	
		Double amount = Double.valueOf(0);

		// Create installments
		for (int installment=1; installment<=account.getInstallments(); installment++){
			
			if (fee>0) {
				amount = price - fee;
				fee = fee - price;
			}
			else {
				amount = price;
			}
			if (amount>0) {	
				UserPayInscription userPayInscription = new UserPayInscription(account, payInscription, amount, installment, account.getInstallments());
				userPayInscriptionService.save(userPayInscription);
			}
		}
	}
	

	/**
	 * Update.
	 *
	 * @param payInscription
	 *            the pay inscription
	 * @return the pay inscription
	 * @throws UniqueException 
	 */
	@Override
	public PayInscription update(PayInscription payInscription) throws UniqueException {
		// It is verified that there is not exist name of trainingType in other trainingType
		PayInscription payInscriptionSearch = payInscriptionRepository.findByYear(payInscription.getYear());
		if (payInscriptionSearch != null) {
			if (payInscriptionSearch.getId() != payInscription.getId()) {
				throw new UniqueException("Year", String.valueOf(payInscription.getYear()));
			}
		}
		return payInscriptionRepository.update(payInscription);
	}

	/**
	 * Find by Name the payInscription.
	 *
	 * @param name the name
	 * @return PayInscription
	 */
	@Override
	public PayInscription findByName(String name) {
		return payInscriptionRepository.findByName(name);
	}

	/**
	 * Find by id returns payInscription which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return payInscription
	 */
	@Override
	public PayInscription findById(Long id) {
		return payInscriptionRepository.findById(id);
	}

	/**
	 * Find by year.
	 *
	 * @param year
	 *            the year
	 * @return the pay inscription
	 */
	public PayInscription findByYear(int year){
		return payInscriptionRepository.findByYear(year);
	}
	
	/**
	 * Get all payInscriptions.
	 *
	 * @return List<PayInscription>
	 */
	@Override
	public List<PayInscription> getPayInscriptionList() {
		return payInscriptionRepository.getPayInscriptionList();
	}

	/**
	 * Get all PayInscription by payInscriptionId.
	 *
	 * @return List<Training>
	 */
	/*
	 * public List<Training> getPayInscriptionListByPayInscriptionId(Long
	 * payInscriptionId){ return
	 * trainingRepository.getPayInscriptionListByPayInscriptionId
	 * (payInscriptionId); }
	 */
}
