
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.TrueFalseStringType;


/**
 * <p>Java class for DynamicTransactionDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicTransactionDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="confirmationMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transactionMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceEndDate" type="{http://www.tracfone.com/CommonTypes}StringDatetype" minOccurs="0"/>
 *         &lt;element name="nextPaymnetDate" type="{http://www.tracfone.com/CommonTypes}StringDatetype" minOccurs="0"/>
 *         &lt;element name="enrolledInAutoRefill" type="{http://www.tracfone.com/CommonTypes}TrueFalseStringType" minOccurs="0"/>
 *         &lt;element name="reserveCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deviceDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}DynamicTransactionSummaryPlanDeviceDetailsType"/>
 *         &lt;element name="servicePlanDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}DynamicTransactionSummaryPlanTransactionDetailsType"/>
 *         &lt;element name="vasPlanDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}DynamicTransactionSummaryPlanTransactionDetailsType" minOccurs="0"/>
 *         &lt;element name="paymentSourceDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}PaymentSourceDetailsType" minOccurs="0"/>
 *         &lt;element name="moreInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicTransactionDetailsType", propOrder = {
    "confirmationMsg",
    "transactionMsg",
    "serviceEndDate",
    "nextPaymnetDate",
    "enrolledInAutoRefill",
    "reserveCount",
    "deviceDetails",
    "servicePlanDetails",
    "vasPlanDetails",
    "paymentSourceDetails",
    "moreInfo"
})
public class DynamicTransactionDetailsType {

    @XmlElement(required = true)
    protected String confirmationMsg;
    @XmlElement(required = true)
    protected String transactionMsg;
    protected String serviceEndDate;
    protected String nextPaymnetDate;
    @XmlSchemaType(name = "string")
    protected TrueFalseStringType enrolledInAutoRefill;
    protected int reserveCount;
    @XmlElement(required = true)
    protected DynamicTransactionSummaryPlanDeviceDetailsType deviceDetails;
    @XmlElement(required = true)
    protected DynamicTransactionSummaryPlanTransactionDetailsType servicePlanDetails;
    protected DynamicTransactionSummaryPlanTransactionDetailsType vasPlanDetails;
    protected PaymentSourceDetailsType paymentSourceDetails;
    protected String moreInfo;

    /**
     * Gets the value of the confirmationMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    /**
     * Sets the value of the confirmationMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmationMsg(String value) {
        this.confirmationMsg = value;
    }

    /**
     * Gets the value of the transactionMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionMsg() {
        return transactionMsg;
    }

    /**
     * Sets the value of the transactionMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionMsg(String value) {
        this.transactionMsg = value;
    }

    /**
     * Gets the value of the serviceEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * Sets the value of the serviceEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceEndDate(String value) {
        this.serviceEndDate = value;
    }

    /**
     * Gets the value of the nextPaymnetDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextPaymnetDate() {
        return nextPaymnetDate;
    }

    /**
     * Sets the value of the nextPaymnetDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextPaymnetDate(String value) {
        this.nextPaymnetDate = value;
    }

    /**
     * Gets the value of the enrolledInAutoRefill property.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseStringType }
     *     
     */
    public TrueFalseStringType getEnrolledInAutoRefill() {
        return enrolledInAutoRefill;
    }

    /**
     * Sets the value of the enrolledInAutoRefill property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseStringType }
     *     
     */
    public void setEnrolledInAutoRefill(TrueFalseStringType value) {
        this.enrolledInAutoRefill = value;
    }

    /**
     * Gets the value of the reserveCount property.
     * 
     */
    public int getReserveCount() {
        return reserveCount;
    }

    /**
     * Sets the value of the reserveCount property.
     * 
     */
    public void setReserveCount(int value) {
        this.reserveCount = value;
    }

    /**
     * Gets the value of the deviceDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicTransactionSummaryPlanDeviceDetailsType }
     *     
     */
    public DynamicTransactionSummaryPlanDeviceDetailsType getDeviceDetails() {
        return deviceDetails;
    }

    /**
     * Sets the value of the deviceDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicTransactionSummaryPlanDeviceDetailsType }
     *     
     */
    public void setDeviceDetails(DynamicTransactionSummaryPlanDeviceDetailsType value) {
        this.deviceDetails = value;
    }

    /**
     * Gets the value of the servicePlanDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicTransactionSummaryPlanTransactionDetailsType }
     *     
     */
    public DynamicTransactionSummaryPlanTransactionDetailsType getServicePlanDetails() {
        return servicePlanDetails;
    }

    /**
     * Sets the value of the servicePlanDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicTransactionSummaryPlanTransactionDetailsType }
     *     
     */
    public void setServicePlanDetails(DynamicTransactionSummaryPlanTransactionDetailsType value) {
        this.servicePlanDetails = value;
    }

    /**
     * Gets the value of the vasPlanDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicTransactionSummaryPlanTransactionDetailsType }
     *     
     */
    public DynamicTransactionSummaryPlanTransactionDetailsType getVasPlanDetails() {
        return vasPlanDetails;
    }

    /**
     * Sets the value of the vasPlanDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicTransactionSummaryPlanTransactionDetailsType }
     *     
     */
    public void setVasPlanDetails(DynamicTransactionSummaryPlanTransactionDetailsType value) {
        this.vasPlanDetails = value;
    }

    /**
     * Gets the value of the paymentSourceDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSourceDetailsType }
     *     
     */
    public PaymentSourceDetailsType getPaymentSourceDetails() {
        return paymentSourceDetails;
    }

    /**
     * Sets the value of the paymentSourceDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSourceDetailsType }
     *     
     */
    public void setPaymentSourceDetails(PaymentSourceDetailsType value) {
        this.paymentSourceDetails = value;
    }

    /**
     * Gets the value of the moreInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoreInfo() {
        return moreInfo;
    }

    /**
     * Sets the value of the moreInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoreInfo(String value) {
        this.moreInfo = value;
    }

}
