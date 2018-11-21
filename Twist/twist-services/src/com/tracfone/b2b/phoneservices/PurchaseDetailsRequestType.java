
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.phonecommontypes.DeviceIdType;


/**
 * <p>Java class for PurchaseDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseDetailsRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="servicePlanId" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdType"/>
 *         &lt;element name="isRecurring" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="deviceId" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseDetailsRequestType", propOrder = {
    "servicePlanId",
    "isRecurring",
    "deviceId"
})
public class PurchaseDetailsRequestType
    extends BaseRequestType
{

    protected int servicePlanId;
    protected boolean isRecurring;
    @XmlElement(required = true)
    protected DeviceIdType deviceId;

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
     * Gets the value of the isRecurring property.
     * 
     */
    public boolean isIsRecurring() {
        return isRecurring;
    }

    /**
     * Sets the value of the isRecurring property.
     * 
     */
    public void setIsRecurring(boolean value) {
        this.isRecurring = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceIdType }
     *     
     */
    public DeviceIdType getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceIdType }
     *     
     */
    public void setDeviceId(DeviceIdType value) {
        this.deviceId = value;
    }

}
