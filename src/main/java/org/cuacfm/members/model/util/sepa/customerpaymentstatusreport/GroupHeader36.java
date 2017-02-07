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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para GroupHeader36 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="GroupHeader36">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text"/>
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}ISODateTime"/>
 *         &lt;element name="InitgPty" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PartyIdentification32" minOccurs="0"/>
 *         &lt;element name="FwdgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader36", propOrder = {
    "msgId",
    "creDtTm",
    "initgPty",
    "fwdgAgt",
    "dbtrAgt",
    "cdtrAgt"
})
public class GroupHeader36 {

    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "CreDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    @XmlElement(name = "InitgPty")
    protected PartyIdentification32 initgPty;
    @XmlElement(name = "FwdgAgt")
    protected BranchAndFinancialInstitutionIdentification4 fwdgAgt;
    @XmlElement(name = "DbtrAgt")
    protected BranchAndFinancialInstitutionIdentification4 dbtrAgt;
    @XmlElement(name = "CdtrAgt")
    protected BranchAndFinancialInstitutionIdentification4 cdtrAgt;

    /**
     * Obtiene el valor de la propiedad msgId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Define el valor de la propiedad msgId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Obtiene el valor de la propiedad creDtTm.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Define el valor de la propiedad creDtTm.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Obtiene el valor de la propiedad initgPty.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification32 }
     *     
     */
    public PartyIdentification32 getInitgPty() {
        return initgPty;
    }

    /**
     * Define el valor de la propiedad initgPty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification32 }
     *     
     */
    public void setInitgPty(PartyIdentification32 value) {
        this.initgPty = value;
    }

    /**
     * Obtiene el valor de la propiedad fwdgAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getFwdgAgt() {
        return fwdgAgt;
    }

    /**
     * Define el valor de la propiedad fwdgAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setFwdgAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.fwdgAgt = value;
    }

    /**
     * Obtiene el valor de la propiedad dbtrAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Define el valor de la propiedad dbtrAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.dbtrAgt = value;
    }

    /**
     * Obtiene el valor de la propiedad cdtrAgt.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Define el valor de la propiedad cdtrAgt.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.cdtrAgt = value;
    }

}
