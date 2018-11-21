
package com.tracfone.b2b.accountservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.phonecommontypes.DeviceWithAccountType;


/**
 * <p>Java class for GetAccountDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAccountDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="deviceCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deviceWithAccount" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceWithAccountType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccountDetailsResponseType", propOrder = {
    "deviceCount",
    "deviceWithAccount"
})
public class GetAccountDetailsResponseType
    extends BaseResponseType
{

    protected int deviceCount;
    @XmlElement(required = true)
    protected List<DeviceWithAccountType> deviceWithAccount;

    /**
     * Gets the value of the deviceCount property.
     * 
     */
    public int getDeviceCount() {
        return deviceCount;
    }

    /**
     * Sets the value of the deviceCount property.
     * 
     */
    public void setDeviceCount(int value) {
        this.deviceCount = value;
    }

    /**
     * Gets the value of the deviceWithAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviceWithAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviceWithAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceWithAccountType }
     * 
     * 
     */
    public List<DeviceWithAccountType> getDeviceWithAccount() {
        if (deviceWithAccount == null) {
            deviceWithAccount = new ArrayList<DeviceWithAccountType>();
        }
        return this.deviceWithAccount;
    }

}
