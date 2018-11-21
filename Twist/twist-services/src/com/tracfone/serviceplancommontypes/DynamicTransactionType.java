
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DynamicTransactionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PURCHASE"/>
 *     &lt;enumeration value="PURCHASE_ILD"/>
 *     &lt;enumeration value="ENROLLMENT"/>
 *     &lt;enumeration value="ENROLLMENT_HPP"/>
 *     &lt;enumeration value="REDEMPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DynamicTransactionType")
@XmlEnum
public enum DynamicTransactionType {

    PURCHASE,
    PURCHASE_ILD,
    ENROLLMENT,
    ENROLLMENT_HPP,
    REDEMPTION;

    public String value() {
        return name();
    }

    public static DynamicTransactionType fromValue(String v) {
        return valueOf(v);
    }

}
