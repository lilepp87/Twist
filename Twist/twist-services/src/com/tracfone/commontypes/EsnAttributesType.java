
package com.tracfone.commontypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EsnAttributesType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EsnAttributesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MIN"/>
 *     &lt;enumeration value="NICKNAME"/>
 *     &lt;enumeration value="BRAND"/>
 *     &lt;enumeration value="TECHNOLOGY"/>
 *     &lt;enumeration value="SIM"/>
 *     &lt;enumeration value="EMAIL"/>
 *     &lt;enumeration value="ISB2B"/>
 *     &lt;enumeration value="STATUS"/>
 *     &lt;enumeration value="ISAUTOREFILL"/>
 *     &lt;enumeration value="ISUNLIMITED"/>
 *     &lt;enumeration value="ENDOFSERVICEDATE"/>
 *     &lt;enumeration value="FORECASTDATE"/>
 *     &lt;enumeration value="HASREGEDPAYMENTSOURCES"/>
 *     &lt;enumeration value="QUEUESIZE"/>
 *     &lt;enumeration value="SERVICEPLANID"/>
 *     &lt;enumeration value="CARRIER"/>
 *     &lt;enumeration value="DEVICE_PARTCLASS"/>
 *     &lt;enumeration value="DEVICE_PARTNUMBER"/>
 *     &lt;enumeration value="BILLING_PARTNUMBER"/>
 *     &lt;enumeration value="SERVICE_PARTNUMBER"/>
 *     &lt;enumeration value="ENROLLMENT_STATUS"/>
 *     &lt;enumeration value="SERVICEPLANNAME"/>
 *     &lt;enumeration value="IS_SAFELINK"/>
 *     &lt;enumeration value="ESN"/>
 *     &lt;enumeration value="CURRENT_SERV_PLAN_NAME"/>
 *     &lt;enumeration value="CURRENT_SERV_PLAN_ID"/>
 *     &lt;enumeration value="LEASESTATUS"/>
 *     &lt;enumeration value="OLD_ESN"/>
 *     &lt;enumeration value="OPERATING_SYSTEM"/>
 *     &lt;enumeration value="DEVICE_TYPE"/>
 *     &lt;enumeration value="X_SOURCESYSTEM"/>
 *     &lt;enumeration value="GROUPID"/>
 *     &lt;enumeration value="AVAILABLE_LINES"/>
 *     &lt;enumeration value="TOTAL_LINES"/>
 *     &lt;enumeration value="ESN_CHANGE_TYPE"/>
 *     &lt;enumeration value="PIN_PART_NUMBER"/>
 *     &lt;enumeration value="WEB_CONTACT_OBJID"/>
 *     &lt;enumeration value="DLL"/>
 *     &lt;enumeration value="SEQUENCE"/>
 *     &lt;enumeration value="UNLOCKING_PROCESS"/>
 *     &lt;enumeration value="DEVICE_UNLOCK_STATUS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EsnAttributesType")
@XmlEnum
public enum EsnAttributesType {

    MIN("MIN"),
    NICKNAME("NICKNAME"),
    BRAND("BRAND"),
    TECHNOLOGY("TECHNOLOGY"),
    SIM("SIM"),
    EMAIL("EMAIL"),
    @XmlEnumValue("ISB2B")
    ISB_2_B("ISB2B"),
    STATUS("STATUS"),
    ISAUTOREFILL("ISAUTOREFILL"),
    ISUNLIMITED("ISUNLIMITED"),
    ENDOFSERVICEDATE("ENDOFSERVICEDATE"),
    FORECASTDATE("FORECASTDATE"),
    HASREGEDPAYMENTSOURCES("HASREGEDPAYMENTSOURCES"),
    QUEUESIZE("QUEUESIZE"),
    SERVICEPLANID("SERVICEPLANID"),
    CARRIER("CARRIER"),
    DEVICE_PARTCLASS("DEVICE_PARTCLASS"),
    DEVICE_PARTNUMBER("DEVICE_PARTNUMBER"),
    BILLING_PARTNUMBER("BILLING_PARTNUMBER"),
    SERVICE_PARTNUMBER("SERVICE_PARTNUMBER"),
    ENROLLMENT_STATUS("ENROLLMENT_STATUS"),
    SERVICEPLANNAME("SERVICEPLANNAME"),
    IS_SAFELINK("IS_SAFELINK"),
    ESN("ESN"),
    CURRENT_SERV_PLAN_NAME("CURRENT_SERV_PLAN_NAME"),
    CURRENT_SERV_PLAN_ID("CURRENT_SERV_PLAN_ID"),
    LEASESTATUS("LEASESTATUS"),
    OLD_ESN("OLD_ESN"),
    OPERATING_SYSTEM("OPERATING_SYSTEM"),
    DEVICE_TYPE("DEVICE_TYPE"),
    X_SOURCESYSTEM("X_SOURCESYSTEM"),
    GROUPID("GROUPID"),
    AVAILABLE_LINES("AVAILABLE_LINES"),
    TOTAL_LINES("TOTAL_LINES"),
    ESN_CHANGE_TYPE("ESN_CHANGE_TYPE"),
    PIN_PART_NUMBER("PIN_PART_NUMBER"),
    WEB_CONTACT_OBJID("WEB_CONTACT_OBJID"),
    DLL("DLL"),
    SEQUENCE("SEQUENCE"),
    UNLOCKING_PROCESS("UNLOCKING_PROCESS"),
    DEVICE_UNLOCK_STATUS("DEVICE_UNLOCK_STATUS");
    private final String value;

    EsnAttributesType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EsnAttributesType fromValue(String v) {
        for (EsnAttributesType c: EsnAttributesType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
