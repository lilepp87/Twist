
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardDisplayType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardDisplayType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/PaymentCommonTypes}CreditCardInfoBaseType">
 *       &lt;sequence>
 *         &lt;element name="cardId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="maskedCardNumber" type="{http://www.tracfone.com/PaymentCommonTypes}MaskedCreditCardNumberType"/>
 *         &lt;element name="isExpired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="paymentSourceId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardDisplayType", propOrder = {
    "cardId",
    "maskedCardNumber",
    "isExpired",
    "paymentSourceId"
})
@XmlSeeAlso({
    CreditCardDisplayTypeMB.class
})
public class CreditCardDisplayType
    extends CreditCardInfoBaseType
{

    @XmlElement(required = true)
    protected String cardId;
    @XmlElement(required = true)
    protected String maskedCardNumber;
    protected boolean isExpired;
    protected String paymentSourceId;

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardId(String value) {
        this.cardId = value;
    }

    /**
     * Gets the value of the maskedCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    /**
     * Sets the value of the maskedCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskedCardNumber(String value) {
        this.maskedCardNumber = value;
    }

    /**
     * Gets the value of the isExpired property.
     * 
     */
    public boolean isIsExpired() {
        return isExpired;
    }

    /**
     * Sets the value of the isExpired property.
     * 
     */
    public void setIsExpired(boolean value) {
        this.isExpired = value;
    }

    /**
     * Gets the value of the paymentSourceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceId() {
        return paymentSourceId;
    }

    /**
     * Sets the value of the paymentSourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceId(String value) {
        this.paymentSourceId = value;
    }

}
