
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardInfoBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardInfoBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardType" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardTypeType"/>
 *         &lt;element name="billingZipcode" type="{http://www.tracfone.com/CommonTypes}ZipCodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardInfoBaseType", propOrder = {
    "cardType",
    "billingZipcode"
})
@XmlSeeAlso({
    CreditCardInfoType.class,
    CreditCardDisplayType.class
})
public class CreditCardInfoBaseType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected CreditCardTypeType cardType;
    @XmlElement(required = true)
    protected String billingZipcode;

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
     * Gets the value of the billingZipcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingZipcode() {
        return billingZipcode;
    }

    /**
     * Sets the value of the billingZipcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingZipcode(String value) {
        this.billingZipcode = value;
    }

}
