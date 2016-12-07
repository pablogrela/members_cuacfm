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
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.CreatePayRoll;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** The Class DirectDebitServiceImpl. */
@Service("directDebitService")
public class BankRemittanceServiceImpl implements BankRemittanceService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DirectDebitRepository directDebitRepository;

	@Autowired
	private BankRemittanceRepository bankRemittanceRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PayMemberService payMemberService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private EventService eventService;

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
		for (DirectDebit directDebit : directDebitRepository.getDirectDebitListByBankRemittanceId(bankRemittance.getId())) {
			if (!directDebit.getState().equals(states.RETURN_BILL)) {
				updateStateDirectDebit(directDebit, state, method, bankRemittance.getDateCharge());
			}
		}
	}

	@Override
	public void payBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittance, states.PAY, methods.DIRECTDEBIT);
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		eventService.save("bankRemittance.successPay", null, 2, arguments);
	}

	@Override
	public void managementBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittance, states.MANAGEMENT, methods.NO_PAY);
		Object[] arguments = { DisplayDate.dateToString(bankRemittance.getDateCharge()) };
		eventService.save("bankRemittance.successManagement", null, 2, arguments);
	}

	/**
	 * Update state direct debit.
	 *
	 * @param directDebit the direct debit
	 * @param state the state
	 * @param method the method
	 * @param datePay the date pay
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	private void updateStateDirectDebit(DirectDebit directDebit, states state, methods method, Date datePay) throws ExistTransactionIdException {
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

	@Override
	public void payDirectDebit(DirectDebit directDebit) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebit, states.PAY, methods.DIRECTDEBIT, new Date());
		Object[] arguments = { directDebit.getConcept() + " " + directDebit.getAccount().getName() };
		eventService.save("directDebit.successPay", null, 2, arguments);
	}

	@Override
	public void returnBill(DirectDebit directDebit) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebit, states.RETURN_BILL, methods.NO_PAY, null);
		Object[] arguments = { directDebit.getConcept() + " " + directDebit.getAccount().getName() };
		eventService.save("directDebit.successReturnBill", null, 2, arguments);
	}

	@Override
	public void managementDirectDebit(DirectDebit directDebit) throws ExistTransactionIdException {
		updateStateDirectDebit(directDebit, states.MANAGEMENT, methods.NO_PAY, new Date());
		Object[] arguments = { directDebit.getConcept() + " " + directDebit.getAccount().getName() };
		eventService.save("directDebit.successPay", null, 2, arguments);
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
	public DirectDebit updateDirectDebit(DirectDebit directDebit) {
		return directDebitRepository.update(directDebit);
	}

	@Override
	public DirectDebit findByDirectDebitId(Long directDebitId) {
		return directDebitRepository.findById(directDebitId);
	}

	@Override
	public List<DirectDebit> getDirectDebitListByBankRemittanceId(Long bankRemittanceId) {
		return directDebitRepository.getDirectDebitListByBankRemittanceId(bankRemittanceId);
	}

	@Override
	public ResponseEntity<byte[]> createTxtBankRemittance(Long bankRemittanceId) {

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
