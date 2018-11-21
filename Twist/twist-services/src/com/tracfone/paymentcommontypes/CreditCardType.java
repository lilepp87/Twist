
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.ExpirationDateType;


/**
 * <p>Java class for CreditCardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/PaymentCommonTypes}BasePaymentSourceType">
 *       &lt;sequence>
 *         &lt;element name="cardType" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardTypeType" minOccurs="0"/>
 *         &lt;element name="expirationDate" type="{http://www.tracfone.com/CommonTypes}ExpirationDateType"/>
 *         &lt;element name="cvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardType", propOrder = {
    "cardType",
    "expirationDate",
    "cvv"
})
public class CreditCardType
    extends BasePaymentSourceType
{

    @XmlSchemaType(name = "string")
    protected CreditCardTypeType cardType;
    @XmlElement(required = true)
    protected ExpirationDateType expirationDate;
    protected String cvv;

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

}
