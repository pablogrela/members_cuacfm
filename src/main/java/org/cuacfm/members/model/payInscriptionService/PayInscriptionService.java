package org.cuacfm.members.model.payInscriptionService;

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.payInscription.PayInscription;


/** The Class PayInscriptionService. */
public interface PayInscriptionService {

	/**
	 * Save.
	 *
	 * @param payInscription
	 *            the pay inscription
	 * @return the pay inscription
	 */
	public PayInscription save(PayInscription payInscription);

	/**
	 * Save user pay inscription.
	 *
	 * @param account
	 *            the account
	 * @param payInscription
	 *            the pay inscription
	 */
	public void saveUserPayInscription(Account account,
			PayInscription payInscription);

	/**
	 * Update.
	 *
	 * @param payInscription
	 *            the pay inscription
	 * @return the pay inscription
	 */
	public PayInscription update(PayInscription payInscription);

	/**
	 * Find by Name the payInscription.
	 *
	 * @param name
	 *            the name
	 * @return PayInscription
	 */
	public PayInscription findByName(String name);

	/**
	 * Find by id returns payInscription which has this identifier.
	 *
	 * @param id
	 *            the id
	 * @return payInscription
	 */
	public PayInscription findById(Long id);

	/**
	 * Find by year.
	 *
	 * @param year
	 *            the year
	 * @return the pay inscription
	 */
	public PayInscription findByYear(int year);

	/**
	 * Get all payInscriptions.
	 *
	 * @return List<PayInscription>
	 */
	public List<PayInscription> getPayInscriptionList();

	/**
	 * Get all trainings by payInscriptionId.
	 *
	 * @return List<Training>
	 */
	/*
	 * public List<Training> getPayInscriptionListByPayInscriptionId(Long
	 * payInscriptionId);
	 */
}
