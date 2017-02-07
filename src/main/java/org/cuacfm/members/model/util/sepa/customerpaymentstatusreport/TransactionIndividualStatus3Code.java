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
 * <p>Clase Java para TransactionIndividualStatus3Code.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionIndividualStatus3Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACTC"/>
 *     &lt;enumeration value="RJCT"/>
 *     &lt;enumeration value="PDNG"/>
 *     &lt;enumeration value="ACCP"/>
 *     &lt;enumeration value="ACSP"/>
 *     &lt;enumeration value="ACSC"/>
 *     &lt;enumeration value="ACWC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionIndividualStatus3Code")
@XmlEnum
public enum TransactionIndividualStatus3Code {

    ACTC,
    RJCT,
    PDNG,
    ACCP,
    ACSP,
    ACSC,
    ACWC;

    public String value() {
        return name();
    }

    public static TransactionIndividualStatus3Code fromValue(String v) {
        return valueOf(v);
    }

}
