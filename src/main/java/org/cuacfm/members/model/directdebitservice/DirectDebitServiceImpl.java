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
package org.cuacfm.members.model.directdebitservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitDTO;
import org.cuacfm.members.model.directdebit.DirectDebitRepository;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class DirectDebitRepositoryImpl. */
@Service("directDebitService")
public class DirectDebitServiceImpl implements DirectDebitService {

	//private static final Logger logger = LoggerFactory.getLogger(DirectDebitServiceImpl.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private DirectDebitRepository directDebitRepository;

	@Autowired
	private PayMemberService payMemberService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private EventService eventService;

	@Override
	public DirectDebit save(DirectDebit directDebit) {
		return directDebitRepository.save(directDebit);
	}

	@Override
	public DirectDebit save(Account account) {
		DirectDebit directDebit = directDebitRepository.getLastDirectDebit(account.getId());

		// If no exists open directDebit, its create a new
		if (directDebit == null) {
			String[] idRevious = directDebitRepository.findLastId().split("_");
			String date = DateUtils.format(new Date(), "ddMMyyyy");
			String idNew = date + "_" + String.format("%03d", Integer.parseInt(idRevious[1]) + 1);
			directDebit = new DirectDebit(account, idNew);
		}
		// Al actualizarse se borra el metodo que existiese
		directDebit.setMethod(null);
		directDebit.setPayPrograms(payProgramService.findNoPayListByAccountId(account.getId()));
		directDebit.setPayMembers(payMemberService.findNoPayListByAccountId(account.getId()));

		// if price is 0, it is not create directDebit
		if (directDebit.getPrice() == 0) {
			directDebitRepository.remove(directDebit);
			return null;
		}

		return directDebitRepository.update(directDebit);
	}

	@Override
	public String refresh() {
		for (Account account : accountService.getAccounts()) {
			save(account);
		}
		return eventService.save("directDebit.successRefresh", null, 1, null);
	}

	@Override
	public List<DirectDebit> findAll() {
		return directDebitRepository.findAll();
	}

	@Override
	public List<DirectDebit> findAllOpen() {
		return directDebitRepository.findAllOpen();
	}

	@Override
	public List<DirectDebit> findAllClose() {
		return directDebitRepository.findAllClose();
	}

	@Override
	public List<DirectDebit> findAllByAccountId(Long accountId) {
		return directDebitRepository.findAllByAccountId(accountId);
	}

	@Override
	public List<DirectDebit> findAllOpenByAccountId(Long accountId) {
		return directDebitRepository.findAllOpenByAccountId(accountId);
	}

	@Override
	public List<DirectDebit> findAllByBankRemittanceId(Long bankRemittanceId) {
		return directDebitRepository.findAllByBankRemittanceId(bankRemittanceId);
	}

	@Override
	public void updateDirectDebit(DirectDebit directDebit, states state, methods method, Date datePay) throws ExistTransactionIdException {
		directDebit.setState(state);
		directDebit.setMethod(method);
		directDebit.setDateUpdate(new Date());
		directDebit.setDatePay(datePay);
		directDebitRepository.update(directDebit);

		if (directDebit.getPayMembers() != null) {
			for (PayMember payMember : directDebit.getPayMembers()) {
				payMember.setState(state);
				payMember.setMethod(method);
				payMember.setDatePay(datePay);
				payMember.setIdTxn(directDebit.getIdTxn());
				payMember.setEmailPayer(directDebit.getEmailPayer());
				payMember.setIdPayer(directDebit.getIdPayer());
				payMemberService.update(payMember);
			}
		}

		if (directDebit.getPayPrograms() != null) {
			for (PayProgram payProgram : directDebit.getPayPrograms()) {
				payProgram.setState(state);
				payProgram.setMethod(method);
				payProgram.setDatePay(datePay);
				payProgram.setIdTxn(directDebit.getIdTxn());
				payProgram.setEmailPayer(directDebit.getEmailPayer());
				payProgram.setIdPayer(directDebit.getIdPayer());
				payProgramService.update(payProgram);
			}
		}
	}

	@Override
	public List<DirectDebitDTO> getDTO(List<DirectDebit> directDebits) {

		List<DirectDebitDTO> directDebitsDTO = new ArrayList<>();
		for (DirectDebit directDebit : directDebits) {
			DirectDebitDTO directDebitDTO = new DirectDebitDTO(directDebit.getId(), accountService.getAccountDTO(directDebit.getAccount()),
					directDebit.getConcept(), directDebit.getPrice(), directDebit.getDateCreate(), directDebit.getDateUpdate(), directDebit.getDatePay(), directDebit.getState(),
					directDebit.getMethod(), directDebit.getSecuence(), directDebit.getIdPayer(), directDebit.getIdTxn(),
					directDebit.getEmailPayer());

			if (directDebit.getReturnReason() != null) {
				directDebitDTO.setReturnReason(directDebit.getReturnReason().getDescription());
			}
			if (directDebit.getBankRemittance() != null) {
				directDebitDTO.setBankRemittance(directDebit.getBankRemittance().getMonthCharge());
			}
			directDebitsDTO.add(directDebitDTO);
		}
		return directDebitsDTO;
	}

	@Override
	public DirectDebit findById(String id) {
		return directDebitRepository.findById(id);
	}

	@Override
	public DirectDebit getLastDirectDebit(Long acoountId) {
		return directDebitRepository.getLastDirectDebit(acoountId);
	}

	@Override
	public String isRcurOrFRST(Long accountId) {
		return directDebitRepository.isRcurOrFRST(accountId);
	}

	@Override
	public String directDebit(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		updateDirectDebit(directDebit, states.PAY, methods.DIRECTDEBIT, new Date());
		Object[] arguments = { directDebit.getConcept() };
		return eventService.save("directDebit.successDirectDebit", account, 2, arguments);
	}

	@Override
	public String markBankDeposit(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		if (directDebit.getState().equals(states.NO_PAY) || directDebit.getState().equals(states.RETURN_BILL)) {
			updateDirectDebit(directDebit, states.MANAGEMENT, methods.BANK_DEPOSIT, null);
			Object[] arguments = { directDebit.getConcept() };
			return eventService.save("directDebit.successBankDeposit.mark", account, 2, arguments);
		}
		return null;
	}

	@Override
	public String cancelBankDeposit(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		if (directDebit.getMethod().equals(methods.BANK_DEPOSIT) || directDebit.getState().equals(states.MANAGEMENT)) {
			updateDirectDebit(directDebit, states.NO_PAY, null, null);
			Object[] arguments = { directDebit.getConcept() };
			return eventService.save("directDebit.successBankDeposit.cancel", account, 2, arguments);
		}
		return null;
	}

	@Override
	public String confirmBankDeposit(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		if (directDebit.getMethod().equals(methods.BANK_DEPOSIT) || directDebit.getState().equals(states.MANAGEMENT)) {
			updateDirectDebit(directDebit, states.PAY, methods.BANK_DEPOSIT,  new Date());
			Object[] arguments = { directDebit.getConcept() };
			return eventService.save("directDebit.successBankDeposit.pay", account, 2, arguments);
		}
		return null;
	}

	@Override
	public String confirmPaypal(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		if (directDebit.getMethod().equals(methods.PAYPAL) || directDebit.getState().equals(states.MANAGEMENT)) {
			updateDirectDebit(directDebit, states.PAY, methods.PAYPAL,  new Date());
			Object[] arguments = { directDebit.getConcept() };
			return eventService.save("directDebit.successPayPal.pay", account, 2, arguments);
		}
		return null;
	}
	
	@Override
	public String cash(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		if (directDebit.getState().equals(states.NO_PAY) || directDebit.getState().equals(states.RETURN_BILL)) {
			updateDirectDebit(directDebit, states.PAY, methods.CASH, new Date());
			Object[] arguments = { directDebit.getConcept() };
			return eventService.save("directDebit.successCash", account, 2, arguments);
		}
		return null;
	}

	@Override
	public void paypal(DirectDebit directDebit, Account account, String idTxn, String idPayer, String emailPayer, String statusPay, String datePay)
			throws ExistTransactionIdException {
		if (directDebit.getMethod() == null || !directDebit.getState().equals(states.CANCEL)) {
			DirectDebit directDebitExist = directDebitRepository.findByIdTxn(idTxn);
			if (directDebitExist != null && directDebitExist.getId() != directDebit.getId()) {
				throw new ExistTransactionIdException(idTxn);
			}

			directDebit.setIdTxn(idTxn);
			directDebit.setEmailPayer(emailPayer);
			directDebit.setIdPayer(idPayer);
			directDebit.setMethod(methods.PAYPAL);
			directDebit.setState(states.MANAGEMENT);

			if (statusPay.contains("Completed")) {
				directDebit.setDatePay(DateUtils.format(datePay, DateUtils.FORMAT_PAYPAL, Locale.US));
				directDebit.setState(states.PAY);
			}

			updateDirectDebit(directDebit, directDebit.getState(), directDebit.getMethod(), directDebit.getDatePay());
			Object[] arguments = { directDebit.getConcept() };
			eventService.save("directDebit.successPayPal", account, 2, arguments);
		}
	}

	@Override
	public String returnBill(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		updateDirectDebit(directDebit, states.RETURN_BILL, methods.DIRECTDEBIT, null);
		Object[] arguments = { directDebit.getConcept() };
		return eventService.save("directDebit.successReturnBill", account, 2, arguments);
	}

	@Override
	public String management(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		updateDirectDebit(directDebit, states.MANAGEMENT, methods.DIRECTDEBIT, null);
		Object[] arguments = { directDebit.getConcept() };
		return eventService.save("directDebit.successManagement", account, 2, arguments);
	}

	@Override
	public String cancel(DirectDebit directDebit, Account account) throws ExistTransactionIdException {
		updateDirectDebit(directDebit, states.CANCEL, null, null);
		Object[] arguments = { directDebit.getConcept() };
		return eventService.save("directDebit.successCancel", account, 2, arguments);
	}
}
