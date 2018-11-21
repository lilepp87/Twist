
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlanIdentifierType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlanIdentifierType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PLAN_ID"/>
 *     &lt;enumeration value="PROGRAM_ID"/>
 *     &lt;enumeration value="PART_NUMBER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PlanIdentifierType")
@XmlEnum
public enum PlanIdentifierType {

    PLAN_ID,
    PROGRAM_ID,
    PART_NUMBER;

    public String value() {
        return name();
    }

    public static PlanIdentifierType fromValue(String v) {
        return valueOf(v);
    }

}
