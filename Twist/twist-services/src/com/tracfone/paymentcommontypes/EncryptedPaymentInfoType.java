
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EncryptedPaymentInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncryptedPaymentInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="encryptedCreditCard" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardEncryptedType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptedPaymentInfoType", propOrder = {
    "encryptedCreditCard"
})
public class EncryptedPaymentInfoType {

    protected CreditCardEncryptedType encryptedCreditCard;

    /**
     * Gets the value of the encryptedCreditCard property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardEncryptedType }
     *     
     */
    public CreditCardEncryptedType getEncryptedCreditCard() {
        return encryptedCreditCard;
    }

    /**
     * Sets the value of the encryptedCreditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardEncryptedType }
     *     
     */
    public void setEncryptedCreditCard(CreditCardEncryptedType value) {
        this.encryptedCreditCard = value;
    }

}
