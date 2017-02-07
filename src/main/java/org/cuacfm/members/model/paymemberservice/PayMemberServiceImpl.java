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
package org.cuacfm.members.model.paymemberservice;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feemember.FeeMemberRepository;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymember.PayMemberRepository;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.CreatePdf;
import org.cuacfm.members.model.util.FileUtils;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfPTable;

/** The Class PayMemberServiceImpl. */
@Service("payMemberService")
public class PayMemberServiceImpl implements PayMemberService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PayMemberRepository payMemberRepository;

	@Autowired
	private FeeMemberRepository feeMemberRepository;

	@Autowired
	private EventService eventService;

	public PayMemberServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public PayMember save(PayMember payMember) {
		return payMemberRepository.save(payMember);
	}

	@Override
	public PayMember update(PayMember payMember) throws ExistTransactionIdException {
		Object[] arguments = { payMember.getFeeMember().getName(), payMember.getAccount().getName() };
		eventService.save("payMember.successModify", null, 2, arguments);
		return payMemberRepository.update(payMember);
	}

	@Override
	public void pay(PayMember payMember) {
		payMember.setState(states.PAY);
		payMember.setMethod(methods.CASH);
		payMember.setDatePay(new Date());
		payMemberRepository.update(payMember);

		Object[] arguments = { payMember.getFeeMember().getName() };
		eventService.save("payMember.successPay", null, 2, arguments);
	}

	@Override
	public void payPayPal(PayMember payMember, String idTxn, String idPayer, String emailPayer, String statusPay, String datePay)
			throws ExistTransactionIdException {

		PayMember paymentExist = payMemberRepository.findByIdTxn(idTxn);
		if ((paymentExist != null) && (paymentExist.getId() != payMember.getId())) {
			throw new ExistTransactionIdException(idTxn);
		}

		payMember.setIdTxn(idTxn);
		payMember.setEmailPayer(emailPayer);
		payMember.setIdPayer(idPayer);
		payMember.setDatePay(DisplayDate.stringPaypalToDate(datePay));
		payMember.setMethod(methods.NO_PAY);
		payMember.setState(states.MANAGEMENT);
		if (statusPay.contains("Completed")) {
			payMember.setState(states.PAY);
			payMember.setMethod(methods.PAYPAL);
		}
		payMemberRepository.update(payMember);

		Object[] arguments = { payMember.getFeeMember().getName() };
		eventService.save("userPayments.successPayPal", null, 2, arguments);
	}

	@Override
	public PayMember findById(Long id) {
		return payMemberRepository.findById(id);
	}

	@Override
	public PayMember findByIdTxn(String idTxn) {
		return payMemberRepository.findByIdTxn(idTxn);
	}

	@Override
	public List<PayMember> findByPayMemberIds(Long accountId, Long feeMemberId) {
		return payMemberRepository.findByPayMemberIds(accountId, feeMemberId);
	}

	@Override
	public List<PayMember> getPayMemberList() {
		return payMemberRepository.getPayMemberList();
	}

	@Override
	public List<PayMember> findNoPayListByAccountId(Long accountId) {
		return payMemberRepository.findNoPayListByAccountId(accountId);
	}

	@Override
	public Map<Account, List<PayMember>> getPayMemberNoPayListByDirectDebit(Date monthCharge) {
		return payMemberRepository.getPayMemberNoPayListByDirectDebit(monthCharge);
	}

	@Override
	public List<PayMember> getPayMemberListByFeeMemberId(Long feeMemberId) {
		return payMemberRepository.getPayMemberListByFeeMemberId(feeMemberId);
	}

	@Override
	public List<PayMember> getPayMemberListByAccountId(Long accountId) {
		return payMemberRepository.getPayMemberListByAccountId(accountId);
	}

	@Override
	public List<String> getUsernamesByFeeMember(Long feeMemberId) {
		return payMemberRepository.getUsernamesByFeeMember(feeMemberId);
	}

	@Override
	public ResponseEntity<byte[]> createPdfFeeMember(Long feeMemberId, String option) {

		FeeMember feeMember = feeMemberRepository.findById(feeMemberId);
		List<PayMember> payMembers = payMemberRepository.getPayMemberListByFeeMemberId(feeMemberId);

		Date date = new Date();
		String fileNameFeeMember = messageSource.getMessage("fileNameFeeMember", null, Locale.getDefault()) + DisplayDate.dateTimeToStringSp(date)
				+ ".pdf";

		FileUtils.createFolderIfNoExist(messageSource.getMessage("pathFeeMember", null, Locale.getDefault()));
		String path = messageSource.getMessage("pathFeeMember", null, Locale.getDefault()) + fileNameFeeMember;

		String title;
		if (option.equals(Constants.PAY)) {
			title = feeMember.getName() + " - " + messageSource.getMessage("feeMember.printPayList", null, Locale.getDefault());
		} else if (option.equals(Constants.NOPAY)) {
			title = feeMember.getName() + " - " + messageSource.getMessage("feeMember.printNoPayList", null, Locale.getDefault());
		} else {
			title = feeMember.getName() + " - " + messageSource.getMessage("feeMember.printAllList", null, Locale.getDefault());

		}
		CreatePdf pdf = new CreatePdf();
		PdfPTable table = pdf.createTablePayMembers(messageSource, option, payMembers);
		pdf.createBody(path, title, table);
		return CreatePdf.viewPdf(path, fileNameFeeMember);
	}
}
