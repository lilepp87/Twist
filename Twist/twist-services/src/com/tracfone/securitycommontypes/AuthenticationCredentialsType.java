
package com.tracfone.securitycommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.accountcommontypes.AccountCredentialsType;
import com.tracfone.accountcommontypes.AccountTokenCredentialsType;
import com.tracfone.accountcommontypes.CsrAccountCredentialsType;
import com.tracfone.accountcommontypes.CsrAccountTokenCredentialsType;
import com.tracfone.phonecommontypes.DeviceCredentialsType;


/**
 * <p>Java class for AuthenticationCredentialsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticationCredentialsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="AccountCredentials" type="{http://www.tracfone.com/AccountCommonTypes}AccountCredentialsType"/>
 *         &lt;element name="DeviceCredentials" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceCredentialsType"/>
 *         &lt;element name="csrAccountCredentials" type="{http://www.tracfone.com/AccountCommonTypes}CsrAccountCredentialsType"/>
 *         &lt;element name="accountTokenCredentials" type="{http://www.tracfone.com/AccountCommonTypes}AccountTokenCredentialsType"/>
 *         &lt;element name="csrAccountTokenCredentials" type="{http://www.tracfone.com/AccountCommonTypes}CsrAccountTokenCredentialsType"/>
 *         &lt;element name="DeviceAccountCredentials" type="{http://www.tracfone.com/SecurityCommonTypes}DeviceAccountCredentialsType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticationCredentialsType", propOrder = {
    "accountCredentials",
    "deviceCredentials",
    "csrAccountCredentials",
    "accountTokenCredentials",
    "csrAccountTokenCredentials",
    "deviceAccountCredentials"
})
public class AuthenticationCredentialsType {

    @XmlElement(name = "AccountCredentials")
    protected AccountCredentialsType accountCredentials;
    @XmlElement(name = "DeviceCredentials")
    protected DeviceCredentialsType deviceCredentials;
    protected CsrAccountCredentialsType csrAccountCredentials;
    protected AccountTokenCredentialsType accountTokenCredentials;
    protected CsrAccountTokenCredentialsType csrAccountTokenCredentials;
    @XmlElement(name = "DeviceAccountCredentials")
    protected DeviceAccountCredentialsType deviceAccountCredentials;

    /**
     * Gets the value of the accountCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCredentialsType }
     *     
     */
    public AccountCredentialsType getAccountCredentials() {
        return accountCredentials;
    }

    /**
     * Sets the value of the accountCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCredentialsType }
     *     
     */
    public void setAccountCredentials(AccountCredentialsType value) {
        this.accountCredentials = value;
    }

    /**
     * Gets the value of the deviceCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public DeviceCredentialsType getDeviceCredentials() {
        return deviceCredentials;
    }

    /**
     * Sets the value of the deviceCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public void setDeviceCredentials(DeviceCredentialsType value) {
        this.deviceCredentials = value;
    }

    /**
     * Gets the value of the csrAccountCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link CsrAccountCredentialsType }
     *     
     */
    public CsrAccountCredentialsType getCsrAccountCredentials() {
        return csrAccountCredentials;
    }

    /**
     * Sets the value of the csrAccountCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link CsrAccountCredentialsType }
     *     
     */
    public void setCsrAccountCredentials(CsrAccountCredentialsType value) {
        this.csrAccountCredentials = value;
    }

    /**
     * Gets the value of the accountTokenCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AccountTokenCredentialsType }
     *     
     */
    public AccountTokenCredentialsType getAccountTokenCredentials() {
        return accountTokenCredentials;
    }

    /**
     * Sets the value of the accountTokenCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountTokenCredentialsType }
     *     
     */
    public void setAccountTokenCredentials(AccountTokenCredentialsType value) {
        this.accountTokenCredentials = value;
    }

    /**
     * Gets the value of the csrAccountTokenCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link CsrAccountTokenCredentialsType }
     *     
     */
    public CsrAccountTokenCredentialsType getCsrAccountTokenCredentials() {
        return csrAccountTokenCredentials;
    }

    /**
     * Sets the value of the csrAccountTokenCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link CsrAccountTokenCredentialsType }
     *     
     */
    public void setCsrAccountTokenCredentials(CsrAccountTokenCredentialsType value) {
        this.csrAccountTokenCredentials = value;
    }

    /**
     * Gets the value of the deviceAccountCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceAccountCredentialsType }
     *     
     */
    public DeviceAccountCredentialsType getDeviceAccountCredentials() {
        return deviceAccountCredentials;
    }

    /**
     * Sets the value of the deviceAccountCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceAccountCredentialsType }
     *     
     */
    public void setDeviceAccountCredentials(DeviceAccountCredentialsType value) {
        this.deviceAccountCredentials = value;
    }

}
