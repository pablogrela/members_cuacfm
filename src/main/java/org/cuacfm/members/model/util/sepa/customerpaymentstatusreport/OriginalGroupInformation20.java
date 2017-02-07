//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 12:46:36 PM CET 
//


package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para OriginalGroupInformation20 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OriginalGroupInformation20">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrgnlMsgId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text"/>
 *         &lt;element name="OrgnlMsgNmId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text"/>
 *         &lt;element name="OrgnlCreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ISODateTime" minOccurs="0"/>
 *         &lt;element name="OrgnlNbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max15NumericText" minOccurs="0"/>
 *         &lt;element name="OrgnlCtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}DecimalNumber" minOccurs="0"/>
 *         &lt;element name="GrpSts" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}TransactionGroupStatus3Code" minOccurs="0"/>
 *         &lt;element name="StsRsnInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}StatusReasonInformation8" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NbOfTxsPerSts" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}NumberOfTransactionsPerStatus3" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalGroupInformation20", propOrder = {
    "orgnlMsgId",
    "orgnlMsgNmId",
    "orgnlCreDtTm",
    "orgnlNbOfTxs",
    "orgnlCtrlSum",
    "grpSts",
    "stsRsnInf",
    "nbOfTxsPerSts"
})
public class OriginalGroupInformation20 {

    @XmlElement(name = "OrgnlMsgId", required = true)
    protected String orgnlMsgId;
    @XmlElement(name = "OrgnlMsgNmId", required = true)
    protected String orgnlMsgNmId;
    @XmlElement(name = "OrgnlCreDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orgnlCreDtTm;
    @XmlElement(name = "OrgnlNbOfTxs")
    protected String orgnlNbOfTxs;
    @XmlElement(name = "OrgnlCtrlSum")
    protected BigDecimal orgnlCtrlSum;
    @XmlElement(name = "GrpSts")
    @XmlSchemaType(name = "string")
    protected TransactionGroupStatus3Code grpSts;
    @XmlElement(name = "StsRsnInf")
    protected List<StatusReasonInformation8> stsRsnInf;
    @XmlElement(name = "NbOfTxsPerSts")
    protected List<NumberOfTransactionsPerStatus3> nbOfTxsPerSts;

    /**
     * Obtiene el valor de la propiedad orgnlMsgId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    /**
     * Define el valor de la propiedad orgnlMsgId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgId(String value) {
        this.orgnlMsgId = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlMsgNmId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgNmId() {
        return orgnlMsgNmId;
    }

    /**
     * Define el valor de la propiedad orgnlMsgNmId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgNmId(String value) {
        this.orgnlMsgNmId = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlCreDtTm.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrgnlCreDtTm() {
        return orgnlCreDtTm;
    }

    /**
     * Define el valor de la propiedad orgnlCreDtTm.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrgnlCreDtTm(XMLGregorianCalendar value) {
        this.orgnlCreDtTm = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlNbOfTxs.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlNbOfTxs() {
        return orgnlNbOfTxs;
    }

    /**
     * Define el valor de la propiedad orgnlNbOfTxs.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlNbOfTxs(String value) {
        this.orgnlNbOfTxs = value;
    }

    /**
     * Obtiene el valor de la propiedad orgnlCtrlSum.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOrgnlCtrlSum() {
        return orgnlCtrlSum;
    }

    /**
     * Define el valor de la propiedad orgnlCtrlSum.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOrgnlCtrlSum(BigDecimal value) {
        this.orgnlCtrlSum = value;
    }

    /**
     * Obtiene el valor de la propiedad grpSts.
     * 
     * @return
     *     possible object is
     *     {@link TransactionGroupStatus3Code }
     *     
     */
    public TransactionGroupStatus3Code getGrpSts() {
        return grpSts;
    }

    /**
     * Define el valor de la propiedad grpSts.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionGroupStatus3Code }
     *     
     */
    public void setGrpSts(TransactionGroupStatus3Code value) {
        this.grpSts = value;
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
     * Gets the value of the nbOfTxsPerSts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nbOfTxsPerSts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNbOfTxsPerSts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NumberOfTransactionsPerStatus3 }
     * 
     * 
     */
    public List<NumberOfTransactionsPerStatus3> getNbOfTxsPerSts() {
        if (nbOfTxsPerSts == null) {
            nbOfTxsPerSts = new ArrayList<NumberOfTransactionsPerStatus3>();
        }
        return this.nbOfTxsPerSts;
    }

}
