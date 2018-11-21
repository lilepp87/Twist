
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlanIdentificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanIdentificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="servicePlanId" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdType"/>
 *         &lt;element name="servicePlanPin" type="{http://www.tracfone.com/CommonTypes}PinCardType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanIdentificationType", propOrder = {
    "servicePlanId",
    "servicePlanPin"
})
public class PlanIdentificationType {

    protected Integer servicePlanId;
    protected String servicePlanPin;

    /**
     * Gets the value of the servicePlanId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getServicePlanId() {
        return servicePlanId;
    }

    /**
     * Sets the value of the servicePlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setServicePlanId(Integer value) {
        this.servicePlanId = value;
    }

    /**
     * Gets the value of the servicePlanPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicePlanPin() {
        return servicePlanPin;
    }

    /**
     * Sets the value of the servicePlanPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicePlanPin(String value) {
        this.servicePlanPin = value;
    }

}
