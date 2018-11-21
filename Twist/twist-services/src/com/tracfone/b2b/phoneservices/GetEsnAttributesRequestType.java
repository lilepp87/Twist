
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.EsnAttributesType;
import com.tracfone.commontypes.MobileRequestType;
import com.tracfone.phonecommontypes.DeviceIdType;


/**
 * <p>Java class for GetEsnAttributesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetEsnAttributesRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="consumerInfo" type="{http://www.tracfone.com/CommonTypes}MobileRequestType" minOccurs="0"/>
 *         &lt;element name="device" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *         &lt;element name="attributeList" type="{http://www.tracfone.com/CommonTypes}EsnAttributesType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEsnAttributesRequestType", propOrder = {
    "consumerInfo",
    "device",
    "attributeList"
})
public class GetEsnAttributesRequestType
    extends BaseRequestType
{

    protected MobileRequestType consumerInfo;
    @XmlElement(required = true)
    protected DeviceIdType device;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected List<EsnAttributesType> attributeList;

    /**
     * Gets the value of the consumerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MobileRequestType }
     *     
     */
    public MobileRequestType getConsumerInfo() {
        return consumerInfo;
    }

    /**
     * Sets the value of the consumerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MobileRequestType }
     *     
     */
    public void setConsumerInfo(MobileRequestType value) {
        this.consumerInfo = value;
    }

    /**
     * Gets the value of the device property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceIdType }
     *     
     */
    public DeviceIdType getDevice() {
        return device;
    }

    /**
     * Sets the value of the device property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceIdType }
     *     
     */
    public void setDevice(DeviceIdType value) {
        this.device = value;
    }

    /**
     * Gets the value of the attributeList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsnAttributesType }
     * 
     * 
     */
    public List<EsnAttributesType> getAttributeList() {
        if (attributeList == null) {
            attributeList = new ArrayList<EsnAttributesType>();
        }
        return this.attributeList;
    }

}
