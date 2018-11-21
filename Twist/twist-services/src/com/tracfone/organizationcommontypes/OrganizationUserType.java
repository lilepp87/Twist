
package com.tracfone.organizationcommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganizationUserType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OrganizationUserType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BUYERADMIN"/>
 *     &lt;enumeration value="BUYER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OrganizationUserType")
@XmlEnum
public enum OrganizationUserType {

    BUYERADMIN,
    BUYER;

    public String value() {
        return name();
    }

    public static OrganizationUserType fromValue(String v) {
        return valueOf(v);
    }

}
