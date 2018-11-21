
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.commontypes.TechnologyType;
import com.tracfone.phonecommontypes.ESNStatusType;


/**
 * <p>Java class for DeviceInformationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceInformationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType"/>
 *         &lt;element name="min" type="{http://www.tracfone.com/PhoneCommonTypes}MINType" minOccurs="0"/>
 *         &lt;element name="sim" type="{http://www.tracfone.com/PhoneCommonTypes}SIMType" minOccurs="0"/>
 *         &lt;element name="technology" type="{http://www.tracfone.com/CommonTypes}TechnologyType"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esnStatus" type="{http://www.tracfone.com/PhoneCommonTypes}ESNStatusType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceInformationResponseType", propOrder = {
    "esn",
    "min",
    "sim",
    "technology",
    "model",
    "esnStatus"
})
public class DeviceInformationResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected String esn;
    protected String min;
    protected String sim;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TechnologyType technology;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected ESNStatusType esnStatus;

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
     * Gets the value of the sim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSim() {
        return sim;
    }

    /**
     * Sets the value of the sim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSim(String value) {
        this.sim = value;
    }

    /**
     * Gets the value of the technology property.
     * 
     * @return
     *     possible object is
     *     {@link TechnologyType }
     *     
     */
    public TechnologyType getTechnology() {
        return technology;
    }

    /**
     * Sets the value of the technology property.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnologyType }
     *     
     */
    public void setTechnology(TechnologyType value) {
        this.technology = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the esnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ESNStatusType }
     *     
     */
    public ESNStatusType getEsnStatus() {
        return esnStatus;
    }

    /**
     * Sets the value of the esnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ESNStatusType }
     *     
     */
    public void setEsnStatus(ESNStatusType value) {
        this.esnStatus = value;
    }

}
