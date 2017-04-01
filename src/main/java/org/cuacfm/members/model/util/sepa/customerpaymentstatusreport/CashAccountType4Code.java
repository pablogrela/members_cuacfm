//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 11:40:20 AM CET 
//

package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CashAccountType4Code.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CashAccountType4Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CASH"/>
 *     &lt;enumeration value="CHAR"/>
 *     &lt;enumeration value="COMM"/>
 *     &lt;enumeration value="TAXE"/>
 *     &lt;enumeration value="CISH"/>
 *     &lt;enumeration value="TRAS"/>
 *     &lt;enumeration value="SACC"/>
 *     &lt;enumeration value="CACC"/>
 *     &lt;enumeration value="SVGS"/>
 *     &lt;enumeration value="ONDP"/>
 *     &lt;enumeration value="MGLD"/>
 *     &lt;enumeration value="NREX"/>
 *     &lt;enumeration value="MOMA"/>
 *     &lt;enumeration value="LOAN"/>
 *     &lt;enumeration value="SLRY"/>
 *     &lt;enumeration value="ODFT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CashAccountType4Code")
@XmlEnum
public enum CashAccountType4Code {

    CASH,
    CHAR,
    COMM,
    TAXE,
    CISH,
    TRAS,
    SACC,
    CACC,
    SVGS,
    ONDP,
    MGLD,
    NREX,
    MOMA,
    LOAN,
    SLRY,
    ODFT;

    public String value() {
        return name();
    }

    public static CashAccountType4Code fromValue(String v) {
        return valueOf(v);
    }

}
