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
 * <p>Clase Java para BranchAndFinancialInstitutionIdentification4 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="BranchAndFinancialInstitutionIdentification4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FinInstnId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}FinancialInstitutionIdentification7"/>
 *         &lt;element name="BrnchId" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}BranchData2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BranchAndFinancialInstitutionIdentification4", propOrder = {
    "finInstnId",
    "brnchId"
})
public class BranchAndFinancialInstitutionIdentification4 {

    @XmlElement(name = "FinInstnId", required = true)
    protected FinancialInstitutionIdentification7 finInstnId;
    @XmlElement(name = "BrnchId")
    protected BranchData2 brnchId;

    /**
     * Obtiene el valor de la propiedad finInstnId.
     * 
     * @return
     *     possible object is
     *     {@link FinancialInstitutionIdentification7 }
     *     
     */
    public FinancialInstitutionIdentification7 getFinInstnId() {
        return finInstnId;
    }

    /**
     * Define el valor de la propiedad finInstnId.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialInstitutionIdentification7 }
     *     
     */
    public void setFinInstnId(FinancialInstitutionIdentification7 value) {
        this.finInstnId = value;
    }

    /**
     * Obtiene el valor de la propiedad brnchId.
     * 
     * @return
     *     possible object is
     *     {@link BranchData2 }
     *     
     */
    public BranchData2 getBrnchId() {
        return brnchId;
    }

    /**
     * Define el valor de la propiedad brnchId.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchData2 }
     *     
     */
    public void setBrnchId(BranchData2 value) {
        this.brnchId = value;
    }

}
