
package com.tracfone.accountcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionPaymentInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionPaymentInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentSourceNickname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentSourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentSourceNumberMask" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionPaymentInfoType", propOrder = {
    "paymentSourceNickname",
    "paymentSourceType",
    "paymentSourceNumberMask"
})
public class TransactionPaymentInfoType {

    @XmlElement(required = true)
    protected String paymentSourceNickname;
    @XmlElement(required = true)
    protected String paymentSourceType;
    @XmlElement(required = true)
    protected String paymentSourceNumberMask;

    /**
     * Gets the value of the paymentSourceNickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceNickname() {
        return paymentSourceNickname;
    }

    /**
     * Sets the value of the paymentSourceNickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceNickname(String value) {
        this.paymentSourceNickname = value;
    }

    /**
     * Gets the value of the paymentSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceType() {
        return paymentSourceType;
    }

    /**
     * Sets the value of the paymentSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceType(String value) {
        this.paymentSourceType = value;
    }

    /**
     * Gets the value of the paymentSourceNumberMask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceNumberMask() {
        return paymentSourceNumberMask;
    }

    /**
     * Sets the value of the paymentSourceNumberMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceNumberMask(String value) {
        this.paymentSourceNumberMask = value;
    }

}
