
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.phonecommontypes.DeviceManufacturerListType;


/**
 * <p>Java class for GetAllManufacturersResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAllManufacturersResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="manufacturerList" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceManufacturerListType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAllManufacturersResponseType", propOrder = {
    "manufacturerList"
})
public class GetAllManufacturersResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected DeviceManufacturerListType manufacturerList;

    /**
     * Gets the value of the manufacturerList property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceManufacturerListType }
     *     
     */
    public DeviceManufacturerListType getManufacturerList() {
        return manufacturerList;
    }

    /**
     * Sets the value of the manufacturerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceManufacturerListType }
     *     
     */
    public void setManufacturerList(DeviceManufacturerListType value) {
        this.manufacturerList = value;
    }

}
