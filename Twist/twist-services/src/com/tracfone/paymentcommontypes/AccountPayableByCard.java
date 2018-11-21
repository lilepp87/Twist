
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.securitycommontypes.DpCryptoType;


/**
 * <p>Java class for AccountPayableByCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountPayableByCard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecureData" type="{http://www.tracfone.com/SecurityCommonTypes}DpCryptoType"/>
 *         &lt;element name="AccountNumberType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="SSN"/>
 *               &lt;enumeration value="CARD_NUMBER"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountPayableByCard", propOrder = {
    "accountNumber",
    "secureData",
    "accountNumberType"
})
public class AccountPayableByCard {

    @XmlElement(name = "AccountNumber", required = true)
    protected String accountNumber;
    @XmlElement(name = "SecureData", required = true)
    protected DpCryptoType secureData;
    @XmlElement(name = "AccountNumberType", required = true)
    protected String accountNumberType;

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

    /**
     * Gets the value of the accountNumberType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumberType() {
        return accountNumberType;
    }

    /**
     * Sets the value of the accountNumberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumberType(String value) {
        this.accountNumberType = value;
    }

}
