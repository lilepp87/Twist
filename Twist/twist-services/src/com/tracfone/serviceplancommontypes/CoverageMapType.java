
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.TracfoneBrandType;


/**
 * <p>Java class for CoverageMapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoverageMapType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brandName" type="{http://www.tracfone.com/CommonTypes}TracfoneBrandType"/>
 *         &lt;element name="servicePlanId" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdType"/>
 *         &lt;element name="carrierParentId" type="{http://www.tracfone.com/CarrierCommonTypes}CarrierParentIdType"/>
 *         &lt;element name="coverageMap" type="{http://www.tracfone.com/CommonTypes}MapIdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoverageMapType", propOrder = {
    "brandName",
    "servicePlanId",
    "carrierParentId",
    "coverageMap"
})
public class CoverageMapType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TracfoneBrandType brandName;
    protected int servicePlanId;
    @XmlElement(required = true)
    protected String carrierParentId;
    @XmlElement(required = true)
    protected String coverageMap;

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link TracfoneBrandType }
     *     
     */
    public TracfoneBrandType getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TracfoneBrandType }
     *     
     */
    public void setBrandName(TracfoneBrandType value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the servicePlanId property.
     * 
     */
    public int getServicePlanId() {
        return servicePlanId;
    }

    /**
     * Sets the value of the servicePlanId property.
     * 
     */
    public void setServicePlanId(int value) {
        this.servicePlanId = value;
    }

    /**
     * Gets the value of the carrierParentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierParentId() {
        return carrierParentId;
    }

    /**
     * Sets the value of the carrierParentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierParentId(String value) {
        this.carrierParentId = value;
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
