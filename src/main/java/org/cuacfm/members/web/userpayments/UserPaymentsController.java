/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.web.userpayments;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.configurationservice.ConfigurationService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitDTO;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.paymemberservice.PayMemberService;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.web.support.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class UserPaymentsController. */
@Controller
public class UserPaymentsController {

	private static final Logger logger = LoggerFactory.getLogger(UserPaymentsController.class);
	private static final String USERPAYMENTS_VIEW_NAME = "userpayments/userpayments";
	private static final String REDIRECT_USERPAYMENTS = "redirect:/userPayments";

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PayMemberService payMemberService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private DirectDebitService directDebitService;

	//	@Autowired
	//	private MessageSource messageSource;

	private List<PayMember> payMembers;
	private List<PayProgram> payPrograms;
	private String email;

	/**
	 * Instantiates a new user payments controller.
	 */
	public UserPaymentsController() {
		super();
	}

	/**
	 * Email.
	 *
	 * @return the string
	 */
	@ModelAttribute("email")
	public String email() {
		return email;
	}

	/**
	 * Pay programs.
	 *
	 * @return the list
	 */
	@ModelAttribute("payPrograms")
	public List<PayProgram> payPrograms() {
		return payPrograms;
	}

	/**
	 * List of PayMember.
	 *
	 * @return List<PayMember>
	 */
	@ModelAttribute("payMembers")
	public List<PayMember> payMembers() {
		return payMembers;
	}

	/**
	 * User payments.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "userPayments")
	public String userPayments(Model model, Principal principal) {
		email = configurationService.getConfiguration().getEmail();
		model.addAttribute("email", email);

		Account account = accountService.findByLogin(principal.getName());
		payMembers = payMemberService.getPayMemberListByAccountId(account.getId());
		model.addAttribute("payMembers", payMembers);

		payPrograms = payProgramService.getPayProgramListByAccountId(account.getId());
		model.addAttribute("payPrograms", payPrograms);

		return USERPAYMENTS_VIEW_NAME;
	}

	/**
	 * Direct debit list.
	 *
	 * @param principal the principal
	 * @return the response entity
	 */
	@RequestMapping(value = "userPayments/directDebitList/", method = RequestMethod.GET)
	public ResponseEntity<List<DirectDebitDTO>> directDebitList(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());
		List<DirectDebitDTO> directDebitsDTO = directDebitService.getDTO(directDebitService.findAllByAccountId(account.getId()));

		if (directDebitsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(directDebitsDTO, HttpStatus.OK);
	}

	/**
	 * Direct debit active list.
	 *
	 * @param principal the principal
	 * @return the response entity
	 */
	@RequestMapping(value = "userPayments/directDebitList/open/", method = RequestMethod.GET)
	public ResponseEntity<List<DirectDebitDTO>> directDebitActiveList(Principal principal) {

		Account account = accountService.findByLogin(principal.getName());
		List<DirectDebitDTO> directDebitsDTO = directDebitService.getDTO(directDebitService.findAllOpenByAccountId(account.getId()));

		if (directDebitsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(directDebitsDTO, HttpStatus.OK);
	}

	/**
	 * View user fee members by fee member id.
	 *
	 * @param directDebitId the direct debit id
	 * @param emailPayer the email payer
	 * @param idPayer the id payer
	 * @param datePay the date pay
	 * @param statusPay the status pay
	 * @param idTxn the id txn
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/directDebitList/paypal/{directDebitId}", method = RequestMethod.POST)
	public String directDebitByPayPal(@PathVariable String directDebitId, @RequestParam("payer_email") String emailPayer,
			@RequestParam("payer_id") String idPayer, @RequestParam("payment_date") String datePay, @RequestParam("payment_status") String statusPay,
			@RequestParam("txn_id") String idTxn, Principal principal, RedirectAttributes ra) {

		// Validar que el pago, este realmente echo en paypal, con la informacion
		// que viene en el post....

		Account account = accountService.findByLogin(principal.getName());
		DirectDebit directDebit = directDebitService.findById(directDebitId);

		// Verified if account is equals to account of userPayAccount
		if (directDebit.getAccount().getId() == account.getId()) {
			try {
				directDebitService.paypal(directDebit, account, idTxn, idPayer, emailPayer, statusPay, datePay);
				MessageHelper.addSuccessAttribute(ra, Constants.SUCCESSPAYPAL, directDebit.getConcept());
			} catch (ExistTransactionIdException e) {
				logger.warn("directDebitByPayPal - ExistTransactionIdException", e);
				MessageHelper.addErrorAttribute(ra, Constants.ERRORPAYPAL, directDebit.getConcept(), e.getIdTxn());
			}
		}

		return REDIRECT_USERPAYMENTS;
	}

	/**
	 * Direct debit by bank deposit.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/directDebitList/markBankDeposit/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> directDebitByMarkBankDeposit(@PathVariable String directDebitId, Principal principal,
			RedirectAttributes ra) {

		Account account = accountService.findByLogin(principal.getName());
		DirectDebit directDebit = directDebitService.findById(directDebitId);

		// Verified if account is equals to account of userPayAccount
		if (directDebit.getAccount().getId() == account.getId() && directDebit.getMethod() == null && !directDebit.getState().equals(states.CANCEL)) {
			//  try {
			String message = directDebitService.markBankDeposit(directDebit, account);
			MessageHelper.addSuccessAttribute(ra, message);
			//	} catch (ExistTransactionIdException e) {
			//		logger.warn("directDebitByMarkBankDeposit - ExistTransactionIdException", e);
			//		Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			//		String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			//		MessageHelper.addErrorAttribute(ra, messageI18n);
			//	}
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Direct debit by cancel.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/directDebitList/cancelBankDeposit/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> directDebitByCancelBankDeposit(@PathVariable String directDebitId, Principal principal,
			RedirectAttributes ra) {

		Account account = accountService.findByLogin(principal.getName());
		DirectDebit directDebit = directDebitService.findById(directDebitId);

		// Verified if account is equals to account of userPayAccount
		if (directDebit.getAccount().getId() == account.getId()) {
			//	try {
			String message = directDebitService.cancelBankDeposit(directDebit, account);
			MessageHelper.addWarningAttribute(ra, message);
			//	} catch (ExistTransactionIdException e) {
			//		logger.warn("directDebitByCancelBankDeposit - ExistTransactionIdException", e);
			//		Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			//		String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			//		MessageHelper.addErrorAttribute(ra, messageI18n);
			//	}
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * View user fee members by fee member id.
	 *
	 * @param payMemberId the user fee member id
	 * @param emailPayer the email payer
	 * @param idPayer the id payer
	 * @param datePay the date pay
	 * @param statusPay the status pay
	 * @param idTxn the id txn
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/payMember/{payMemberId}", method = RequestMethod.POST)
	public String payMemberByPayPal(@PathVariable Long payMemberId, @RequestParam("payer_email") String emailPayer,
			@RequestParam("payer_id") String idPayer, @RequestParam("payment_date") String datePay, @RequestParam("payment_status") String statusPay,
			@RequestParam("txn_id") String idTxn, Principal principal, RedirectAttributes ra) {

		// Validar que el pago, este realmente echo en paypal, con la informacion
		// que viene en el post....

		Account account = accountService.findByLogin(principal.getName());
		PayMember payMember = payMemberService.findById(payMemberId);

		// Verified if account is equals to account of userPayAccount
		if (payMember.getAccount().getId() == account.getId()) {
			try {
				payMemberService.payPayPal(payMember, idTxn, idPayer, emailPayer, statusPay, datePay);
				MessageHelper.addSuccessAttribute(ra, Constants.SUCCESSPAYPAL, payMember.getFeeMember().getName());
			} catch (ExistTransactionIdException e) {
				logger.warn("payMemberByPayPal - ExistTransactionIdException", e);
				MessageHelper.addErrorAttribute(ra, Constants.ERRORPAYPAL, payMember.getFeeMember().getName(), e.getIdTxn());
			}
		}
		return REDIRECT_USERPAYMENTS;
	}

	/**
	 * View user fee members by fee member id.
	 *
	 * @param payProgramId the pay program id
	 * @param emailPayer the email payer
	 * @param idPayer the id payer
	 * @param datePay the date pay
	 * @param statusPay the status pay
	 * @param idTxn the id txn
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "userPayments/payProgram/{payProgramId}", method = RequestMethod.POST)
	public String payProgramByPayPal(@PathVariable Long payProgramId, @RequestParam("payer_email") String emailPayer,
			@RequestParam("payer_id") String idPayer, @RequestParam("payment_date") String datePay, @RequestParam("payment_status") String statusPay,
			@RequestParam("txn_id") String idTxn, Principal principal, RedirectAttributes ra) {

		Account account = accountService.findByLogin(principal.getName());
		PayProgram payProgram = payProgramService.findById(payProgramId);

		// Verified if account is equals to account of userPayAccount
		if (payProgram.getProgram().getAccounts().contains(account)) {
			try {
				payProgramService.payPayPal(payProgram, account.getName() + " " + account.getSurname(), idTxn, idPayer, emailPayer, statusPay,
						datePay);
				MessageHelper.addSuccessAttribute(ra, Constants.SUCCESSPAYPAL, payProgram.getProgram().getName());
			} catch (ExistTransactionIdException e) {
				logger.warn("payProgramByPayPal - ExistTransactionIdException", e);
				MessageHelper.addErrorAttribute(ra, Constants.ERRORPAYPAL, payProgram.getProgram().getName());
			}
		}
		return REDIRECT_USERPAYMENTS;
	}
}
