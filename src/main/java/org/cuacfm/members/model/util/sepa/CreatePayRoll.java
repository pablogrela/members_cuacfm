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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.bankaccount.BankAccount;
import org.cuacfm.members.model.bankremittance.BankRemittance;
import org.cuacfm.members.model.bankremittanceservice.BankRemittanceService;
import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// TODO, ahora no se usa, posiblemente lo haya que borrar cuando se valide el XML
/** The Class CreatePayRoll. */
public class CreatePayRoll {

	private static final Logger logger = LoggerFactory.getLogger(BankRemittanceService.class);

	/** The Constant NOTEBOOK_VERSION. */
	private static final String NOTEBOOK_VERSION = "19143";

	/** Instantiates a new creates the pay roll. */
	protected CreatePayRoll() {
		// Default empty constructor.
	}

	/**
	 * Creates the pay roll.
	 *
	 * @param path the path
	 * @param messageSource the message source
	 * @param bankRemittance the bank remittance
	 * @param directDebits the direct debits
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public CreatePayRoll(String path, MessageSource messageSource, BankRemittance bankRemittance, List<DirectDebit> directDebits) throws IOException {

		File archivo = new File(path);
		BufferedWriter bw;

		String dateChargeFormat = DateUtils.format(bankRemittance.getDateCharge(), DateUtils.FORMAT_DATE_DIRECTDEBIT);
		String dateDebitFormat = DateUtils.format(bankRemittance.getDateDebit(), DateUtils.FORMAT_DATE_DIRECTDEBIT);
		bw = new BufferedWriter(new FileWriter(archivo));

		// Line 1, Head Presenter
		String result = createPresenter(messageSource);

		// Line 2, Head Creditor
		result = result + createCreditor(messageSource, dateChargeFormat);

		Double totalPrice = Double.valueOf(0);
		int totalDebtors = 0;

		// Line 3 and next, debtors
		for (DirectDebit directDebit : directDebits) {
			result = result + createDebtor(messageSource, dateDebitFormat, directDebit);
			totalPrice = totalPrice + directDebit.getPrice();
			totalDebtors = totalDebtors + 1;
		}

		// Normalize total price
		int integer = totalPrice.intValue();
		String decimal = String.valueOf(totalPrice - integer).substring(2);

		if (decimal.length() < 2) {
			decimal = decimal + 0;
		}
		if (decimal.length() > 2) {
			decimal.subSequence(0, 2);
		}
		String totalPriceString = integer + decimal;
		totalPriceString = String.format("%1$-11s", totalPriceString);

		// Final Lines
		// REGISTROS DE TOTALES DE ACREEDOR POR FECHA DE COBRO
		int totalRegisters = totalDebtors + 2;
		result = result + createCreditorTotalRegisterByDateCharge(messageSource, dateChargeFormat, totalDebtors, totalRegisters, totalPriceString);

		// REGISTROS DE TOTALES DE ACREEDOR, en este caso el mismo que el anterior
		totalRegisters = totalRegisters + 1;
		result = result + createCreditorTotalRegister(messageSource, totalDebtors, totalRegisters, totalPriceString);

		// REGISTRO DE TOTALES GENERAL
		totalRegisters = totalRegisters + 2;
		result = result + createTotalRegisterGeneral(totalDebtors, totalRegisters, totalPriceString);

		// Replace Forbidden Characters
		result = result.replace("ñ", "n");
		result = result.replace("Ñ", "N");
		result = result.replace("[", "(");
		result = result.replace("[", ")");

		bw.write(result);
		bw.close();
	}

	/**
	 * View txt.
	 *
	 * @param path the path
	 * @param file the file
	 * @return the response entity
	 */
	public static ResponseEntity<byte[]> viewTxt(String path, String file) {
		Path path2 = Paths.get(path);
		byte[] contents = null;
		try {
			contents = Files.readAllBytes(path2);
		} catch (IOException e) {
			logger.error("viewTxt: ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{file}").buildAndExpand(file).toUri());
		// Download PDF
		// headers.setContentDispositionFormData(pathForm.getFile(),
		// pathForm.getFile())
		// View PDF
		headers.add("content-disposition", "attachment; filename=" + file + ";");
		return new ResponseEntity<>(contents, headers, HttpStatus.OK);
	}

	/**
	 * Creates the presenter.
	 *
	 * @param messageSource the message source
	 * @return the string
	 */
	public String createPresenter(MessageSource messageSource) {
		// field 01: Cabecera del presentador
		String headPresenter;

		// fiel 01: Codigo de registro.
		headPresenter = "01";

		// field 02: Versión del cuaderno
		headPresenter = headPresenter + NOTEBOOK_VERSION;

		// field 03: Número de dato
		headPresenter = headPresenter + "001";

		// field 04: Id del presentador, 35 ch,
		String presenterId = messageSource.getMessage("presenterId", null, Locale.getDefault());
		presenterId = String.format("%1$-35s", presenterId);
		headPresenter = headPresenter + presenterId;

		// field 05: Nombre del presentador,70 ch, cuacFM
		String presenterName = messageSource.getMessage("presenterName", null, Locale.getDefault());
		presenterName = String.format("%1$-70s", presenterName);
		headPresenter = headPresenter + presenterName;

		// field 06: Fecha de creación del fichero: en formato AAAAMMDD, 8ch
		String date = DateUtils.format(DateUtils.FORMAT_DATE_DIRECTDEBIT);
		headPresenter = headPresenter + date;

		// Time = HHMMSSmmmmm (hora minuto segundo y 5 posiciones de
		// milisegundos = 11 caracteres)
		String time = DateUtils.format(DateUtils.FORMAT_TIME_DIRECTDEBIT);

		// field 07: Identificación del fichero, 35 ch
		String presenterIdRef = messageSource.getMessage("presenterIdRef", null, Locale.getDefault());
		headPresenter = headPresenter + "PRE" + date + time + presenterIdRef;

		// field 08: entidad receptora
		String nrbe = messageSource.getMessage("nrbe", null, Locale.getDefault());
		headPresenter = headPresenter + nrbe;

		// field 09: oficina receptora
		String office = messageSource.getMessage("office", null, Locale.getDefault());
		headPresenter = headPresenter + office;

		// field 10: libre
		headPresenter = String.format("%1$-600s", headPresenter);

		return headPresenter + "\n";
	}

	/**
	 * Creates the creditor.
	 *
	 * @param messageSource the message source
	 * @param dateCharge the date charge
	 * @return the string
	 */
	public static String createCreditor(MessageSource messageSource, String dateCharge) {
		String headCreditor;

		// field 01: Codigo de registro.
		headCreditor = "02";

		// field 02: Versión del cuaderno
		headCreditor = headCreditor + NOTEBOOK_VERSION;

		// field 03: Número de dato
		headCreditor = headCreditor + "003";

		// field 04: Identificador del acreedor (AT-02), 35 ch.
		String credatorId = messageSource.getMessage("creditorId", null, Locale.getDefault());
		credatorId = String.format("%1$-35s", credatorId);
		headCreditor = headCreditor + credatorId;

		// field 05: Fecha de cobro pasar en el metodo.
		headCreditor = headCreditor + dateCharge;

		// field 06: Nombre del acreedor, 70 ch, cuacFM
		String creditorName = messageSource.getMessage("creditorName", null, Locale.getDefault());
		creditorName = String.format("%1$-70s", creditorName);
		headCreditor = headCreditor + creditorName;

		// field 07: via, número de via, 50 ch
		String creditorAddress = messageSource.getMessage("creditorAddress", null, Locale.getDefault());
		creditorAddress = String.format("%1$-50s", creditorAddress);
		headCreditor = headCreditor + creditorAddress;

		// field 08: CP y localidad, 50 ch
		String creditorCP = messageSource.getMessage("creditorCP", null, Locale.getDefault());
		creditorCP = String.format("%1$-50s", creditorCP);
		headCreditor = headCreditor + creditorCP;

		// field 09: Provincia, 40 ch
		String creditorProvince = messageSource.getMessage("creditorProvince", null, Locale.getDefault());
		creditorProvince = String.format("%1$-40s", creditorProvince);
		headCreditor = headCreditor + creditorProvince;

		// field 10: Pais, 2 ch
		String creditorCountry = messageSource.getMessage("creditorCountry", null, Locale.getDefault());
		headCreditor = headCreditor + creditorCountry;

		// field 11: iban, 36 ch
		String creditorIBAN = messageSource.getMessage("creditorIBAN", null, Locale.getDefault());
		headCreditor = headCreditor + creditorIBAN;

		// field 10: libre
		headCreditor = String.format("%1$-600s", headCreditor);

		return headCreditor + "\n";
	}

	/**
	 * Creates the debtor.
	 *
	 * @param messageSource the message source
	 * @param dateDebit the date debit
	 * @param directDebit the direct debit
	 * @return the string
	 */
	public String createDebtor(MessageSource messageSource, String dateDebit, DirectDebit directDebit) {

		// Get Bank Account active
		BankAccount bankAccount = directDebit.getAccount().activeBankAccount();

		String debtor;

		// field 01: Codigo de registro.
		debtor = "03";

		// field 02: Versión del cuaderno
		debtor = debtor + NOTEBOOK_VERSION;

		// field 03: Número de dato
		debtor = debtor + "003";

		// field 04: Referencia del adeudo (AT-10), 35 ch.
		String debtorId = String.valueOf(directDebit.getAccount().getId());
		debtorId = String.format("%1$-35s", debtorId);
		debtor = debtor + debtorId;

		// field 05: Referencia única del mandato (AT-01), 35 ch.
		String debtorMandate = directDebit.getAccount().activeBankAccount().getMandate();
		debtorMandate = String.format("%1$-35s", debtorMandate);
		debtor = debtor + debtorMandate;

		// field 06: Secuencia del adeudo (AT-21), 4 ch.
		String debtorSecuence = directDebit.getSecuence();
		debtor = debtor + debtorSecuence;

		// field 07: Categoría de propósito (AT-59), 4 ch, FNAL or RCUR.
		String debtorCategory = messageSource.getMessage("debtorCategory", null, Locale.getDefault());
		debtor = debtor + debtorCategory;

		// field 08: Importe del adeudo (AT-06), 11 ch. las dos finales los
		// decimales
		int integer = directDebit.getPrice().intValue();
		String decimal = String.valueOf(directDebit.getPrice() - integer).substring(2);

		if (decimal.length() < 2) {
			decimal = decimal + 0;
		}
		if (decimal.length() > 2) {
			decimal.subSequence(0, 2);
		}
		String debtorPrice = integer + decimal;
		debtorPrice = String.format("%1$-11s", debtorPrice);
		debtor = debtor + debtorPrice;

		// field 09: Fecha de firma del mandato (AT-25), 8ch.
		debtor = debtor + dateDebit;

		// field 10: Entidad del deudor (AT-13), 11ch.
		String debtorBic = bankAccount.getBic();
		debtorBic = String.format("%1$-50s", debtorBic);
		debtor = debtor + debtorBic;

		// field 11: Nombre del deudor (AT-14), 50.
		String debtorName = directDebit.getAccount().getName();
		debtorName = String.format("%1$-50s", debtorName);
		debtor = debtor + debtorName;

		// field 12: via, número de via, 50 ch.
		String debtorAddress = directDebit.getAccount().getAddress();
		debtorAddress = String.format("%1$-50s", debtorAddress);
		debtor = debtor + debtorAddress;

		// field 13: CP y localidad, 50 ch.
		String debtorCP = directDebit.getAccount().getCp();
		debtorCP = String.format("%1$-50s", debtorCP);
		debtor = debtor + debtorCP;

		// field 14: Provincia, 40 ch.
		String debtorProvince = directDebit.getAccount().getProvince();
		debtorProvince = String.format("%1$-40s", debtorProvince);
		debtor = debtor + debtorProvince;

		// field 15: Pais, 2 ch
		String debtorCountry = directDebit.getAccount().getCodeCountry();
		debtor = debtor + debtorCountry;
		// field 16: Tipo de Identificación del deudor, 1 ch.
		// 1 – Organización
		// 2 – Persona
		String type = "2";
		if ((directDebit.getAccount().getAccountType() != null) && (directDebit.getAccount().getAccountType().isOrganization())) {
			type = "1";
		}
		debtor = debtor + type;

		// field 17: Identificación del deudor (Código)–(AT-27)= Código de
		// identificación como organización o persona, 36 ch.
		// A seguido de 35 posiciones = Código BIC – Organización
		// J seguido de 35 posiciones = NIF or NIE – Persona
		String debtorIdentification = "B";
		if ((directDebit.getAccount().getAccountType() != null) && (directDebit.getAccount().getAccountType().isOrganization())) {
			debtorIdentification = "A" + directDebit.getAccount().getDni();

		}

		debtorIdentification = debtorIdentification + directDebit.getAccount().getDni();
		debtorIdentification = String.format("%1$-36s", debtorIdentification);
		debtor = debtor + debtorIdentification;

		// field 18: Identificación del deudor emisor código (Otro)–(AT-27), 35
		// ch. opcional
		String debtorIdentificationOptional = "";
		debtorIdentification = String.format("%1$-36s", debtorIdentificationOptional);
		debtor = debtor + debtorIdentificationOptional;

		// field 19: Identificador de la cuenta del deudor, 1 ch. A=IBAN
		debtor = debtor + "A";

		// field 20: iban, 36 ch
		String debtorIBAN = bankAccount.getIban();
		debtor = debtor + debtorIBAN;

		// field 20: Propósito del adeudo (AT-58), 4ch
		String debtorPurpose = messageSource.getMessage("debtorPurpose", null, Locale.getDefault());
		debtor = debtor + debtorPurpose;

		// field 21: Concepto (AT-58), 140ch
		String creditorConcept = directDebit.getConcept();
		debtor = debtor + creditorConcept;

		// field 10: libre
		debtor = String.format("%1$-600s", debtor);

		return debtor + "\n";
	}

	/**
	 * Creates the creditor total register by date charge.
	 *
	 * @param messageSource the message source
	 * @param dateCharge the date charge
	 * @param totalDebtors the total debtors
	 * @param totalRegisters the total registers
	 * @param totalPrice the total price
	 * @return the string
	 */
	public String createCreditorTotalRegisterByDateCharge(MessageSource messageSource, String dateCharge, int totalDebtors, int totalRegisters,
			String totalPrice) {
		String footCreditor;

		// field 01: Código de registro.
		footCreditor = "04";

		// field 02: Identificador del acreedor (AT-02), 35 ch.
		String credatorId = messageSource.getMessage("creditorId", null, Locale.getDefault());
		credatorId = String.format("%1$-35s", credatorId);
		footCreditor = footCreditor + credatorId;

		// field 03: Fecha de cobro pasar en el metodo, 8ch.
		footCreditor = footCreditor + dateCharge;

		// field 04: Total de importes, 17ch.
		footCreditor = footCreditor + totalPrice;

		// field 05: Número de adeudos(obligatorios), 8 ch
		String totalRegisterString = String.format("%1$-8s", String.valueOf(totalDebtors));
		footCreditor = footCreditor + totalRegisterString;

		// field 06: Total de registros + cabezera de acreedor, 10 ch
		totalRegisterString = String.format("%1$-8s", String.valueOf(totalRegisters));
		footCreditor = footCreditor + totalRegisterString;

		// field 07: libre
		footCreditor = String.format("%1$-600s", footCreditor);

		return footCreditor + "\n";
	}

	/**
	 * Creates the creditor total register.
	 *
	 * @param messageSource the message source
	 * @param totalDebtors the total debtors
	 * @param totalRegisters the total registers
	 * @param totalPrice the total price
	 * @return the string
	 */
	public String createCreditorTotalRegister(MessageSource messageSource, int totalDebtors, int totalRegisters, String totalPrice) {
		String footCreditor;

		// field 01: Codigo de registro.
		footCreditor = "05";

		// field 02: Identificador del acreedor (AT-02), 35 ch.
		String credatorId = messageSource.getMessage("creditorId", null, Locale.getDefault());
		credatorId = String.format("%1$-35s", credatorId);
		footCreditor = footCreditor + credatorId;

		// field 03: Total de importes, 17ch.
		footCreditor = footCreditor + totalPrice;

		// field 04: Número de adeudos(obligatorios), 8 ch
		String totalRegisterString = String.format("%1$-8s", String.valueOf(totalDebtors));
		footCreditor = footCreditor + totalRegisterString;

		// field 05: Total de registros + cabezera de acreedor, 10 ch
		totalRegisterString = String.format("%1$-8s", String.valueOf(totalRegisters));
		footCreditor = footCreditor + totalRegisterString;

		// field 06: libre
		footCreditor = String.format("%1$-600s", footCreditor);

		return footCreditor + "\n";
	}

	/**
	 * Creates the total register general.
	 *
	 * @param totalDebtors the total debtors
	 * @param totalRegisters the total registers
	 * @param totalPrice the total price
	 * @return the string
	 */
	public String createTotalRegisterGeneral(int totalDebtors, int totalRegisters, String totalPrice) {
		String footGeneral;

		// field 01: Codigo de registro.
		footGeneral = "99";

		// field 02: Total de importes, 17ch.
		footGeneral = footGeneral + totalPrice;

		// field 03: Número de adeudos(obligatorios), 8 ch
		String totalRegisterString = String.format("%1$-8s", String.valueOf(totalDebtors));
		footGeneral = footGeneral + totalRegisterString;

		// field 04: Total de registros + cabezera de acreedor, 10 ch
		totalRegisterString = String.format("%1$-8s", String.valueOf(totalRegisters));
		footGeneral = footGeneral + totalRegisterString;

		// field 05: libre
		footGeneral = String.format("%1$-600s", footGeneral);

		return footGeneral + "\n";
	}

}