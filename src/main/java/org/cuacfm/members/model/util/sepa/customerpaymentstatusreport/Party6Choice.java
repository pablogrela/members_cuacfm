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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Party6Choice complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Party6Choice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="OrgId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}OrganisationIdentification4"/>
 *           &lt;element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}PersonIdentification5"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party6Choice", propOrder = {
    "orgId",
    "prvtId"
})
public class Party6Choice {

    @XmlElement(name = "OrgId")
    protected OrganisationIdentification4 orgId;
    @XmlElement(name = "PrvtId")
    protected PersonIdentification5 prvtId;

    /**
     * Obtiene el valor de la propiedad orgId.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification4 }
     *     
     */
    public OrganisationIdentification4 getOrgId() {
        return orgId;
    }

    /**
     * Define el valor de la propiedad orgId.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification4 }
     *     
     */
    public void setOrgId(OrganisationIdentification4 value) {
        this.orgId = value;
    }

    /**
     * Obtiene el valor de la propiedad prvtId.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification5 }
     *     
     */
    public PersonIdentification5 getPrvtId() {
        return prvtId;
    }

    /**
     * Define el valor de la propiedad prvtId.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification5 }
     *     
     */
    public void setPrvtId(PersonIdentification5 value) {
        this.prvtId = value;
    }

}
