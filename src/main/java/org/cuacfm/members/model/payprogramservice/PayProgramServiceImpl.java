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
package org.cuacfm.members.model.payprogramservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogram.FeeProgramRepository;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogram.PayProgramRepository;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.CreatePdf;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfPTable;

/** The Class PayProgramServiceImpl. */
@Service("payProgramService")
public class PayProgramServiceImpl implements PayProgramService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PayProgramRepository payProgramRepository;

	@Autowired
	private FeeProgramRepository feeProgramRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private EventService eventService;

	/** Instantiates a new payInscription service. */
	public PayProgramServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public PayProgram save(PayProgram payProgram) {
		return payProgramRepository.save(payProgram);
	}

	@Override
	public PayProgram update(PayProgram payProgram) throws ExistTransactionIdException {
		PayProgram paymentExist = payProgramRepository.findByIdTxn(payProgram.getIdTxn());
		if ((paymentExist != null) && (paymentExist.getId() != payProgram.getId())) {
			throw new ExistTransactionIdException(payProgram.getIdTxn());
		}

		Object[] arguments = { payProgram.getFeeProgram().getName() };
		eventService.save("payProgram.successModify", null, 2, arguments);
		return payProgramRepository.update(payProgram);
	}

	@Override
	public void pay(PayProgram payProgram) {
		payProgram.setState(states.PAY);
		payProgram.setDatePay(new Date());
		payProgram.setMethod(methods.CASH);
		payProgramRepository.update(payProgram);

		Object[] arguments = { payProgram.getFeeProgram().getName() };
		eventService.save("payProgram.successPay", null, 2, arguments);
	}

	@Override
	public void payPayPal(PayProgram payProgram, String accountPayer, String idTxn, String idPayer, String emailPayer, String statusPay,
			String datePay) throws ExistTransactionIdException {

		PayProgram paymentExist = payProgramRepository.findByIdTxn(idTxn);
		if ((paymentExist != null) && (paymentExist.getId() != payProgram.getId())) {
			throw new ExistTransactionIdException(idTxn);
		}

		payProgram.setAccountPayer(accountPayer);
		payProgram.setIdTxn(idTxn);
		payProgram.setEmailPayer(emailPayer);
		payProgram.setIdPayer(idPayer);
		payProgram.setDatePay(DisplayDate.stringPaypalToDate(datePay));
		payProgram.setState(states.MANAGEMENT);
		payProgram.setMethod(methods.NO_PAY);
		if (statusPay.contains("Completed")) {
			payProgram.setState(states.PAY);
			payProgram.setMethod(methods.PAYPAL);
		}
		payProgramRepository.update(payProgram);

		Object[] arguments = { payProgram.getFeeProgram().getName() };
		eventService.save("payProgram.successModify", null, 2, arguments);
	}

	@Override
	public PayProgram findById(Long id) {
		return payProgramRepository.findById(id);
	}

	@Override
	public PayProgram findByIdTxn(String idTxn) {
		return payProgramRepository.findByIdTxn(idTxn);
	}

	@Override
	public PayProgram findByPayProgramIds(Long programId, Long feeProgramId) {
		return payProgramRepository.findByPayProgramIds(programId, feeProgramId);
	}

	@Override
	public List<PayProgram> getPayProgramList() {
		return payProgramRepository.getPayProgramList();
	}

	@Override
	public Map<Account, List<PayProgram>> getPayProgramNoPayListByDirectDebit(Date monthCharge) {
		return payProgramRepository.getPayProgramNoPayListByDirectDebit(monthCharge);
	}

	@Override
	public List<PayProgram> getPayProgramListByFeeProgramId(Long feeProgramId) {
		return payProgramRepository.getPayProgramListByFeeProgramId(feeProgramId);
	}

	@Override
	public List<PayProgram> getPayProgramListByProgramId(Long programId) {
		return payProgramRepository.getPayProgramListByProgramId(programId);
	}

	@Override
	public List<PayProgram> getPayProgramListByAccountId(Long accountId) {

		Account account = accountService.findById(accountId);
		List<PayProgram> payProgramsResult = new ArrayList<PayProgram>();
		if (account.getPrograms() == null) {
			return payProgramsResult;
		}

		List<PayProgram> payPrograms = payProgramRepository.getPayProgramListByAccountId(accountId);
		for (PayProgram payProgram : payPrograms) {
			if (account.getPrograms().contains(payProgram.getProgram())) {
				payProgramsResult.add(payProgram);
			}
		}
		return payProgramsResult;
	}

	@Override
	public ResponseEntity<byte[]> createPdfFeeProgram(Long feeProgramId, String option) {

		FeeProgram feeProgram = feeProgramRepository.findById(feeProgramId);
		List<PayProgram> payPrograms = payProgramRepository.getPayProgramListByFeeProgramId(feeProgramId);

		Date date = new Date();
		String fileNameFeeProgram = messageSource.getMessage("fileNameFeeProgram", null, Locale.getDefault()) + DisplayDate.dateTimeToStringSp(date)
				+ ".pdf";

		//String path = System.getProperty("user.dir") + "/" + fileNameFeeProgram
		String path = messageSource.getMessage("path", null, Locale.getDefault()) + fileNameFeeProgram;

		String title;
		if (option.equals(Constants.PAY)) {
			title = feeProgram.getName() + " - " + messageSource.getMessage("feeProgram.printPayList", null, Locale.getDefault());
		} else if (option.equals(Constants.NOPAY)) {
			title = feeProgram.getName() + " - " + messageSource.getMessage("feeProgram.printNoPayList", null, Locale.getDefault());
		} else {
			title = feeProgram.getName() + " - " + messageSource.getMessage("feeProgram.printAllList", null, Locale.getDefault());

		}
		CreatePdf pdf = new CreatePdf();
		PdfPTable table = pdf.createTablePayPrograms(messageSource, option, payPrograms);
		pdf.createBody(path, title, table);
		return CreatePdf.viewPdf(path, fileNameFeeProgram);
	}
}
