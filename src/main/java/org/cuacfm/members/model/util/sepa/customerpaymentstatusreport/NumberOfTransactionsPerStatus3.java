//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 12:46:36 PM CET 
//


package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para NumberOfTransactionsPerStatus3 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="NumberOfTransactionsPerStatus3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DtldNbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max15NumericText"/>
 *         &lt;element name="DtldSts" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}TransactionIndividualStatus3Code"/>
 *         &lt;element name="DtldCtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}DecimalNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberOfTransactionsPerStatus3", propOrder = {
    "dtldNbOfTxs",
    "dtldSts",
    "dtldCtrlSum"
})
public class NumberOfTransactionsPerStatus3 {

    @XmlElement(name = "DtldNbOfTxs", required = true)
    protected String dtldNbOfTxs;
    @XmlElement(name = "DtldSts", required = true)
    @XmlSchemaType(name = "string")
    protected TransactionIndividualStatus3Code dtldSts;
    @XmlElement(name = "DtldCtrlSum")
    protected BigDecimal dtldCtrlSum;

    /**
     * Obtiene el valor de la propiedad dtldNbOfTxs.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtldNbOfTxs() {
        return dtldNbOfTxs;
    }

    /**
     * Define el valor de la propiedad dtldNbOfTxs.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtldNbOfTxs(String value) {
        this.dtldNbOfTxs = value;
    }

    /**
     * Obtiene el valor de la propiedad dtldSts.
     * 
     * @return
     *     possible object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public TransactionIndividualStatus3Code getDtldSts() {
        return dtldSts;
    }

    /**
     * Define el valor de la propiedad dtldSts.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public void setDtldSts(TransactionIndividualStatus3Code value) {
        this.dtldSts = value;
    }

    /**
     * Obtiene el valor de la propiedad dtldCtrlSum.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDtldCtrlSum() {
        return dtldCtrlSum;
    }

    /**
     * Define el valor de la propiedad dtldCtrlSum.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDtldCtrlSum(BigDecimal value) {
        this.dtldCtrlSum = value;
    }

}
