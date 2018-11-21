
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.phonecommontypes.UnitsInfoType;


/**
 * <p>Java class for RedemptionBenefitsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RedemptionBenefitsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceEndDate" type="{http://www.tracfone.com/CommonTypes}StringDatetype" minOccurs="0"/>
 *         &lt;element name="transactionDate" type="{http://www.tracfone.com/CommonTypes}StringDatetype" minOccurs="0"/>
 *         &lt;element name="servicePlanInfo" type="{http://www.tracfone.com/ServicePlanCommonTypes}ServicePlanDetailsType"/>
 *         &lt;element name="unitsInfo" type="{http://www.tracfone.com/PhoneCommonTypes}UnitsInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RedemptionBenefitsType", propOrder = {
    "serviceEndDate",
    "transactionDate",
    "servicePlanInfo",
    "unitsInfo"
})
public class RedemptionBenefitsType {

    protected String serviceEndDate;
    protected String transactionDate;
    @XmlElement(required = true)
    protected ServicePlanDetailsType servicePlanInfo;
    @XmlElement(required = true)
    protected UnitsInfoType unitsInfo;

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
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionDate(String value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the servicePlanInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePlanDetailsType }
     *     
     */
    public ServicePlanDetailsType getServicePlanInfo() {
        return servicePlanInfo;
    }

    /**
     * Sets the value of the servicePlanInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePlanDetailsType }
     *     
     */
    public void setServicePlanInfo(ServicePlanDetailsType value) {
        this.servicePlanInfo = value;
    }

    /**
     * Gets the value of the unitsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsInfoType }
     *     
     */
    public UnitsInfoType getUnitsInfo() {
        return unitsInfo;
    }

    /**
     * Sets the value of the unitsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsInfoType }
     *     
     */
    public void setUnitsInfo(UnitsInfoType value) {
        this.unitsInfo = value;
    }

}
