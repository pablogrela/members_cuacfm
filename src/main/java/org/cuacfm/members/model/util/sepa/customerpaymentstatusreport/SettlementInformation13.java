//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 12:46:36 PM CET 
//


package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SettlementInformation13 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SettlementInformation13">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SttlmMtd" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}SettlementMethod1Code"/>
 *         &lt;element name="SttlmAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16" minOccurs="0"/>
 *         &lt;element name="ClrSys" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ClearingSystemIdentification3Choice" minOccurs="0"/>
 *         &lt;element name="InstgRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *         &lt;element name="InstgRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16" minOccurs="0"/>
 *         &lt;element name="InstdRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *         &lt;element name="InstdRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16" minOccurs="0"/>
 *         &lt;element name="ThrdRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *         &lt;element name="ThrdRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CashAccount16" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementInformation13", propOrder = {
    "sttlmMtd",
    "sttlmAcct",
    "clrSys",
    "instgRmbrsmntAgt",
    "instgRmbrsmntAgtAcct",
    "instdRmbrsmntAgt",
    "instdRmbrsmntAgtAcct",
    "thrdRmbrsmntAgt",
    "thrdRmbrsmntAgtAcct"
})
public class SettlementInformation13 {

    @XmlElement(name = "SttlmMtd", required = true)
    @XmlSchemaType(name = "string")
    protected SettlementMethod1Code sttlmMtd;
    @XmlElement(name = "SttlmAcct")
    protected CashAccount16 sttlmAcct;
    @XmlElement(name = "ClrSys")
    protected ClearingSystemIdentification3Choice clrSys;
    @XmlElement(name = "InstgRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification4 instgRmbrsmntAgt;
    @XmlElement(name = "InstgRmbrsmntAgtAcct")
    protected CashAccount16 instgRmbrsmntAgtAcct;
    @XmlElement(name = "InstdRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification4 instdRmbrsmntAgt;
    @XmlElement(name = "InstdRmbrsmntAgtAcct")
    protected CashAccount16 instdRmbrsmntAgtAcct;
    @XmlElement(name = "ThrdRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification4 thrdRmbrsmntAgt;
    @XmlElement(name = "ThrdRmbrsmntAgtAcct")
    protected CashAccount16 thrdRmbrsmntAgtAcct;

    /**
     * Obtiene el valor de la propiedad sttlmMtd.
     * 
     * @return
     *     possible object is
     *     {@link SettlementMethod1Code }
     *     
     */
    public SettlementMethod1Code getSttlmMtd() {
        return sttlmMtd;
    }

    /**
     * Define el valor de la propiedad sttlmMtd.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementMethod1Code }
     *     
     */
    public void setSttlmMtd(SettlementMethod1Code value) {
        this.sttlmMtd = value;
    }

    /**
     * Obtiene el valor de la propiedad sttlmAcct.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getSttlmAcct() {
        return sttlmAcct;
    }

    /**
     * Define el valor de la propiedad sttlmAcct.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setSttlmAcct(CashAccount16 value) {
        this.sttlmAcct = value;
    }

    /**
     * Obtiene el valor de la propiedad clrSys.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemIdentification3Choice }
     *     
     */
    public ClearingSystemIdentification3Choice getClrSys() {
        return clrSys;
    }

    /**
     * Define el valor de la propiedad clrSys.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemIdentification3Choice }
     *     
     */
    public void setClrSys(ClearingSystemIdentification3Choice value) {
        this.clrSys = value;
    }

    /**
     * Obtiene el valor de la propiedad instgRmbrsmntAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getInstgRmbrsmntAgt() {
        return instgRmbrsmntAgt;
    }

    /**
     * Define el valor de la propiedad instgRmbrsmntAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setInstgRmbrsmntAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.instgRmbrsmntAgt = value;
    }

    /**
     * Obtiene el valor de la propiedad instgRmbrsmntAgtAcct.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getInstgRmbrsmntAgtAcct() {
        return instgRmbrsmntAgtAcct;
    }

    /**
     * Define el valor de la propiedad instgRmbrsmntAgtAcct.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setInstgRmbrsmntAgtAcct(CashAccount16 value) {
        this.instgRmbrsmntAgtAcct = value;
    }

    /**
     * Obtiene el valor de la propiedad instdRmbrsmntAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getInstdRmbrsmntAgt() {
        return instdRmbrsmntAgt;
    }

    /**
     * Define el valor de la propiedad instdRmbrsmntAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setInstdRmbrsmntAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.instdRmbrsmntAgt = value;
    }

    /**
     * Obtiene el valor de la propiedad instdRmbrsmntAgtAcct.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getInstdRmbrsmntAgtAcct() {
        return instdRmbrsmntAgtAcct;
    }

    /**
     * Define el valor de la propiedad instdRmbrsmntAgtAcct.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setInstdRmbrsmntAgtAcct(CashAccount16 value) {
        this.instdRmbrsmntAgtAcct = value;
    }

    /**
     * Obtiene el valor de la propiedad thrdRmbrsmntAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getThrdRmbrsmntAgt() {
        return thrdRmbrsmntAgt;
    }

    /**
     * Define el valor de la propiedad thrdRmbrsmntAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setThrdRmbrsmntAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.thrdRmbrsmntAgt = value;
    }

    /**
     * Obtiene el valor de la propiedad thrdRmbrsmntAgtAcct.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getThrdRmbrsmntAgtAcct() {
        return thrdRmbrsmntAgtAcct;
    }

    /**
     * Define el valor de la propiedad thrdRmbrsmntAgtAcct.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setThrdRmbrsmntAgtAcct(CashAccount16 value) {
        this.thrdRmbrsmntAgtAcct = value;
    }

}
