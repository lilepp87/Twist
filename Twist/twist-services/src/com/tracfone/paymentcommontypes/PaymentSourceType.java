
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="creditCard" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardType"/>
 *         &lt;element name="achAccount" type="{http://www.tracfone.com/PaymentCommonTypes}AchType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentSourceType", propOrder = {
    "creditCard",
    "achAccount"
})
public class PaymentSourceType {

    protected CreditCardType creditCard;
    protected AchType achAccount;

    /**
     * Gets the value of the creditCard property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardType }
     *     
     */
    public CreditCardType getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the value of the creditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardType }
     *     
     */
    public void setCreditCard(CreditCardType value) {
        this.creditCard = value;
    }

    /**
     * Gets the value of the achAccount property.
     * 
     * @return
     *     possible object is
     *     {@link AchType }
     *     
     */
    public AchType getAchAccount() {
        return achAccount;
    }

    /**
     * Sets the value of the achAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AchType }
     *     
     */
    public void setAchAccount(AchType value) {
        this.achAccount = value;
    }

}
