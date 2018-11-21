
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for GetDataUsageResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDataUsageResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="usageLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastUpdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDataUsageResponseType", propOrder = {
    "usageLevel",
    "lastUpdate"
})
public class GetDataUsageResponseType
    extends BaseResponseType
{

    protected String usageLevel;
    protected String lastUpdate;

    /**
     * Gets the value of the usageLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageLevel() {
        return usageLevel;
    }

    /**
     * Sets the value of the usageLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageLevel(String value) {
        this.usageLevel = value;
    }

    /**
     * Gets the value of the lastUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the value of the lastUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdate(String value) {
        this.lastUpdate = value;
    }

}
