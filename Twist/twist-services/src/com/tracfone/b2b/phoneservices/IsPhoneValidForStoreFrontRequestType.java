
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;


/**
 * <p>Java class for IsPhoneValidForStoreFrontRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IsPhoneValidForStoreFrontRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType" minOccurs="0"/>
 *         &lt;element name="phoneModel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneManufacturer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IsPhoneValidForStoreFrontRequestType", propOrder = {
    "esn",
    "phoneModel",
    "phoneManufacturer"
})
public class IsPhoneValidForStoreFrontRequestType
    extends BaseRequestType
{

    protected String esn;
    @XmlElement(required = true)
    protected String phoneModel;
    @XmlElement(required = true)
    protected String phoneManufacturer;

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
     * Gets the value of the phoneModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * Sets the value of the phoneModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneModel(String value) {
        this.phoneModel = value;
    }

    /**
     * Gets the value of the phoneManufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneManufacturer() {
        return phoneManufacturer;
    }

    /**
     * Sets the value of the phoneManufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneManufacturer(String value) {
        this.phoneManufacturer = value;
    }

}
