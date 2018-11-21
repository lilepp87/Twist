
package com.tracfone.accountcommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VALID_ACCOUNT"/>
 *     &lt;enumeration value="DUMMY_ACCOUNT"/>
 *     &lt;enumeration value="NO_ACCOUNT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountStatusType")
@XmlEnum
public enum AccountStatusType {

    VALID_ACCOUNT,
    DUMMY_ACCOUNT,
    NO_ACCOUNT;

    public String value() {
        return name();
    }

    public static AccountStatusType fromValue(String v) {
        return valueOf(v);
    }

}
