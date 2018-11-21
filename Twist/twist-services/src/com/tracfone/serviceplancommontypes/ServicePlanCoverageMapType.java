
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicePlanCoverageMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServicePlanCoverageMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePlan" type="{http://www.tracfone.com/ServicePlanCommonTypes}ServicePlanType"/>
 *         &lt;element name="coverageMap" type="{http://www.tracfone.com/CommonTypes}MapIdType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicePlanCoverageMapType", propOrder = {
    "servicePlan",
    "coverageMap"
})
public class ServicePlanCoverageMapType {

    @XmlElement(required = true)
    protected ServicePlanType servicePlan;
    protected String coverageMap;

    /**
     * Gets the value of the servicePlan property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePlanType }
     *     
     */
    public ServicePlanType getServicePlan() {
        return servicePlan;
    }

    /**
     * Sets the value of the servicePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePlanType }
     *     
     */
    public void setServicePlan(ServicePlanType value) {
        this.servicePlan = value;
    }

    /**
     * Gets the value of the coverageMap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoverageMap() {
        return coverageMap;
    }

    /**
     * Sets the value of the coverageMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoverageMap(String value) {
        this.coverageMap = value;
    }

}
