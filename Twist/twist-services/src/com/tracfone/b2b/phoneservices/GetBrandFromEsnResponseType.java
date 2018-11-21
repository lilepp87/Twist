
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.commontypes.TracfoneBrandType;


/**
 * <p>Java class for GetBrandFromEsnResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetBrandFromEsnResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="brand" type="{http://www.tracfone.com/CommonTypes}TracfoneBrandType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBrandFromEsnResponseType", propOrder = {
    "brand"
})
public class GetBrandFromEsnResponseType
    extends BaseResponseType
{

    @XmlSchemaType(name = "string")
    protected TracfoneBrandType brand;

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link TracfoneBrandType }
     *     
     */
    public TracfoneBrandType getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link TracfoneBrandType }
     *     
     */
    public void setBrand(TracfoneBrandType value) {
        this.brand = value;
    }

}
