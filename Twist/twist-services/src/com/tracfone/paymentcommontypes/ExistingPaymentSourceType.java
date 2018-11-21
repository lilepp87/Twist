
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExistingPaymentSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExistingPaymentSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentSourceId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="webUserObjid" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="cvv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExistingPaymentSourceType", propOrder = {
    "paymentSourceId",
    "webUserObjid",
    "cvv"
})
public class ExistingPaymentSourceType {

    @XmlElement(required = true)
    protected String paymentSourceId;
    @XmlElement(required = true)
    protected String webUserObjid;
    @XmlElement(required = true)
    protected String cvv;

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

    /**
     * Gets the value of the webUserObjid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebUserObjid() {
        return webUserObjid;
    }

    /**
     * Sets the value of the webUserObjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebUserObjid(String value) {
        this.webUserObjid = value;
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
