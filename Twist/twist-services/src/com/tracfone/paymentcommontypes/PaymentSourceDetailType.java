
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentSourceDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentSourceDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="billObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="accountObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="defaultFundingSource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="paymentSourceStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentSourceDetail" type="{http://www.tracfone.com/PaymentCommonTypes}PaymentSourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentSourceDetailType", propOrder = {
    "cardObjId",
    "billObjId",
    "accountObjId",
    "defaultFundingSource",
    "paymentSourceStatus",
    "paymentSourceDetail"
})
public class PaymentSourceDetailType {

    @XmlElement(required = true)
    protected String cardObjId;
    @XmlElement(required = true)
    protected String billObjId;
    @XmlElement(required = true)
    protected String accountObjId;
    protected boolean defaultFundingSource;
    @XmlElement(required = true)
    protected String paymentSourceStatus;
    @XmlElement(required = true)
    protected PaymentSourceType paymentSourceDetail;

    /**
     * Gets the value of the cardObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardObjId() {
        return cardObjId;
    }

    /**
     * Sets the value of the cardObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardObjId(String value) {
        this.cardObjId = value;
    }

    /**
     * Gets the value of the billObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillObjId() {
        return billObjId;
    }

    /**
     * Sets the value of the billObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillObjId(String value) {
        this.billObjId = value;
    }

    /**
     * Gets the value of the accountObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountObjId() {
        return accountObjId;
    }

    /**
     * Sets the value of the accountObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountObjId(String value) {
        this.accountObjId = value;
    }

    /**
     * Gets the value of the defaultFundingSource property.
     * 
     */
    public boolean isDefaultFundingSource() {
        return defaultFundingSource;
    }

    /**
     * Sets the value of the defaultFundingSource property.
     * 
     */
    public void setDefaultFundingSource(boolean value) {
        this.defaultFundingSource = value;
    }

    /**
     * Gets the value of the paymentSourceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentSourceStatus() {
        return paymentSourceStatus;
    }

    /**
     * Sets the value of the paymentSourceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentSourceStatus(String value) {
        this.paymentSourceStatus = value;
    }

    /**
     * Gets the value of the paymentSourceDetail property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSourceType }
     *     
     */
    public PaymentSourceType getPaymentSourceDetail() {
        return paymentSourceDetail;
    }

    /**
     * Sets the value of the paymentSourceDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSourceType }
     *     
     */
    public void setPaymentSourceDetail(PaymentSourceType value) {
        this.paymentSourceDetail = value;
    }

}
