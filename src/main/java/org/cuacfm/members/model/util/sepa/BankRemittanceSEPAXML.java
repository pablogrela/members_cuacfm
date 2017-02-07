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

package org.cuacfm.members.model.util.sepa;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.AccountIdentification4Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.ActiveOrHistoricCurrencyAndAmount;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.BranchAndFinancialInstitutionIdentification4;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.CashAccount16;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.CategoryPurpose1Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.ChargeBearerType1Code;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.CustomerDirectDebitInitiationV02;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.DirectDebitTransaction6;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.DirectDebitTransactionInformation9;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.Document;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.FinancialInstitutionIdentification7;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.GenericOrganisationIdentification1;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.GenericPersonIdentification1;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.GroupHeader39;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.LocalInstrument2Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.MandateRelatedInformation6;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.ObjectFactory;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.OrganisationIdentification4;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.OrganisationIdentificationSchemeName1Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.Party6Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PartyIdentification32;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PaymentIdentification1;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PaymentInstructionInformation4;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PaymentMethod2Code;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PaymentTypeInformation20;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PersonIdentification5;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.PersonIdentificationSchemeName1Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.Purpose2Choice;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.RemittanceInformation5;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.SequenceType1Code;
import org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation.ServiceLevel8Choice;
import org.cuacfm.members.web.support.DisplayDate;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Representa un fichero especificado por el CSB19 para la realización de adeudos por domiciliación en soporte magnético.
 */
public class BankRemittanceSEPAXML {

	// The max decimals in SEPA
	private static final int DECIMALS = 5;
	
	private ObjectFactory factory = new ObjectFactory();
	private MessageSource messageSource;
	private int totalReceiptsFile;
	private BigDecimal totalImportFile;
	private int totalReceiptsDebtor;
	private BigDecimal totalImportDebtor;

	/**
	 * Instantiates a new bank remittance SEPAXML.
	 */
	public BankRemittanceSEPAXML() {
		super();
	}

	/**
	 * Instantiates a new Bank Remittance SEPAXML.
	 *
	 * @param path the path
	 * @param messageSource the message source
	 * @param bankRemittance the bank remittance
	 * @param directDebits the direct debits
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws DatatypeConfigurationException the datatype configuration exception
	 */

	public BankRemittanceSEPAXML(String path, MessageSource messageSource, BankRemittance bankRemittance, List<DirectDebit> directDebits)
			throws FileNotFoundException, IOException, JAXBException, DatatypeConfigurationException {

		this.messageSource = messageSource;
		FileOutputStream file = new FileOutputStream(path);

		JAXBElement<Document> element = factory.createDocument(generateDocument(bankRemittance, directDebits));
		JAXBContext context = JAXBContext.newInstance("org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation");
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
		marshaller.marshal(element, file);

		file.close();
	}

	/**
	 * Generate group header.
	 *
	 * @return the group header 39
	 * @throws DatatypeConfigurationException the datatype configuration exception
	 */
	// Cabecera
	private GroupHeader39 generateGroupHeader() throws DatatypeConfigurationException {

		GroupHeader39 groupHeader = factory.createGroupHeader39();
		groupHeader.setMsgId("CUACFM" + DisplayDate.format(new Date(), "yyyyMMdd"));
		GregorianCalendar today = new GregorianCalendar();
		XMLGregorianCalendar todayXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(today);
		todayXML.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		todayXML.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		groupHeader.setCreDtTm(todayXML);
		groupHeader.setNbOfTxs(Integer.toString(totalReceiptsFile));
		groupHeader.setCtrlSum(totalImportFile.setScale(DECIMALS, RoundingMode.HALF_UP));
		groupHeader.setInitgPty(generatePresentadorPartyIdentification());

		return groupHeader;
	}

	/**
	 * Generate presenter party identification.
	 *
	 * @return the party identification 32
	 */
	private PartyIdentification32 generatePresentadorPartyIdentification() {
		GenericOrganisationIdentification1 otherorginfo = factory.createGenericOrganisationIdentification1();
		// The suffix is necessary for SEPA
		otherorginfo.setId(BankRemittanceUtils.calculateSEPACreditorID(getValue("country"), getValue("suffix"), getValue("presenterNif")));

		OrganisationIdentificationSchemeName1Choice schemeOrganization = factory.createOrganisationIdentificationSchemeName1Choice();
		schemeOrganization.setCd("COR1");
		otherorginfo.setSchmeNm(schemeOrganization);

		OrganisationIdentification4 organizationPresenter = factory.createOrganisationIdentification4();
		List<GenericOrganisationIdentification1> listotherorganization = organizationPresenter.getOthr();
		listotherorganization.add(otherorginfo);

		Party6Choice partyChoicePresenter = factory.createParty6Choice();
		partyChoicePresenter.setOrgId(organizationPresenter);

		PartyIdentification32 partyIdentification = factory.createPartyIdentification32();
		partyIdentification.setNm(getValue("presenterName"));
		partyIdentification.setId(partyChoicePresenter);
		return partyIdentification;
	}

	/**
	 * Generate debtor() party identification.
	 *
	 * @param directDebit the direct debit
	 * @return the party identification 32
	 */
	// Cabecera del ordenante, debtors
	private PartyIdentification32 generateOrdenantePartyIdentification(DirectDebit directDebit) {

		// CuacFM, nombre de cuacfm
		PartyIdentification32 partyIdentification = factory.createPartyIdentification32();
		partyIdentification.setNm(directDebit.getAccount().getName());
		return partyIdentification;
	}

	/**
	 * Generate ordenante domiciliacion party identification.
	 *
	 * @param directDebit the direct debit
	 * @return the party identification 32
	 */
	//Datos del  presenter, creditor
	private PartyIdentification32 generateOrdenanteDomiciliacionPartyIdentification(DirectDebit directDebit) {
		GenericPersonIdentification1 otherpersoninfo = factory.createGenericPersonIdentification1();

		// The suffix is necessary for SEPA
		otherpersoninfo.setId(BankRemittanceUtils.calculateSEPACreditorID(getValue("country"), getValue("suffix"), getValue("presenterNif")));

		// field 04: Referencia del adeudo (AT-10), 35 ch.
		String debtorId = String.valueOf(directDebit.getAccount().getId());
		otherpersoninfo.setId(String.format("%1$-35s", debtorId));

		PersonIdentificationSchemeName1Choice schemePerson = factory.createPersonIdentificationSchemeName1Choice();
		schemePerson.setPrtry("SEPA");
		otherpersoninfo.setSchmeNm(schemePerson);

		PersonIdentification5 personIdentification = factory.createPersonIdentification5();
		List<GenericPersonIdentification1> listOtherPersonIdentification = personIdentification.getOthr();
		listOtherPersonIdentification.add(otherpersoninfo);

		Party6Choice partyChoicePresenter = factory.createParty6Choice();

		partyChoicePresenter.setPrvtId(personIdentification);

		PartyIdentification32 partyIdentification = factory.createPartyIdentification32();
		partyIdentification.setId(partyChoicePresenter);
		return partyIdentification;
	}

	/**
	 * Presenter, Fill data payment intructions information.
	 *
	 * @param paymentInstructionsInformation the payment instructions information
	 * @param directDebit the direct debit
	 * @param bankRemittance the bank remittance
	 * @return the payment instruction information 4
	 * @throws DatatypeConfigurationException the datatype configuration exception
	 */
	private PaymentInstructionInformation4 fillDataPaymentIntructionsInformation(PaymentInstructionInformation4 paymentInstructionsInformation,
			DirectDebit directDebit, BankRemittance bankRemittance) throws DatatypeConfigurationException {

		// Identificación de información de pago
		// paymentInstructionsInformation.setPmtInfId(fileId + "-" + ordenante.getSepaSeqType());
		paymentInstructionsInformation.setPmtInfId(directDebit.getId());

		paymentInstructionsInformation.setPmtMtd(PaymentMethod2Code.DD);
		paymentInstructionsInformation.setNbOfTxs(Integer.toString(totalReceiptsDebtor));
		paymentInstructionsInformation.setCtrlSum(totalImportDebtor.setScale(DECIMALS, RoundingMode.HALF_UP));

		PaymentTypeInformation20 paymentTypeInformation = factory.createPaymentTypeInformation20();

		ServiceLevel8Choice serviceLevel = factory.createServiceLevel8Choice();
		serviceLevel.setCd("SEPA");

		paymentTypeInformation.setSvcLvl(serviceLevel);

		LocalInstrument2Choice localInstrument = factory.createLocalInstrument2Choice();
		localInstrument.setCd("COR1");
		paymentTypeInformation.setLclInstrm(localInstrument);
		//FRST(first) o RCUR(Recursive)
		paymentTypeInformation.setSeqTp(SequenceType1Code.fromValue(directDebit.getSecuence()));

		CategoryPurpose1Choice categoryPurpose = factory.createCategoryPurpose1Choice();
		categoryPurpose.setCd(getValue("debtorCategory"));
		paymentTypeInformation.setCtgyPurp(categoryPurpose);
		paymentInstructionsInformation.setPmtTpInf(paymentTypeInformation);

		GregorianCalendar fechaCobro = new GregorianCalendar();
		fechaCobro.setTime(bankRemittance.getDateCharge());

		// ReqdColltnDt: Convert Date to gregorian Calendar
		paymentInstructionsInformation.setReqdColltnDt(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(fechaCobro.get(Calendar.YEAR),
				fechaCobro.get(Calendar.MONTH) + 1, fechaCobro.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED));

		//Head, Cabecera
		paymentInstructionsInformation.setCdtr(generateOrdenantePartyIdentification(directDebit));

		CashAccount16 cashAccount = factory.createCashAccount16();
		AccountIdentification4Choice accountIdentification = factory.createAccountIdentification4Choice();
		accountIdentification.setIBAN(getValue("creditorIBAN"));
		cashAccount.setId(accountIdentification);
		paymentInstructionsInformation.setCdtrAcct(cashAccount);

		BranchAndFinancialInstitutionIdentification4 financialIdentification = factory.createBranchAndFinancialInstitutionIdentification4();
		FinancialInstitutionIdentification7 financialId = factory.createFinancialInstitutionIdentification7();
		financialId.setBIC(getValue("creditorBIC"));
		financialIdentification.setFinInstnId(financialId);
		paymentInstructionsInformation.setCdtrAgt(financialIdentification);
		paymentInstructionsInformation.setChrgBr(ChargeBearerType1Code.SLEV);

		return paymentInstructionsInformation;
	}

	/**
	 * Generate direct debit information, debtor.
	 *
	 * @param directDebit the direct debit
	 * @return the direct debit transaction information 9
	 * @throws DatatypeConfigurationException the datatype configuration exception
	 */
	private DirectDebitTransactionInformation9 generateDirectDebitInformation(DirectDebit directDebit) throws DatatypeConfigurationException {

		DirectDebitTransactionInformation9 directDebitInformation = factory.createDirectDebitTransactionInformation9();
		PaymentIdentification1 paymentIdentification = factory.createPaymentIdentification1();

		//Campo de 35 caracteres, identificador del pago, para saber lo que se devolvio
		paymentIdentification.setEndToEndId(String.valueOf(directDebit.getId()));

		directDebitInformation.setPmtId(paymentIdentification);

		ActiveOrHistoricCurrencyAndAmount currencyAndAmount = factory.createActiveOrHistoricCurrencyAndAmount();
		currencyAndAmount.setCcy(getValue("currencyAndAmount"));
		BigDecimal importe = new BigDecimal(directDebit.getPrice()).setScale(DECIMALS, RoundingMode.UP);
		// Antes se dividia por 100 ¿Por que se divide?
		currencyAndAmount.setValue(importe.divide(new BigDecimal(100)).setScale(DECIMALS, RoundingMode.HALF_UP));
		directDebitInformation.setInstdAmt(currencyAndAmount);

		DirectDebitTransaction6 directDebitTransaction = factory.createDirectDebitTransaction6();

		MandateRelatedInformation6 mandateInformation = factory.createMandateRelatedInformation6();
		mandateInformation.setMndtId(directDebit.getAccount().activeBankAccount().getMandate());
		// Date Mandante
		GregorianCalendar fechaFirmaMandato = new GregorianCalendar();
		fechaFirmaMandato.setTime(directDebit.getAccount().activeBankAccount().getDateMandate());

		mandateInformation.setDtOfSgntr(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(fechaFirmaMandato.get(Calendar.YEAR),
				fechaFirmaMandato.get(Calendar.MONTH) + 1, fechaFirmaMandato.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED));

		directDebitTransaction.setMndtRltdInf(mandateInformation);
		directDebitTransaction.setCdtrSchmeId(generateOrdenanteDomiciliacionPartyIdentification(directDebit));
		directDebitInformation.setDrctDbtTx(directDebitTransaction);

		BranchAndFinancialInstitutionIdentification4 financialIdentification = factory.createBranchAndFinancialInstitutionIdentification4();
		FinancialInstitutionIdentification7 financialId = factory.createFinancialInstitutionIdentification7();

		// BIC, si esta vacio poner notprovided
		if (directDebit.getAccount().activeBankAccount().getBic() != null || !directDebit.getAccount().activeBankAccount().getBic().isEmpty()) {
			financialId.setBIC(directDebit.getAccount().activeBankAccount().getBic());
		} else {
			financialId.setBIC("NOTPROVIDED");
		}

		financialIdentification.setFinInstnId(financialId);
		directDebitInformation.setDbtrAgt(financialIdentification);
		PartyIdentification32 partyDeudor = factory.createPartyIdentification32();
		partyDeudor.setNm(directDebit.getAccount().getName());
		directDebitInformation.setDbtr(partyDeudor);

		CashAccount16 cashAccount = factory.createCashAccount16();
		AccountIdentification4Choice accountIdentification = factory.createAccountIdentification4Choice();
		accountIdentification.setIBAN(directDebit.getAccount().activeBankAccount().getIban());
		cashAccount.setId(accountIdentification);
		directDebitInformation.setDbtrAcct(cashAccount);

		Purpose2Choice purpose = factory.createPurpose2Choice();
		purpose.setCd(getValue("debtorPurpose"));
		directDebitInformation.setPurp(purpose);

		RemittanceInformation5 informacionConcepto = factory.createRemittanceInformation5();
		informacionConcepto.getUstrd().add(directDebit.getConcept());
		directDebitInformation.setRmtInf(informacionConcepto);

		return directDebitInformation;
	}

	/**
	 * Generate document.
	 *
	 * @param bankRemittance the bank remittance
	 * @param directDebits the direct debits
	 * @return the document
	 * @throws DatatypeConfigurationException the datatype configuration exception
	 */
	private Document generateDocument(BankRemittance bankRemittance, List<DirectDebit> directDebits) throws DatatypeConfigurationException {

		totalReceiptsFile = 0;
		totalImportFile = new BigDecimal("0.00");

		CustomerDirectDebitInitiationV02 customerDirectDebitInitiation = factory.createCustomerDirectDebitInitiationV02();
		List<PaymentInstructionInformation4> listadoOrdenantesRemesa = customerDirectDebitInitiation.getPmtInf();

		// Una remesa tiene pagos directos, cada pago engloba varias cuaotas de un mismo usuario
		for (DirectDebit directDebit : directDebits) {
			totalReceiptsDebtor = 0;
			totalImportDebtor = new BigDecimal("0.00");
			PaymentInstructionInformation4 paymentInstructionsInformation = factory.createPaymentInstructionInformation4();
			List<DirectDebitTransactionInformation9> listadoAdeudosOrdenante = paymentInstructionsInformation.getDrctDbtTxInf();

			// Collection<Domiciliacion> domiciliaciones = domiciliacionesOrdenante.get(clienteOrdenante);
			// for (Domiciliacion domiciliacion : domiciliaciones) {

			DirectDebitTransactionInformation9 directDebitInformation = generateDirectDebitInformation(directDebit);
			listadoAdeudosOrdenante.add(directDebitInformation);

			totalReceiptsDebtor++;
			BigDecimal importe = new BigDecimal(directDebit.getPrice());
			totalImportDebtor = totalImportDebtor.add(importe.divide(new BigDecimal(100)).setScale(DECIMALS, RoundingMode.HALF_UP));

			// }

			fillDataPaymentIntructionsInformation(paymentInstructionsInformation, directDebit, bankRemittance);
			listadoOrdenantesRemesa.add(paymentInstructionsInformation);

			totalReceiptsFile += totalReceiptsDebtor;
			totalImportFile = totalImportFile.add(totalImportDebtor.setScale(DECIMALS, RoundingMode.HALF_UP));
		}

		customerDirectDebitInitiation.setGrpHdr(generateGroupHeader());

		Document doc = factory.createDocument();
		doc.setCstmrDrctDbtInitn(customerDirectDebitInitiation);

		return doc;
	}

	/**
	 * View txt.
	 *
	 * @param path the path
	 * @param file the file
	 * @param mediaType the media type
	 * @return the response entity
	 */
	public static ResponseEntity<byte[]> downloadFile(String path, String file, MediaType mediaType) {
		Path pathAux = Paths.get(path);
		byte[] contents = null;
		try {
			contents = Files.readAllBytes(pathAux);
		} catch (IOException e) {
			e.getMessage();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{file}").buildAndExpand(file).toUri());
		headers.add("content-disposition", "attachment; filename=" + file + ";");
		return new ResponseEntity<>(contents, headers, HttpStatus.OK);
	}

	/**
	 * Gets the value of I18N.
	 *
	 * @param code the code
	 * @return the value
	 */
	private String getValue(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

}
