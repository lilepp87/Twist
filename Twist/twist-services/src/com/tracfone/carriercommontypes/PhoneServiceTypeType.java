
package com.tracfone.carriercommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhoneServiceTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PhoneServiceTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Landline"/>
 *     &lt;enumeration value="Wireless"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PhoneServiceTypeType")
@XmlEnum
public enum PhoneServiceTypeType {

    @XmlEnumValue("Landline")
    LANDLINE("Landline"),
    @XmlEnumValue("Wireless")
    WIRELESS("Wireless");
    private final String value;

    PhoneServiceTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PhoneServiceTypeType fromValue(String v) {
        for (PhoneServiceTypeType c: PhoneServiceTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
