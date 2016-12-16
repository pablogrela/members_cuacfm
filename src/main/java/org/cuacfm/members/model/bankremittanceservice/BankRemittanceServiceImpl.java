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

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittance.BankRemittanceRepository;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.CreatePayRoll;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** The Class DirectDebitServiceImpl. */
@Service("bankRemittanceService")
public class BankRemittanceServiceImpl implements BankRemittanceService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private BankRemittanceRepository bankRemittanceRepository;

	@Autowired
	private AccountService accountService;

	//	@Autowired
	//	private PayMemberService payMemberService;
	//
	//	@Autowired
	//	private PayProgramService payProgramService;

	@Autowired
	private EventService eventService;

	@Override
	public void createBankRemittance(Date dateCharge, Date monthCharge) {

		BankRemittance bankRemittance = new BankRemittance(dateCharge, monthCharge);
		bankRemittanceRepository.save(bankRemittance);

		// Direct debit of fee member
		// Map<Account, List<PayMember>> accountPayMembers = payMemberService.getPayMemberNoPayListByDirectDebit(monthCharge);

		// Direct debit of fee program
		// Map<Account, List<PayProgram>> accountPayPrograms = payProgramService.getPayProgramNoPayListByDirectDebit(monthCharge);

		for (Account account : accountService.getUsersDirectDebit()) {
			if (account.activeBankAccount() != null && account.getMethodPayment().isDirectDebit()) {
				
				// Actuliza el directDebit de cada usuario
				DirectDebit directDebit = directDebitService.save(account);

				if (directDebit != null) {
					directDebit.setBankRemittance(bankRemittance);
					directDebit.setMandate(account.activeBankAccount().getMandate());
					directDebit.setSecuence(directDebitService.isRcurOrFRST(account.getId()));
					directDebit.setMethod(methods.DIRECTDEBIT);
				}

			}
			//	if (account.activeBankAccount() != null && (accountPayMembers.get(account) != null || accountPayPrograms.get(account) != null)) {
			//
			//	Double price = Double.valueOf(0);
			//	String concept = "";
			//	List<PayMember> payMembers = accountPayMembers.get(account);
			//	if (payMembers != null) {
			//		for (PayMember payMember : payMembers) {
			//			price = payMember.getPrice();
			//			concept = concept + ", " + payMember.getFeeMember().getName();
			//		}
			//	}
			//	List<PayProgram> payPrograms = accountPayPrograms.get(account);
			//	if (payPrograms != null) {
			//		for (PayProgram payProgram : payPrograms) {
			//				price = price + payProgram.getPrice();
			//				concept = concept + ", " + payProgram.getFeeProgram().getName() + " [" + payProgram.getProgram().getName() + "]";
			//		}
			//	}
			//	concept = concept.substring(1, concept.length());
			//	if (concept.length() > 140) {
			//		concept = concept.substring(0, 140);
			//	}
			//	DirectDebit directDebit = new DirectDebit(account, bankRemittance, payMembers, payPrograms, price, concept,
			//		account.activeBankAccount().getMandate(), directDebitService.isRcurOrFRST(account.getId()));
			//		directDebitService.save(directDebit);
			//	}
		}
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		eventService.save("bankRemittance.successCreate", null, 2, arguments);
	}

	@Override
	public BankRemittance update(BankRemittance bankRemittance) {
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		eventService.save("bankRemittance.successManagement", null, 2, arguments);
		return bankRemittanceRepository.update(bankRemittance);
	}

	/**
	 * Update state bank remittance.
	 *
	 * @param bankRemittance the bank remittance
	 * @param state the state
	 * @param method the method
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	private void updateStateBankRemittance(BankRemittance bankRemittance, states state, methods method) throws ExistTransactionIdException {
		bankRemittance.setState(state);
		bankRemittanceRepository.update(bankRemittance);
		for (DirectDebit directDebit : directDebitService.findAllByBankRemittanceId(bankRemittance.getId())) {
			if (!directDebit.getState().equals(states.RETURN_BILL)) {
				directDebitService.updateDirectDebit(directDebit, state, method, bankRemittance.getDateCharge());
			}
		}
	}

	@Override
	public String payBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittance, states.PAY, methods.DIRECTDEBIT);
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		return eventService.save("bankRemittance.successPay", null, 2, arguments);
	}

	@Override
	public String managementBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittance, states.MANAGEMENT, methods.NO_PAY);
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		return eventService.save("bankRemittance.successManagement", null, 2, arguments);
	}

	@Override
	public BankRemittance findById(Long id) {
		return bankRemittanceRepository.findById(id);
	}

	@Override
	public List<BankRemittance> getBankRemittanceList() {
		return bankRemittanceRepository.getBankRemittanceList();
	}

	@Override
	public ResponseEntity<byte[]> createTxtBankRemittance(Long bankRemittanceId) {

		BankRemittance bankRemittance = bankRemittanceRepository.findById(bankRemittanceId);
		Date date = new Date();
		String file = messageSource.getMessage("fileBankRemittance", null, Locale.getDefault()) + DisplayDate.dateTimeToStringSp(date) + ".txt";

		//String path = System.getProperty("user.dir") + "/" + file
		String path = messageSource.getMessage("path", null, Locale.getDefault()) + file;

		try {
			new CreatePayRoll(path, messageSource, bankRemittance, directDebitService.findAllByBankRemittanceId(bankRemittanceId));
		} catch (IOException e) {
			e.getMessage();
		}

		return CreatePayRoll.viewTxt(path, file);
	}
}
