
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingDirectionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BillingDirectionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREDIT_CARD"/>
 *     &lt;enumeration value="AIRTIME"/>
 *     &lt;enumeration value="BUY_AIRTIME"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BillingDirectionType")
@XmlEnum
public enum BillingDirectionType {

    CREDIT_CARD,
    AIRTIME,
    BUY_AIRTIME;

    public String value() {
        return name();
    }

    public static BillingDirectionType fromValue(String v) {
        return valueOf(v);
    }

}
