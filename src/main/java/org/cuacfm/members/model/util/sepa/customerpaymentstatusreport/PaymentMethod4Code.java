//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 12:46:36 PM CET 
//


package org.cuacfm.members.model.util.sepa.customerpaymentstatusreport;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para PaymentMethod4Code.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentMethod4Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHK"/>
 *     &lt;enumeration value="TRF"/>
 *     &lt;enumeration value="DD"/>
 *     &lt;enumeration value="TRA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentMethod4Code")
@XmlEnum
public enum PaymentMethod4Code {

    CHK,
    TRF,
    DD,
    TRA;

    public String value() {
        return name();
    }

    public static PaymentMethod4Code fromValue(String v) {
        return valueOf(v);
    }

}
