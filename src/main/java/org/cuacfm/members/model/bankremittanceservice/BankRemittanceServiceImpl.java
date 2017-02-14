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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

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
import org.cuacfm.members.model.util.sepa.BankRemittanceSEPAXML;
import org.cuacfm.members.model.util.sepa.ReturnBankRemittanceSEPAXML;
import org.cuacfm.members.model.util.FileUtils;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** The Class bankRemittanceService. */
@Service("bankRemittanceService")
public class BankRemittanceServiceImpl implements BankRemittanceService {

	private static final Logger LOGGER = Logger.getLogger(BankRemittanceServiceImpl.class.getName());

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private BankRemittanceRepository bankRemittanceRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private EventService eventService;

	@Override
	public void createBankRemittance(Date dateCharge, Date monthCharge) {

		BankRemittance bankRemittance = new BankRemittance(dateCharge, monthCharge);
		bankRemittanceRepository.save(bankRemittance);

		for (Account account : accountService.getUsersDirectDebit()) {
			if (account.activeBankAccount() != null && account.getMethodPayment().isDirectDebit()) {

				// Actualiza el directDebit de cada usuario
				DirectDebit directDebit = directDebitService.save(account);

				if (directDebit != null) {
					directDebit.setBankRemittance(bankRemittance);
					directDebit.setSecuence(directDebitService.isRcurOrFRST(account.getId()));
					directDebit.setMethod(methods.DIRECTDEBIT);
				}
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
		updateStateBankRemittance(bankRemittance, states.MANAGEMENT, methods.DIRECTDEBIT);
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
	public ResponseEntity<byte[]> generateXML(Long bankRemittanceId) {

		LOGGER.info(LOGGER.getName() + "generateXML");

		BankRemittance bankRemittance = bankRemittanceRepository.findById(bankRemittanceId);
		Date date = new Date();

		FileUtils.createFolderIfNoExist(messageSource.getMessage("pathBankRemittance", null, Locale.getDefault()));
		String fileXML = messageSource.getMessage("fileBankRemittance", null, Locale.getDefault()) + DisplayDate.dateTimeToStringSp(date) + ".xml";
		String pathXML = messageSource.getMessage("pathBankRemittance", null, Locale.getDefault()) + fileXML;

		try {
			new BankRemittanceSEPAXML(pathXML, messageSource, bankRemittance, directDebitService.findAllByBankRemittanceId(bankRemittanceId));
		} catch (IOException | JAXBException | DatatypeConfigurationException e) {
			LOGGER.info("Logger Name: " + LOGGER.getName() + e.getMessage());
			LOGGER.log(Level.SEVERE, "Exception occur", e.getStackTrace());
		}

		return BankRemittanceSEPAXML.downloadFile(pathXML, fileXML, MediaType.TEXT_XML);
	}

	@Override
	public String processXML(MultipartFile file) {

		LOGGER.info(LOGGER.getName() + "processXML");

		try {
			byte[] bytes = file.getBytes();
			FileUtils.createFolderIfNoExist(messageSource.getMessage("pathReturnBankRemittance", null, Locale.getDefault()));
			Path pathXML = Paths.get(messageSource.getMessage("pathReturnBankRemittance", null, Locale.getDefault()) + file.getOriginalFilename());
			Files.write(pathXML, bytes);
			new ReturnBankRemittanceSEPAXML(pathXML.toString());

		} catch (Exception e) {
			LOGGER.info("Logger Name: " + LOGGER.getName() + e.getMessage());
			LOGGER.log(Level.SEVERE, "Exception occur", e.getStackTrace());
			Object[] arguments = {};
			eventService.save("bankRemittance.failUpload", null, 2, arguments);
			return "bankRemittance.failUpload";
		}
		Object[] arguments = { file.getOriginalFilename() };
		eventService.save("bankRemittance.successUpload", null, 2, arguments);
		return "bankRemittance.successUpload";
	}

}
