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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TaxRecord1 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TaxRecord1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="Ctgy" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="CtgyDtls" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="DbtrSts" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="CertId" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="FrmsCd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max35Text" minOccurs="0"/>
 *         &lt;element name="Prd" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}TaxPeriod1" minOccurs="0"/>
 *         &lt;element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}TaxAmount1" minOccurs="0"/>
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.001.02}Max140Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxRecord1", propOrder = {
    "tp",
    "ctgy",
    "ctgyDtls",
    "dbtrSts",
    "certId",
    "frmsCd",
    "prd",
    "taxAmt",
    "addtlInf"
})
public class TaxRecord1 {

    @XmlElement(name = "Tp")
    protected String tp;
    @XmlElement(name = "Ctgy")
    protected String ctgy;
    @XmlElement(name = "CtgyDtls")
    protected String ctgyDtls;
    @XmlElement(name = "DbtrSts")
    protected String dbtrSts;
    @XmlElement(name = "CertId")
    protected String certId;
    @XmlElement(name = "FrmsCd")
    protected String frmsCd;
    @XmlElement(name = "Prd")
    protected TaxPeriod1 prd;
    @XmlElement(name = "TaxAmt")
    protected TaxAmount1 taxAmt;
    @XmlElement(name = "AddtlInf")
    protected String addtlInf;

    /**
     * Obtiene el valor de la propiedad tp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Define el valor de la propiedad tp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Obtiene el valor de la propiedad ctgy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgy() {
        return ctgy;
    }

    /**
     * Define el valor de la propiedad ctgy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgy(String value) {
        this.ctgy = value;
    }

    /**
     * Obtiene el valor de la propiedad ctgyDtls.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgyDtls() {
        return ctgyDtls;
    }

    /**
     * Define el valor de la propiedad ctgyDtls.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgyDtls(String value) {
        this.ctgyDtls = value;
    }

    /**
     * Obtiene el valor de la propiedad dbtrSts.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbtrSts() {
        return dbtrSts;
    }

    /**
     * Define el valor de la propiedad dbtrSts.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbtrSts(String value) {
        this.dbtrSts = value;
    }

    /**
     * Obtiene el valor de la propiedad certId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertId() {
        return certId;
    }

    /**
     * Define el valor de la propiedad certId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertId(String value) {
        this.certId = value;
    }

    /**
     * Obtiene el valor de la propiedad frmsCd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrmsCd() {
        return frmsCd;
    }

    /**
     * Define el valor de la propiedad frmsCd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrmsCd(String value) {
        this.frmsCd = value;
    }

    /**
     * Obtiene el valor de la propiedad prd.
     * 
     * @return
     *     possible object is
     *     {@link TaxPeriod1 }
     *     
     */
    public TaxPeriod1 getPrd() {
        return prd;
    }

    /**
     * Define el valor de la propiedad prd.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPeriod1 }
     *     
     */
    public void setPrd(TaxPeriod1 value) {
        this.prd = value;
    }

    /**
     * Obtiene el valor de la propiedad taxAmt.
     * 
     * @return
     *     possible object is
     *     {@link TaxAmount1 }
     *     
     */
    public TaxAmount1 getTaxAmt() {
        return taxAmt;
    }

    /**
     * Define el valor de la propiedad taxAmt.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAmount1 }
     *     
     */
    public void setTaxAmt(TaxAmount1 value) {
        this.taxAmt = value;
    }

    /**
     * Obtiene el valor de la propiedad addtlInf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddtlInf() {
        return addtlInf;
    }

    /**
     * Define el valor de la propiedad addtlInf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddtlInf(String value) {
        this.addtlInf = value;
    }

}
