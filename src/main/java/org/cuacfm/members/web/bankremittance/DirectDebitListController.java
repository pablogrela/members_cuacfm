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
package org.cuacfm.members.web.bankremittance;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.DirectDebitDTO;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.util.Constants;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class DirectDebitListController. */
@Controller
public class DirectDebitListController {

	private static final String DIRECTDEBIT_REMITTANCE_VIEW_NAME = "bankremittance/directdebitlist";
	private static final String DIRECTDEBIT_VIEW_NAME = "directdebit/directdebitlist";
	private static final String DIRECTDEBIT_CLOSE_VIEW_NAME = "directdebit/directdebitlistclose";

	@Autowired
	private BankRemittanceService bankRemittanceService;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private MessageSource messageSource;

	// Se almacena el bankRemittance para la consulta
	private BankRemittance bankRemittance;

	/**
	 * Instantiates a new direct debit list controller.
	 */
	public DirectDebitListController() {
		// Default empty constructor.
	}

	/**
	 * Bank remittance.
	 *
	 * @return the bank remittance
	 */
	@ModelAttribute("bankRemittance")
	public BankRemittance bankRemittance() {
		return bankRemittance;
	}

	/**
	 * Direct debit view.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "directDebitList")
	public String directDebitView(Model model, Principal principal) {
		return DIRECTDEBIT_VIEW_NAME;
	}

	/**
	 * Direct debit view.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "directDebitList/close")
	public String directDebitCloseView(Model model, Principal principal) {
		return DIRECTDEBIT_CLOSE_VIEW_NAME;
	}

	/**
	 * Direct debit view.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "directDebitList/remittance")
	public String directDebitRemittanceView(Model model, Principal principal) {
		return DIRECTDEBIT_REMITTANCE_VIEW_NAME;
	}

	/**
	 * Load direct debit.
	 *
	 * @param model the model
	 * @param bankRemittanceId the bank remittance id
	 * @return the string
	 */
	@RequestMapping(value = "bankRemittanceList/directDebitList/{bankRemittanceId}", method = RequestMethod.POST)
	public String loadBankRemittance(Model model, @PathVariable Long bankRemittanceId) {
		bankRemittance = bankRemittanceService.findById(bankRemittanceId);
		model.addAttribute(bankRemittance);
		return DIRECTDEBIT_REMITTANCE_VIEW_NAME;
	}

	/**
	 * Direct debit list.
	 *
	 * @param principal the principal
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/", method = RequestMethod.GET)
	public ResponseEntity<List<DirectDebitDTO>> directDebitList(Principal principal) {

		List<DirectDebitDTO> directDebitsDTO = directDebitService.getDTO(directDebitService.findAllOpen());

		if (directDebitsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(directDebitsDTO, HttpStatus.OK);
	}

	/**
	 * Direct debit list close.
	 *
	 * @param principal the principal
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/close/", method = RequestMethod.GET)
	public ResponseEntity<List<DirectDebitDTO>> directDebitListClose(Principal principal) {

		List<DirectDebitDTO> directDebitsDTO = directDebitService.getDTO(directDebitService.findAllClose());

		if (directDebitsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(directDebitsDTO, HttpStatus.OK);
	}

	/**
	 * Direct debit list close.
	 *
	 * @param principal the principal
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/remittance/", method = RequestMethod.GET)
	public ResponseEntity<List<DirectDebitDTO>> directDebitListRemittance(Principal principal) {

		List<DirectDebitDTO> directDebitsDTO = directDebitService.getDTO(directDebitService.findAllByBankRemittanceId(bankRemittance.getId()));

		if (directDebitsDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(directDebitsDTO, HttpStatus.OK);
	}

	/**
	 * Direct debit by bank deposit.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "directDebitList/markBankDeposit/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> markBankDeposit(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.markBankDeposit(directDebit, null);
			MessageHelper.addSuccessAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Cancel bank deposit.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/cancelBankDeposit/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> cancelBankDeposit(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.cancelBankDeposit(directDebit, null);
			MessageHelper.addWarningAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Pay bank deposit.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/payBankDeposit/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> payBankDeposit(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.payBankDeposit(directDebit, null);
			MessageHelper.addWarningAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
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
	@RequestMapping(value = "directDebitList/cancel/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> directDebitByCancel(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.cancel(directDebit, null);
			MessageHelper.addWarningAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Direct debit by cash.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/cash/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> directDebitByCash(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.cash(directDebit, null);
			MessageHelper.addWarningAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Direct debit by return bill.
	 *
	 * @param directDebitId the direct debit id
	 * @param principal the principal
	 * @param ra the ra
	 * @return the response entity
	 */
	@RequestMapping(value = "directDebitList/returnBill/{directDebitId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, ?>> directDebitByReturnBill(@PathVariable String directDebitId, Principal principal, RedirectAttributes ra) {

		DirectDebit directDebit = directDebitService.findById(directDebitId);

		try {
			String message = directDebitService.returnBill(directDebit, null);
			MessageHelper.addWarningAttribute(ra, message);
		} catch (ExistTransactionIdException e) {
			Object[] arguments = { directDebit.getIdTxn(), directDebit.getConcept() };
			String messageI18n = messageSource.getMessage(Constants.ERRORIDEXCEPTION, arguments, Locale.getDefault());
			MessageHelper.addErrorAttribute(ra, messageI18n);
		}
		return new ResponseEntity<>(ra.getFlashAttributes(), HttpStatus.OK);
	}

	/**
	 * Direct debit view.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "directDebitList/refresh", method = RequestMethod.POST)
	public String directDebitRefresh(RedirectAttributes ra) {
		directDebitService.refreshAll();
		
		MessageHelper.addErrorAttribute(ra, "directDebit.successUpdateDirectDebits", "");
		return "redirect:/directDebitList";
	}
}
