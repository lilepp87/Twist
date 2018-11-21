
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.AddressType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for WifiEligibilityStatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WifiEligibilityStatusResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="min" type="{http://www.tracfone.com/PhoneCommonTypes}MINType" minOccurs="0"/>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType" minOccurs="0"/>
 *         &lt;element name="esnEligibilityStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="simEligibilityStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WifiEligibilityStatusResponseType", propOrder = {
    "min",
    "esn",
    "esnEligibilityStatus",
    "simEligibilityStatus",
    "address"
})
public class WifiEligibilityStatusResponseType
    extends BaseResponseType
{

    protected String min;
    protected String esn;
    @XmlElement(required = true)
    protected String esnEligibilityStatus;
    @XmlElement(required = true)
    protected String simEligibilityStatus;
    @XmlElement(required = true)
    protected AddressType address;

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMin(String value) {
        this.min = value;
    }

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsn(String value) {
        this.esn = value;
    }

    /**
     * Gets the value of the esnEligibilityStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsnEligibilityStatus() {
        return esnEligibilityStatus;
    }

    /**
     * Sets the value of the esnEligibilityStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsnEligibilityStatus(String value) {
        this.esnEligibilityStatus = value;
    }

    /**
     * Gets the value of the simEligibilityStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimEligibilityStatus() {
        return simEligibilityStatus;
    }

    /**
     * Sets the value of the simEligibilityStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimEligibilityStatus(String value) {
        this.simEligibilityStatus = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

}
