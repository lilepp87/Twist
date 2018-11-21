
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.EsnKeyValuePairType;
import com.tracfone.phonecommontypes.DeviceIdType;


/**
 * <p>Java class for SetEsnAttributesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SetEsnAttributesRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="device" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *         &lt;element name="attributeList" type="{http://www.tracfone.com/CommonTypes}EsnKeyValuePairType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetEsnAttributesRequestType", propOrder = {
    "device",
    "attributeList"
})
public class SetEsnAttributesRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected DeviceIdType device;
    @XmlElement(required = true)
    protected List<EsnKeyValuePairType> attributeList;

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
     * {@link EsnKeyValuePairType }
     * 
     * 
     */
    public List<EsnKeyValuePairType> getAttributeList() {
        if (attributeList == null) {
            attributeList = new ArrayList<EsnKeyValuePairType>();
        }
        return this.attributeList;
    }

}
