
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.ExpirationDateType;
import com.tracfone.commontypes.FullAddressType;
import com.tracfone.commontypes.PersonNameType;
import com.tracfone.securitycommontypes.DpCryptoType;


/**
 * <p>Java class for CreditCardEncryptedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardEncryptedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="secureData" type="{http://www.tracfone.com/SecurityCommonTypes}DpCryptoType"/>
 *         &lt;element name="cardType" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardTypeType"/>
 *         &lt;element name="expirationDate" type="{http://www.tracfone.com/CommonTypes}ExpirationDateType"/>
 *         &lt;element name="cvv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountHolderName" type="{http://www.tracfone.com/CommonTypes}PersonNameType"/>
 *         &lt;element name="address" type="{http://www.tracfone.com/CommonTypes}FullAddressType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardEncryptedType", propOrder = {
    "secureData",
    "cardType",
    "expirationDate",
    "cvv",
    "accountHolderName",
    "address"
})
public class CreditCardEncryptedType {

    @XmlElement(required = true)
    protected DpCryptoType secureData;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected CreditCardTypeType cardType;
    @XmlElement(required = true)
    protected ExpirationDateType expirationDate;
    @XmlElement(required = true)
    protected String cvv;
    @XmlElement(required = true)
    protected PersonNameType accountHolderName;
    @XmlElement(required = true)
    protected FullAddressType address;

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
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardTypeType }
     *     
     */
    public CreditCardTypeType getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardTypeType }
     *     
     */
    public void setCardType(CreditCardTypeType value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpirationDateType }
     *     
     */
    public ExpirationDateType getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpirationDateType }
     *     
     */
    public void setExpirationDate(ExpirationDateType value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the cvv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the value of the cvv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCvv(String value) {
        this.cvv = value;
    }

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
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link FullAddressType }
     *     
     */
    public FullAddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullAddressType }
     *     
     */
    public void setAddress(FullAddressType value) {
        this.address = value;
    }

}
