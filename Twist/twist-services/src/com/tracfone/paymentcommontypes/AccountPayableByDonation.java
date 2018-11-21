
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountPayableByDonation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountPayableByDonation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paybyDonation" type="{http://www.tracfone.com/PaymentCommonTypes}AccountPayableSourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountPayableByDonation", propOrder = {
    "paybyDonation"
})
public class AccountPayableByDonation {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AccountPayableSourceType paybyDonation;

    /**
     * Gets the value of the paybyDonation property.
     * 
     * @return
     *     possible object is
     *     {@link AccountPayableSourceType }
     *     
     */
    public AccountPayableSourceType getPaybyDonation() {
        return paybyDonation;
    }

    /**
     * Sets the value of the paybyDonation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountPayableSourceType }
     *     
     */
    public void setPaybyDonation(AccountPayableSourceType value) {
        this.paybyDonation = value;
    }

}
