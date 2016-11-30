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
package org.cuacfm.members.model.bankremittanceservice;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittance.BankRemittanceRepository;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitRepository;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.util.States.methods;
import org.cuacfm.members.model.util.States.states;
import org.cuacfm.members.web.support.CreatePayRoll;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** The Class DirectDebitServiceImpl. */
@Service("directDebitService")
public class BankRemittanceServiceImpl implements BankRemittanceService {

	/** The direct debit repository. */
	@Autowired
	private DirectDebitRepository directDebitRepository;

	/** The bank remittance repository. */
	@Autowired
	private BankRemittanceRepository bankRemittanceRepository;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The PayMemberService. */
	@Autowired
	private PayMemberService payMemberService;

	/** The pay program service. */
	@Autowired
	private PayProgramService payProgramService;

	/**
	 * Creates the bank remittance.
	 *
	 * @param dateCharge the date charge
	 * @param monthCharge the month charge
	 */
	@Override
	public void createBankRemittance(Date dateCharge, Date monthCharge) {

		BankRemittance bankRemittance = new BankRemittance(dateCharge, monthCharge);
		bankRemittanceRepository.save(bankRemittance);

		// Direct debit of fee member
		Map<Account, List<PayMember>> accountPayMembers = payMemberService.getPayMemberNoPayListByDirectDebit(monthCharge);

		// Direct debit of fee program
		Map<Account, List<PayProgram>> accountPayPrograms = payProgramService.getPayProgramNoPayListByDirectDebit(monthCharge);

		for (Account account : accountService.getUsersDirectDebit()) {
			if (account.activeBankAccount() != null && (accountPayMembers.get(account) != null || accountPayPrograms.get(account) != null)) {

				Double price = Double.valueOf(0);
				String concept = "";
				List<PayMember> payMembers = accountPayMembers.get(account);
				if (payMembers != null) {
					for (PayMember payMember : payMembers) {
						price = payMember.getPrice();
						concept = concept + ", " + payMember.getFeeMember().getName();
					}
				}
				List<PayProgram> payPrograms = accountPayPrograms.get(account);
				if (payPrograms != null) {
					for (PayProgram payProgram : payPrograms) {
						price = price + payProgram.getPrice();
						concept = concept + ", " + payProgram.getFeeProgram().getName() + " [" + payProgram.getProgram().getName() + "]";
					}
				}
				concept = concept.substring(1, concept.length());
				if (concept.length() > 140) {
					concept = concept.substring(0, 140);
				}
				DirectDebit directDebit = new DirectDebit(account, bankRemittance, payMembers, payPrograms, price, concept,
						account.activeBankAccount().getMandate(), directDebitRepository.isRcurOrFRST(account.getId()));
				directDebitRepository.save(directDebit);
			}
		}
	}

	/**
	 * Update.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the bank remittance
	 */
	@Override
	public BankRemittance update(BankRemittance bankRemittance) {
		return bankRemittanceRepository.update(bankRemittance);
	}

	/**
	 * Update state bank remittance.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @param state the state
	 * @param method the method
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void updateStateBankRemittance(Long bankRemittanceId, states state, methods method) throws ExistTransactionIdException {
		BankRemittance bankRemittance = bankRemittanceRepository.findById(bankRemittanceId);
		bankRemittance.setState(state);
		bankRemittanceRepository.update(bankRemittance);
		for (DirectDebit directDebit : directDebitRepository.getDirectDebitListByBankRemittanceId(bankRemittance.getId())) {
			if (!directDebit.getState().equals(states.RETURN_BILL)) {
				updateStateDirectDebit(directDebit.getId(), state, method, bankRemittance.getDateCharge());
			}
		}
	}

	/**
	 * Pay bank remittance.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Override
	public void payBankRemittance(Long bankRemittanceId) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittanceId, states.PAY, methods.DIRECTDEBIT);
	}

	/**
	 * Management bank remittance.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Override
	public void managementBankRemittance(Long bankRemittanceId) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittanceId, states.MANAGEMENT, methods.NO_PAY);
	}

	/**
	 * Update state direct debit.
	 *
	 * @param directDebitId the direct debit id
	 * @param state the state
	 * @param method the method
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public void updateStateDirectDebit(Long directDebitId, states state, methods method, Date datePay) throws ExistTransactionIdException {
		DirectDebit directDebit = directDebitRepository.findById(directDebitId);
		directDebit.setState(state);
		directDebitRepository.update(directDebit);

		if (directDebit.getPayMembers() != null) {
			for (PayMember payMember : directDebit.getPayMembers()) {
				payMember.setState(state);
				payMember.setMethod(method);
				payMember.setDatePay(datePay);
				payMemberService.update(payMember);
			}
		}

		if (directDebit.getPayPrograms() != null) {
			for (PayProgram payProgram : directDebit.getPayPrograms()) {
				payProgram.setState(state);
				payProgram.setMethod(method);
				payProgram.setDatePay(datePay);
				payProgramService.update(payProgram);
			}
		}
	}

	/**
	 * Pay direct debit.
	 *
	 * @param directDebitId the direct debit id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Override
	public void payDirectDebit(Long directDebitId) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebitId, states.PAY, methods.DIRECTDEBIT, new Date());
	}

	/**
	 * Return bill.
	 *
	 * @param directDebitId the direct debit id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Override
	public void returnBill(Long directDebitId) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebitId, states.RETURN_BILL, methods.NO_PAY, null);
	}

	/**
	 * Management direct debit.
	 *
	 * @param directDebitId the direct debit id
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	@Override
	public void managementDirectDebit(Long directDebitId) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebitId, states.MANAGEMENT, methods.NO_PAY, new Date());
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the bank remittance
	 */
	@Override
	public BankRemittance findById(Long id) {
		return bankRemittanceRepository.findById(id);
	}

	/**
	 * Gets the bank remittance list.
	 *
	 * @return the bank remittance list
	 */
	@Override
	public List<BankRemittance> getBankRemittanceList() {
		return bankRemittanceRepository.getBankRemittanceList();
	}

	/**
	 * Update direct debit.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit
	 */
	@Override
	public DirectDebit updateDirectDebit(DirectDebit directDebit) {
		return directDebitRepository.update(directDebit);
	}

	/**
	 * Find by direct debit id.
	 *
	 * @param directDebitId the direct debit id
	 * @return the direct debit
	 */
	@Override
	public DirectDebit findByDirectDebitId(Long directDebitId) {
		return directDebitRepository.findById(directDebitId);
	}

	/**
	 * Gets the direct debit list by bank remittance id.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the direct debit list by bank remittance id
	 */
	@Override
	public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId) {
		return directDebitRepository.getDirectDebitListByBankRemittanceId(bankRemittanceId);
	}

	/**
	 * create Txt Bank Remittance.
	 *
	 * @param messageSource the message source
	 * @param bankRemittanceId the bank remittance id
	 * @return the response entity
	 */
	@Override
	public ResponseEntity<byte[]> createTxtBankRemittance(MessageSource messageSource, Long bankRemittanceId) {

		BankRemittance bankRemittance = bankRemittanceRepository.findById(bankRemittanceId);
		Date date = new Date();
		String file = messageSource.getMessage("fileBankRemittance", null, Locale.getDefault()) + DisplayDate.dateTimeToStringSp(date) + ".txt";

		//String path = System.getProperty("user.dir") + "/" + file
		String path = messageSource.getMessage("path", null, Locale.getDefault()) + file;

		try {
			new CreatePayRoll(path, messageSource, bankRemittance, directDebitRepository.getDirectDebitListByBankRemittanceId(bankRemittanceId));
		} catch (IOException e) {
			e.getMessage();
		}

		return CreatePayRoll.viewTxt(path, file);
	}
}
