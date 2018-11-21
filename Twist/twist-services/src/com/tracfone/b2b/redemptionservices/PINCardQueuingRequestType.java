
package com.tracfone.b2b.redemptionservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.phonecommontypes.DeviceIdType;


/**
 * <p>Java class for PINCardQueuingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PINCardQueuingRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="deviceId" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *         &lt;element name="pinCard" type="{http://www.tracfone.com/ServicePlanCommonTypes}RedemptionCodeType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PINCardQueuingRequestType", propOrder = {
    "deviceId",
    "pinCard"
})
public class PINCardQueuingRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected DeviceIdType deviceId;
    @XmlElement(required = true)
    protected String pinCard;

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

    /**
     * Gets the value of the pinCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinCard() {
        return pinCard;
    }

    /**
     * Sets the value of the pinCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinCard(String value) {
        this.pinCard = value;
    }

}
