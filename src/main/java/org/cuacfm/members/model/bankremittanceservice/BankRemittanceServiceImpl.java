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
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** The Class bankRemittanceService. */
@Service("bankRemittanceService")
public class BankRemittanceServiceImpl implements BankRemittanceService {

	private static final Logger logger = LoggerFactory.getLogger(BankRemittanceService.class);

	@Value("${path}${pathBankRemittance}")
	private String pathBankRemittance;

	@Value("${path}${pathReturnBankRemittance}")
	private String pathReturnBankRemittance;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private BankRemittanceRepository bankRemittanceRepository;

	@Autowired
	private BankRemittanceSEPAXML bankRemittanceSEPAXML;

	@Autowired
	private ReturnBankRemittanceSEPAXML returnBankRemittanceSEPAXML;

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
		Object[] arguments = { DateUtils.format(bankRemittance.getDateCharge(), DateUtils.FORMAT_DATE) };
		eventService.save("bankRemittance.successCreate", null, 2, arguments);
	}

	@Override
	public BankRemittance update(BankRemittance bankRemittance) {
		Object[] arguments = { DateUtils.format(bankRemittance.getDateCharge(), DateUtils.FORMAT_DATE) };
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
		Object[] arguments = { DateUtils.format(bankRemittance.getDateCharge(), DateUtils.FORMAT_DATE) };
		return eventService.save("bankRemittance.successPay", null, 2, arguments);
	}

	@Override
	public String managementBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException {
		updateStateBankRemittance(bankRemittance, states.MANAGEMENT, methods.DIRECTDEBIT);
		Object[] arguments = { DateUtils.format(bankRemittance.getDateCharge(), DateUtils.FORMAT_DATE) };
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
		logger.info("generateXML");

		BankRemittance bankRemittance = bankRemittanceRepository.findById(bankRemittanceId);

		FileUtils.createFolderIfNoExist(pathBankRemittance);
		String fileXML = messageSource.getMessage("fileBankRemittance", null, Locale.getDefault())
				+ DateUtils.format(new Date(), DateUtils.FORMAT_FILE) + ".xml";

		try {
			bankRemittanceSEPAXML.create(pathBankRemittance + fileXML, bankRemittance,
					directDebitService.findAllByBankRemittanceId(bankRemittanceId));
		} catch (IOException | JAXBException | DatatypeConfigurationException e) {
			logger.error("processXML: ", e);
		}

		return FileUtils.downloadFile(pathBankRemittance, fileXML, MediaType.TEXT_XML);
	}

	@Override
	public String processXML(MultipartFile file) {
		logger.info("processXML");

		try {
			byte[] bytes = file.getBytes();
			FileUtils.createFolderIfNoExist(pathReturnBankRemittance);

			String[] originalFilename = file.getOriginalFilename().split(".xml");
			Path pathXML = Paths
					.get(pathReturnBankRemittance + originalFilename[0] + DateUtils.format(new Date(), DateUtils.FORMAT_FILE) + ".xml");
			Files.write(pathXML, bytes);
			returnBankRemittanceSEPAXML.load(pathXML.toString());

		} catch (Exception e) {
			logger.error("processXML: ", e);
			Object[] arguments = {};
			eventService.save("bankRemittance.failUpload", null, 2, arguments);
			return "bankRemittance.failUpload";
		}
		Object[] arguments = { file.getOriginalFilename() };
		eventService.save("bankRemittance.successUpload", null, 2, arguments);
		return "bankRemittance.successUpload";
	}

}
