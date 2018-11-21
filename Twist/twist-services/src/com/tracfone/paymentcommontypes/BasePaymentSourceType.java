
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.AddressType;
import com.tracfone.commontypes.PersonNameType;
import com.tracfone.securitycommontypes.DpCryptoType;


/**
 * <p>Java class for BasePaymentSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BasePaymentSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountHolderName" type="{http://www.tracfone.com/CommonTypes}PersonNameType"/>
 *         &lt;element name="phoneNumber" type="{http://www.tracfone.com/PhoneCommonTypes}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.tracfone.com/CommonTypes}EmailType"/>
 *         &lt;element name="address" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentSourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentSourceNickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountNumber" type="{http://www.tracfone.com/CommonTypes}AccountNumberType"/>
 *         &lt;element name="secureData" type="{http://www.tracfone.com/SecurityCommonTypes}DpCryptoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasePaymentSourceType", propOrder = {
    "accountHolderName",
    "phoneNumber",
    "email",
    "address",
    "country",
    "paymentSourceType",
    "paymentSourceNickname",
    "accountNumber",
    "secureData"
})
@XmlSeeAlso({
    AchType.class,
    CreditCardType.class
})
public class BasePaymentSourceType {

    @XmlElement(required = true)
    protected PersonNameType accountHolderName;
    protected String phoneNumber;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected AddressType address;
    protected String country;
    @XmlElement(required = true)
    protected String paymentSourceType;
    protected String paymentSourceNickname;
    @XmlElement(required = true)
    protected String accountNumber;
    protected DpCryptoType secureData;

    /**
     * Gets the value of the accountHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Sets the value of the accountHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setAccountHolderName(PersonNameType value) {
        this.accountHolderName = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

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
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the paymentSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceType() {
        return paymentSourceType;
    }

    /**
     * Sets the value of the paymentSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceType(String value) {
        this.paymentSourceType = value;
    }

    /**
     * Gets the value of the paymentSourceNickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceNickname() {
        return paymentSourceNickname;
    }

    /**
     * Sets the value of the paymentSourceNickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceNickname(String value) {
        this.paymentSourceNickname = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the secureData property.
     * 
     * @return
     *     possible object is
     *     {@link DpCryptoType }
     *     
     */
    public DpCryptoType getSecureData() {
        return secureData;
    }

    /**
     * Sets the value of the secureData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DpCryptoType }
     *     
     */
    public void setSecureData(DpCryptoType value) {
        this.secureData = value;
    }

}
