//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.01.31 a las 11:40:20 AM CET 
//


package org.cuacfm.members.model.util.sepa.customerdirectdebitinitiation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ChargeBearerType1Code.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="ChargeBearerType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEBT"/>
 *     &lt;enumeration value="CRED"/>
 *     &lt;enumeration value="SHAR"/>
 *     &lt;enumeration value="SLEV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChargeBearerType1Code")
@XmlEnum
public enum ChargeBearerType1Code {

    DEBT,
    CRED,
    SHAR,
    SLEV;

    public String value() {
        return name();
    }

    public static ChargeBearerType1Code fromValue(String v) {
        return valueOf(v);
    }

}
