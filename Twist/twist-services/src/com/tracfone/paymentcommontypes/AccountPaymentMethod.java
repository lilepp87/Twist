
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountPaymentMethod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountPaymentMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="payByCard" type="{http://www.tracfone.com/PaymentCommonTypes}AccountPayableByCard"/>
 *         &lt;element name="payByCheck" type="{http://www.tracfone.com/PaymentCommonTypes}AccountPayableByCheck"/>
 *         &lt;element name="payByDonation" type="{http://www.tracfone.com/PaymentCommonTypes}AccountPayableByDonation"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountPaymentMethod", propOrder = {
    "payByCard",
    "payByCheck",
    "payByDonation"
})
public class AccountPaymentMethod {

    protected AccountPayableByCard payByCard;
    protected AccountPayableByCheck payByCheck;
    protected AccountPayableByDonation payByDonation;

    /**
     * Gets the value of the payByCard property.
     * 
     * @return
     *     possible object is
     *     {@link AccountPayableByCard }
     *     
     */
    public AccountPayableByCard getPayByCard() {
        return payByCard;
    }

    /**
     * Sets the value of the payByCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountPayableByCard }
     *     
     */
    public void setPayByCard(AccountPayableByCard value) {
        this.payByCard = value;
    }

    /**
     * Gets the value of the payByCheck property.
     * 
     * @return
     *     possible object is
     *     {@link AccountPayableByCheck }
     *     
     */
    public AccountPayableByCheck getPayByCheck() {
        return payByCheck;
    }

    /**
     * Sets the value of the payByCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountPayableByCheck }
     *     
     */
    public void setPayByCheck(AccountPayableByCheck value) {
        this.payByCheck = value;
    }

    /**
     * Gets the value of the payByDonation property.
     * 
     * @return
     *     possible object is
     *     {@link AccountPayableByDonation }
     *     
     */
    public AccountPayableByDonation getPayByDonation() {
        return payByDonation;
    }

    /**
     * Sets the value of the payByDonation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountPayableByDonation }
     *     
     */
    public void setPayByDonation(AccountPayableByDonation value) {
        this.payByDonation = value;
    }

}
