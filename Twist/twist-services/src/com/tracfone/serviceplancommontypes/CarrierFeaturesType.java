
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.KeyValuePairType;


/**
 * <p>Java class for CarrierFeaturesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CarrierFeaturesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="keyValue" type="{http://www.tracfone.com/CommonTypes}KeyValuePairType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarrierFeaturesType", propOrder = {
    "keyValue"
})
public class CarrierFeaturesType {

    @XmlElement(required = true)
    protected KeyValuePairType keyValue;

    /**
     * Gets the value of the keyValue property.
     * 
     * @return
     *     possible object is
     *     {@link KeyValuePairType }
     *     
     */
    public KeyValuePairType getKeyValue() {
        return keyValue;
    }

    /**
     * Sets the value of the keyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyValuePairType }
     *     
     */
    public void setKeyValue(KeyValuePairType value) {
        this.keyValue = value;
    }

}
