
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentDetailsInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDetailsInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="existingPaymentSourceInfo" type="{http://www.tracfone.com/PaymentCommonTypes}ExistingPaymentSourceType"/>
 *         &lt;element name="encryptedPaymentDetails" type="{http://www.tracfone.com/PaymentCommonTypes}EncryptedPaymentInfoType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDetailsInfoType", propOrder = {
    "existingPaymentSourceInfo",
    "encryptedPaymentDetails"
})
public class PaymentDetailsInfoType {

    protected ExistingPaymentSourceType existingPaymentSourceInfo;
    protected EncryptedPaymentInfoType encryptedPaymentDetails;

    /**
     * Gets the value of the existingPaymentSourceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExistingPaymentSourceType }
     *     
     */
    public ExistingPaymentSourceType getExistingPaymentSourceInfo() {
        return existingPaymentSourceInfo;
    }

    /**
     * Sets the value of the existingPaymentSourceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExistingPaymentSourceType }
     *     
     */
    public void setExistingPaymentSourceInfo(ExistingPaymentSourceType value) {
        this.existingPaymentSourceInfo = value;
    }

    /**
     * Gets the value of the encryptedPaymentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptedPaymentInfoType }
     *     
     */
    public EncryptedPaymentInfoType getEncryptedPaymentDetails() {
        return encryptedPaymentDetails;
    }

    /**
     * Sets the value of the encryptedPaymentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptedPaymentInfoType }
     *     
     */
    public void setEncryptedPaymentDetails(EncryptedPaymentInfoType value) {
        this.encryptedPaymentDetails = value;
    }

}
