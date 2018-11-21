
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicTransactionRetentionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DynamicTransactionRetentionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADD_NOW"/>
 *     &lt;enumeration value="ADD_TO_RESERVE"/>
 *     &lt;enumeration value="ENROLL_NOW"/>
 *     &lt;enumeration value="ENROLL_ON_DUE_DATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DynamicTransactionRetentionType")
@XmlEnum
public enum DynamicTransactionRetentionType {

    ADD_NOW,
    ADD_TO_RESERVE,
    ENROLL_NOW,
    ENROLL_ON_DUE_DATE;

    public String value() {
        return name();
    }

    public static DynamicTransactionRetentionType fromValue(String v) {
        return valueOf(v);
    }

}
