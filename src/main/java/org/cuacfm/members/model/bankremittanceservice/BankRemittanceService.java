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

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/** The Interface BankRemittanceService. */
public interface BankRemittanceService {

	/**
	 * Creates the bank remittance.
	 *
	 * @param dateCharge the date charge
	 * @param monthCharge the month charge
	 */
	public void createBankRemittance(Date dateCharge, Date monthCharge);

	/**
	 * Update.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the bank remittance
	 */
	public BankRemittance update(BankRemittance bankRemittance);

	/**
	 * Pay bank remittance.
	 *
	 * @param bankRemittance the bank remittance
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String payBankRemittance(BankRemittance bankRemittance) throws ExistTransactionIdException;

	/**
	 * Management bank remittance.
	 *
	 * @param bankRemittanc the bank remittanc
	 * @return the string
	 * @throws ExistTransactionIdException the exist transaction id exception
	 */
	public String managementBankRemittance(BankRemittance bankRemittanc) throws ExistTransactionIdException;

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the bank remittance
	 */
	public BankRemittance findById(Long id);

	/**
	 * Gets the bank remittance list.
	 *
	 * @return the bank remittance list
	 */
	public List<BankRemittance> getBankRemittanceList();

	/**
	 * generate XML Bank Remittance.
	 *
	 * @param bankRemittanceId the bank remittance id
	 * @return the response entity
	 */
	public ResponseEntity<byte[]> generateXML(Long bankRemittanceId);

	/**
	 * Process XML.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String processXML(MultipartFile path);
}
