//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 12:46:36 PM CET 
//


package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para PaymentTransactionInformation25 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PaymentTransactionInformation25">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StsId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="OrgnlInstrId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="OrgnlEndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="TxSts" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}TransactionIndividualStatus3Code" minOccurs="0"/>
 *         &lt;element name="StsRsnInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}StatusReasonInformation8" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ChrgsInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ChargesInformation5" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AccptncDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ISODateTime" minOccurs="0"/>
 *         &lt;element name="AcctSvcrRef" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="ClrSysRef" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="OrgnlTxRef" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}OriginalTransactionReference13" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTransactionInformation25", propOrder = {
    "stsId",
    "orgnlInstrId",
    "orgnlEndToEndId",
    "txSts",
    "stsRsnInf",
    "chrgsInf",
    "accptncDtTm",
    "acctSvcrRef",
    "clrSysRef",
    "orgnlTxRef"
})
public class PaymentTransactionInformation25 {

    @XmlElement(name = "StsId")
    protected String stsId;
    @XmlElement(name = "OrgnlInstrId")
    protected String orgnlInstrId;
    @XmlElement(name = "OrgnlEndToEndId")
    protected String orgnlEndToEndId;
    @XmlElement(name = "TxSts")
    @XmlSchemaType(name = "string")
    protected TransactionIndividualStatus3Code txSts;
    @XmlElement(name = "StsRsnInf")
    protected List<StatusReasonInformation8> stsRsnInf;
    @XmlElement(name = "ChrgsInf")
    protected List<ChargesInformation5> chrgsInf;
    @XmlElement(name = "AccptncDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar accptncDtTm;
    @XmlElement(name = "AcctSvcrRef")
    protected String acctSvcrRef;
    @XmlElement(name = "ClrSysRef")
    protected String clrSysRef;
    @XmlElement(name = "OrgnlTxRef")
    protected OriginalTransactionReference13 orgnlTxRef;

    /**
     * Obtiene el valor de la propiedad stsId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStsId() {
        return stsId;
    }

    /**
     * Define el valor de la propiedad stsId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStsId(String value) {
        this.stsId = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlInstrId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlInstrId() {
        return orgnlInstrId;
    }

    /**
     * Define el valor de la propiedad orgnlInstrId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlInstrId(String value) {
        this.orgnlInstrId = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlEndToEndId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlEndToEndId() {
        return orgnlEndToEndId;
    }

    /**
     * Define el valor de la propiedad orgnlEndToEndId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlEndToEndId(String value) {
        this.orgnlEndToEndId = value;
    }

    /**
     * Obtiene el valor de la propiedad txSts.
     * 
     * @return
     *     possible object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public TransactionIndividualStatus3Code getTxSts() {
        return txSts;
    }

    /**
     * Define el valor de la propiedad txSts.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public void setTxSts(TransactionIndividualStatus3Code value) {
        this.txSts = value;
    }

    /**
     * Gets the value of the stsRsnInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stsRsnInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStsRsnInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusReasonInformation8 }
     * 
     * 
     */
    public List<StatusReasonInformation8> getStsRsnInf() {
        if (stsRsnInf == null) {
            stsRsnInf = new ArrayList<StatusReasonInformation8>();
        }
        return this.stsRsnInf;
    }

    /**
     * Gets the value of the chrgsInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chrgsInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChrgsInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChargesInformation5 }
     * 
     * 
     */
    public List<ChargesInformation5> getChrgsInf() {
        if (chrgsInf == null) {
            chrgsInf = new ArrayList<ChargesInformation5>();
        }
        return this.chrgsInf;
    }

    /**
     * Obtiene el valor de la propiedad accptncDtTm.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAccptncDtTm() {
        return accptncDtTm;
    }

    /**
     * Define el valor de la propiedad accptncDtTm.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAccptncDtTm(XMLGregorianCalendar value) {
        this.accptncDtTm = value;
    }

    /**
     * Obtiene el valor de la propiedad acctSvcrRef.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctSvcrRef() {
        return acctSvcrRef;
    }

    /**
     * Define el valor de la propiedad acctSvcrRef.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctSvcrRef(String value) {
        this.acctSvcrRef = value;
    }

    /**
     * Obtiene el valor de la propiedad clrSysRef.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClrSysRef() {
        return clrSysRef;
    }

    /**
     * Define el valor de la propiedad clrSysRef.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClrSysRef(String value) {
        this.clrSysRef = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlTxRef.
     * 
     * @return
     *     possible object is
     *     {@link OriginalTransactionReference13 }
     *     
     */
    public OriginalTransactionReference13 getOrgnlTxRef() {
        return orgnlTxRef;
    }

    /**
     * Define el valor de la propiedad orgnlTxRef.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginalTransactionReference13 }
     *     
     */
    public void setOrgnlTxRef(OriginalTransactionReference13 value) {
        this.orgnlTxRef = value;
    }

}
