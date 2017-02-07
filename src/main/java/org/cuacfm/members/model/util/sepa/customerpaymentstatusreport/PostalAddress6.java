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


/**
 * <p>Clase Java para PostalAddress6 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PostalAddress6">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdrTp" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}AddressType2Code" minOccurs="0"/>
 *         &lt;element name="Dept" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max70Text" minOccurs="0"/>
 *         &lt;element name="SubDept" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max70Text" minOccurs="0"/>
 *         &lt;element name="StrtNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max70Text" minOccurs="0"/>
 *         &lt;element name="BldgNb" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max16Text" minOccurs="0"/>
 *         &lt;element name="PstCd" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max16Text" minOccurs="0"/>
 *         &lt;element name="TwnNm" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="CtrySubDvsn" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="Ctry" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}CountryCode" minOccurs="0"/>
 *         &lt;element name="AdrLine" type="{urn:iso:std:iso:20022:tech:xsd:pain.002.001.03}Max70Text" maxOccurs="7" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddress6", propOrder = {
    "adrTp",
    "dept",
    "subDept",
    "strtNm",
    "bldgNb",
    "pstCd",
    "twnNm",
    "ctrySubDvsn",
    "ctry",
    "adrLine"
})
public class PostalAddress6 {

    @XmlElement(name = "AdrTp")
    @XmlSchemaType(name = "string")
    protected AddressType2Code adrTp;
    @XmlElement(name = "Dept")
    protected String dept;
    @XmlElement(name = "SubDept")
    protected String subDept;
    @XmlElement(name = "StrtNm")
    protected String strtNm;
    @XmlElement(name = "BldgNb")
    protected String bldgNb;
    @XmlElement(name = "PstCd")
    protected String pstCd;
    @XmlElement(name = "TwnNm")
    protected String twnNm;
    @XmlElement(name = "CtrySubDvsn")
    protected String ctrySubDvsn;
    @XmlElement(name = "Ctry")
    protected String ctry;
    @XmlElement(name = "AdrLine")
    protected List<String> adrLine;

    /**
     * Obtiene el valor de la propiedad adrTp.
     * 
     * @return
     *     possible object is
     *     {@link AddressType2Code }
     *     
     */
    public AddressType2Code getAdrTp() {
        return adrTp;
    }

    /**
     * Define el valor de la propiedad adrTp.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType2Code }
     *     
     */
    public void setAdrTp(AddressType2Code value) {
        this.adrTp = value;
    }

    /**
     * Obtiene el valor de la propiedad dept.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDept() {
        return dept;
    }

    /**
     * Define el valor de la propiedad dept.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDept(String value) {
        this.dept = value;
    }

    /**
     * Obtiene el valor de la propiedad subDept.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubDept() {
        return subDept;
    }

    /**
     * Define el valor de la propiedad subDept.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubDept(String value) {
        this.subDept = value;
    }

    /**
     * Obtiene el valor de la propiedad strtNm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrtNm() {
        return strtNm;
    }

    /**
     * Define el valor de la propiedad strtNm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrtNm(String value) {
        this.strtNm = value;
    }

    /**
     * Obtiene el valor de la propiedad bldgNb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBldgNb() {
        return bldgNb;
    }

    /**
     * Define el valor de la propiedad bldgNb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBldgNb(String value) {
        this.bldgNb = value;
    }

    /**
     * Obtiene el valor de la propiedad pstCd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPstCd() {
        return pstCd;
    }

    /**
     * Define el valor de la propiedad pstCd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPstCd(String value) {
        this.pstCd = value;
    }

    /**
     * Obtiene el valor de la propiedad twnNm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwnNm() {
        return twnNm;
    }

    /**
     * Define el valor de la propiedad twnNm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwnNm(String value) {
        this.twnNm = value;
    }

    /**
     * Obtiene el valor de la propiedad ctrySubDvsn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtrySubDvsn() {
        return ctrySubDvsn;
    }

    /**
     * Define el valor de la propiedad ctrySubDvsn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtrySubDvsn(String value) {
        this.ctrySubDvsn = value;
    }

    /**
     * Obtiene el valor de la propiedad ctry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * Define el valor de la propiedad ctry.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtry(String value) {
        this.ctry = value;
    }

    /**
     * Gets the value of the adrLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adrLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdrLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAdrLine() {
        if (adrLine == null) {
            adrLine = new ArrayList<String>();
        }
        return this.adrLine;
    }

}
