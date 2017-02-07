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

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.cuacfm.members.model.directdebit.DirectDebit;
import org.cuacfm.members.model.directdebit.ReturnReason;
import org.cuacfm.members.model.directdebit.ReturnReasonRepository;
import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.exceptions.ExistTransactionIdException;
import org.cuacfm.members.model.util.Constants.methods;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.sepa.customerpaymentstatusreport.Document;
import org.cuacfm.members.model.util.sepa.customerpaymentstatusreport.OriginalPaymentInformation1;
import org.cuacfm.members.model.util.sepa.customerpaymentstatusreport.PaymentTransactionInformation25;
import org.cuacfm.members.model.util.sepa.customerpaymentstatusreport.StatusReasonInformation8;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Representa un fichero especificado por el CSB19 para la realización de adeudos por domiciliación en soporte magnético.
 */
public class ReturnBankRemittanceSEPAXML {

	@Autowired
	private ReturnReasonRepository returnReasonRepository;

	@Autowired
	private DirectDebitService directDebitService;

	/**
	 * Constructor de clase.
	 *
	 * @param nameFile the name file
	 * @throws JAXBException the JAXB exception
	 * @throws ExistTransactionIdException
	 */
	public ReturnBankRemittanceSEPAXML(String nameFile) throws Exception {

		JAXBContext context = JAXBContext.newInstance("org.cuacfm.members.model.util.sepa.customerpaymentstatusreport");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<Document> element = unmarshaller.unmarshal(new StreamSource(nameFile), Document.class);
		parse(element.getValue());
	}

	/**
	 * Escribe las domiciliaciones y clientes a los que representa en el fichero con path nombreFichero en el formato especificado por el CSB19.
	 *
	 * @param document the document
	 * @throws ExistTransactionIdException
	 */
	private void parse(Document document) throws Exception {

		// _domiciliacionesDevueltas = new ArrayList<Domiciliacion>();

		List<OriginalPaymentInformation1> listPaymentsInformation = document.getCstmrPmtStsRpt().getOrgnlPmtInfAndSts();
		for (OriginalPaymentInformation1 paymentInformation : listPaymentsInformation) {
			List<PaymentTransactionInformation25> listTxInfAndSts = paymentInformation.getTxInfAndSts();

			for (PaymentTransactionInformation25 paymentTransaction : listTxInfAndSts) {
				// ClienteOrdenante ordenante = null;
				// String codigoReferencia = paymentTransaction.getOrgnlTxRef().getMndtRltdInf().getMndtId();
				// String titularDomiciliacion = paymentTransaction.getOrgnlTxRef().getDbtr().getNm();
				// String numeroCuenta = paymentTransaction.getOrgnlTxRef().getDbtrAcct().getId().getIBAN();
				// int importe = paymentTransaction.getOrgnlTxRef().getAmt().getInstdAmt().getValue().scaleByPowerOfTen(2).intValueExact();
				// String concepto = "";
				// for (String tmpstr : paymentTransaction.getOrgnlTxRef().getRmtInf().getUstrd()) {
				// concepto += tmpstr;
				// }

				String directDebitId = paymentTransaction.getOrgnlEndToEndId();
				ReturnReason returnReason = null;
				List<StatusReasonInformation8> stsRsnInf = paymentTransaction.getStsRsnInf();

				for (StatusReasonInformation8 motivosDevolucion : stsRsnInf) {
					String codiReturnReason = motivosDevolucion.getRsn().getCd();
					returnReason = returnReasonRepository.findById(codiReturnReason);
					if (returnReason == null) {
						returnReasonRepository.save(new ReturnReason(codiReturnReason, codiReturnReason));
					}
				}

				DirectDebit directDebit = directDebitService.findById(directDebitId);
				directDebit.setReturnReason(returnReason);
				directDebitService.updateDirectDebit(directDebit, states.RETURN_BILL, methods.DIRECTDEBIT, null);
			}
		}

	}

	//	public Collection<Domiciliacion> getDomiciliacionesDevueltas() {
	//		return _domiciliacionesDevueltas;
	//	}

}
