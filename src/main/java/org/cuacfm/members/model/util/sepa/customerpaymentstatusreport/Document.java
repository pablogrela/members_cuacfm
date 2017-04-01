//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 11:40:20 AM CET 
//

package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Document complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Document">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CstmrPmtStsRpt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CustomerPaymentStatusReportV03"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "cstmrPmtStsRpt"
})
public class Document {

    @XmlElement(name = "CstmrPmtStsRpt", required = true)
    protected CustomerPaymentStatusReportV03 cstmrPmtStsRpt;

    /**
     * Obtiene el valor de la propiedad cstmrPmtStsRpt.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPaymentStatusReportV03 }
     *     
     */
    public CustomerPaymentStatusReportV03 getCstmrPmtStsRpt() {
        return cstmrPmtStsRpt;
    }

    /**
     * Define el valor de la propiedad cstmrPmtStsRpt.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPaymentStatusReportV03 }
     *     
     */
    public void setCstmrPmtStsRpt(CustomerPaymentStatusReportV03 value) {
        this.cstmrPmtStsRpt = value;
    }

}
