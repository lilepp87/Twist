
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionDeatilsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionDeatilsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceDays" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voiceUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="textUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionDeatilsType", propOrder = {
    "applicationSystem",
    "serviceDays",
    "voiceUnits",
    "textUnits",
    "dataUnits"
})
public class TransactionDeatilsType {

    protected String applicationSystem;
    protected String serviceDays;
    protected String voiceUnits;
    protected String textUnits;
    protected String dataUnits;

    /**
     * Gets the value of the applicationSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationSystem() {
        return applicationSystem;
    }

    /**
     * Sets the value of the applicationSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationSystem(String value) {
        this.applicationSystem = value;
    }

    /**
     * Gets the value of the serviceDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceDays() {
        return serviceDays;
    }

    /**
     * Sets the value of the serviceDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceDays(String value) {
        this.serviceDays = value;
    }

    /**
     * Gets the value of the voiceUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoiceUnits() {
        return voiceUnits;
    }

    /**
     * Sets the value of the voiceUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoiceUnits(String value) {
        this.voiceUnits = value;
    }

    /**
     * Gets the value of the textUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextUnits() {
        return textUnits;
    }

    /**
     * Sets the value of the textUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextUnits(String value) {
        this.textUnits = value;
    }

    /**
     * Gets the value of the dataUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUnits() {
        return dataUnits;
    }

    /**
     * Sets the value of the dataUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUnits(String value) {
        this.dataUnits = value;
    }

}
