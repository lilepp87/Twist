
package com.tracfone.securitycommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.phonecommontypes.DeviceIdType;


/**
 * <p>Java class for DeviceAccountCredentialsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceAccountCredentialsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="email" type="{http://www.tracfone.com/CommonTypes}EmailType"/>
 *           &lt;element name="deviceId" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="securityPin" type="{http://www.tracfone.com/PhoneCommonTypes}SecurityPinType"/>
 *           &lt;element name="password" type="{http://www.tracfone.com/AccountCommonTypes}PasswordType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="loggable" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" fixed="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceAccountCredentialsType", propOrder = {
    "email",
    "deviceId",
    "securityPin",
    "password"
})
public class DeviceAccountCredentialsType {

    protected String email;
    protected DeviceIdType deviceId;
    protected String securityPin;
    protected String password;
    @XmlAttribute(name = "loggable", required = true)
    protected boolean loggable;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceIdType }
     *     
     */
    public DeviceIdType getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceIdType }
     *     
     */
    public void setDeviceId(DeviceIdType value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the securityPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityPin() {
        return securityPin;
    }

    /**
     * Sets the value of the securityPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityPin(String value) {
        this.securityPin = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the loggable property.
     * 
     */
    public boolean isLoggable() {
        return loggable;
    }

    /**
     * Sets the value of the loggable property.
     * 
     */
    public void setLoggable(boolean value) {
        this.loggable = value;
    }

}
