
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.phonecommontypes.DeviceCredentialsType;


/**
 * <p>Java class for CreditCardListRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardListRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="deviceCredentials" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceCredentialsType"/>
 *         &lt;element name="filterOutExpiredCards" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardListRequestType", propOrder = {
    "deviceCredentials",
    "filterOutExpiredCards"
})
public class CreditCardListRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected DeviceCredentialsType deviceCredentials;
    protected boolean filterOutExpiredCards;

    /**
     * Gets the value of the deviceCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public DeviceCredentialsType getDeviceCredentials() {
        return deviceCredentials;
    }

    /**
     * Sets the value of the deviceCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public void setDeviceCredentials(DeviceCredentialsType value) {
        this.deviceCredentials = value;
    }

    /**
     * Gets the value of the filterOutExpiredCards property.
     * 
     */
    public boolean isFilterOutExpiredCards() {
        return filterOutExpiredCards;
    }

    /**
     * Sets the value of the filterOutExpiredCards property.
     * 
     */
    public void setFilterOutExpiredCards(boolean value) {
        this.filterOutExpiredCards = value;
    }

}
