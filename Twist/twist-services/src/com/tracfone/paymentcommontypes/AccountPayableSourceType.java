
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountPayableSourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountPayableSourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CASHCARD"/>
 *     &lt;enumeration value="CHECK"/>
 *     &lt;enumeration value="DONATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountPayableSourceType")
@XmlEnum
public enum AccountPayableSourceType {

    CASHCARD,
    CHECK,
    DONATION;

    public String value() {
        return name();
    }

    public static AccountPayableSourceType fromValue(String v) {
        return valueOf(v);
    }

}
