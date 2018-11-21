
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountPayableByCheck complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountPayableByCheck">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paybyCheck" type="{http://www.tracfone.com/PaymentCommonTypes}AccountPayableSourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountPayableByCheck", propOrder = {
    "paybyCheck"
})
public class AccountPayableByCheck {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AccountPayableSourceType paybyCheck;

    /**
     * Gets the value of the paybyCheck property.
     * 
     * @return
     *     possible object is
     *     {@link AccountPayableSourceType }
     *     
     */
    public AccountPayableSourceType getPaybyCheck() {
        return paybyCheck;
    }

    /**
     * Sets the value of the paybyCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountPayableSourceType }
     *     
     */
    public void setPaybyCheck(AccountPayableSourceType value) {
        this.paybyCheck = value;
    }

}
