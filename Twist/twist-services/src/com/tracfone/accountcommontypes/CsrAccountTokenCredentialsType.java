
package com.tracfone.accountcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CsrAccountTokenCredentialsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CsrAccountTokenCredentialsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="csrAccount" type="{http://www.tracfone.com/AccountCommonTypes}AccountIdentifierType"/>
 *         &lt;element name="customer" type="{http://www.tracfone.com/AccountCommonTypes}AccountIdentifierType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CsrAccountTokenCredentialsType", propOrder = {
    "token",
    "csrAccount",
    "customer"
})
public class CsrAccountTokenCredentialsType {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected AccountIdentifierType csrAccount;
    @XmlElement(required = true)
    protected AccountIdentifierType customer;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the csrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link AccountIdentifierType }
     *     
     */
    public AccountIdentifierType getCsrAccount() {
        return csrAccount;
    }

    /**
     * Sets the value of the csrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountIdentifierType }
     *     
     */
    public void setCsrAccount(AccountIdentifierType value) {
        this.csrAccount = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link AccountIdentifierType }
     *     
     */
    public AccountIdentifierType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountIdentifierType }
     *     
     */
    public void setCustomer(AccountIdentifierType value) {
        this.customer = value;
    }

}
