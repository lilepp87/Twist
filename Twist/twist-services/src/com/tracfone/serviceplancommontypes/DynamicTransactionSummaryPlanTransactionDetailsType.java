
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicTransactionSummaryPlanTransactionDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicTransactionSummaryPlanTransactionDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}ServicePlanDetailsType"/>
 *         &lt;element name="trasactionInfo" type="{http://www.tracfone.com/ServicePlanCommonTypes}DynamicTransactionSummaryTransactionInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicTransactionSummaryPlanTransactionDetailsType", propOrder = {
    "planDetails",
    "trasactionInfo"
})
public class DynamicTransactionSummaryPlanTransactionDetailsType {

    @XmlElement(required = true)
    protected ServicePlanDetailsType planDetails;
    protected DynamicTransactionSummaryTransactionInfoType trasactionInfo;

    /**
     * Gets the value of the planDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePlanDetailsType }
     *     
     */
    public ServicePlanDetailsType getPlanDetails() {
        return planDetails;
    }

    /**
     * Sets the value of the planDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePlanDetailsType }
     *     
     */
    public void setPlanDetails(ServicePlanDetailsType value) {
        this.planDetails = value;
    }

    /**
     * Gets the value of the trasactionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicTransactionSummaryTransactionInfoType }
     *     
     */
    public DynamicTransactionSummaryTransactionInfoType getTrasactionInfo() {
        return trasactionInfo;
    }

    /**
     * Sets the value of the trasactionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicTransactionSummaryTransactionInfoType }
     *     
     */
    public void setTrasactionInfo(DynamicTransactionSummaryTransactionInfoType value) {
        this.trasactionInfo = value;
    }

}
