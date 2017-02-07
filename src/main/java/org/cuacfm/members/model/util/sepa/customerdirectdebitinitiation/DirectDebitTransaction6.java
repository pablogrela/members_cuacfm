//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 11:40:20 AM CET 
//


package org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para DirectDebitTransaction6 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DirectDebitTransaction6">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MndtRltdInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}MandateRelatedInformation6" minOccurs="0"/>
 *         &lt;element name="CdtrSchmeId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}PartyIdentification32" minOccurs="0"/>
 *         &lt;element name="PreNtfctnId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="PreNtfctnDt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}ISODate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectDebitTransaction6", propOrder = {
    "mndtRltdInf",
    "cdtrSchmeId",
    "preNtfctnId",
    "preNtfctnDt"
})
public class DirectDebitTransaction6 {

    @XmlElement(name = "MndtRltdInf")
    protected MandateRelatedInformation6 mndtRltdInf;
    @XmlElement(name = "CdtrSchmeId")
    protected PartyIdentification32 cdtrSchmeId;
    @XmlElement(name = "PreNtfctnId")
    protected String preNtfctnId;
    @XmlElement(name = "PreNtfctnDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar preNtfctnDt;

    /**
     * Obtiene el valor de la propiedad mndtRltdInf.
     * 
     * @return
     *     possible object is
     *     {@link MandateRelatedInformation6 }
     *     
     */
    public MandateRelatedInformation6 getMndtRltdInf() {
        return mndtRltdInf;
    }

    /**
     * Define el valor de la propiedad mndtRltdInf.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateRelatedInformation6 }
     *     
     */
    public void setMndtRltdInf(MandateRelatedInformation6 value) {
        this.mndtRltdInf = value;
    }

    /**
     * Obtiene el valor de la propiedad cdtrSchmeId.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification32 }
     *     
     */
    public PartyIdentification32 getCdtrSchmeId() {
        return cdtrSchmeId;
    }

    /**
     * Define el valor de la propiedad cdtrSchmeId.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification32 }
     *     
     */
    public void setCdtrSchmeId(PartyIdentification32 value) {
        this.cdtrSchmeId = value;
    }

    /**
     * Obtiene el valor de la propiedad preNtfctnId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreNtfctnId() {
        return preNtfctnId;
    }

    /**
     * Define el valor de la propiedad preNtfctnId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreNtfctnId(String value) {
        this.preNtfctnId = value;
    }

    /**
     * Obtiene el valor de la propiedad preNtfctnDt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreNtfctnDt() {
        return preNtfctnDt;
    }

    /**
     * Define el valor de la propiedad preNtfctnDt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreNtfctnDt(XMLGregorianCalendar value) {
        this.preNtfctnDt = value;
    }

}
