//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 11:40:20 AM CET 
//


package org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CreditorReferenceType2 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CreditorReferenceType2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CdOrPrtry" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}CreditorReferenceType1Choice"/>
 *         &lt;element name="Issr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditorReferenceType2", propOrder = {
    "cdOrPrtry",
    "issr"
})
public class CreditorReferenceType2 {

    @XmlElement(name = "CdOrPrtry", required = true)
    protected CreditorReferenceType1Choice cdOrPrtry;
    @XmlElement(name = "Issr")
    protected String issr;

    /**
     * Obtiene el valor de la propiedad cdOrPrtry.
     * 
     * @return
     *     possible object is
     *     {@link CreditorReferenceType1Choice }
     *     
     */
    public CreditorReferenceType1Choice getCdOrPrtry() {
        return cdOrPrtry;
    }

    /**
     * Define el valor de la propiedad cdOrPrtry.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorReferenceType1Choice }
     *     
     */
    public void setCdOrPrtry(CreditorReferenceType1Choice value) {
        this.cdOrPrtry = value;
    }

    /**
     * Obtiene el valor de la propiedad issr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssr() {
        return issr;
    }

    /**
     * Define el valor de la propiedad issr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssr(String value) {
        this.issr = value;
    }

}